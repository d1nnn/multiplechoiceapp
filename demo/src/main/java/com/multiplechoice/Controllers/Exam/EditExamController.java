package com.multiplechoice.Controllers.Exam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.multiplechoice.Models.Exam.ExamModel;
import com.multiplechoice.Models.Question.Question;
import com.multiplechoice.Models.User.User;
import com.multiplechoice.Models.User.UserModel;

import com.multiplechoice.Models.Exam.Exam;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class EditExamController implements Initializable {
	@FXML TextField examNameInput, examTimeLimitInput;
	@FXML Label examIdLabel,examSemesterLabel,examQuantityLabel ;
	@FXML
	TableView<Exam> tblExam;
	@FXML TableView<User> tblUser;

	@FXML TableColumn<User, String> colScore, colUsername, colUserId;

	@FXML
	TableColumn<Exam, String> colSemester,colId,colName,colNum,colTime,colDate;
	@FXML VBox questionView;
	
	ArrayList<Exam> listExam;
	ArrayList<User> listUser = new ArrayList<>();
	public void addTable() {



		colScore.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getScore()));
		colUsername.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getRealName()));
		colUserId.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getIduser()));

		 colSemester.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getIdSemester()));
		 colId.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getIdSubExam()));
		 colName.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getNameexam()));
		 colNum.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getQuantity()));
		 colTime.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getTime()));
		 colDate.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getDatecreate()));

		ObservableList<Exam> listHK=FXCollections.observableArrayList(listExam);
		tblExam.setItems(listHK);

		tblUser.getSelectionModel().selectFirst();
		tblExam.getSelectionModel().selectFirst();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tblExam.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == null) return;
			examNameInput.setText(newValue.getNameexam());
			examIdLabel.setText(newValue.getIdSubExam());
			examQuantityLabel.setText(newValue.getQuantity());
			examSemesterLabel.setText(newValue.getIdSemester());
			examTimeLimitInput.setText(newValue.getTime());


			try {
				listUser = UserModel.getAllUserWithScoreFromExam(newValue.getIdexam());
				tblUser.setItems(FXCollections.observableArrayList(listUser));
			} catch (SQLException | ClassNotFoundException e) {
				System.out.println(e);
			}


			int index = 0;
			questionView.getChildren().clear();

			for(Question q : newValue.getQuestionList()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Exam/CreateExamQuestionDetail.fxml"));
				try {
					Node node = loader.load();
					CreateExamQuestionDetailController controller = loader.getController();
					controller.setQuestion(q, index + 1);
					questionView.getChildren().add(node);
				} catch (IOException e) {
					System.out.println("Something wrong happened in EditExamController trying to load QuestionDetail: " + e);
				}
				index++;
			}
		});


		try {
			listExam = ExamModel.getAllExam();
		} catch (Exception e) {
			// TODO: handle exception
		}
		addTable();
	}


	public void updateExam() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Thông báo cập nhật");

		if(examNameInput.getText().trim().isEmpty()) {
			alert.setAlertType(Alert.AlertType.WARNING);
			alert.setContentText("Cần nhập tên đề thi");
			alert.showAndWait();
			return;

		}

		if(isInteger(examTimeLimitInput.getText())) {
			if (Integer.parseInt(examTimeLimitInput.getText()) < 1) {
				alert.setAlertType(Alert.AlertType.WARNING);
				alert.setContentText("Thời gian làm bài không được nhỏ hơn 1 phút!");
				alert.showAndWait();
				return;
			}
		} else {
			alert.setAlertType(Alert.AlertType.WARNING);
			alert.setContentText("Thời gian làm bài phải là số nguyên!");
			alert.showAndWait();
			return;
		}

		Exam e = tblExam.getSelectionModel().getSelectedItem();
		e.setNameexam(examNameInput.getText());
		e.setTime(examTimeLimitInput.getText());
		tblExam.refresh();

		if(ExamModel.updateExam(e)) {
			alert.setContentText("Cập nhật thành công!");
		} else {
			alert.setContentText("Cập nhật thất bại!");
		}
		alert.showAndWait();
	}

	public void deleteExam() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Bạn có chắc muốn xóa không?");
		if(alert.showAndWait().get() == ButtonType.OK) {
			Exam exam = tblExam.getSelectionModel().getSelectedItem();
			alert.setAlertType(Alert.AlertType.INFORMATION);
			try {
				ExamModel.delete(exam.getIdexam());
				tblExam.getItems().remove(exam);
				alert.setContentText("Xóa thành công!");
				alert.showAndWait();
				tblExam.refresh();
			} catch (SQLException | ClassNotFoundException e) {
				alert.setAlertType(Alert.AlertType.WARNING);
				alert.setContentText("Không xóa được đề thi");
				alert.showAndWait();
			}
		}
	}


	public void printExam() throws JRException, FileNotFoundException {


		ArrayList<Exam> chosenExam = new ArrayList<>();
		
		Exam exam = tblExam.getSelectionModel().getSelectedItem();
		chosenExam.add(tblExam.getSelectionModel().getSelectedItem());
		ArrayList<Question> examQuestions = chosenExam.get(0).getQuestionList();
		JRBeanCollectionDataSource examBeanCollection = new JRBeanCollectionDataSource(chosenExam);
		for(Question question : examQuestions) {
			question.setOptionList(new ArrayList<>(Arrays.asList(question.getOptions())));
			Collections.shuffle(question.getOptionList());
		}
		
		JRBeanCollectionDataSource questionBeanCollection = new JRBeanCollectionDataSource(chosenExam.get(0).getQuestionList());


		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CollectionBean", examBeanCollection);
		param.put("questionCollectionBean", questionBeanCollection);
		param.put("chosenExam", exam);
		
		

		JasperDesign jasperDesign = JRXmlLoader.load(new File(getClass().getResource("/com/multiplechoice/Reports").toString().substring(6) + "/ExamReport.jrxml"));
		
		JasperReport jr = JasperCompileManager.compileReport(jasperDesign);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jr, param, new JREmptyDataSource());
		

		JasperViewer.viewReport(jasperPrint, false);
	}

	public boolean isInteger(String text)  {
		try {
			Integer.parseInt(text);
		} catch(Exception e) {
			return false;
		}

		return true;
	}
	
}

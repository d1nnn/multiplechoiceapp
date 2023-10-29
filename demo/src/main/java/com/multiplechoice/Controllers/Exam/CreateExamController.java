package com.multiplechoice.Controllers.Exam;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.multiplechoice.Controllers.Chapter.ChapterController;
import com.multiplechoice.Models.Chapter.Chapter;
import com.multiplechoice.Models.Exam.Exam;
import com.multiplechoice.Models.Exam.ExamModel;
import com.multiplechoice.Models.Question.Question;
import com.multiplechoice.Models.Question.QuestionModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

public class CreateExamController implements Initializable {

	@FXML
	FlowPane flpSemete2;

	@FXML
	Button thu;

	@FXML
	Label txtDate,txtTotal,txtNote, txtIdExam, datePicker;

	@FXML
	TextField txtNameExam,txfIdsub,txfTime;
	@FXML FlowPane chapterQuestions;

	Exam exam = new Exam();

	
	ArrayList<Chapter> ChapterList = new ArrayList<>();
	Map<Chapter, ArrayList<Question>> examMap;
	ArrayList<Node> nodeList = new ArrayList<Node>();
	ArrayList<TextField> textFieldsList = new ArrayList<TextField>();
	ArrayList<Question> QuestionList;
	
	String SemesterString="HK01";
	 public void chooseSemester2() {
		SemesterString="HK02";
		chapterQuestions.getChildren().clear();
		initialize(null, null);
	}

	public void chooseSemester1() {
		SemesterString="HK01";
		chapterQuestions.getChildren().clear();
		initialize(null, null);
	}

	//click tạo đề
	public void clickCreateExam() {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Thông báo");

		if(txfIdsub.getText().trim().isEmpty() || txtNameExam.getText().trim().isEmpty()) {
			alert.setAlertType(Alert.AlertType.WARNING);
			alert.setContentText("Cần nhập đủ dữ liệu");
			alert.showAndWait();
			return;

		}

		 if(Integer.parseInt(txtTotal.getText()) == 0) {
			 alert.setAlertType(Alert.AlertType.WARNING);
			 alert.setContentText("Chọn các câu hỏi đi!");
			 alert.showAndWait();
			 return;
		 }


		if(!isInteger(txfTime.getText())) {
			alert.setContentText("Thời gian làm bài phải là số nguyên");
			alert.showAndWait();
			return;
		}

		if(Integer.parseInt(txfTime.getText()) < 1) {
			 alert.setContentText("Thời gian làm bài không được nhỏ hơn 1 phút!");
			 alert.showAndWait();
			 return;
		 }
		exam.setNameexam(txtNameExam.getText());
		exam.setQuantity(txtTotal.getText());
		exam.setTime(txfTime.getText());
		exam.setIdSemester(SemesterString);
		exam.setIdSubExam(txfIdsub.getText());


		try {
			ExamModel.addExam(exam);
			alert.setContentText("Tạo đề thành công!");alert.showAndWait();
		} catch (Exception e) {
			// TODO: handle exception
			//txtNote.setVisible(true);
			if(e.getMessage().contains("input string:")) {
				txtNote.setVisible(true);
			}
			if(e.getMessage().contains("UNIQUE KEY")) {
				txtIdExam.setVisible(true);
			}
			if(e.getMessage().contains("smalldatetime")) {
				txtDate.setVisible(true);
			}
		}


	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated medatePicker.getValue();thod stub

		txfTime.textProperty().addListener((observable, oldValue, newValue) -> {
  txtNote.setVisible(false);
            });
txfIdsub.textProperty().addListener((observable, oldValue, newValue) -> {
  txtIdExam.setVisible(false);
            });

datePicker.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		txtNote.setVisible(false);
txtIdExam.setVisible(false);
txtDate.setVisible(false);



		txtNote.setVisible(false);
		textFieldsList.clear();
		flpSemete2.getChildren().clear();
		ChapterList.clear();
		txtTotal.setText(String.valueOf(0));
		exam.setExamMap(new HashMap<>());
		examMap = exam.getExamMap();
		try {
			this.ChapterList = ExamModel.getChapterFromSemester(SemesterString);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error occurred in CreateExamController when initializing: " + e);
		}
		
		for (Chapter i : ChapterList) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Chapter/Chapter.fxml"));
			try {
				Node ChapterNode = loader.load();
				nodeList.add(ChapterNode);

				ChapterController chap1 = loader.getController();
				//chap1.xuLyChapter=i;
				textFieldsList.add(chap1.getTextfield());
				chap1.setText(i.getNameChapter());
				chap1.getTextfield().textProperty().addListener((observable, oldValue, newValue) -> {
					// Lấy giá trị của tất cả các textfield
						if(newValue.isEmpty())
							return;
						i.setQuantityQuestion(Integer.parseInt(newValue));
						if(i.getQuantityQuestion() == 0) {
							examMap.remove(i);
						} else {
							try {
								QuestionList = QuestionModel.getQuestionsFromChapter(i, i.getQuantityQuestion());
							} catch (Exception e) {
								chap1.getTextfield().setText(oldValue);
								return;
							}
							examMap.put(i, QuestionList);
						}
						int index = 0;
						chapterQuestions.getChildren().clear();
						for(Chapter ch : examMap.keySet()) {
							for (Question question : examMap.get(ch)) {
								FXMLLoader questionLoader = new FXMLLoader(getClass().getResource("/Fxml/Exam/CreateExamQuestionDetail.fxml"));

								try {
									Node node = questionLoader.load();
									CreateExamQuestionDetailController controller = questionLoader.getController();
									controller.setQuestion(question, index + 1);

									chapterQuestions.getChildren().add(node);
								} catch (IOException e) {
									System.out.println("Error happened in CreateExamController trying to load each Question from Chapter: " + e);
								}
								index++;
							}
						}
					int sum = 0;
					int number = 0;
					for (TextField j : textFieldsList) {
						try {
							number = Integer.parseInt(j.getText());

						} catch (Exception e) {
							// TODO: handle exception
						}

						sum += number;
					}

					txtTotal.setText(String.valueOf(sum));
				});

				flpSemete2.getChildren().add(ChapterNode);

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Something wrong happened when loading Chapters in CreateExamController: " + e);
			}
		}
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

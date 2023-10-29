package com.multiplechoice.Controllers.Question;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.multiplechoice.Models.Chapter.Chapter;
import com.multiplechoice.Models.Exam.ExamModel;
import com.multiplechoice.Models.Option.Option;
import com.multiplechoice.Models.Question.Question;
import com.multiplechoice.Models.Question.QuestionModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class QuestionEditController implements Initializable {

    @FXML
    TableView<Chapter> tblChapter;
    @FXML
    TableView<Question> tblQuestion;
    @FXML
    TableColumn<Chapter, String> colIdChapter, colNameChapter;
    @FXML
    TableColumn<Question, String> colIdQuestion, colContent;
    @FXML
    Label txtIdChapter, txtNameChapter;

    @FXML
    Button btnAddQuestion;
    @FXML
    TextArea txaContent;
    @FXML
    TextField txfIdQuestion,txfA,txfB,txfC,txfD;
    ArrayList<Chapter> ChapterList = new ArrayList<>();
    ArrayList<Question> QuestionList = new ArrayList<Question>();
    String SemesterString = "HK01";
    Question currentQuestion;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    Alert delAlert = new Alert(AlertType.CONFIRMATION);

    public void chooseSemester2() {
        SemesterString = "HK02";
        initialize(null, null);
    }

    public void chooseSemester1() {
        SemesterString = "HK01";
        initialize(null, null);
    }

    public boolean checkInput() {
        if(txfA.getText().trim().isEmpty()||txfB.getText().trim().isEmpty()||txfC.getText().trim().isEmpty()||txfD.getText().trim().isEmpty()||txaContent.getText().trim().isEmpty()) {
            return false;
        }
        return true;
    }
    public void clickAddQuestion() {
        alert.setHeaderText("Thông báo");
        if(!checkInput()) {
            alert.setContentText("Điền đầy đủ thông tin vào Nội dung và các ô Lựa chọn");
            alert.showAndWait();
            return;
        }
        try {
            ArrayList<String> options = new ArrayList<>(Arrays.asList(txfA.getText(), txfB.getText(), txfC.getText(), txfD.getText()));
            QuestionModel.add(txfIdQuestion.getText(),txtIdChapter.getText(), txaContent.getText(), options);
            alert.setContentText("Thêm câu hỏi vào ngân hàng đề thành công!");
            alert.showAndWait();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e);
            alert.setContentText("Không thêm được câu hỏi");
            alert.showAndWait();
            System.out.println(e);
            // e.printStackTrace();
        }
    }

    public void clickDelQuestion() {
        delAlert.setContentText("Bạn có chắc muốn xóa câu hỏi?");

        try {
            if (delAlert.showAndWait().get() == ButtonType.OK) {
                QuestionModel.delete(txfIdQuestion.getText());
                alert.setContentText("Xóa câu hỏi khỏi ngân hàng đề thành công!");
                alert.showAndWait();
                txfA.clear();
                txfB.clear();
                txfC.clear();
                txfD.clear();
                txfIdQuestion.clear();
                txaContent.clear();
            }

        } catch (ClassNotFoundException | SQLException e) {
            alert.setContentText("Không xóa được câu hỏi!!");
            alert.showAndWait();
        }
    }

    public void clickAdjustQuestion() {
        try {
            currentQuestion.setTitle(txaContent.getText());
            Option[] opts = currentQuestion.getOptions();
            opts[0].setTitle(txfA.getText());
            opts[1].setTitle(txfB.getText());
            opts[2].setTitle(txfC.getText());
            opts[3].setTitle(txfD.getText());

            QuestionModel.adjust(currentQuestion);
            alert.setContentText("Cập nhật câu hỏi thành công!");
            alert.showAndWait();
            tblQuestion.refresh();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

    }

    public void addTableChapter() {
        ObservableList<Chapter> listshowChapters = FXCollections.observableArrayList(ChapterList);
        colIdChapter.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getIdChapter()));
        colNameChapter.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getNameChapter()));
        tblChapter.setItems(FXCollections.observableArrayList(listshowChapters));
    }

    public void addTableQuestion() {
        ObservableList<Question> listshowQuestions = FXCollections.observableArrayList(QuestionList);
        colIdQuestion.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getId()));
        colContent.setCellValueFactory(Cell -> new SimpleStringProperty(Cell.getValue().getTitle()));
        tblQuestion.setItems(listshowQuestions);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        // ExamModel examModel=new ExamModel();
        // QuestionModel questionModel=new QuestionModel ();

        tblQuestion.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) return;
            currentQuestion = newValue;
            txfIdQuestion.setText(newValue.getId());
            txaContent.setText(newValue.getTitle());
            Option[] options = newValue.getOptions();
            txfA.setText(options[0].getTitle());
            txfB.setText(options[1].getTitle());
            txfC.setText(options[2].getTitle());
            txfD.setText(options[3].getTitle());

        });

        try {
            ChapterList = ExamModel.getChapterFromSemester(SemesterString);

        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
        addTableChapter();
            tblChapter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

                txtIdChapter.setText(newValue.getIdChapter());
                txtNameChapter.setText(newValue.getNameChapter());
                try {
                    QuestionList = QuestionModel.getAllQuestionsFromChapter(newValue.getIdChapter());
                    addTableQuestion();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            });
    }
}

package com.multiplechoice.Controllers.Exam;

import com.multiplechoice.Listeners.UserListener;
import com.multiplechoice.Models.Exam.Exam;
import com.multiplechoice.Models.Exam.ExamModel;
import com.multiplechoice.Models.Question.Question;
import com.multiplechoice.Models.Question.QuestionModel;
import com.multiplechoice.Models.User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExamCardController implements Initializable {
    @FXML private Button startExamBtn;
    @FXML private Label examTitle,noOfQuestions, timeLimit;
    User user;
    private UserListener userListener;
    private Exam currentExam;

    public void setCurrentExam(Exam exam) {
        this.currentExam = exam;
    }

    public Exam getCurrentExam() {
        return this.currentExam;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void disableAttempted() {
        try {
            if(ExamModel.checkAttempted(currentExam.getIdexam(), user.getIduser())) {
                startExamBtn.setDisable(true);
                startExamBtn.setText("Đã thi");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Can't check attempted from server: " + e);
        }
    }

    public void setNoOfQuestions(int num) {
        noOfQuestions.setText("Số câu: " + num);
    }

    public void setTitle(String title) {
        examTitle.setText(title);
    }
    public void setTimeLimit(int time) {timeLimit.setText("Thời gian: " + time + " phút");};

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
        user = userListener.GetUser();
    }

    public void startExam() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Exam/ExamQuestion.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            try {
                ArrayList<Question> questions = QuestionModel.getAllQuestionsFromExam(currentExam);
                ExamQuestionController qqController = loader.getController();
                qqController.setQuestions(questions);
                qqController.setExamTitle(currentExam);
                qqController.setUserListener(this.userListener);
                qqController.setTimer(Integer.parseInt(currentExam.getTime()));
                Stage stage = (Stage)examTitle.getScene().getWindow();
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Error occurred in ExamCardController trying to getQuestionsFromExam: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Something wrong happened when loading from ExamCardController: " + e.getMessage());
        }
    }

}

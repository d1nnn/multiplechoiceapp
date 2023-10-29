package com.multiplechoice.Controllers.Exam;

import com.multiplechoice.Listeners.UserListener;
import com.multiplechoice.Models.Exam.Exam;
import com.multiplechoice.Models.Exam.ExamModel;
import com.multiplechoice.Models.User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExamListController implements Initializable {
    @FXML private FlowPane examListContainer;


    private User user;
    private UserListener userListener;
    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
        user = userListener.GetUser();
    }

    ArrayList<Exam> examList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            examList = ExamModel.getAllExam();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Can't get Exams in ExamListController: " + e);
        }
    }


    public void addCards() {
        for(Exam q : examList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Exam/ExamCard.fxml"));
            try {
                Node node = loader.load();
                ExamCardController qcController = loader.getController();
                qcController.setTitle("Đề: " + q.getNameexam());
                qcController.setCurrentExam(q);
                qcController.setNoOfQuestions(Integer.parseInt(q.getQuantity()));
                qcController.setTimeLimit(Integer.parseInt(q.getTime()));
                qcController.setUserListener(this.userListener);
                qcController.disableAttempted();
                examListContainer.getChildren().add(node);
            } catch (IOException e) {
                System.out.println("Error happened in ExamListController: " + e.getMessage());
            }
        }
    }

}

package com.multiplechoice.Controllers.Student;

import com.multiplechoice.Controllers.Exam.ExamListController;
import com.multiplechoice.Listeners.UserListener;
import com.multiplechoice.Models.User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentMainScreenController implements Initializable {
    @FXML private StackPane stackPanel;
    @FXML private Button backBtn;
    @FXML private Label userNameLabel;

    private UserListener userListener;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setUserListener(UserListener listener) {
        this.userListener = listener;

        user = this.userListener.GetUser();
        userNameLabel.setText(user.getRealName());
    }


    public void addExamList() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Exam/ExamList.fxml"));
        try {
            Node node = loader.load();
            ExamListController examListController = loader.getController();
            examListController.setUserListener(this.userListener);
            examListController.addCards();
            stackPanel.getChildren().add(node);
        } catch (IOException e) {
            System.out.println("Something wrong happened in StudentMainScreenController: " + e.getMessage());
        }
    }

    public void signOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) stackPanel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}



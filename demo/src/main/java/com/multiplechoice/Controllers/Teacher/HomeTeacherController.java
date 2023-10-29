package com.multiplechoice.Controllers.Teacher;

import com.multiplechoice.Listeners.UserListener;
import com.multiplechoice.Models.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeTeacherController implements Initializable {
	
	@FXML
	Pane paneMain;
	@FXML
	Label adminNameLabel;

	private UserListener userListener;
	private User user;

	public void setUserListener(UserListener listener) {
		this.userListener = listener;
		user = this.userListener.GetUser();
		adminNameLabel.setText(user.getRealName());
	}
	public void goToHomeTeacher(UserListener listener) {
		try {
			Stage stage=new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Home/HomeTeacher.fxml"));
		//	Parent root = FXMLLoader.load(getClass().getResource("chaper.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("createExam.fxml"));
			setUserListener(listener);
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void clickCreateExam() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Exam/CreateExam.fxml"));
		try {
			Node chapterNode = loader.load();
			paneMain.getChildren().clear();
			paneMain.getChildren().add(chapterNode);

		} catch (Exception e) {
			throw e;
		}
	}

	public void viewExam() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Exam/EditExam.fxml"));

		try {
			Node node = loader.load();
			paneMain.getChildren().clear();
			paneMain.getChildren().add(node);

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	public void signOut() throws IOException {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Bạn có chắc muốn đăng xuất?");
		if(alert.showAndWait().get() == ButtonType.OK) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home/Login.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = (Stage) paneMain.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
	}


    public void editQuestion(ActionEvent actionEvent) throws Exception  {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Question/QuestionEdit.fxml"));

		try {
			Node node = loader.load();
			paneMain.getChildren().clear();
			paneMain.getChildren().add(node);

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}

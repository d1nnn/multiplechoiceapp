package com.multiplechoice;

import com.multiplechoice.Utils.AppHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppTN extends Application {
    @Override
    public void start(Stage stage) throws Exception {
    	System.out.println(getClass().getResource("/").toString().substring(5));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home/Login.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home/HomeTeacher.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        AppHandler.addCloseAppEvent(stage);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        launch(args);
    }
}

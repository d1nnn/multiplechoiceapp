package com.multiplechoice.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AppHandler {
    private static Alert alert = new Alert(Alert.AlertType.INFORMATION);
    public static void addCloseAppEvent(Stage stage) {
        stage.setOnCloseRequest(e -> {
            e.consume();
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Xác nhận thoát chương trình");
            alert.setContentText("Bạn có muốn thoát chương trình không?");
            if(alert.showAndWait().get() == ButtonType.OK) {
                stage.close();
            }
        });
    }
}

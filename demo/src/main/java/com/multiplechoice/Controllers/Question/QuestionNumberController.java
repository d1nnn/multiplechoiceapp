package com.multiplechoice.Controllers.Question;

import com.multiplechoice.Listeners.LabelListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionNumberController implements Initializable {
    @FXML private Label questionNumber;
    @FXML private StackPane questionNumberPane;

    private LabelListener qNumberClicked;

    public void setqNumberClicked(LabelListener listener) {
        this.qNumberClicked = listener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionNumberPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                qNumberClicked.ChangeLabelText(questionNumber.getText());
            }
        });
    }

    public void setQuestionNumber(int num) {
        this.questionNumber.setText(String.valueOf(num));
    }
}

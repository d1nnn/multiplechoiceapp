package com.multiplechoice.Controllers.Exam;

import com.multiplechoice.Models.Option.Option;
import com.multiplechoice.Models.Question.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateExamQuestionDetailController implements Initializable {

    @FXML private Text txtA, txtB, txtC, txtD, txtContent;
    @FXML private Label txtLabel;
    @FXML private BorderPane borderPane;
    @FXML private AnchorPane anchorPane;

    private Question question = null;
    private int questionNumber;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(question != null) {
            txtLabel.setText("Câu " + questionNumber + ":");
            txtContent.setText(question.getTitle());
            if(question.getImage() != null) {
                ImageView imgView = new ImageView(question.getImage());
                imgView.setFitWidth(100);
                imgView.setFitHeight(100);
                borderPane.setLeft(imgView);
            } else {
                borderPane.setLeft(null);
            }
            String[] choices = {"a. ", "b. ", "c. ", "d. "};
            Option[] options = question.getOptions();
            txtA.setText(choices[0] + options[0].getTitle());
            txtB.setText(choices[1] + options[1].getTitle());
            txtC.setText(choices[2] + options[2].getTitle());
            txtD.setText(choices[3] + options[3].getTitle());
//            for(int i = 0; i < options.length; i++) {
//                Label newLabel = new Label(choices[i] + options[i].getTitle());
//                questionOptions.getChildren().add(newLabel);
//            }
        }
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question, int questionNumber) {
        this.question = question;
        this.questionNumber = questionNumber;
        initialize(null,null);
    }
}

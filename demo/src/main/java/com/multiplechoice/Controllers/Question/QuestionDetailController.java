package com.multiplechoice.Controllers.Question;

import com.multiplechoice.Models.Option.Option;
import com.multiplechoice.Models.Question.Question;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionDetailController implements Initializable {
    @FXML private VBox questionOptions;
    @FXML private Label qTitle;

    private Question question = null;
    private int questionNumber;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(question != null) {
            qTitle.setText(questionNumber + ". " + question.getTitle());
            ObservableList<Node> labels = questionOptions.getChildren();
            ObservableList<Toggle> options = question.getOptionGroup().getToggles();
            Toggle toggle = question.getOptionGroup().getSelectedToggle();


            for(int i = 0; i < options.size(); i++) {
                Option o = (Option)options.get(i).getUserData();
                String[] choices = {"a. ", "b. ", "c. ", "d. "};
                Label newLabel = new Label(choices[i] + o.getTitle());
                Toggle currentToggle = options.get(i);
                if(o.getIsCorrect() && toggle != null)
                    newLabel.setStyle("-fx-text-fill: green");


                if(currentToggle == toggle) {
                    if(o.getIsCorrect())
                        newLabel.setStyle("-fx-text-fill: green");
                    else
                        newLabel.setStyle("-fx-text-fill: red");

                }
                labels.add(newLabel);
//                ((Label)labels.get(i)).setText(o.getTitle());
            }
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

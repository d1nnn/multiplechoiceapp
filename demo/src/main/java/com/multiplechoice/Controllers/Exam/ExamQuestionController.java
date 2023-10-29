package com.multiplechoice.Controllers.Exam;

import com.multiplechoice.Controllers.Question.QuestionNumberController;
import com.multiplechoice.Listeners.LabelListener;
import com.multiplechoice.Listeners.UserListener;
import com.multiplechoice.Models.Exam.Exam;
import com.multiplechoice.Models.Option.Option;
import com.multiplechoice.Models.Question.Question;
import com.multiplechoice.Models.User.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ExamQuestionController implements Initializable {
    @FXML private FlowPane progressPane;
    @FXML private Label currentQuestionLabel, timer,questionTitle, examTitle, examNameLabel, studentNameLabel;
    @FXML private VBox questionOptionsBox;
    @FXML private ImageView questionImage;
    @FXML private SplitPane splitPane;
    @FXML private Button prevBtn, nextBtn;
    private ArrayList<Question> questions;
    Exam currentExam;
    ObjectProperty<Duration> timeLimit;
    User user;
    private UserListener userListener;
    Timeline countDownTimeLine;

    public void setTimer(int time) {
        timeLimit = new SimpleObjectProperty<>(Duration.ofMinutes(time));
        timer.textProperty().bind(Bindings.createStringBinding(() ->
                String.format("%02d:%02d", timeLimit.get().toMinutes(), timeLimit.get().toSecondsPart()),
                timeLimit
        ));

        countDownTimeLine = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), (ActionEvent event) ->
                timeLimit.setValue(timeLimit.get().minus(1, ChronoUnit.SECONDS))));
        countDownTimeLine.setCycleCount((int) timeLimit.get().getSeconds());

        countDownTimeLine.setOnFinished(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Đã hết giờ làm bài!");
            alert.show();

            goToExamResult();
        });

        countDownTimeLine.play();
    }

//
    public void setQuestions(ArrayList<Question> q) {
        this.questions = q;
        for(int i = 0; i < q.size(); i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Question/QuestionNumber.fxml"));
            Node node = null;
            Option[] questionOptions = q.get(i).getOptions();
            ToggleGroup currentQuestionGroup = q.get(i).getOptionGroup();
            setQuestionToggleGroup(questionOptions, currentQuestionGroup);

            try {
                node = loader.load();
                QuestionNumberController qnController = loader.getController();
                qnController.setQuestionNumber(i + 1);
                qnController.setqNumberClicked(new LabelListener() {
                    @Override
                    public void ChangeLabelText(String text) {
                        currentQuestionLabel.setText(text);
                    }

                    @Override
                    public void handle(Event event) {

                    }
                });
                progressPane.getChildren().add(node);
            } catch (IOException e) {
                System.out.println("Error occurred in ExamQuestionController trying to set progressPane: " + e.getMessage());
            }
        }
        currentQuestionLabel.setText("1");
    }

    public void setQuestionToggleGroup(Option[] questionOptions, ToggleGroup currentQuestionGroup) {
        List<Option> shuffledQuestionOptions = Arrays.asList(questionOptions);
        Collections.shuffle(shuffledQuestionOptions);
        RadioButton btn1 = new RadioButton(shuffledQuestionOptions.get(0).getTitle());
        RadioButton btn2 = new RadioButton(shuffledQuestionOptions.get(1).getTitle());
        RadioButton btn3 = new RadioButton(shuffledQuestionOptions.get(2).getTitle());
        RadioButton btn4 = new RadioButton(shuffledQuestionOptions.get(3).getTitle());

        btn1.setUserData(shuffledQuestionOptions.get(0));
        btn2.setUserData(shuffledQuestionOptions.get(1));
        btn3.setUserData(shuffledQuestionOptions.get(2));
        btn4.setUserData(shuffledQuestionOptions.get(3));

        btn1.setToggleGroup(currentQuestionGroup);
        btn2.setToggleGroup(currentQuestionGroup);
        btn3.setToggleGroup(currentQuestionGroup);
        btn4.setToggleGroup(currentQuestionGroup);

    }

    public void addQuestionOptionsToBox(Question q) {
        questionOptionsBox.getChildren().clear();
        for(Toggle t : q.getOptionGroup().getToggles()) {
            questionOptionsBox.getChildren().add((RadioButton)t);
        }
    }

    public void submitExam() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Bạn có muốn nộp bài không?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            countDownTimeLine.stop();

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Exam/ExamResult.fxml"));
//
//            try {
//                Parent root = loader.load();
//                Scene scene = new Scene(root);
//                ExamResultController qrController = loader.getController();
//                qrController.setQuestionList(questions);
//                qrController.submitExam(currentExam.getIdexam(), user.getIduser());
//                qrController.setUserListener(this.userListener);
//                Stage stage = (Stage) splitPane.getScene().getWindow();
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                System.out.println("Something wrong happened when loading ExamResult from ExamQuestionController: " + e.getMessage());
//            }
            goToExamResult();
        }
    }

    public void goToExamResult() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Exam/ExamResult.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ExamResultController qrController = loader.getController();
            qrController.setQuestionList(questions);
            qrController.submitExam(currentExam.getIdexam(), user.getIduser());
            qrController.setExamNameLabel(currentExam.getIdSubExam() + " - " + currentExam.getIdSemester());
            qrController.setUserListener(userListener);
            Stage stage = (Stage) splitPane.getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            System.out.println("Something wrong happened when loading ExamResult from ExamQuestionController: " + e.getMessage());
        }

    }

    public void setQuestionTitle(String qTitle) {
        this.questionTitle.setText(qTitle);
    }

    public void nextQuestion() {
        int current = Integer.parseInt(currentQuestionLabel.getText());
        if(current == questions.size())
            return;
        currentQuestionLabel.setText(String.valueOf(current + 1));
    }

    public void prevQuestion() {
        int current = Integer.parseInt(currentQuestionLabel.getText());
        if(current <= 1)
            return;
        currentQuestionLabel.setText(String.valueOf(current -1));
    }

    public void setExamTitle(Exam exam) {
        currentExam = exam;
        examTitle.setText("Đề: " + exam.getNameexam());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        currentQuestionLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                nextBtn.setDisable(Integer.parseInt(newValue) - questions.size() == 0);
                prevBtn.setDisable(Integer.parseInt(newValue) == 1);

                Question q = questions.get(Integer.parseInt(newValue) - 1);
                int index = 0;
                ObservableList<Toggle> optionList = q.getOptionGroup().getToggles();
//                Collections.shuffle(optionList);
                questionTitle.setText(q.getTitle());
//                if(!q.getImage().isEmpty())
//                    questionImage.setImage(new Image(q.getImage()));
                addQuestionOptionsToBox(q);
                String[] choices = {"a. ", "b. ", "c. ", "d. "};
                for(Toggle t : q.getOptionGroup().getToggles()) {
                    ((RadioButton)t).setText(choices[index] + ((Option)optionList.get(index).getUserData()).getTitle());
                    index++;
                }
            }
        });
    }



    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
        user = userListener.GetUser();
    }
}

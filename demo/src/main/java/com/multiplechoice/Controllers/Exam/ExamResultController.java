package com.multiplechoice.Controllers.Exam;

import com.multiplechoice.Controllers.Question.QuestionDetailController;
import com.multiplechoice.Controllers.Student.StudentMainScreenController;
import com.multiplechoice.Listeners.UserListener;
import com.multiplechoice.Models.Option.Option;
import com.multiplechoice.Models.Question.Question;
import com.multiplechoice.Models.User.User;
import com.multiplechoice.Models.User.UserModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExamResultController implements Initializable {
    @FXML private ScrollPane answerBox;
    @FXML private Label rightAnswersLabel, evaluationLabel, studentNameLabel, examNameLabel;
    @FXML private int numberOfCorrectAnswers = 0;
    private ArrayList<Question> questionList = null;
    private UserListener userListener;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(questionList != null) {
            VBox vb = new VBox();
            int index = 0;
            for(Question q : questionList) {
                Option chosenOption = null;
                Toggle selected = q.getOptionGroup().getSelectedToggle();
                if(selected != null) {
                    chosenOption = (Option) (q.getOptionGroup().getSelectedToggle().getUserData());
                    if(chosenOption.getIsCorrect()) {
                        numberOfCorrectAnswers++;
                    }
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Question/QuestionDetail.fxml"));
                try {
                    Node node = loader.load();
                    QuestionDetailController qdController = loader.getController();
                    qdController.setQuestion(q, index + 1);
                    vb.getChildren().add(node);
                } catch (IOException e) {
                    System.out.println("Something wrong happened when loading QuestionDetail from ExamResultController: " + e);
                }
                index++;
            }
            evaluationLabel.setText(gba((float)numberOfCorrectAnswers / questionList.size() * 10f));
            rightAnswersLabel.setText(numberOfCorrectAnswers + "/" + questionList.size());
            answerBox.setContent(vb);
        }
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setExamNameLabel(String examSubId) {
        this.examNameLabel.setText(examSubId);
    }
    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
        initialize(null,null);
    }

    public String gba(float x) {
String evaluate ="";
if(x<2.5f) {
evaluate= "F - Mẹ biết mẹ buồn đó";}
else if(x < 4.5f) {
evaluate="D - Qua như không qua";
}
else if(x < 6.5f) {
evaluate="C - Cố gắng lên em";
}
else if(x < 8.5f) {
evaluate="B - Phát huy rất tốt";
}
else  {
evaluate="A - Chừa cho bạn học nữa mày";
}
return evaluate;
    }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
        this.user = userListener.GetUser();
        this.studentNameLabel.setText(this.user.getRealName());
    }

    public void submitExam(String idexam, String iduser) {

        float score = (float)numberOfCorrectAnswers / questionList.size() * 10f;
        try {
            UserModel.submitExam(idexam, iduser, score);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Không nộp bài thi không thành công: " + e);
        }
    }

    public void confirm() throws IOException {
        goToStudentMainScreen();
    }

    public void goToStudentMainScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home/StudentMainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        StudentMainScreenController controller = loader.getController();
        controller.setUserListener(new UserListener() {
            @Override
            public void handle(Event event) {

            }

            @Override
            public User GetUser() {
                return user;
            }
        });
        controller.addExamList();
        Stage stage = (Stage)answerBox.getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}

package com.multiplechoice.Models.Question;

import com.multiplechoice.Database.Interfaces.IRepository;
import com.multiplechoice.Database.Repositories.RepositoriesFactory;
import com.multiplechoice.Database.Repositories.SqlServerRepository;
import com.multiplechoice.Models.Chapter.Chapter;
import com.multiplechoice.Models.Exam.Exam;
import com.multiplechoice.Models.Option.Option;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionModel {
    private static final IRepository repo = RepositoriesFactory.createRepositoryInstance(SqlServerRepository.class);
    public static ArrayList<Question> getAllQuestionsFromExam(String examId) throws SQLException, ClassNotFoundException {

        String getAllQuery = "SELECT questionbank.idquestion, questionbank.contentquestions as q_title, questionoption.title as o_title, questionoption.isCorrect" +
                " FROM questionbank, questionoption" +
                " WHERE questionoption.idquestion = question.idquestion";

        String getAllQuestionsFromexamIdQuery = String.format(
                "SELECT exam.idexam, exam.nameexam as exam_title, questionbank.idquestion, questionbank.contentquestions as qu_title, questionoption.title as o_title, questionoption.isCorrect" +
                        " FROM exam, questionbank,questionoption" +
                        " WHERE question.quiz_id = '%s' AND question.question_id = [option].question_id AND question.quiz_id = quiz.quiz_id",
                examId.trim()
        );
        ResultSet rs = repo.ExecuteQuery(getAllQuestionsFromexamIdQuery);
        return getAllQuestionFromResult(rs);
    }


    public static void delete(String id ) throws SQLException, ClassNotFoundException {
        id = id.replaceAll("[^a-zA-Z0-9]", "");
        String addQuestionQuery = String.format("delete from  QuestionBank where IDQUESTION ='%s'",
                id);
        repo.ExecuteUpdateQuery(addQuestionQuery);
    }

    public static void add(String id,String idchapter,String content, ArrayList<String> Options) throws SQLException, ClassNotFoundException {
        id = id.replaceAll("[^a-zA-Z0-9]", "");
        idchapter = idchapter.replaceAll("[^a-zA-Z0-9]", ""); // Loại bỏ tất cả các ký tự không phải chữ cái, số hoặc dấu cách
        String addQuestionQuery = String.format("INSERT INTO QuestionBank(IDQUESTION, IDCHAPTER,ContentQuestions) VALUES ('%s', '%s',N'%s')",
                id,idchapter,content);
        repo.ExecuteUpdateQuery(addQuestionQuery);
        int index = 0;
		for (String opt : Options) {
            String addQuestionOptionQuery = String.format(
                    "INSERT INTO questionoption(title, isCorrect, idquestion) " +
                    "VALUES ('%s', %d, '%s')",
                    opt, index == 0 ? 1 : 0, id
            );
            index++;

			repo.ExecuteUpdateQuery(addQuestionOptionQuery);
		}
    }
    public static void adjust(Question question) throws SQLException, ClassNotFoundException {
        String updateQuestionQuery = String.format("Update QuestionBank set ContentQuestions =N'%s' where idquestion='%s'",question.getTitle() ,question.getId().replaceAll("[^a-zA-Z0-9]", ""));
        repo.ExecuteUpdateQuery(updateQuestionQuery);
        for(Option opt : question.getOptions()) {
            String updateOptionQuery = String.format(
                    "Update questionoption " +
                    "SET title = '%s' " +
                    "WHERE idquestion = '%s' AND idoption = '%s'",
                    opt.getTitle(), question.getId(), opt.getId()
            );
            repo.ExecuteUpdateQuery(updateOptionQuery);
        }
    }


    public static ArrayList<Question> getAllQuestionsFromChapter(String idChapter) throws Exception {
        ArrayList<Question> questionList = new ArrayList<>();
        idChapter = idChapter.replaceAll("[^a-zA-Z0-9]", ""); // Loại bỏ tất cả các ký tự không phải chữ cái, số hoặc dấu cách

        String sql = String.format(
                "SELECT  idquestion, contentquestions, image FROM questionbank WHERE questionbank.idchapter = '%s'",
                idChapter);

        ResultSet rs = repo.ExecuteQuery(sql);

        while (rs.next()) {
            Question q = new Question();
            q.setId(rs.getString("idquestion"));
            q.setTitle(rs.getString("contentquestions"));
            q.setImage(rs.getString("image"));
            // q.setChapter(chapter);
            questionList.add(q);
        }
        for (Question qu : questionList) {
            qu.setOptions(getOptionsFromQuestion(qu));
        }
        return questionList;
    }
    public static ArrayList<Question> getAllQuestionsFromExam2(Exam exam) throws SQLException, ClassNotFoundException {
        ArrayList<Question> questionList = new ArrayList<>();
        exam.setIdexam(exam.getIdexam().replaceAll("[^a-zA-Z0-9]", ""));

        String query = String.format(
                "SELECT questionbank.idquestion, contentquestions, image " +
                "FROM examdetail, questionbank " +
                "WHERE examdetail.idquestion = questionbank.idquestion AND examdetail.idexam = %s",
                exam.getIdexam()
        );

        ResultSet rs = repo.ExecuteQuery(query);

        while(rs.next()) {
            Question q = new Question();
            q.setId(rs.getString("idquestion"));
            q.setTitle(rs.getString("contentquestions"));
            q.setImage(rs.getString("image"));
            q.setOptions(getOptionsFromQuestion(q));

            questionList.add(q);
        }

        return questionList;
    }


    public static Question get(int Id) throws SQLException, ClassNotFoundException {
        String getQuestionQuery = "SELECT question.question_id, question.title as q_title, [option].title as o_title, [option].isCorrect " +
                " FROM question,[option]" +
                " WHERE question.question_id = " + Id;
        ResultSet rs = repo.ExecuteQuery(getQuestionQuery);
        return getQuestionFromResultSet(rs);
    }

    public static ArrayList<Question> getQuestionsFromChapter(Chapter chapter, int numberOfQuestions) throws Exception {
        ArrayList<Question> questionList = new ArrayList<>();

        String sql = String.format(
                "SELECT TOP %d idquestion, contentquestions, image " +
                "FROM questionbank, chapter " +
                "WHERE questionbank.idchapter = '%s' AND questionbank.idchapter = chapter.idchapter " +
                "ORDER BY NEWID()",
                numberOfQuestions, chapter.getIdChapter().replaceAll("[^a-zA-Z0-9]", "")
        );

            ResultSet rs = repo.ExecuteQuery(sql);
            int rowCount = 0;
            while(rs.next()) {
                Question q = new Question();
                q.setId(rs.getString("idquestion"));
                q.setTitle(rs.getString("contentquestions"));
                q.setImage(rs.getString("image"));
                q.setChapter(chapter);
                questionList.add(q);
                rowCount++;
            }
            if(rowCount < numberOfQuestions)
                throw new Exception("Not enough questions");
        for(Question qu : questionList) {
            qu.setOptions(getOptionsFromQuestion(qu));
        }

        return questionList;
    }

    private static Option[] getOptionsFromQuestion(Question qu) {
        Option[] options = new Option[4];
        String query = String.format(
                "SELECT questionoption.idoption, questionoption.title, questionoption.isCorrect " +
                "FROM questionoption " +
                "WHERE questionoption.idquestion = '%s'",
                qu.getId().replaceAll("[^a-zA-Z0-9]", "")
    );
        try {
            ResultSet rs = repo.ExecuteQuery(query);
            int index = 0;
            while(rs.next()) {
                Option o = new Option();
                o.setId(rs.getString("idoption"));
                o.setTitle(rs.getString("title"));
                o.setIsCorrect(rs.getBoolean("isCorrect"));
                options[index] = o;
                index++;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error happened in QuestionModel trying to get OptionsFromQuestion: " + e);
        }


        return options;
    }

    public static void add(Question obj) throws SQLException, ClassNotFoundException {
        String addQuestionQuery = String.format("INSERT INTO Question(title, quiz_id) VALUES ('%s', %d)", obj.getTitle(), obj);

        repo.ExecuteUpdateQuery(addQuestionQuery);
        for(Option opt : obj.getOptions()) {
            String addQuestionOptionQuery = String.format("INSERT INTO Option(title, isCorrect, question_id) VALUES(" +
                    "'%s', %b, '%s')",
                    opt.getTitle(), opt.getIsCorrect(), obj.getId());
            repo.ExecuteUpdateQuery(addQuestionOptionQuery);
        }
    }

    public static void update(Question obj) throws SQLException, ClassNotFoundException {
        String updateQuestion = String.format(
                "UPDATE Question" +
                "SET title = '%s', image_url = '%s'", obj.getTitle(), obj.getImage());

        repo.ExecuteUpdateQuery(updateQuestion);

        for(Option opt : obj.getOptions()) {
            String updateQuestionOption = String.format(
                    "UPDATE Option" +
                    "SET title = '%s', isCorrect = %b", opt.getTitle(), opt.getIsCorrect());
            repo.ExecuteUpdateQuery(updateQuestionOption);
        }
    }

    public static void delete(int Id) throws SQLException, ClassNotFoundException {
        String deleteQuestion = String.format("DELETE FROM Question WHERE question_id = %d", Id);

        repo.ExecuteUpdateQuery(deleteQuestion);
    }


    private static ArrayList<Question> getAllQuestionFromResult(ResultSet rs) throws SQLException {
        ArrayList<Question> list = new ArrayList<>();
        Question q = new Question();
        Exam exam = new Exam();
        int index = 0;
        while(rs.next()) {
            exam.setIdexam(rs.getString("idexam"));
            exam.setNameexam(rs.getString("exam_title"));
            q.setId(rs.getString("question_id"));
            q.setTitle(rs.getString("qu_title"));
            q.setExam(exam);

            Option opt = new Option();
            opt.setTitle(rs.getString("o_title"));
            opt.setIsCorrect(rs.getBoolean("isCorrect"));
            q.setOptionAt(index, opt);

            index++;
            if(index > 3) {
                index = 0;
                list.add(q);
                q = new Question();
            }
        }
        return list;
    }


    private static Question getQuestionFromResultSet(ResultSet rs) throws SQLException {
        Question q = new Question();
        int index = 0;
        while(rs.next()) {
            q.setId(rs.getString("question_id"));
            q.setTitle(rs.getString("q_title"));

            Option opt = new Option();
            opt.setIsCorrect(rs.getBoolean("isCorrect"));
            opt.setTitle(rs.getString("o_title"));
            if(index > 3)
                index = 0;
            q.setOptionAt(index, opt);
            index++;
        }

        return q;
    }
}

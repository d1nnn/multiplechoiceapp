package com.multiplechoice.Models.Exam;

import com.multiplechoice.Database.Interfaces.IRepository;
import com.multiplechoice.Database.Repositories.RepositoriesFactory;
import com.multiplechoice.Database.Repositories.SqlServerRepository;
import com.multiplechoice.Models.Chapter.Chapter;
import com.multiplechoice.Models.Question.Question;
import com.multiplechoice.Models.Question.QuestionModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExamModel {
    private final static IRepository repo = RepositoriesFactory.createRepositoryInstance(SqlServerRepository.class);

    public ExamModel() {
    }

    public static ArrayList<Exam> getAllExam() throws SQLException, ClassNotFoundException {
        String examQuery = "SELECT * FROM exam";
        ResultSet rs = repo.ExecuteQuery(examQuery);
        ArrayList<Exam> examList = new ArrayList<>();
        while(rs.next()) {
            Exam exam = new Exam();
            exam.setIdexam(rs.getString("idexam"));
            exam.setNameexam(rs.getString("nameexam"));
            exam.setTime(rs.getString("timeexam"));
            exam.setDatecreate(rs.getString("datecreate"));
            exam.setIdSubExam(rs.getString("idsubexam"));
            exam.setQuantity(rs.getString("quantity"));
            exam.setIdSemester(rs.getString("idsemester"));
            examList.add(exam);
        }


        for(Exam exam : examList) {
            exam.setQuestionList(QuestionModel.getAllQuestionsFromExam2(exam));
        }

        return examList;
    }

    public static Map<Exam, Integer> getAllExamWithQuestionCount() {
        Map<Exam, Integer> examMap = new HashMap<>();
        Exam mapKey = null;
        String query = "SELECT quiz.quiz_id, quiz.title, COUNT(*) as question_count " +
                "FROM quiz, question " +
                "WHERE quiz.quiz_id = question.quiz_id " +
                "GROUP BY quiz.quiz_id, quiz.title";
        ArrayList<Exam> examList = new ArrayList<>();
        try {
            ResultSet rs = repo.ExecuteQuery(query);
            while (rs.next()) {
                Exam tempExam = new Exam();
                tempExam.setIdexam(rs.getString("quiz_id"));
                tempExam.setNameexam(rs.getString("title"));

                examMap.put(tempExam, rs.getInt("question_count"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error occurred in ExamModel getAllExamWithQuestionCount: " + e.getMessage());
        }
        return examMap;
    }


    private static ArrayList<Exam> getAllExamFromResultSet(ResultSet rs) {
        ArrayList<Exam> examList = new ArrayList<>();
        while (true) {
            try {
                if (!rs.next()) break;
                Exam q = new Exam();
                q.setIdexam(rs.getString("quiz_id"));
                q.setNameexam(rs.getString("title"));
                examList.add(q);
            } catch (SQLException e) {
                System.out.println("Error trying to get all exam from result set: " + e.getMessage());
            }

        }
        return examList;
    }

    public static ArrayList<Chapter> getChapterFromSemester(String hocKy) {
        String sql = String.format("select * from chapter where idSemester='%s'", hocKy);
        ArrayList<Chapter> chapterList = new ArrayList<>();
        try {
            ResultSet rs = repo.ExecuteQuery(sql);
            while (rs.next()) {
                Chapter chapter = new Chapter(rs.getString("idChapter"), rs.getString("nameChapter"),
                        rs.getString("idSemester"));
                chapterList.add(chapter);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Can't get Chapter from Semester id: " + e);
        }
        return chapterList;
    }

    public static void addExam(Exam exam) throws SQLException, ClassNotFoundException {
        String examQuery = String.format(
                "INSERT INTO exam(idsubexam, nameexam, quantity, timeexam, idsemester) " +
                        "VALUES ('%s', N'%s', %s, %s, '%s')",
                exam.getIdSubExam(),exam.getNameexam(), exam.getQuantity(), exam.getTime(), exam.getIdSemester()
        );

//        try {
            repo.ExecuteUpdateQuery(examQuery);
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Can't insert exam into Exam: " + e);
//        }

        for (Chapter ch : exam.getExamMap().keySet())
            for(Question q : exam.getExamMap().get(ch)) {
                String examDetailQuery = String.format(
                        "INSERT INTO examdetail " +
                        "VALUES( " +
                        "(SELECT idexam FROM exam WHERE idexam = (SELECT MAX(idexam) FROM exam)),'%s')  ",
                        q.getId()
                );

                try {
                    repo.ExecuteUpdateQuery(examDetailQuery);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Can't insert exam into ExamDetail: " + e);
                }
            }
    }

    public static boolean updateExam(Exam exam) {
        String query = String.format(
                "UPDATE exam " +
                "SET nameexam = N'%s', timeexam = %s " +
                "WHERE exam.idexam = '%s'",
                exam.getNameexam(), exam.getTime(), exam.getIdexam()
        );

        try {
            repo.ExecuteUpdateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static void delete(String examId) throws SQLException, ClassNotFoundException {
        String query = String.format(
                "DELETE FROM exam " +
                "WHERE exam.idexam = '%s'",
                examId
        );

        repo.ExecuteUpdateQuery(query);
    }

    public static boolean checkAttempted(String examId, String userId) throws SQLException, ClassNotFoundException {
        String query = String.format(
                "SELECT * FROM examresult " +
                "WHERE iduser = '%s' AND idexam = '%s'",
                userId, examId
        );

        ResultSet rs = repo.ExecuteQuery(query);
        if(rs.next()) {
            return true;
        }

        return false;
    }

}



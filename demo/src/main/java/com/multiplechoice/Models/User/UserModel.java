package com.multiplechoice.Models.User;

import com.multiplechoice.Database.Interfaces.IRepository;
import com.multiplechoice.Database.Repositories.RepositoriesFactory;
import com.multiplechoice.Database.Repositories.SqlServerRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    private static final IRepository repo = RepositoriesFactory.createRepositoryInstance(SqlServerRepository.class);

    public static User Login(String username, String password) throws SQLException, ClassNotFoundException {
        User user = null;
        String query = String.format(
                "SELECT id, username, realname, pass, isAdmin " +
                "FROM tblUser " +
                "WHERE username = '%s' AND pass = '%s'",
                username, password
        );

        ResultSet rs = repo.ExecuteQuery(query);
        if(rs.next()) {
            user = new User();
            user.setIduser(rs.getString("id"));
            user.setPassword(rs.getString("pass"));
            user.setUsername(rs.getString("username"));
            user.setRealName(rs.getString("realname"));
            user.setAdmin(rs.getBoolean("isAdmin"));
        }
        return user;
    }


    public static ArrayList<User> getAllUserWithScoreFromExam(String examId) throws SQLException, ClassNotFoundException {
        ArrayList<User> userList = new ArrayList<>();
        String userQuery = String.format(
                "SELECT er.iduser, u.realname, er.score " +
                "FROM examresult er, exam e, tblUser u " +
                "WHERE er.idexam = e.idexam AND er.iduser = u.id AND er.idexam = '%s'",
                examId
        );

        ResultSet rs = repo.ExecuteQuery(userQuery);
        while(rs.next()) {
            User user = new User();
            user.setIduser(rs.getString("iduser"));
            user.setRealName(rs.getString("realname"));
            user.setScore(rs.getString("score"));
            userList.add(user);
        }

        return userList;
    }

    public static void submitExam(String idexam, String iduser, float score) throws SQLException, ClassNotFoundException {
        String query = String.format(
                "INSERT INTO examresult(idexam, iduser, score) " +
                "VALUES ('%s', '%s', %.2f)",
                idexam, iduser, score
        );

        repo.ExecuteUpdateQuery(query);
    }
}

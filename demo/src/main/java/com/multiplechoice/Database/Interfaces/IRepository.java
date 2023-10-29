package com.multiplechoice.Database.Interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRepository {
    ResultSet ExecuteQuery(String query) throws SQLException, ClassNotFoundException;
    void ExecuteUpdateQuery(String query) throws SQLException, ClassNotFoundException;
}

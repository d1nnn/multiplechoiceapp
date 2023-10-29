package com.multiplechoice.Models;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IModel<T> {
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    T get(int Id) throws SQLException, ClassNotFoundException;
    void add(T obj) throws SQLException, ClassNotFoundException;
    void update(T obj) throws SQLException, ClassNotFoundException;
    void delete(int Id) throws SQLException, ClassNotFoundException;
}

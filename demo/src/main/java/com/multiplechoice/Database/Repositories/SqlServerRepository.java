package com.multiplechoice.Database.Repositories;

import com.multiplechoice.Database.Interfaces.IRepository;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class SqlServerRepository implements IRepository {
    private static final String connString = "jdbc:sqlserver://localhost;trustServerCertificate=true;integratedSecurity=true;encrypt=true;databaseName=multipleChoice";
    private static final String JDBC_Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static Connection conn = null;
    @Override
    public ResultSet ExecuteQuery(String query) throws SQLException, ClassNotFoundException {
        Statement smt = null;
        ResultSet rs = null;
        CachedRowSet crs = null;

        try {
            dbConnect();
            smt = conn.createStatement();
            rs = smt.executeQuery(query);
            RowSetFactory rf = RowSetProvider.newFactory();
            crs = rf.createCachedRowSet();
            crs.populate(rs);
        } finally {
            if(smt != null)
                smt.close();
            if(rs != null)
                rs.close();
//            if(crs != null) {
//                crs.close();
//            }
        }
        dbDisConnect();
        return crs;
    }

    @Override
    public void ExecuteUpdateQuery(String query) throws SQLException, ClassNotFoundException {
        dbConnect();
        try (Statement smt = conn.createStatement()) {

            smt.executeUpdate(query);
        }
        dbDisConnect();
    }

    private static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_Driver);
            conn = DriverManager.getConnection(connString);
        } catch(SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred on connection: " + e.getMessage());
            throw e;
        }
    }

    private static void dbDisConnect() throws SQLException {
        try {
            if(conn != null && !conn.isClosed())
                conn.close();
        } catch(SQLException e) {
            System.out.println("An error occurred trying to disconnect: " + e.getMessage());
        }

    }
}

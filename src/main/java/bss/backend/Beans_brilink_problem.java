/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend;

import bss.view.Data_problem_log;
import bss.view.Fraud_warning;
import com.vaadin.addon.sqlcontainer.connection.J2EEConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author ivanariwibawa
 */
public class Beans_brilink_problem {

    Db1 db;
    J2EEConnectionPool connectionPool;
    int no;
    String tanggal;
    String problem;
    String detail_problem;
    String status;
    String date_close;
    int id;

    public Beans_brilink_problem(int no, String tanggal, String problem, String detail_problem, String status, String date_close, int id) {
        this.no = no;
        this.tanggal = tanggal;
        this.problem = problem;
        this.detail_problem = detail_problem;
        this.status = status;
        this.date_close = date_close;
        this.id = id;
    }

    public Beans_brilink_problem() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDetail_problem() {
        return detail_problem;
    }

    public void setDetail_problem(String detail_problem) {
        this.detail_problem = detail_problem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_close() {
        return date_close;
    }

    public void setDate_close(String date_close) {
        this.date_close = date_close;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String generateSQL() throws SQLException {

        String sql = " Select * from problem_brilink order by id desc ";
        return sql;
    }

    public Collection<Beans_brilink_problem> getData() throws SQLException {
        Collection<Beans_brilink_problem> beans_brilink_problems = new ArrayList<Beans_brilink_problem>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQL());
        int no = 0;

        while (rs.next()) {
            no++;
            Beans_brilink_problem beans_brilink_problem = new Beans_brilink_problem(no, rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(1));

            beans_brilink_problems.add(beans_brilink_problem);
        }

        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return beans_brilink_problems;
    }

    private void setupConnection() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }

    public void insert() throws SQLException {
        setupConnection();
        Connection con = connectionPool.reserveConnection();
        
        String sql = "insert into problem_brilink (tanggal, problem, detail_problem, status) "
                + "values (?,?,?,?) ";
        PreparedStatement pstatement = con.prepareStatement(sql);
        pstatement.setString(1, getTanggal());
        pstatement.setString(2, getProblem());
        pstatement.setString(3, getDetail_problem());
        pstatement.setString(4, Data_problem_log.STATUS[0]);

        int i = pstatement.executeUpdate();
        
        System.out.println(" i "+i);
        
        con.commit();
        con.close();
        connectionPool.releaseConnection(con);
    }

    public void update() throws SQLException {
        setupConnection();
        Connection con = connectionPool.reserveConnection();
        String sql = "update problem_brilink set tanggal = ?, problem = ?, detail_problem = ?, status =? "
                + " , tanggal_close = ? where id = ? ";
        PreparedStatement pstatement = con.prepareStatement(sql);
        pstatement.setString(1, getTanggal());
        pstatement.setString(2, getProblem());
        pstatement.setString(3, getDetail_problem());
        pstatement.setString(4, getStatus());
        pstatement.setString(5, getDate_close()); 
        pstatement.setInt(6, getId());

        
        pstatement.executeUpdate();
        con.commit();
        con.close();
        connectionPool.releaseConnection(con);
    }

    public void delete() throws SQLException {
        setupConnection();
        Connection con = connectionPool.reserveConnection();
        String sql = "delete from problem_brilink "
                + " where id = ? ";
        PreparedStatement pstatement = con.prepareStatement(sql);
        pstatement.setInt(1, getId());

        pstatement.executeUpdate();

        con.commit();
        con.close();
        connectionPool.releaseConnection(con);
    }
}

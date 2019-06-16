/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend;

import com.vaadin.addon.sqlcontainer.connection.J2EEConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author IVAN
 */
public class Kanwil {
    String kd_kanwil;
    String kanwil;
    
    J2EEConnectionPool connectionPool;
    Db1 db ;

    public Kanwil() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }
    
    // this contructor only initiate as pojo object
    public Kanwil(String kd_kanwil, String kanwil){
        this.kd_kanwil = kd_kanwil;
        this.kanwil = kanwil;
    }


    public String getKd_kanwil() {
        return kd_kanwil;
    }

    public void setKd_kanwil(String kd_kanwil) {
        this.kd_kanwil = kd_kanwil;
    }

    public String getKanwil() {
        return kanwil;
    }

    public void setKanwil(String kanwil) {
        this.kanwil = kanwil;
    }
    
    public Collection<Kanwil> getAllKanwil() throws SQLException{
        Collection<Kanwil> kanwils = new ArrayList<Kanwil>();
     
        String sql = "select kd_kanwil, KANWIL from kanwil ";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        while (rs.next()) {            
            Kanwil kanwil = new Kanwil(rs.getString(1), rs.getString(2));
            kanwils.add(kanwil);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return kanwils;
    }

  
    
}

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
import java.text.DecimalFormat;

/**
 *
 * @author IVAN
 */
public class Beans_laporan_mingguanx {
  
    Db1 db;
    J2EEConnectionPool connectionPool;
    
    


   
    
    public Beans_laporan_mingguanx() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }
    
    public Collection<String> getListDateRka() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select  distinct bulan from rka_all ";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
        
        System.out.println(sql_1);
        ArrayList<String> array;
        array = new ArrayList<>();
        while (rs_1.next()){
            array.add(rs_1.getString(1));
        }
        
        con_1.close();
        connectionPool.releaseConnection(con_1);
       
        return array;
      }
     

}

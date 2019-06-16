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

/**
 *
 * @author IVAN
 */
public class Longlat {

    J2EEConnectionPool connectionPool;
    Db1 db = new Db1();

    public Longlat() {
        this.connectionPool = db.getConnectionPool1();
    }

  
       public ArrayList<Object[]> getLonglat(String region) throws SQLException {
        
        // get max tanggal
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select table_name from information_schema.tables where table_name like 'tid_edc_20%' "
                + "order by table_name desc limit 1";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
       
        String table ="";
        if (rs_1.next()){
          table =  rs_1.getString(1);
        }
        
        connectionPool.releaseConnection(con_1);
       
        
           
        ArrayList<Object[]> array = new ArrayList();
        String sql = "SELECT TID, NAMA_AGEN, LOKASI, KD_KANWIL, NAMA_KANWIL, longitude, latitude FROM map_longlat "
                + "where kd_kanwil =  '"+region+"' "
                + "and ( longitude > 0 or longitude < 0) and ( latitude > 0 or latitude < 0) "
                + "and latitude not like '% %' and longitude not like '% %' ";

        System.out.println(sql);
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        while (rs.next()) {            
            Object[] obj = new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)};
            array.add(obj);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        
        return array;

    }
     
     
     
   
}

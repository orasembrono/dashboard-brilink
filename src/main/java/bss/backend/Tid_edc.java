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
public class Tid_edc {

    J2EEConnectionPool connectionPool;
    Db1 db = new Db1();

    public Tid_edc() {
        this.connectionPool = db.getConnectionPool1();
    }

  

   

    public ArrayList<Object[]> getLapharian_datatid() throws SQLException {
        
        ArrayList<Object[]> array = new ArrayList();
        String sql = "select kd_kanwil, KANWIL , count(*) as jumlah  from tid_edc_20180109 group by kd_kanwil";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        while (rs.next()) {            
            Object[] obj = new Object[] {++no, rs.getString(1), rs.getString(2), rs.getString(3)};
            array.add(obj);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        
        return array;

    }
    
    public ArrayList<Object[]> getLapharian_datatid(String kd_kanwil ) throws SQLException {
        
        ArrayList<Object[]> array = new ArrayList();
        String sql = "select kd_cabang, cabang , count(*) as jumlah  from tid_edc_20180109 where kd_kanwil = '"+kd_kanwil+"' group by kd_cabang ";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        while (rs.next()) {            
            Object[] obj = new Object[] {++no, rs.getString(1), rs.getString(2), rs.getString(3)};
            array.add(obj);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        
        return array;

    }
    
     public ArrayList<Object[]> getLapharian_datatid(String kd_kanwil, String kd_kanca ) throws SQLException {
        
        ArrayList<Object[]> array = new ArrayList();
        String sql = "select kd_uker , uker , count(*) as jumlah  from tid_edc_20180109 where kd_kanwil = '"+kd_kanwil+"' "
                + " and kd_cabang = '"+kd_kanca+"' group by kd_uker ";

        System.out.println(sql);
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        while (rs.next()) {            
            Object[] obj = new Object[] {++no, rs.getString(1), rs.getString(2), rs.getString(3)};
            array.add(obj);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        
        return array;

    }
     
     
       public ArrayList<Object[]> getLonglat() throws SQLException {
        
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
        String sql = "select kd_kanwil, kanwil, `LATITUTE_-_LONGITUDE`as longlat  from "+table+" "
                + "where `latitute_-_longitude` is not null and `latitute_-_longitude` like '%-%'"
                + "and length(`latitute_-_longitude`) >= 23 limit 10 ";

        System.out.println(sql);
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        while (rs.next()) {            
            Object[] obj = new Object[] { rs.getString(1), rs.getString(2), rs.getString(3)};
            array.add(obj);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        
        return array;

    }
     
     
     
   
}

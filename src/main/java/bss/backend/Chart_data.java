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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IVAN
 */
public class Chart_data {

    J2EEConnectionPool connectionPool;
    Db1 db = new Db1();

    public Chart_data() {
        this.connectionPool = db.getConnectionPool1();
    }

    public ArrayList<Map> getChartPieEDC_Mobile() throws SQLException {
        /*
        CREATE TABLE `chart_data` (
        `Id` int(11) NOT NULL auto_increment,
        `keterangan` varchar(255) default NULL,
        `date` datetime default NULL,
        `value` varchar(255) default NULL,
         PRIMARY KEY  (`Id`)
         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
         B DEFAULT CHARSET=latin1;

         */
         Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select  replace(a.table_name,'tid_edc_','') as table_name from information_schema.tables a\n" +
            "inner join " +
            " information_schema.tables b " +
            " on ( replace(a.table_name,'tid_edc_','')  =  replace(b.table_name,'outlet_web_','') )" +
            " where  a.table_name like 'tid_edc_%' " +
            " and b.table_name like 'outlet_web_%' and  length(a.table_name) > 15 " +
            " order by table_name desc limit 1";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
        
        String tanggal_newest ="";
        
        if (rs_1.next()){
          tanggal_newest = rs_1.getString(1).replaceAll("tid_edc_","");
        }
        
        con_1.close();
        connectionPool.releaseConnection(con_1);
       
        
        
        
        
        
       
        ArrayList<Map> array = new ArrayList();
         
        String sql = "select * from ( select  count(*) as jumlah_edc from tid_edc_"+tanggal_newest+" ) a inner join\n" +
                    "( select  count(*) as jumlah_web from outlet_web_"+tanggal_newest+" ) b";
        
        System.out.println(sql);
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
      
        while (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("date" ,tanggal_newest);
            aMap.put("keterangan" , "jumlah_brilink_edc" );
            aMap.put("value" , rs.getString(1));   
            array.add(aMap);
            aMap = new HashMap<String, String>();
            aMap.put("date" ,tanggal_newest);
            aMap.put("keterangan" , "jumlah_brilink_mobile" );
            aMap.put("value" , rs.getString(2));   
            array.add(aMap);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return array;
    }
    
    
    
    
    
   
       public ArrayList<Map> getDataActivated() throws SQLException {
        /*
        CREATE TABLE `chart_data` (
        `Id` int(11) NOT NULL auto_increment,
        `keterangan` varchar(255) default NULL,
        `date` datetime default NULL,
        `value` varchar(255) default NULL,
         PRIMARY KEY  (`Id`)
         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
         B DEFAULT CHARSET=latin1;

         */
         Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "select  a.table_name as table_name from information_schema.tables a\n" +
            " where a.table_name like 'outlet_web_%' and  length(a.table_name) > 15 \n" +
            " order by a.table_name desc limit 1";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
        
        String tanggal_newest ="";
        
        if (rs_1.next()){
          tanggal_newest = rs_1.getString(1);
        }
        
        con_1.close();
        connectionPool.releaseConnection(con_1);
       
        
        
        
        
        
       
        ArrayList<Map> array = new ArrayList();
         
        String sql = "   select ACTIVATION_DESC, count(*) as jumlah from "+tanggal_newest+" group by ACTIVATION_DESC  ";
        
        System.out.println(sql);
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        Map<String, String> aMap = new HashMap<String, String>();
         
        if  (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
            aMap.put(rs.getString(1) ,rs.getString(2));        
           
        }
        if  (rs.next()) {
            aMap.put(rs.getString(1) ,rs.getString(2));        
            
        }
        array.add(aMap);
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return array;
    }
    
    
  
    
    public ArrayList<Map> getChartPieStaging() throws SQLException {
        /*
        CREATE TABLE `chart_data` (
        `Id` int(11) NOT NULL auto_increment,
        `keterangan` varchar(255) default NULL,
        `date` datetime default NULL,
        `value` varchar(255) default NULL,
         PRIMARY KEY  (`Id`)
         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
         B DEFAULT CHARSET=latin1;

         */
       
       
        ArrayList<Map> array = new ArrayList();
         
        String sql = "select date, keterangan, value  from chart_data where "
                + "keterangan = 'staging' "
                + "order by date desc limit 1";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        
       
        
        while (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
           
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("date" , rs.getString("date"));
            aMap.put("keterangan" , rs.getString("keterangan"));
            aMap.put("value" , rs.getString("value"));                 
            array.add(aMap);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);

        return array;

    }

    
    public ArrayList<Map> getChartNonPerformance() throws SQLException {
        /*
        CREATE TABLE `chart_data` (
        `Id` int(11) NOT NULL auto_increment,
        `keterangan` varchar(255) default NULL,
        `date` datetime default NULL,
        `value` varchar(255) default NULL,
         PRIMARY KEY  (`Id`)
         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
         B DEFAULT CHARSET=latin1;

         */
       
       
        ArrayList<Map> array = new ArrayList();
         
        String sql = "select date, keterangan, value  from chart_data where "
                + "keterangan = 'NON PERFORMANCE' "
                + "order by date desc limit 1";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        
       
        
        while (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
           
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("date" , rs.getString("date"));
            aMap.put("keterangan" , rs.getString("keterangan"));
            aMap.put("value" , rs.getString("value"));                 
            array.add(aMap);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);

        return array;

    }

}

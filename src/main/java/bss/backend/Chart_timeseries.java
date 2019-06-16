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
public class Chart_timeseries {

    J2EEConnectionPool connectionPool;
    Db1 db = new Db1();

    public Chart_timeseries() {
        this.connectionPool = db.getConnectionPool1();
    }

    public ArrayList<Map> getTimeseries() throws SQLException {
        /*
        CREATE TABLE `timeseries` (
        `Id` int(11) NOT NULL auto_increment,
        `tahun` varchar(255) default NULL,
        `jumlah_brlink` varchar(255) default NULL,
        `feebased` varchar(255) default NULL,
        `transaksi` varchar(255) default NULL,
        `bep` varchar(255) default NULL,
        `agen_transaksi` varchar(255) default NULL,
        PRIMARY KEY  (`Id`)
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

         */
       
       
        ArrayList<Map> array = new ArrayList();
         
        String sql = "select tahun, jumlah_brilink,bep,agen_transaksi from chart_timeseries";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        
       
        
        while (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
           
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("tahun" , rs.getString("tahun"));
            aMap.put("jumlah_brilink" , rs.getString("jumlah_brilink"));
            aMap.put("bep" , rs.getString("bep"));
            aMap.put("agen_transaksi" , rs.getString("agen_transaksi"));            
            array.add(aMap);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);

        return array;

    }
    
    
    
     public ArrayList<Map> getTimeseriesMtoM() throws SQLException {
        /*
        CREATE TABLE `timeseries` (
        `Id` int(11) NOT NULL auto_increment,
        `tahun` varchar(255) default NULL,
        `jumlah_brlink` varchar(255) default NULL,
        `feebased` varchar(255) default NULL,
        `transaksi` varchar(255) default NULL,
        `bep` varchar(255) default NULL,
        `agen_transaksi` varchar(255) default NULL,
        PRIMARY KEY  (`Id`)
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

         */
       
       
        ArrayList<Map> array = new ArrayList();
         
        String sql = "select  bulan, jumlah, delta_transaksi, delta_fee from view_rekap_pencapaian "
                + "order by bulan";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        
       
        
        while (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
           
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("bulan" , rs.getString("bulan"));
            aMap.put("transaksi" , rs.getString("delta_transaksi"));
            aMap.put("fee" , rs.getString("delta_fee"));
            aMap.put("jumlah" , rs.getString("jumlah"));            
            array.add(aMap);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);

        return array;

    }
    
     
      public ArrayList<Map> getCountMonthly() throws SQLException {
    
        ArrayList<Map> array = new ArrayList();
        String sql = "select bulan, jumlah, delta_transaksi, delta_fee from view_rekap_pencapaian order by bulan";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
       
        while (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
           
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("bulan" , rs.getString("bulan"));
          
            aMap.put("jumlah" , rs.getString("jumlah"));            
            array.add(aMap);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);

        return array;

    }
      
      
    public ArrayList<Map> getFBITimeSeriesEDC() throws SQLException {
        /*
       

         */
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select table_name from information_schema.tables where table_name like 'trx_edc_fin%' "
                + "and length(table_name) = 18 order by table_name desc limit 5";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
        
        String[] tgl_1 = new String[5];
        int i=5;
        while (rs_1.next()){
          tgl_1[--i]=  rs_1.getString(1).replaceAll("trx_edc_fin_", "");
        }
        
        connectionPool.releaseConnection(con_1);
       
        ArrayList<Map> array = new ArrayList();
         
        String sql = "select * from ( select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[0]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[0]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[0]+" from trx_edc_fin_"+tgl_1[0]+"\n" +
                    ") a\n" +
                    "inner join\n" +
                    "( select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[1]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[1]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[1]+" from trx_edc_fin_"+tgl_1[1]+" ) b\n" +
                    "inner join\n" +
                    "( select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[2]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[2]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[2]+" from trx_edc_fin_"+tgl_1[2]+")c\n" +
                    " inner join\n" +
                    "(select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[3]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[3]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[3]+" from trx_edc_fin_"+tgl_1[3]+" ) d\n" +
                    "inner join\n" +
                    "( select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[4]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[4]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[4]+" from trx_edc_fin_"+tgl_1[4]+" ) e";

        
        System.out.println(sql);
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        
       
        
        while (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
           
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("fee_bri_"+tgl_1[0] , rs.getString("fee_bri_"+tgl_1[0]));
            aMap.put("fee_bri_"+tgl_1[1] , rs.getString("fee_bri_"+tgl_1[1]));
            aMap.put("fee_bri_"+tgl_1[2] , rs.getString("fee_bri_"+tgl_1[2]));
            aMap.put("fee_bri_"+tgl_1[3], rs.getString("fee_bri_"+tgl_1[3])); 
            aMap.put("fee_bri_"+tgl_1[4], rs.getString("fee_bri_"+tgl_1[4]));  
            array.add(aMap);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);

        return array;

    }

      
    public ArrayList<Map> getFBITimeSeriesMobile() throws SQLException {
        /*
       

         */
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select table_name from information_schema.tables where table_name like 'trx_web_%' "
                + "and length(table_name) = 14 order by table_name desc limit 5";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
        
        String[] tgl_1 = new String[5];
        int i=5;
        while (rs_1.next()){
          tgl_1[--i]=  rs_1.getString(1).replaceAll("trx_web_", "");
        }
        
        connectionPool.releaseConnection(con_1);
       
        ArrayList<Map> array = new ArrayList();
         
        String sql = "select * from ( select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[0]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[0]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[0]+" from trx_web_"+tgl_1[0]+"\n" +
                    ") a\n" +
                    "inner join\n" +
                    "( select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[1]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[1]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[1]+" from trx_web_"+tgl_1[1]+" ) b\n" +
                    "inner join\n" +
                    "( select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[2]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[2]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[2]+" from trx_web_"+tgl_1[2]+")c\n" +
                    " inner join\n" +
                    "(select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[3]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[3]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[3]+" from trx_web_"+tgl_1[3]+" ) d\n" +
                    "inner join\n" +
                    "( select sum(replace(total_fee_bri,',','')) as fee_bri_"+tgl_1[4]+", sum(replace(total_transaksi,',','')) as trx_"+tgl_1[4]+", \n" +
                    "sum(replace(total_nominal,',','')) as nominal_"+tgl_1[4]+" from trx_web_"+tgl_1[4]+" ) e";

        
        System.out.println(sql);
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Integer no = 0;
        
       
        
        while (rs.next()) {
          //  Object[] obj = new Object[]{++no, rs.getString(1), rs.getString(2), rs.getString(3)};
           
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("fee_bri_"+tgl_1[0] , rs.getString("fee_bri_"+tgl_1[0]));
            aMap.put("fee_bri_"+tgl_1[1] , rs.getString("fee_bri_"+tgl_1[1]));
            aMap.put("fee_bri_"+tgl_1[2] , rs.getString("fee_bri_"+tgl_1[2]));
            aMap.put("fee_bri_"+tgl_1[3], rs.getString("fee_bri_"+tgl_1[3])); 
            aMap.put("fee_bri_"+tgl_1[4], rs.getString("fee_bri_"+tgl_1[4]));  
            array.add(aMap);
        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);

        return array;

    }

}

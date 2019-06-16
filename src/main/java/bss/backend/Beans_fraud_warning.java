/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend;

import bss.view.Fraud_warning;
import com.vaadin.addon.sqlcontainer.connection.J2EEConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author ivanariwibawa
 */
public class Beans_fraud_warning {
    
    Db1 db;
    J2EEConnectionPool connectionPool;
    int no;
    String tid;
    String mid;
    String jenis_agen;
    String nama_agen;
    String kanwil;
    double fee;
    double transaksi;
    double vol_pertrx;
    double volume;
    String current_date;
    public static byte EDC =0;
    public static byte MOBILE = 1;

    public Beans_fraud_warning(int no, String tid, String mid, String jenis_agen, String nama_agen, String kanwil, double fee, double transaksi, double vol_pertrx, double volume) {
        this.no = no;
        this.tid = tid;
        this.mid = mid;
        this.jenis_agen = jenis_agen;
        this.nama_agen = nama_agen;
        this.kanwil = kanwil;
        this.fee = fee;
        this.transaksi = transaksi;
        this.vol_pertrx = vol_pertrx;
        this.volume = volume;
    }

    public Beans_fraud_warning() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }
    
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getJenis_agen() {
        return jenis_agen;
    }

    public void setJenis_agen(String jenis_agen) {
        this.jenis_agen = jenis_agen;
    }

    public String getNama_agen() {
        return nama_agen;
    }

    public void setNama_agen(String nama_agen) {
        this.nama_agen = nama_agen;
    }

    public String getKanwil() {
        return kanwil;
    }

    public void setKanwil(String kanwil) {
        this.kanwil = kanwil;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(double transaksi) {
        this.transaksi = transaksi;
    }

    public double getVol_pertrx() {
        return vol_pertrx;
    }

    public void setVol_pertrx(double vol_pertrx) {
        this.vol_pertrx = vol_pertrx;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    
    
     private String generateSQLEDC(String item) throws SQLException{
         String table = getTableEDCAvailable();
         String sql_fee = "select tid, mid, 'EDC' as jenis_agen, mid_name as nama_agen, kanwil,\n" +
                "replace(TOTAL_FEE,',','') as fee, TOTAL_TRANSAKSI as transaksi, \n" +
                "replace(TOTAL_NOMINAL,',','') / TOTAL_TRANSAKSI as vol_pertrx,\n" +
                "replace(TOTAL_NOMINAL,',','') as volume  from "+table+" order by replace(TOTAL_FEE,',','') * 1 desc limit 100 ";
         
         String sql_transaksi =  "select tid, mid, 'EDC' as jenis_agen, mid_name as nama_agen, kanwil,\n" +
                "replace(TOTAL_FEE,',','') as fee, TOTAL_TRANSAKSI as transaksi, \n" +
                "replace(TOTAL_NOMINAL,',','') / TOTAL_TRANSAKSI as vol_pertrx,\n" +
                "replace(TOTAL_NOMINAL,',','') as volume  from "+table+" order by TOTAL_TRANSAKSI * 1 desc limit 100 ";
         
          
         String sql_vol_per_trx =  "select tid, mid, 'EDC' as jenis_agen, mid_name as nama_agen, kanwil,\n" +
                "replace(TOTAL_FEE,',','') as fee, TOTAL_TRANSAKSI as transaksi, \n" +
                "replace(TOTAL_NOMINAL,',','') / TOTAL_TRANSAKSI as vol_pertrx,\n" +
                "replace(TOTAL_NOMINAL,',','') as volume  from "+table+" where TOTAL_TRANSAKSI > 200 order by replace(TOTAL_NOMINAL,',','')  / TOTAL_TRANSAKSI  asc limit 100 ";
         
        String sql_volume =  "select tid, mid, 'EDC' as jenis_agen, mid_name as nama_agen, kanwil,\n" +
                "replace(TOTAL_FEE,',','') as fee, TOTAL_TRANSAKSI as transaksi, \n" +
                "replace(TOTAL_NOMINAL,',','') / TOTAL_TRANSAKSI as vol_pertrx,\n" +
                "replace(TOTAL_NOMINAL,',','') as volume  from "+table+" order by replace(TOTAL_NOMINAL,',','') * 1 desc limit 100 ";
         
        
        String sql =""; 
        if (item.equals(Fraud_warning.TIPES[0]))  sql = sql_fee;
        if (item.equals(Fraud_warning.TIPES[1]))  sql = sql_transaksi;
        if (item.equals(Fraud_warning.TIPES[2]))  sql = sql_vol_per_trx;
        if (item.equals(Fraud_warning.TIPES[3]))  sql = sql_volume;
        
         System.out.println(sql);
         return sql;
     }
    
      private String generateSQLMobile(String item) throws SQLException{
         String table = getTableMobileAvailable();
         String sql_fee = "select id_outlet as tid, id_merchant as mid, 'Mobile' as jenis_agen, nama_outlet as nama_agen, kanwil," +
                "replace(TOTAL_FEE,',','') as fee, TOTAL_TRANSAKSI as transaksi, \n" +
                "replace(TOTAL_NOMINAL,',','') / TOTAL_TRANSAKSI as vol_pertrx,\n" +
                "replace(TOTAL_NOMINAL,',','') as volume  from "+table+" order by replace(TOTAL_FEE,',','') * 1 desc limit 100 ";
         
         String sql_transaksi =  "select id_outlet as tid, id_merchant as mid, 'Mobile' as jenis_agen, nama_outlet as nama_agen, kanwil," +
                "replace(TOTAL_FEE,',','') as fee, TOTAL_TRANSAKSI as transaksi, \n" +
                "replace(TOTAL_NOMINAL,',','') / TOTAL_TRANSAKSI as vol_pertrx,\n" +
                "replace(TOTAL_NOMINAL,',','') as volume  from "+table+" order by TOTAL_TRANSAKSI * 1 desc limit 100 ";
         
          
         String sql_vol_per_trx =  "select id_outlet as tid, id_merchant as mid, 'Mobile' as jenis_agen, nama_outlet as nama_agen, kanwil," +
                "replace(TOTAL_FEE,',','') as fee, TOTAL_TRANSAKSI as transaksi, \n" +
                "replace(TOTAL_NOMINAL,',','') / TOTAL_TRANSAKSI as vol_pertrx,\n" +
                "replace(TOTAL_NOMINAL,',','') as volume  from "+table+" where TOTAL_TRANSAKSI > 200 order by replace(TOTAL_NOMINAL,',','')  / TOTAL_TRANSAKSI  asc limit 100 ";
         
        String sql_volume =  "select id_outlet as tid, id_merchant as mid, 'Mobile' as jenis_agen, nama_outlet as nama_agen, kanwil," +
                "replace(TOTAL_FEE,',','') as fee, TOTAL_TRANSAKSI as transaksi, \n" +
                "replace(TOTAL_NOMINAL,',','') / TOTAL_TRANSAKSI as vol_pertrx,\n" +
                "replace(TOTAL_NOMINAL,',','') as volume  from "+table+" order by replace(TOTAL_NOMINAL,',','') * 1 desc limit 100 ";
         
        
        String sql =""; 
        if (item.equals(Fraud_warning.TIPES[0]))  sql = sql_fee;
        if (item.equals(Fraud_warning.TIPES[1]))  sql = sql_transaksi;
        if (item.equals(Fraud_warning.TIPES[2]))  sql = sql_vol_per_trx;
        if (item.equals(Fraud_warning.TIPES[3]))  sql = sql_volume;
        
       
         return sql;
     }
      
      public String getTableEDCAvailable() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select table_name from information_schema.tables where table_name like 'trx_edc_fin%' "
        + "order by table_name desc limit 1";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
        
        String ret="";
        while (rs_1.next()){
           ret = rs_1.getString(1);
        }
        
        con_1.close();
        connectionPool.releaseConnection(con_1);
       
        setCurrent_date(ret);
        return ret;
      }
      
       public String getTableMobileAvailable() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select table_name from information_schema.tables where table_name like 'trx_web_%' "
        + "order by table_name desc limit 1";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
        
        String ret="";
        while (rs_1.next()){
           ret = rs_1.getString(1);
        }
        
        con_1.close();
        connectionPool.releaseConnection(con_1);
       
        setCurrent_date(ret);
        return ret;
      }
      
      
       public Collection<Beans_fraud_warning> getData(String tipe_report, int item_deivice) throws SQLException {
        Collection<Beans_fraud_warning> beans_fraud_warnings = new ArrayList<Beans_fraud_warning>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = (item_deivice==EDC)?st.executeQuery(generateSQLEDC(tipe_report)):st.executeQuery(generateSQLMobile(tipe_report));
        
        int no = 0;

        while (rs.next()) {
            no++;
            Beans_fraud_warning beans_fraud_warning= new Beans_fraud_warning
            (no,rs.getString("tid"),rs.getString("mid"),rs.getString("jenis_agen"),
            rs.getString("nama_agen"),rs.getString("kanwil"),rs.getDouble("fee"),
                    rs.getDouble("transaksi"),rs.getDouble("vol_pertrx"), rs.getDouble("volume"));
            
            beans_fraud_warnings.add(beans_fraud_warning);
        }
       
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return beans_fraud_warnings;
    }
    
}

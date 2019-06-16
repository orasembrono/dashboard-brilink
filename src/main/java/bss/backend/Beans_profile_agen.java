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
public class Beans_profile_agen {
  
    Db1 db;
    J2EEConnectionPool connectionPool;
    
    String id_agen;
    String nama_agen;
    String mid;
    String kanwil;
    String kd_cabang;
    String nama_cabang;
    String tangga_pks;
    String alamat;
    String jenis_usaha;
    


    public Beans_profile_agen getDataByTid(String tid) throws SQLException {
        Beans_profile_agen profile_agens = null;
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        String table_tid = getTableNameTID_EDC();
        String table_mid = getTableNameMID_EDC();
        
        
        String sql  = "Select b.`ID_AGEN`, b.`NAMA_AGEN`, b.`MID`, b.`KANWIL`, b.`KD_CABANG`, b.`CABANG`, b.`TANGGAL_PKS`, "
                + "    b.`ALAMAT_MID`, b.`JENIS_USAHA_LKD` from "+table_tid+" a inner join "+table_mid+" b on (a.mid = b.mid) where a.tid = '"+tid+"' ";
        
        System.out.println(sql);
       
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()){
           profile_agens =
                new Beans_profile_agen(rs.getString("ID_AGEN"),rs.getString("NAMA_AGEN"),rs.getString("MID"),
                rs.getString("KANWIL"),rs.getString("KD_CABANG"),rs.getString("CABANG"),rs.getString("TANGGAL_PKS"),
                rs.getString("ALAMAT_MID"),rs.getString("JENIS_USAHA_LKD"));
         
        }
        
        
        con.close();
        connectionPool.releaseConnection(con);
        return profile_agens;
    }

    public Beans_profile_agen(String id_agen, String nama_agen, String mid, String kanwil, String kd_cabang, String nama_cabang, String tangga_pks, String alamat, String jenis_usaha) {
        this.id_agen = id_agen;
        this.nama_agen = nama_agen;
        this.mid = mid;
        this.kanwil = kanwil;
        this.kd_cabang = kd_cabang;
        this.nama_cabang = nama_cabang;
        this.tangga_pks = tangga_pks;
        this.alamat = alamat;
        this.jenis_usaha = jenis_usaha;
    }

   
    
    public Beans_profile_agen() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }
    
    public void getProfileByTID(String tid) throws SQLException{
        String table_tid = getTableNameTID_EDC();
        String table_mid = getTableNameMID_EDC();
        
        String sql  = "Select * from "+table_tid+" a inner join "+table_mid+" b on (a.id_agen = b.id_agen) where a.tid = '"+tid+"' ";
        
        System.out.println(sql);
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()){
            
        }
        
        con.close();
        connectionPool.releaseConnection(con);
     }

    
    private String getTableNameTID_EDC() throws SQLException{
        String sql = "select table_name from information_schema.tables where table_name like 'tid_edc_20%' order by table_name desc limit 1";
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String table_name ="";
        if (rs.next()){
            table_name = rs.getString(1);
        } 
        con.close();
        connectionPool.releaseConnection(con);
        return table_name;
    }
    
     private String getTableNameMID_EDC() throws SQLException{
        String sql = "select table_name from information_schema.tables where table_name like 'mid_edc_20%' order by table_name desc limit 1";
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String table_name ="";
        if (rs.next()){
            table_name = rs.getString(1);
        }
        con.close();
        connectionPool.releaseConnection(con);
        return table_name;
    }

    public String getId_agen() {
        return id_agen;
    }

    public void setId_agen(String id_agen) {
        this.id_agen = id_agen;
    }

    public String getNama_agen() {
        return nama_agen;
    }

    public void setNama_agen(String nama_agen) {
        this.nama_agen = nama_agen;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getKanwil() {
        return kanwil;
    }

    public void setKanwil(String kanwil) {
        this.kanwil = kanwil;
    }

    public String getKd_cabang() {
        return kd_cabang;
    }

    public void setKd_cabang(String kd_cabang) {
        this.kd_cabang = kd_cabang;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }

    public String getTangga_pks() {
        return tangga_pks;
    }

    public void setTangga_pks(String tangga_pks) {
        this.tangga_pks = tangga_pks;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_usaha() {
        return jenis_usaha;
    }

    public void setJenis_usaha(String jenis_usaha) {
        this.jenis_usaha = jenis_usaha;
    }
      
    
     

}

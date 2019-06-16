/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend;

import com.vaadin.addon.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.server.ThemeResource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author IVAN
 */
public class Beans_laporan_bulanan_kanca {

    public static double rka_transaksi_perdevice = 100.0;
    public static double rka_fbi_perdevice = 200000.0;
    
    int no;
    String kode_kanwil;
    String nama_kanwil;
    String kode_cabang;
    String nama_cabang;
    double total_transaksi_web;
    double total_transaksi_edcf;
    double total_transaksi_all;
    double total_fee_web;
    double total_fee_edcf;
    double total_fee_all;
    double total_nominal_web;
    double total_nominal_edcf;
    double total_nominal_all;
    double edc_trx;
    double web_trx;
    double trx_all;
    double bep_edc;
    double bep_web;
    double bep_all;
    double jumlah_edc;
    double jumlah_web;
    double jumlah_all;
    double jumlah_edc_2017;
    double jumlah_web_2017;
    double jumlah_all_2017;
    double casa_web;
    double casa_edc;
    double casa_all;
  
  
    
    
    double grand_jumlah_edc =0.0;
    double grand_jumlah_web = 0.0;
    double grand_jumlah_all = 0.0;
    double grand_jumlah_all_2017 =0.0;
    
    double grand_total_transaksi_web;
    double grand_total_transaksi_edcf;
    double grand_total_transaksi_all;

    double grand_total_fee_web;
    double grand_total_fee_edcf;
    double grand_total_fee_all;
    
    double grand_edc_trx;
    double grand_web_trx;
    double grand_trx_all;
    
    double grand_bep_edc;
    double grand_bep_web;
    double grand_bep_all;
    
    double grand_casa_web;
    double grand_casa_edc;
    double grand_casa_all;
    
    double grand_bep_edc_persen;
    double grand_bep_web_persen;
    double grand_bep_web_all;
  
    
    Db1 db;
    J2EEConnectionPool connectionPool;


    public Collection<Beans_laporan_bulanan_kanca> getData(String tgl, String kanwil) throws SQLException {
        Collection<Beans_laporan_bulanan_kanca> dashboard_pencapaian_brilinks = new ArrayList<Beans_laporan_bulanan_kanca>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaian(tgl,kanwil));
        int no = 0;

        while (rs.next()) {
            no++;
            Beans_laporan_bulanan_kanca dashboard_pencapaian_brilink= new Beans_laporan_bulanan_kanca
            (no,rs.getString("kode_kanwil"),
            rs.getString("nama_kanwil"),
            rs.getString("kode_cabang"),
            rs.getString("nama_cabang"),
            rs.getDouble("total_transaksi_web"),
            rs.getDouble("total_transaksi_edcf"),
            rs.getDouble("total_transaksi_all"),
            rs.getDouble("total_fee_web"),
            rs.getDouble("total_fee_edcf"),
            rs.getDouble("total_fee_all"),
            rs.getDouble("total_nominal_web"),
            rs.getDouble("total_nominal_edcf"),
            rs.getDouble("total_nominal_all"),
            rs.getDouble("edc_trx"),
            rs.getDouble("web_trx"),
            rs.getDouble("trx_all"),
            rs.getDouble("bep_edc"),
            rs.getDouble("bep_web"),
            rs.getDouble("bep_all"),
            rs.getDouble("jumlah_edc"),
            rs.getDouble("jumlah_web"),
            rs.getDouble("jumlah_all"),
            rs.getDouble("jumlah_edc_2017"),
            rs.getDouble("jumlah_web_2017"),
            rs.getDouble("jumlah_all_2017"),
            rs.getDouble("casa_web"),
            rs.getDouble("casa_edc"),
            rs.getDouble("casa_all"));
             dashboard_pencapaian_brilinks.add(dashboard_pencapaian_brilink);
             
             grand_jumlah_edc =  grand_jumlah_edc + dashboard_pencapaian_brilink.getJumlah_edc();
             grand_jumlah_web =  grand_jumlah_web + dashboard_pencapaian_brilink.getJumlah_web();
             grand_jumlah_all =  grand_jumlah_all + dashboard_pencapaian_brilink.getJumlah_all();
             grand_jumlah_all_2017 =  grand_jumlah_all_2017 + dashboard_pencapaian_brilink.getJumlah_all_2017();
           
             
             grand_total_transaksi_web =  grand_total_transaksi_web + dashboard_pencapaian_brilink.getTotal_transaksi_web();
             grand_total_transaksi_edcf =  grand_total_transaksi_edcf + dashboard_pencapaian_brilink.getTotal_transaksi_edcf();
             grand_total_transaksi_all =  grand_total_transaksi_all + dashboard_pencapaian_brilink.getTotal_transaksi_all();
             
             
             grand_total_fee_web =  grand_total_fee_web + dashboard_pencapaian_brilink.getTotal_fee_web();
             grand_total_fee_edcf =  grand_total_fee_edcf + dashboard_pencapaian_brilink.getTotal_fee_edcf();
             grand_total_fee_all =  grand_total_fee_all + dashboard_pencapaian_brilink.getTotal_fee_all();
             
             grand_edc_trx = grand_edc_trx + dashboard_pencapaian_brilink.getEdc_trx();
             grand_web_trx = grand_web_trx + dashboard_pencapaian_brilink.getWeb_trx();
             
             grand_bep_edc =  grand_bep_edc + dashboard_pencapaian_brilink.getBep_edc();
             grand_bep_web =  grand_bep_web + dashboard_pencapaian_brilink.getBep_web();
              
             grand_casa_all =  grand_casa_all + dashboard_pencapaian_brilink.getCasa_all();
             
             
        }
        
        grand_edc_trx = grand_edc_trx / grand_jumlah_edc * 100.0;
        grand_web_trx = grand_web_trx / grand_jumlah_web * 100.0;
        
        
        
        
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }

    public Beans_laporan_bulanan_kanca(int no, String kode_kanwil, String nama_kanwil,String kode_cabang, String nama_cabang, double total_transaksi_web, double total_transaksi_edcf, double total_transaksi_all, double total_fee_web, double total_fee_edcf, double total_fee_all, double total_nominal_web, double total_nominal_edcf, double total_nominal_all, double edc_trx, double web_trx, double trx_all, double bep_edc, double bep_web, double bep_all, double jumlah_edc, double jumlah_web, double jumlah_all, double jumlah_edc_2017, double jumlah_web_2017, double jumlah_all_2017, double casa_web, double casa_edc, double casa_all) {
        this.no = no;
        this.kode_kanwil = kode_kanwil;
        this.nama_kanwil = nama_kanwil;
        this.kode_cabang = kode_cabang;
        this.nama_cabang = nama_cabang;
        this.total_transaksi_web = total_transaksi_web;
        this.total_transaksi_edcf = total_transaksi_edcf;
        this.total_transaksi_all = total_transaksi_all;
        this.total_fee_web = total_fee_web;
        this.total_fee_edcf = total_fee_edcf;
        this.total_fee_all = total_fee_all;
        this.total_nominal_web = total_nominal_web;
        this.total_nominal_edcf = total_nominal_edcf;
        this.total_nominal_all = total_nominal_all;
        this.edc_trx = edc_trx;
        this.web_trx = web_trx;
        this.trx_all = trx_all;
        this.bep_edc = bep_edc;
        this.bep_web = bep_web;
        this.bep_all = bep_all;
        this.jumlah_edc = jumlah_edc;
        this.jumlah_web = jumlah_web;
        this.jumlah_all = jumlah_all;
        this.jumlah_edc_2017 = jumlah_edc_2017;
        this.jumlah_web_2017 = jumlah_web_2017;
        this.jumlah_all_2017 = jumlah_all_2017;
        this.casa_web = casa_web;
        this.casa_edc = casa_edc;
        this.casa_all = casa_all;
       
    }
  
    public Beans_laporan_bulanan_kanca() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    
    
    public String getKode_kanwil() {
        return kode_kanwil;
    }

    public void setKode_kanwil(String kode_kanwil) {
        this.kode_kanwil = kode_kanwil;
    }

    public String getNama_kanwil() {
        return nama_kanwil;
    }

    public void setNama_kanwil(String nama_kanwil) {
        this.nama_kanwil = nama_kanwil;
    }


  

    public double getTotal_transaksi_web() {
        return total_transaksi_web;
    }

    public void setTotal_transaksi_web(double total_transaksi_web) {
        this.total_transaksi_web = total_transaksi_web;
    }

    public double getTotal_transaksi_edcf() {
        return total_transaksi_edcf;
    }

    public void setTotal_transaksi_edcf(double total_transaksi_edcf) {
        this.total_transaksi_edcf = total_transaksi_edcf;
    }

    public double getTotal_transaksi_all() {
        return total_transaksi_all;
    } 
    
     public double getPencapaian_transaksi() {
        return total_transaksi_all / ( jumlah_all * rka_transaksi_perdevice ) * 100.0 ;
    } 

    public void setTotal_transaksi_all(double total_transaksi_all) {
        this.total_transaksi_all = total_transaksi_all;
    }

    public double getTotal_fee_web() {
        return total_fee_web;
    }
    
   

    public void setTotal_fee_web(double total_fee_web) {
        this.total_fee_web = total_fee_web;
    }

    public double getTotal_fee_edcf() {
        return total_fee_edcf;
    }
    
 

    public void setTotal_fee_edcf(double total_fee_edcf) {
        this.total_fee_edcf = total_fee_edcf;
    }

    public double getTotal_fee_all() {
        return total_fee_all;
    }
    
  
     
      public double getPencapaianFBI() {
          double penc = total_fee_all / (jumlah_all * rka_fbi_perdevice) * 100.0;
          return penc;
    }
      
    
  

    public void setTotal_fee_all(double total_fee_all) {
        this.total_fee_all = total_fee_all;
    }

    public double getTotal_nominal_web() {
        return total_nominal_web;
    }

    public void setTotal_nominal_web(double total_nominal_web) {
        this.total_nominal_web = total_nominal_web;
    }

    public double getTotal_nominal_edcf() {
        return total_nominal_edcf;
    }

    public void setTotal_nominal_edcf(double total_nominal_edcf) {
        this.total_nominal_edcf = total_nominal_edcf;
    }

    public double getTotal_nominal_all() {
        return total_nominal_all;
    }

    public void setTotal_nominal_all(double total_nominal_all) {
        this.total_nominal_all = total_nominal_all;
    }

    public double getEdc_trx() {
        return edc_trx;
    }
    
       public double getEdc_trx_persen() {
        return edc_trx / jumlah_edc * 100.0 ;
    }

    public void setEdc_trx(double edc_trx) {
        this.edc_trx = edc_trx;
    }

    public double getWeb_trx() {
        return web_trx;
    }
    
     public double getWeb_trx_persen() {
        return web_trx / jumlah_web * 100.0;
    }

    public void setWeb_trx(double web_trx) {
        this.web_trx = web_trx;
    }

    public double getTrx_all() {
        return trx_all;
    }

    public void setTrx_all(double trx_all) {
        this.trx_all = trx_all;
    }

    public double getBep_edc() {
        return bep_edc;
    }
    
     public double getBep_edc_persen() {
         if (jumlah_edc <= 0) return 100.0;
         
        return bep_edc / jumlah_edc * 100.0;
    }

    public void setBep_edc(double bep_edc) {
        this.bep_edc = bep_edc;
    }

    public double getBep_web() {
        return bep_web;
    }

    public void setBep_web(double bep_web) {
        this.bep_web = bep_web;
    }

    public double getBep_all() {
        return bep_all;
    }

    public void setBep_all(double bep_all) {
        this.bep_all = bep_all;
    }

    public double getJumlah_edc() {
        return jumlah_edc;
    }

    public void setJumlah_edc(double jumlah_edc) {
        this.jumlah_edc = jumlah_edc;
    }

    public double getJumlah_web() {
        return jumlah_web;
    }

    public void setJumlah_web(double jumlah_web) {
        this.jumlah_web = jumlah_web;
    }

    public double getJumlah_all() {
        return jumlah_all;
    }

    public double getPencapaian_jumlah(){
        return (jumlah_all - jumlah_all_2017) ;
    }
    
    public void setJumlah_all(double jumlah_all) {
        this.jumlah_all = jumlah_all;
    }

    public double getJumlah_edc_2017() {
        return jumlah_edc_2017;
    }

    public void setJumlah_edc_2017(double jumlah_edc_2017) {
        this.jumlah_edc_2017 = jumlah_edc_2017;
    }

    public double getJumlah_web_2017() {
        return jumlah_web_2017;
    }

    public void setJumlah_web_2017(double jumlah_web_2017) {
        this.jumlah_web_2017 = jumlah_web_2017;
    }

    public double getJumlah_all_2017() {
        return jumlah_all_2017;
    }

    public void setJumlah_all_2017(double jumlah_all_2017) {
        this.jumlah_all_2017 = jumlah_all_2017;
    }

  

    public double getCasa_web() {
        return casa_web;
    }

    public void setCasa_web(double casa_web) {
        this.casa_web = casa_web;
    }

    public double getCasa_edc() {
        return casa_edc;
    }

    public void setCasa_edc(double casa_edc) {
        this.casa_edc = casa_edc;
    }

    public double getCasa_all() {
        return casa_all;
    }

    public void setCasa_all(double casa_all) {
        this.casa_all = casa_all;
    }

 
    
  
   
 

    private String generateSQLPencapaian(String tgl, String kd_kanwil) {
        
        String sql_pencapaian = "select * from view_pencapaian_kanca_"+tgl;
        
        if (!kd_kanwil.equals("all")){
          sql_pencapaian = sql_pencapaian +" where kode_kanwil = '"+kd_kanwil+"'";
        }
        
        sql_pencapaian = sql_pencapaian +" order by kode_kanwil";
        
        return sql_pencapaian;
    }
    
    
  
      
    public double getGrand_jumlah_edc() {
        return grand_jumlah_edc;
    }

    public void setGrand_jumlah_edc(double grand_jumlah_edc) {
        this.grand_jumlah_edc = grand_jumlah_edc;
    }

    public double getGrand_jumlah_web() {
        return grand_jumlah_web;
    }

    public void setGrand_jumlah_web(double grand_jumlah_web) {
        this.grand_jumlah_web = grand_jumlah_web;
    }

    public double getGrand_jumlah_all() {
        return grand_jumlah_all;
    }

    public void setGrand_jumlah_all(double grand_jumlah_all) {
        this.grand_jumlah_all = grand_jumlah_all;
    }

    public double getGrand_total_transaksi_web() {
        return grand_total_transaksi_web;
    }

    public double getGrand_total_transaksi_edcf() {
        return grand_total_transaksi_edcf;
    }

    public double getGrand_total_transaksi_all() {
        return grand_total_transaksi_all;
    }

    public double getGrand_total_fee_web() {
        return grand_total_fee_web;
    }

    public double getGrand_total_fee_edcf() {
        return grand_total_fee_edcf;
    }

    public double getGrand_total_fee_all() {
        return grand_total_fee_all;
    }

    public double getGrand_edc_trx() {
        return grand_edc_trx;
    }

    public double getGrand_web_trx() {
        return grand_web_trx;
    }

    public double getGrand_trx_all() {
        return grand_trx_all;
    }

    public double getGrand_bep_edc() {
        return grand_bep_edc;
    }

    public double getGrand_bep_web() {
        return grand_bep_web;
    }

    public double getGrand_bep_all() {
        return grand_bep_all;
    }

    public double getGrand_casa_web() {
        return grand_casa_web;
    }

    public double getGrand_casa_edc() {
        return grand_casa_edc;
    }

    public double getGrand_casa_all() {
        return grand_casa_all;
    }

  

  

    public double getGrand_pencapaian_jumlah() {
        return grand_jumlah_all - grand_jumlah_all_2017 ;
    }

    public double getGrand_pencapaian_fbi() {
       return grand_total_fee_all / (grand_jumlah_all * rka_fbi_perdevice) * 100.0 ;
    }

    public double getGrand_pencapaian_trx() {
       return grand_total_transaksi_all / (grand_jumlah_all * rka_transaksi_perdevice) * 100.0 ;
    }

    public String getKode_cabang() {
        return kode_cabang;
    }

    public void setKode_cabang(String kode_cabang) {
        this.kode_cabang = kode_cabang;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }
      
    public double getGrand_bep_edc_persen() {
        return grand_bep_edc / grand_jumlah_edc * 100.0;
    }

    public double getGrand_bep_web_persen() {
        return grand_bep_web_persen / grand_jumlah_web * 100.0;
    }

    public double getGrand_bep_web_all() {
        return grand_bep_web_all / grand_jumlah_all * 100.0;
    }
      
      

}

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
public class Beans_laporan_bulanan_kanwil {

    int no;
    String kode_kanwil;
    String nama_kanwil;
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
    double edc_trx_count;
    double web_trx;
    double web_trx_count;
    double trx_all;
    double bep_edc;
    double bep_edc_count;
    double bep_web;
    double bep_web_count;
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
  
    double rka_jumlah;
    double rka_fbi;
    double rka_trx;
    double rka_casa;
    
    
    double grand_jumlah_edc =0.0;
    double grand_jumlah_web = 0.0;
    double grand_jumlah_all = 0.0;
    double grand_jumlah_all_2017 = 0.0;
    
    double grand_total_transaksi_web;
    double grand_total_transaksi_edcf;
    double grand_total_transaksi_all;

    double grand_total_fee_web;
    double grand_total_fee_edcf;
    double grand_total_fee_all;
    
    double grand_edc_trx;
    double grand_edc_trx_count;
    double grand_web_trx;
    double grand_web_trx_count;
    double grand_trx_all;
    
    double grand_bep_edc;
    double grand_bep_edc_count;
    double grand_bep_web;
    double grand_bep_web_count;
    double grand_bep_all;
    
   
    double grand_casa_web;
    double grand_casa_edc;
    double grand_casa_all;
    
    double grand_rka_jumlah;
    double grand_rka_fbi;
    double grand_rka_trx;
    double grand_rka_casa;
  
    
    Db1 db;
    J2EEConnectionPool connectionPool;


    public Collection<Beans_laporan_bulanan_kanwil> getData(String tgl) throws SQLException {
        Collection<Beans_laporan_bulanan_kanwil> dashboard_pencapaian_brilinks = new ArrayList<Beans_laporan_bulanan_kanwil>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaianKanwil(tgl));
        int no = 0;

        while (rs.next()) {
            no++;
            Beans_laporan_bulanan_kanwil dashboard_pencapaian_brilink= new Beans_laporan_bulanan_kanwil
            (no,rs.getString("kode_kanwil"),
            rs.getString("nama_kanwil"),
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
            rs.getDouble("edc_trx_count"),
            rs.getDouble("web_trx"),
            rs.getDouble("web_trx_count"),
            rs.getDouble("trx_all"),
            rs.getDouble("bep_edc"),
            rs.getDouble("bep_edc_count"),        
            rs.getDouble("bep_web"),
            rs.getDouble("bep_web_count"),        
            rs.getDouble("bep_all"),
            rs.getDouble("jumlah_edc"),
            rs.getDouble("jumlah_web"),
            rs.getDouble("jumlah_all"),
            rs.getDouble("jumlah_edc_2017"),
            rs.getDouble("jumlah_web_2017"),
            rs.getDouble("jumlah_all_2017"),
            rs.getDouble("casa_web"),
            rs.getDouble("casa_edc"),
            rs.getDouble("casa_all"), 
            rs.getDouble("rka_jumlah") ,
            rs.getDouble("rka_fbi"),
            rs.getDouble("rka_trx"),
             rs.getDouble("rka_casa"));
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
             grand_edc_trx_count = grand_edc_trx_count + dashboard_pencapaian_brilink.getEdc_trx_count();
             grand_web_trx = grand_web_trx + dashboard_pencapaian_brilink.getWeb_trx();
             grand_web_trx_count = grand_web_trx_count + dashboard_pencapaian_brilink.getWeb_trx_count();
             grand_trx_all = grand_trx_all + dashboard_pencapaian_brilink.getTrx_all();
             
             grand_bep_edc =  grand_bep_edc + dashboard_pencapaian_brilink.getBep_edc();
             grand_bep_edc_count = grand_bep_edc_count + dashboard_pencapaian_brilink.getBep_edc_count();
             grand_bep_web =  grand_bep_web + dashboard_pencapaian_brilink.getBep_web();
             grand_bep_web_count = grand_bep_web_count + dashboard_pencapaian_brilink.getBep_web_count();
             grand_bep_all = grand_bep_all + dashboard_pencapaian_brilink.getBep_all();
             
             grand_casa_all = grand_casa_all + dashboard_pencapaian_brilink.getCasa_all();
             grand_rka_jumlah  =  grand_rka_jumlah + dashboard_pencapaian_brilink.getRka_jumlah();
             grand_rka_fbi =  grand_rka_fbi + dashboard_pencapaian_brilink.getRka_fbi();
             grand_rka_trx =  grand_rka_trx + dashboard_pencapaian_brilink.getRka_trx();
             grand_rka_casa =  grand_rka_casa + dashboard_pencapaian_brilink.getRka_casa();
            
        }
        
       
       
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }

    public Beans_laporan_bulanan_kanwil(int no, String kode_kanwil, String nama_kanwil, double total_transaksi_web, double total_transaksi_edcf, double total_transaksi_all, double total_fee_web, double total_fee_edcf, double total_fee_all, double total_nominal_web, double total_nominal_edcf, double total_nominal_all, 
            double edc_trx, double edc_trx_count, double web_trx, double web_trx_count, double trx_all, double bep_edc,double bep_edc_count, double bep_web, double bep_web_count, double bep_all, double jumlah_edc, double jumlah_web, double jumlah_all, double jumlah_edc_2017, double jumlah_web_2017, double jumlah_all_2017, double casa_web, double casa_edc, double casa_all, double rka_jumlah, double rka_fbi, double rka_trx, double rka_casa) {
        this.no = no;
        this.kode_kanwil = kode_kanwil;
        this.nama_kanwil = nama_kanwil;
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
        this.edc_trx_count = edc_trx_count;
        this.web_trx = web_trx;
        this.web_trx_count = web_trx_count;
        this.trx_all = trx_all;
        this.bep_edc = bep_edc;
        this.bep_edc_count = bep_edc_count;
        this.bep_web = bep_web;
        this.bep_web_count = bep_web_count;
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
        this.rka_jumlah = rka_jumlah;
        this.rka_fbi = rka_fbi;
        this.rka_trx = rka_trx;
        this.rka_casa = rka_casa;
    }
  
    public Beans_laporan_bulanan_kanwil() {
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
        return total_transaksi_all / rka_trx * 100.0;
    } 
     
      public double getPencapaian_bep() {
        return ( bep_all / ( bep_edc_count + bep_web_count) ) * 100.0 * 2.0 ;
    } 
      
         public double getPencapaian_bertrx() {
         return trx_all / ( edc_trx_count + web_trx_count) * 100.0  ;
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
          double penc = total_fee_all / rka_fbi * 100.0;
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
        return edc_trx  ;
    }

    public void setEdc_trx(double edc_trx) {
        this.edc_trx = edc_trx;
    }

    public double getWeb_trx() {
        return web_trx;
    }
    
     public double getWeb_trx_persen() {
        return web_trx ;
    }

    public void setWeb_trx(double web_trx) {
        this.web_trx = web_trx;
    }

    public double getTrx_all() {
        return trx_all;
    }
    
    public double getTrx_All_persen() {
        return trx_all ;
    }

    public void setTrx_all(double trx_all) {
        this.trx_all = trx_all;
    }

    public double getBep_edc() {
        return bep_edc;
    }
    
     public double getBep_edc_persen() {
        return bep_edc ;
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
        return jumlah_all  / rka_jumlah * 100.0;
    }
    
    public double getPencapaian_casa(){
        return casa_all  / rka_casa * 100.0;
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

 public double getRka_jumlah() {
        return rka_jumlah;
    }

    public void setRka_jumlah(double rka_jumlah) {
        this.rka_jumlah = rka_jumlah;
    }

    public double getRka_fbi() {
        return rka_fbi;
    }
    
  
    public void setRka_fbi(double rka_fbi) {
        this.rka_fbi = rka_fbi;
    }

    public double getRka_trx() {
        return rka_trx;
    }
    
 

    public void setRka_trx(double rka_trx) {
        this.rka_trx = rka_trx;
    }

    public double getRka_casa() {
        return rka_casa;
    }

    public void setRka_casa(double rka_casa) {
        this.rka_casa = rka_casa;
    }

    public double getGrand_rka_casa() {
        return grand_rka_casa;
    }

    public void setGrand_rka_casa(double grand_rka_casa) {
        this.grand_rka_casa = grand_rka_casa;
    }
   
 

    
    
    private String generateSQLPencapaianKanwil(String tgl){
        String sql_pencapaian = "select penc.*, rj."+tgl+" as rka_jumlah ,rf."+tgl+" as rka_fbi,rt."+tgl+" as rka_trx ,rc."+tgl+" as rka_casa from\n" +
                        "\n" +
                        "(\n" +
                        "select \n" +
                        "kode_kanwil, nama_kanwil,\n" +
                        "sum(total_transaksi_web) as total_transaksi_web,\n" +
                        "sum(total_transaksi_edcf) as total_transaksi_edcf,\n" +
                        "sum(total_transaksi_all) as total_transaksi_all,\n" +
                        "sum(total_fee_web) as total_fee_web,\n" +
                        "sum(total_fee_edcf) as total_fee_edcf,\n" +
                        "sum(total_fee_all) as total_fee_all,\n" +
                        "sum(total_nominal_web) as total_nominal_web,\n" +
                        "sum(total_nominal_edcf) as total_nominal_edcf,\n" +
                        "sum(total_nominal_all) as total_nominal_all,\n" +
                        "sum(edc_trx)  as edc_trx,\n" +
                        "sum(edc_trx_count)  as edc_trx_count,\n" +
                        "sum(web_trx)  as web_trx,\n" +
                        "sum(web_trx_count)  as web_trx_count,\n" +
                        "sum(trx_all) as trx_all,\n" +
                        "sum(bep_edc)  as bep_edc,\n" +
                        "sum(bep_edc_count)  as bep_edc_count,\n" +
                        "sum(bep_web) as bep_web,\n" +
                        "sum(bep_web_count) as bep_web_count,\n" +
                        "sum(bep_all)  as bep_all,\n" +
                        "sum(jumlah_edc) as jumlah_edc,\n" +
                        "sum(jumlah_web) as jumlah_web,\n" +
                        "sum(jumlah_all) as jumlah_all,\n" +
                        "sum(jumlah_edc_2017) as jumlah_edc_2017,\n" +
                        "sum(jumlah_web_2017) as jumlah_web_2017,\n" +
                        "sum(jumlah_all_2017) as jumlah_all_2017,\n" +
                        "          \n" +
                        "sum(casa_web) as casa_web,\n" +
                        "sum(casa_edc) as casa_edc,\n" +
                        "sum(casa_all) as casa_all\n" +
                        "              \n" +
                        " from view_pencapaian_kanca_"+tgl+
                        " group by kode_kanwil, nama_kanwil\n" +
                        ") penc \n" +
                        "inner join rka_jumlah rj\n" +
                        "on (penc.kode_kanwil = rj.kd_kanwil)\n" +
                        "inner join rka_fbi rf\n" +
                        "on (penc.kode_kanwil = rf.kd_kanwil)\n" +
                        "inner join rka_trxfin rt\n" +
                        "on (penc.kode_kanwil = rt.kd_kanwil)"+
                        "inner join rka_casa rc\n" +
                        "on (penc.kode_kanwil = rc.kd_kanwil)";
        
        System.out.println(sql_pencapaian);
        return sql_pencapaian;
    }
    
      public Collection<String> getListViewPencapaian() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select table_name from information_schema.tables where table_name like 'view_pencapaian_kanca_%' "
+ "order by table_name desc";
        ResultSet rs_1 = st_1.executeQuery(sql_1);
        
        ArrayList<String> array;
        array = new ArrayList<>();
        while (rs_1.next()){
            array.add(rs_1.getString(1).replaceAll("view_pencapaian_kanca_", ""));
        }
        
        con_1.close();
        connectionPool.releaseConnection(con_1);
       
        return array;
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

    public double getGrand_rka_jumlah() {
        return grand_rka_jumlah;
    }

    public double getGrand_rka_fbi() {
        return grand_rka_fbi;
    }

    public double getGrand_rka_trx() {
        return grand_rka_trx;
    }

    public double getGrand_pencapaian_jumlah() {
        return grand_jumlah_all  / grand_rka_jumlah * 100.0;
    }

    public double getGrand_pencapaian_fbi() {
       return grand_total_fee_all / grand_rka_fbi * 100.0;
    }

    public double getGrand_pencapaian_trx() {
       return (grand_total_transaksi_all - grand_jumlah_all_2017) / grand_rka_trx * 100.0;
    }

    public double getGrand_pencapaian_bertrxall(){
     return grand_trx_all / (grand_edc_trx_count + grand_web_trx_count) * 100.0  ;
    }
    
    public double getGrand_pencapaian_bepall(){
      return grand_bep_all / (grand_bep_edc_count + grand_bep_web_count) * 100.0 * 2.0;
    }
       
    public double getGrand_pencapaian_casa(){
      return grand_casa_all / grand_rka_casa * 100.0;
    }
       
    public double getGrand_bep_edc_persen() {
        return grand_bep_edc ;
    }

    public double getGrand_bep_web_persen() {
        return grand_bep_web ;
    }

    public double getGrand_bep_all_persen() {
        return grand_bep_all ;
    }

    public double getEdc_trx_count() {
        return edc_trx_count;
    }

    public double getWeb_trx_count() {
        return web_trx_count;
    }

    public double getBep_edc_count() {
        return bep_edc_count;
    }

    public double getBep_web_count() {
        return bep_web_count;
    }

    public double getGrand_edc_trx_count() {
        return grand_edc_trx_count;
    }

    public double getGrand_web_trx_count() {
        return grand_web_trx_count;
    }

    public double getGrand_bep_edc_count() {
        return grand_bep_edc_count;
    }

    public double getGrand_bep_web_count() {
        return grand_bep_web_count;
    }
      
    
      

}

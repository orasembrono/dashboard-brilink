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
import java.text.DecimalFormat;

/**
 *
 * @author IVAN
 */
public class Beans_laporan_harian_kanca {

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
    
   
    double grand_total_transaksi_web;
    double grand_total_transaksi_edcf;
    double grand_total_transaksi_all;

    double grand_total_fee_web;
    double grand_total_fee_edcf;
    double grand_total_fee_all;
    
    double grand_total_nominal_web;
    double grand_total_nominal_edcf;
    double grand_total_nominal_all;
    
   
    
  
    
    Db1 db;
    J2EEConnectionPool connectionPool;


    public Collection<Beans_laporan_harian_kanca> getData(String tgl, String kanwil) throws SQLException {
        Collection<Beans_laporan_harian_kanca>  laporan_harian_kanca = new ArrayList<Beans_laporan_harian_kanca>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaian(tgl,kanwil));
        int no = 0;

        while (rs.next()) {
            no++;
            Beans_laporan_harian_kanca dashboard_pencapaian_brilink= new Beans_laporan_harian_kanca
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
            rs.getDouble("total_nominal_all"));
             laporan_harian_kanca.add(dashboard_pencapaian_brilink);
             
            
             grand_total_transaksi_web =  grand_total_transaksi_web + dashboard_pencapaian_brilink.getTotal_transaksi_web();
             grand_total_transaksi_edcf =  grand_total_transaksi_edcf + dashboard_pencapaian_brilink.getTotal_transaksi_edcf();
             grand_total_transaksi_all =  grand_total_transaksi_all + dashboard_pencapaian_brilink.getTotal_transaksi_all();
             
             
             grand_total_fee_web =  grand_total_fee_web + dashboard_pencapaian_brilink.getTotal_fee_web();
             grand_total_fee_edcf =  grand_total_fee_edcf + dashboard_pencapaian_brilink.getTotal_fee_edcf();
             grand_total_fee_all =  grand_total_fee_all + dashboard_pencapaian_brilink.getTotal_fee_all();
             
             grand_total_nominal_web =  grand_total_nominal_web + dashboard_pencapaian_brilink.getTotal_nominal_web();
             grand_total_nominal_edcf =  grand_total_nominal_edcf + dashboard_pencapaian_brilink.getTotal_nominal_edcf();
             grand_total_nominal_all =  grand_total_nominal_all + dashboard_pencapaian_brilink.getTotal_nominal_all();
           
            
          
        }
        
      
        
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return laporan_harian_kanca;
    }
    
    
   

    public Beans_laporan_harian_kanca(int no, String kode_kanwil, String nama_kanwil,String kode_cabang, String nama_cabang, double total_transaksi_web, double total_transaksi_edcf, double total_transaksi_all, double total_fee_web, double total_fee_edcf, double total_fee_all,
            double total_nominal_web, double total_nominal_edcf, double total_nominal_all) {
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
        
       
    }
  
    public Beans_laporan_harian_kanca() {
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

 

    private String generateSQLPencapaian(String tgl, String kd_kanwil) {
        int mm = Integer.valueOf(tgl.substring(4,6));
        int tahun = Integer.valueOf(tgl.substring(0,4));
        String tgl_rka = tgl.substring(0,6);
        // get keragaan before, to sum all 
        if (mm <= 1 ){
            mm = 13;
            tahun--;
        }
        
        DecimalFormat format_decimal_percent = new DecimalFormat( "00" );  
        String tgl_before_full_download = String.valueOf(tahun) + format_decimal_percent.format(mm - 1);
        
        StringBuilder sql = new  StringBuilder("select \n" +
                "a.kode_kanwil, replace(a.nama_kanwil,'KANWIL ','') as nama_kanwil, a.kode_cabang, a.nama_cabang ,  \n" +
                "ifnull(b.total_transaksi_web,0) + ifnull(pos_lalu.total_transaksi_web,0) as total_transaksi_web, \n" +
                " ifnull(c.total_transaksi_edcf,0) + ifnull(pos_lalu.total_transaksi_edcf,0)  as total_transaksi_edcf ,\n" +
                " ifnull(b.total_transaksi_web,0) + ifnull(pos_lalu.total_transaksi_web,0)  + ifnull(c.total_transaksi_edcf,0) + ifnull(pos_lalu.total_transaksi_edcf,0) as total_transaksi_all,\n" +
                " ifnull(b.total_nominal_web,0) + ifnull(pos_lalu.total_nominal_web ,0) as total_nominal_web,\n" +
                " ifnull(c.total_nominal_edcf,0) +  ifnull(pos_lalu.total_nominal_edcf,0)  as  total_nominal_edcf, \n" +
                "ifnull(b.total_nominal_web,0) + ifnull(pos_lalu.total_nominal_web,0) + ifnull(c.total_nominal_edcf,0)+  ifnull(pos_lalu.total_nominal_edcf,0) as total_nominal_all,\n" +
                " ifnull( b.total_fee_web,0) +   ifnull(pos_lalu.total_fee_web,0) as total_fee_web, \n" +
                "  ifnull(c.total_fee_edcf,0) + ifnull(pos_lalu.total_fee_edcf,0) as total_fee_edcf,\n" +
                " ifnull(b.total_fee_web,0) +  ifnull( pos_lalu.total_fee_web,0)+   ifnull(c.total_fee_edcf,0) + ifnull(pos_lalu.total_fee_edcf,0) as total_fee_all\n" +
                " from (\n" +
                "select kode_kanwil, nama_kanwil, kode_cabang, nama_cabang  from dwh_branch where nama_uker1 not like 'VENDOR%' and TIPE_KANTOR = 'CABANG'  and kode_kanwil  not in ('S','T','U','V','Y')\n" +
                ") a\n" +
                "left outer join\n" +
                "(\n" +
                "select  kd_kanwil, kanwil,  kd_cabang , cabang, sum(ifnull(total_transaksi,0)) as total_transaksi_web, sum(replace(ifnull(total_nominal,0),',','')) as total_nominal_web \n" +
                "				, sum(replace(ifnull(total_fee_bri,0),',','')) as total_fee_web  from   trx_web_"+tgl+"						\n" +
                "				 group by kd_kanwil, kd_cabang , kanwil,  kd_cabang , cabang\n" +
                ") b on (a.kode_cabang = kd_cabang)				 \n" +
                "left outer join\n" +
                "(			 \n" +
                "select kd_cabang , cabang, sum( ifnull(total_transaksi,0) ) as total_transaksi_edcf, sum(replace(total_nominal,',','')) as total_nominal_edcf,\n" +
                "				 sum(replace(total_fee_bri,',','')) as total_fee_edcf   from trx_edc_fin_"+tgl+"				\n" +
                "				 group by kd_cabang ,  cabang\n" +
                ") c on (a.kode_cabang = c.kd_cabang)			 				 				 \n" +
                " inner join \n" +
                "(\n" +
                "select kode_kanwil, nama_kanwil, kode_cabang, nama_cabang, sum(total_transaksi_web) as total_transaksi_web , \n" +
                "sum(total_nominal_web) total_nominal_web,  sum(total_transaksi_edcf) as total_transaksi_edcf ,\n" +
                "sum(total_nominal_edcf) as total_nominal_edcf, sum(total_fee_web) as total_fee_web, sum(total_fee_edcf) as total_fee_edcf  \n" +
                "from view_pencapaian_kanca_201801\n" +
                "group by kode_kanwil, nama_kanwil	, kode_cabang, nama_cabang\n" +
                ")  pos_lalu\n" +
                "on (a.kode_cabang = pos_lalu.kode_cabang)");

        if (!kd_kanwil.equalsIgnoreCase("all")){
            sql.append(" where a.kode_kanwil = '"+kd_kanwil+"'" ); 
        }
        
            sql.append(" order by a.kode_kanwil");
        System.out.println(sql);
        
        return sql.toString();
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

   
    public double getGrand_total_nominal_web() {
        return grand_total_nominal_web;
    }

    public void setGrand_total_nominal_web(double grand_total_nominal_web) {
        this.grand_total_nominal_web = grand_total_nominal_web;
    }

    public double getGrand_total_nominal_edcf() {
        return grand_total_nominal_edcf;
    }

    public void setGrand_total_nominal_edcf(double grand_total_nominal_edcf) {
        this.grand_total_nominal_edcf = grand_total_nominal_edcf;
    }

    public double getGrand_total_nominal_all() {
        return grand_total_nominal_all;
    }

    public void setGrand_total_nominal_all(double grand_total_nominal_all) {
        this.grand_total_nominal_all = grand_total_nominal_all;
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
      
    
      

}

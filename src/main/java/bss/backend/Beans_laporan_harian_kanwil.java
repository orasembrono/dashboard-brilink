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
public class Beans_laporan_harian_kanwil {

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
    double web_trx;
    double trx_all;
    double rka_jumlah;
    double rka_fbi;
    double rka_trx;
    
    
   
    
    double grand_total_transaksi_web;
    double grand_total_transaksi_edcf;
    double grand_total_transaksi_all;

    double grand_total_fee_web;
    double grand_total_fee_edcf;
    double grand_total_fee_all;
    
    double grand_total_nominal_web;
    double grand_total_nominal_edcf;
    double grand_total_nominal_all;
     
    double grand_rka_jumlah;
    double grand_rka_fbi;
    double grand_rka_trx;
  
    
    Db1 db;
    J2EEConnectionPool connectionPool;


    public Collection<Beans_laporan_harian_kanwil> getData(String tgl) throws SQLException {
        Collection<Beans_laporan_harian_kanwil> dashboard_pencapaian_brilinks = new ArrayList<>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaianKanwil(tgl));
        int i; 
        i =0;

        while (rs.next()) {
            i++;
            Beans_laporan_harian_kanwil dashboard_pencapaian_brilink= new Beans_laporan_harian_kanwil
            (i,rs.getString("kode_kanwil"),
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
            rs.getDouble("rka_fbi"),
            rs.getDouble("rka_trx"));
             dashboard_pencapaian_brilinks.add(dashboard_pencapaian_brilink);
             
            
             
             grand_total_transaksi_web =  grand_total_transaksi_web + dashboard_pencapaian_brilink.getTotal_transaksi_web();
             grand_total_transaksi_edcf =  grand_total_transaksi_edcf + dashboard_pencapaian_brilink.getTotal_transaksi_edcf();
             grand_total_transaksi_all =  grand_total_transaksi_all + dashboard_pencapaian_brilink.getTotal_transaksi_all();
             
             
             grand_total_fee_web =  grand_total_fee_web + dashboard_pencapaian_brilink.getTotal_fee_web();
             grand_total_fee_edcf =  grand_total_fee_edcf + dashboard_pencapaian_brilink.getTotal_fee_edcf();
             grand_total_fee_all =  grand_total_fee_all + dashboard_pencapaian_brilink.getTotal_fee_all();
             
             grand_total_nominal_web =  grand_total_nominal_web + dashboard_pencapaian_brilink.getTotal_nominal_web();
             grand_total_nominal_edcf =  grand_total_nominal_edcf + dashboard_pencapaian_brilink.getTotal_nominal_edcf();
             grand_total_nominal_all =  grand_total_nominal_all + dashboard_pencapaian_brilink.getTotal_nominal_all();
            
                       
             grand_rka_jumlah  =  grand_rka_jumlah + dashboard_pencapaian_brilink.getRka_jumlah();
             grand_rka_fbi =  grand_rka_fbi + dashboard_pencapaian_brilink.getRka_fbi();
             grand_rka_trx =  grand_rka_trx + dashboard_pencapaian_brilink.getRka_trx();
            
        }
        
     
        
        
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }

    public Beans_laporan_harian_kanwil(int no, String kode_kanwil, String nama_kanwil, double total_transaksi_web, double total_transaksi_edcf, double total_transaksi_all, double total_fee_web, double total_fee_edcf, double total_fee_all, double total_nominal_web, double total_nominal_edcf, double total_nominal_all,
              double rka_fbi, double rka_trx) {
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
        this.rka_fbi = rka_fbi;
        this.rka_trx = rka_trx;
    }
  
    public Beans_laporan_harian_kanwil() {
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
   
 

    
    
    private String generateSQLPencapaianKanwil(String tgl){
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
        
        
        
        
        String sql_pencapaian = "select a1.kode_kanwil, a1.nama_kanwil, " +
                        " a1.total_transaksi_web + pos_lalu.total_transaksi_web as total_transaksi_web,  " +
                        " a1.total_transaksi_edcf + pos_lalu.total_transaksi_edcf  as total_transaksi_edcf , " +
                        "  a1.total_transaksi_web + pos_lalu.total_transaksi_web  + a1.total_transaksi_edcf + pos_lalu.total_transaksi_edcf as total_transaksi_all, " +
                        "   trx."+tgl_rka+" as rka_trx, " +
                        " a1.total_nominal_web + pos_lalu.total_nominal_web as total_nominal_web, " +
                        " a1.total_nominal_edcf +  pos_lalu.total_nominal_edcf  as  total_nominal_edcf,  " +
                        "a1.total_nominal_web + pos_lalu.total_nominal_web + a1.total_nominal_edcf +  pos_lalu.total_nominal_edcf as total_nominal_all, " +
                        "  a1.total_fee_web +   pos_lalu.total_fee_web as total_fee_web,  " +
                        "  a1.total_fee_edcf + pos_lalu.total_fee_edcf as total_fee_edcf, " +
                        " a1.total_fee_web +   pos_lalu.total_fee_web+   a1.total_fee_edcf + pos_lalu.total_fee_edcf as total_fee_all, " +
                        "  fbi."+tgl_rka+" as rka_fbi " +
                        "   from ( " +
                        "select kode_kanwil, nama_kanwil, sum(total_transaksi_web) as total_transaksi_web ,  " +
                        "sum(total_nominal_web) total_nominal_web,  sum(total_transaksi_edcf) as total_transaksi_edcf , " +
                        "sum(total_nominal_edcf) as total_nominal_edcf, sum(total_fee_web) as total_fee_web, sum(total_fee_edcf) as total_fee_edcf from ( " +
                        " " +
                        "select  " +
                        "a.kode_kanwil, replace(a.nama_kanwil,'KANWIL ','') as nama_kanwil, a.kode_cabang, a.nama_cabang ,   " +
                        "ifnull(total_transaksi_web,0) as total_transaksi_web ,  ifnull(total_transaksi_edcf,0) as total_transaksi_edcf , " +
                        "IFNULL(total_nominal_web,0) as total_nominal_web,  " +
                        "ifnull(total_nominal_edcf,0) as total_nominal_edcf, ifnull(total_fee_web,0) as total_fee_web , IFNULL(total_fee_edcf,0) as total_fee_edcf " +
                        " from ( " +
                       "select kode_kanwil, nama_kanwil, kode_cabang, nama_cabang  from dwh_branch where nama_uker1 not like 'VENDOR%' and TIPE_KANTOR = 'CABANG'  and kode_kanwil  not in ('S','T','U','V','Y') " +
                        ") a " +
                        "left outer join " +
                        "( " +
                        "select  kd_kanwil, kanwil,  kd_cabang , cabang, sum(ifnull(total_transaksi,0)) as total_transaksi_web, sum(replace(ifnull(total_nominal,0),',','')) as total_nominal_web  " +
                        "				, sum(replace(ifnull(total_fee_bri,0),',','')) as total_fee_web  from   trx_web_"+tgl+ 
                        " group by kd_kanwil, kd_cabang , kanwil,  kd_cabang , cabang " +
                        ") b on (a.kode_cabang = kd_cabang)				  " +
                        "left outer join " +
                        "(			  " +
                        "select kd_cabang , cabang, sum( ifnull(total_transaksi,0) ) as total_transaksi_edcf, sum(replace(total_nominal,',','')) as total_nominal_edcf, " +
                        "				 sum(replace(total_fee_bri,',','')) as total_fee_edcf   from trx_edc_fin_"+tgl+" " +
                        "				 group by kd_cabang ,  cabang " +
                        ") c on (a.kode_cabang = c.kd_cabang) " +
                        ") x group by kode_kanwil, nama_kanwil				 				  " +
                        "				  " +
                        ") a1 inner join rka_fbi fbi " +
                        "on (a1.kode_kanwil = fbi.kd_kanwil)	 " +
                        "inner join rka_trxfin trx " +
                        "on (a1.kode_kanwil = trx.kd_kanwil) " +
                        "inner join  " +
                        "( " ;
        
                        String add_above_jan ="select kode_kanwil, nama_kanwil, sum(total_transaksi_web) as total_transaksi_web ,  " +
                        " sum(total_nominal_web) total_nominal_web,  sum(total_transaksi_edcf) as total_transaksi_edcf , " +
                        " sum(total_nominal_edcf) as total_nominal_edcf, sum(total_fee_web) as total_fee_web, sum(total_fee_edcf) as total_fee_edcf  " +
                        " from view_pencapaian_kanca_"+tgl_before_full_download+" "+
                        " group by kode_kanwil, nama_kanwil	 " +
                        " )  pos_lalu " +
                        " on (a1.kode_kanwil = pos_lalu.kode_kanwil)";
                        
                         String add_jan ="select kode_kanwil, nama_kanwil, '0' as total_transaksi_web ,  " +
                        " '0' as total_nominal_web,  '0' as total_transaksi_edcf , " +
                        " '0' as total_nominal_edcf, '0' as total_fee_web, '0' as total_fee_edcf  " +
                        " from view_pencapaian_kanca_"+tgl_before_full_download+" "+
                        " group by kode_kanwil, nama_kanwil	 " +
                        " )  pos_lalu " +
                        " on (a1.kode_kanwil = pos_lalu.kode_kanwil)";

        if (tgl.startsWith("201801")){
            sql_pencapaian = sql_pencapaian + add_jan;
        }else{
            sql_pencapaian = sql_pencapaian + add_above_jan;
        }
                
        System.out.println(sql_pencapaian);
        return sql_pencapaian;
    }
    
      public Collection<String> getListDateReady() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select  replace(a.table_name,'trx_web_','') as table_name from information_schema.tables a" +
                        " inner join " +
                        " information_schema.tables b " +
                        " on ( replace(a.table_name,'trx_web_','')  =  replace(b.table_name,'trx_edc_fin_','') )" +
                        " where  a.table_name like 'trx_web_%' " +
                        " and b.table_name like 'trx_edc_fin%' and length(a.table_name) >= 16  "  +
                        " order by table_name desc ";
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

  

    public double getGrand_rka_jumlah() {
        return grand_rka_jumlah;
    }

    public double getGrand_rka_fbi() {
        return grand_rka_fbi;
    }

    public double getGrand_rka_trx() {
        return grand_rka_trx;
    }

   
    public double getGrand_pencapaian_fbi() {
       return grand_total_fee_all / grand_rka_fbi * 100.0;
    }

    public double getGrand_pencapaian_trx() {
       return (grand_total_transaksi_all ) / grand_rka_trx * 100.0;
    }
      
    
      

}

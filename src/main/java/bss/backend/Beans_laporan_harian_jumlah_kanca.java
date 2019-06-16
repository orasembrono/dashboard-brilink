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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author IVAN
 */
public class Beans_laporan_harian_jumlah_kanca {

    int no;
    String kode_kanwil;
    String nama_kanwil;
    String nama_kanca;
    
    double jumlah_web;
    double past_jumlah_web;
    double jumlah_web_delta;
    double jumlah_edc;
    double past_jumlah_edc;
    double jumlah_edc_delta;
    double total_all;
    double total_delta;
  
   
    
    double grand_jumlah_web;
    double grand_past_jumlah_web;
    double grand_jumlah_web_delta;

    double grand_jumlah_edc;
    double grand_past_jumlah_edc;
    double grand_jumlah_edc_delta;
    
    double grand_total_delta;
   
    double grand_total_all;
    
    Db1 db;
    J2EEConnectionPool connectionPool;


    public Collection<Beans_laporan_harian_jumlah_kanca> getData(String tgl, String kanwil) throws SQLException {
        Collection<Beans_laporan_harian_jumlah_kanca> dashboard_pencapaian_brilinks = new ArrayList<>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaian(tgl,kanwil));
        int i; 
        i =0;

    
        while (rs.next()) {
            i++;
            Beans_laporan_harian_jumlah_kanca dashboard_pencapaian_brilink= new Beans_laporan_harian_jumlah_kanca
            (i,rs.getString("kode_kanwil"),
            rs.getString("nama_kanwil"),
            rs.getString("nama_cabang"),
            rs.getDouble("jumlah_web"),
            rs.getDouble("past_jumlah_web"),
            rs.getDouble("jumlah_web_delta"),
            rs.getDouble("jumlah_edc"),
            rs.getDouble("past_jumlah_edc"),
            rs.getDouble("jumlah_edc_delta"),
            rs.getDouble("total_delta"));
             dashboard_pencapaian_brilinks.add(dashboard_pencapaian_brilink);
          
             
             grand_jumlah_web =  grand_jumlah_web + dashboard_pencapaian_brilink.getJumlah_web();
             grand_past_jumlah_web =  grand_past_jumlah_web + dashboard_pencapaian_brilink.getPast_jumlah_web();
             grand_jumlah_web_delta =  grand_jumlah_web_delta + dashboard_pencapaian_brilink.getJumlah_web_delta();
             
             
             grand_jumlah_edc =  grand_jumlah_edc + dashboard_pencapaian_brilink.getJumlah_edc();
             grand_past_jumlah_edc =  grand_past_jumlah_edc + dashboard_pencapaian_brilink.getPast_jumlah_edc();
             grand_jumlah_edc_delta = grand_jumlah_edc_delta + dashboard_pencapaian_brilink.getJumlah_edc_delta();
             
             grand_total_delta =  grand_total_delta + dashboard_pencapaian_brilink.getTotal_delta();
         
        }
        
        grand_total_all = grand_jumlah_web + grand_jumlah_edc;
        
        
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }

    public Beans_laporan_harian_jumlah_kanca(int no, String kode_kanwil, String nama_kanwil, String nama_kanca,
            double jumlah_web, double past_jumlah_web, double jumlah_web_delta, 
            double jumlah_edc, double past_jumlah_edc, double jumlah_edc_delta, 
            double total_delta) {
        this.no = no;
        this.kode_kanwil = kode_kanwil;
        this.nama_kanwil = nama_kanwil;
        this.nama_kanca = nama_kanca;
        this.jumlah_web = jumlah_web;
        this.past_jumlah_web = past_jumlah_web;
        this.jumlah_web_delta = jumlah_web_delta;
        this.jumlah_edc = jumlah_edc;
        this.past_jumlah_edc = past_jumlah_edc;
        this.jumlah_edc_delta = jumlah_edc_delta;
        this.total_delta = total_delta;
      
    }

  
    
  
    public Beans_laporan_harian_jumlah_kanca() {
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


  
   private String generateSQLPencapaian(String tgl, String kd_kanwil) {
       int mm = Integer.valueOf(tgl.substring(4,6));
        int tahun = Integer.valueOf(tgl.substring(0,4));
        String tgl_rka = tgl.substring(0,6);
        // get keragaan before, to sum all 
        String past_year =  String.valueOf(--tahun)+"12";
        
        DecimalFormat format_decimal_percent = new DecimalFormat( "00" );  
        String tgl_before_full_download = String.valueOf(tahun) + format_decimal_percent.format(mm - 1);
        
        StringBuilder sql = new  StringBuilder(" select \n" +
                    "a.kode_kanwil, replace(a.nama_kanwil,'KANWIL ','') as nama_kanwil, a.kode_cabang, a.nama_cabang \n" +
                    ", ifnull(jumlah_web,0) as jumlah_web, ifnull(past_jumlah_web,0) as past_jumlah_web,\n" +
                    " ifnull(jumlah_web,0) - ifnull(past_jumlah_web,0) as jumlah_web_delta ,\n" +
                    "ifnull(jumlah_edc,0) as jumlah_edc, ifnull(past_jumlah_edc,0) as past_jumlah_edc, \n" +
                    "ifnull(jumlah_edc,0) - ifnull(past_jumlah_edc,0) as jumlah_edc_delta,\n" +
                    "ifnull(jumlah_edc,0) + ifnull(jumlah_web,0) as total_all,\n" +
                    "(ifnull(jumlah_edc,0) - ifnull(past_jumlah_edc,0) ) + (ifnull(jumlah_web,0) - ifnull(past_jumlah_web,0) )  as total_delta from (\n" +
                    "select kode_kanwil, nama_kanwil, kode_cabang, nama_cabang  from dwh_branch where nama_uker1 not like 'VENDOR%' and TIPE_KANTOR = 'CABANG'  and kode_kanwil  not in ('S','T','U','V','Y')"+
                    ") a\n" +
                    "left outer join\n" +
                    "(\n" +
                    "select  kd_kanwil, kanwil,  kd_cabang , cabang, count(*) as jumlah_web  from outlet_web_"+tgl+"  						\n" +
                    "				 group by kd_kanwil, kd_cabang , kanwil,  kd_cabang , cabang\n" +
                    ") b on (a.kode_cabang = kd_cabang)				 \n" +
                    "left outer join\n" +
                    "(			 \n" +
                    "select kd_cabang , cabang, count(*) as jumlah_edc   from tid_edc_"+tgl+"				\n" +
                    "				 group by kd_cabang ,  cabang\n" +
                    ") c on (a.kode_cabang = c.kd_cabang)			 				 				 \n" +
                    " inner join \n" +
                    "(\n" +
                    "select kode_kanwil, nama_kanwil, kode_cabang, nama_cabang, sum(jumlah_web) as past_jumlah_web , \n" +
                    "sum(jumlah_edc)  as past_jumlah_edc\n" +
                    "from view_pencapaian_kanca_"+past_year+" " +
                    "group by kode_kanwil, nama_kanwil	, kode_cabang, nama_cabang\n" +
                    ")  pos_lalu\n" +
                    "on (a.kode_cabang = pos_lalu.kode_cabang) ");

        if (!kd_kanwil.equalsIgnoreCase("all")){
            sql.append(" where a.kode_kanwil = '"+kd_kanwil+"'" ); 
        }
        
            sql.append(" order by a.kode_kanwil");
        System.out.println(sql);
        
        return sql.toString();
    }
    
    
      public Collection<String> getListDateReady() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select  replace(a.table_name,'tid_edc_','') as table_name from information_schema.tables a\n" +
                    "  inner join \n" +
                    " information_schema.tables b \n" +
                    "  on ( replace(a.table_name,'tid_edc_','')  =  replace(b.table_name,'outlet_web_','') )\n" +
                    "  where  a.table_name like 'tid_edc_%'\n" +
                    "  and b.table_name like 'outlet_web%' and length(a.table_name) >= 16\n" +
                    "  order by table_name desc ";
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



    public double getJumlah_web() {
        return jumlah_web;
    }

    public void setJumlah_web(double jumlah_web) {
        this.jumlah_web = jumlah_web;
    }

    public double getPast_jumlah_web() {
        return past_jumlah_web;
    }

    public void setPast_jumlah_web(double past_jumlah_web) {
        this.past_jumlah_web = past_jumlah_web;
    }

    public double getJumlah_web_delta() {
        return jumlah_web_delta;
    }

    public void setJumlah_web_delta(double jumlah_web_delta) {
        this.jumlah_web_delta = jumlah_web_delta;
    }

    public double getJumlah_edc() {
        return jumlah_edc;
    }

    public void setJumlah_edc(double jumlah_edc) {
        this.jumlah_edc = jumlah_edc;
    }

    public double getJumlah_edc_delta() {
        return jumlah_edc_delta;
    }

    public void setJumlah_edc_delta(double jumlah_edc_delta) {
        this.jumlah_edc_delta = jumlah_edc_delta;
    }

    public double getTotal_delta() {
        return total_delta;
    }

    public void setTotal_delta(double total_delta) {
        this.total_delta = total_delta;
    }

   

    public double getPast_jumlah_edc() {
        return past_jumlah_edc;
    }

    public double getGrand_jumlah_web() {
        return grand_jumlah_web;
    }

    public double getGrand_past_jumlah_web() {
        return grand_past_jumlah_web;
    }

    public double getGrand_jumlah_web_delta() {
        return grand_jumlah_web_delta;
    }

    public double getGrand_jumlah_edc() {
        return grand_jumlah_edc;
    }

    public double getGrand_past_jumlah_edc() {
        return grand_past_jumlah_edc;
    }

    public double getGrand_total_delta() {
        return grand_total_delta;
    }

 

    public double getTotal_all() {
        return getJumlah_edc() + getJumlah_web();
    }

    public void setTotal_all(double total_all) {
        this.total_all = total_all;
    }

    public double getGrand_total_all() {
        return getGrand_jumlah_edc() + getGrand_jumlah_web();
    }

    public void setGrand_total_all(double grand_total_all) {
        this.grand_total_all = grand_total_all;
    }

    public double getGrand_jumlah_edc_delta() {
        return grand_jumlah_edc_delta;
    }

    public String getNama_kanca() {
        return nama_kanca;
    }

    public void setNama_kanca(String nama_kanca) {
        this.nama_kanca = nama_kanca;
    }

      

}

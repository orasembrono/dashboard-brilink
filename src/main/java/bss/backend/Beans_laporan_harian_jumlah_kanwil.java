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

/**
 *
 * @author IVAN
 */
public class Beans_laporan_harian_jumlah_kanwil {

    int no;
    String kode_kanwil;
    String nama_kanwil;
    
    double jumlah_web;
    double past_jumlah_web;
    double jumlah_compare_web;
    double jumlah_web_delta;
    double delta_compare_web;
    double jumlah_edc;
    double past_jumlah_edc;
    double jumlah_compare_edc;
    double delta_compare_edc;
    double past_all;
    double jumlah_edc_delta;
    double total_all;
    double total_delta;
    double total_delta_compare;
    double rka_jumlah;
    double pencapaian_jumlah;
    
   
    
    double grand_jumlah_web;
    double grand_past_jumlah_web;
    double grand_jumlah_web_delta;

    double grand_jumlah_edc;
    double grand_past_jumlah_edc;
    double grand_jumlah_edc_delta;
    
    double grand_total_delta;
    double grand_rka_jumlah;
    double grand_pencapaian_jumlah;
    double grand_total_all;
    double grand_past_all;
    
    double grand_compare_web;
    double grand_delta_compare_web;
    
    double grand_compare_edc;
    double grand_delta_compare_edc;
   
    double grand_compare_all;
    double grand_delta_compare_all;
    
    
    Db1 db;
    J2EEConnectionPool connectionPool;


    public Collection<Beans_laporan_harian_jumlah_kanwil> getData(String tgl, String tgl2) throws SQLException {
        Collection<Beans_laporan_harian_jumlah_kanwil> dashboard_pencapaian_brilinks = new ArrayList<>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaianKanwil(tgl,tgl2));
        int i; 
        i =0;

    
        while (rs.next()) {
            i++;
            Beans_laporan_harian_jumlah_kanwil dashboard_pencapaian_brilink= new Beans_laporan_harian_jumlah_kanwil
            (i,rs.getString("kode_kanwil"),
            rs.getString("nama_kanwil"),
            rs.getDouble("jumlah_web"),
            rs.getDouble("past_jumlah_web"),
            rs.getDouble("jumlah_web_compare"),
            rs.getDouble("jumlah_web_delta"),
            rs.getDouble("delta_compare_web"),
            rs.getDouble("jumlah_edc"),
            rs.getDouble("past_jumlah_edc"),
            rs.getDouble("jumlah_edc_compare"),
            rs.getDouble("jumlah_edc_delta"),
            rs.getDouble("delta_compare_edc"),         
            rs.getDouble("total_delta"),
            rs.getDouble("total_delta_compare"),        
            rs.getDouble("rka_jumlah"),
            rs.getDouble("pencapaian_jumlah"));
             dashboard_pencapaian_brilinks.add(dashboard_pencapaian_brilink);
          
             System.out.println("compare " +dashboard_pencapaian_brilink.getJumlah_compare_web());
             grand_jumlah_web =  grand_jumlah_web + dashboard_pencapaian_brilink.getJumlah_web();
             grand_past_jumlah_web =  grand_past_jumlah_web + dashboard_pencapaian_brilink.getPast_jumlah_web();
             grand_jumlah_web_delta =  grand_jumlah_web_delta + dashboard_pencapaian_brilink.getJumlah_web_delta();
             
             
             grand_jumlah_edc =  grand_jumlah_edc + dashboard_pencapaian_brilink.getJumlah_edc();
             grand_past_jumlah_edc =  grand_past_jumlah_edc + dashboard_pencapaian_brilink.getPast_jumlah_edc();
             grand_jumlah_edc_delta = grand_jumlah_edc_delta + dashboard_pencapaian_brilink.getJumlah_edc_delta();
             
             grand_total_delta =  grand_total_delta + dashboard_pencapaian_brilink.getTotal_delta();
             grand_rka_jumlah  =  grand_rka_jumlah + dashboard_pencapaian_brilink.getRka_jumlah();
             
             
             grand_compare_web = grand_compare_web + dashboard_pencapaian_brilink.getJumlah_compare_web();
             grand_compare_edc = grand_compare_edc + dashboard_pencapaian_brilink.getJumlah_compare_edc();
                     
             grand_compare_all = grand_compare_all + dashboard_pencapaian_brilink.getTotal_compare();
             grand_delta_compare_all = grand_delta_compare_all + dashboard_pencapaian_brilink.getTotal_delta_compare();
          
        }
        
        grand_total_all = grand_jumlah_web + grand_jumlah_edc;
        
        
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }

    public Beans_laporan_harian_jumlah_kanwil(int no, String kode_kanwil, String nama_kanwil,
            double jumlah_web, double past_jumlah_web, double jumlah_compare_web, double jumlah_web_delta, double delta_compare_web,
            double jumlah_edc, double past_jumlah_edc, double jumlah_compare_edc, double jumlah_edc_delta, double delta_compare_edc,
            double total_delta,  double total_delta_compare, double rka_jumlah, double pencapaian_jumlah) {
        this.no = no;
        this.kode_kanwil = kode_kanwil;
        this.nama_kanwil = nama_kanwil;
        this.jumlah_web = jumlah_web;
        this.past_jumlah_web = past_jumlah_web;
        this.jumlah_compare_web = jumlah_compare_web;
        this.delta_compare_web = delta_compare_web;
        this.jumlah_web_delta = jumlah_web_delta;
        this.jumlah_edc = jumlah_edc;
        this.past_jumlah_edc = past_jumlah_edc;
        this.jumlah_compare_edc = jumlah_compare_edc;
        this.jumlah_edc_delta = jumlah_edc_delta;
        this.delta_compare_edc = delta_compare_edc;
        this.total_delta = total_delta;
        this.total_delta_compare = total_delta_compare;
        this.rka_jumlah = rka_jumlah;
        this.pencapaian_jumlah = pencapaian_jumlah;
    }

  
    
  
    public Beans_laporan_harian_jumlah_kanwil() {
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


  
    
    private String generateSQLPencapaianKanwil(String tgl,String tgl2){
        int mm = Integer.valueOf(tgl.substring(4,6));
        int tahun = Integer.valueOf(tgl.substring(0,4));
        String tgl_rka = tgl.substring(0,4)+"12";
        // get keragaan before, to sum all 
        String past_year =  String.valueOf(--tahun)+"12";
        
        String sql_pencapaian = "select  a.kode_kanwil, nama_kanwil, jumlah_web, past_jumlah_web, jumlah_web_compare, jumlah_web_delta, jumlah_web - jumlah_web_compare as delta_compare_web,\n" +
        "jumlah_edc, past_jumlah_edc ,jumlah_edc_compare,  jumlah_edc_delta, jumlah_edc - jumlah_edc_compare as delta_compare_edc,\n" +
        "total_delta,\n" +
        "(jumlah_web - jumlah_web_compare + jumlah_edc - jumlah_edc_compare) as total_delta_compare,"+
        "rka."+tgl_rka+"  as rka_jumlah , (jumlah_web + jumlah_edc) / rka."+tgl_rka+" * 100 as pencapaian_jumlah from  " +
        "( " +
        " " +
        "select  jumlah.kode_kanwil, jumlah.nama_kanwil, sum(jumlah_web) as jumlah_web, sum(past_jumlah_web) as past_jumlah_web , " +
        "sum(jumlah_web_posisi) as jumlah_web_delta, " +
        "sum(jumlah_edc) as jumlah_edc, sum(past_jumlah_edc) as past_jumlah_edc , sum(jumlah_edc_posisi) as jumlah_edc_delta, " +
        "sum(jumlah_edc_posisi) + sum(jumlah_web_posisi)  as total_delta " +
        " " +
        "from ( " +
        " " +
        " " +
        "select  " +
        "a.kode_kanwil, replace(a.nama_kanwil,'KANWIL ','') as nama_kanwil, a.kode_cabang, a.nama_cabang  " +
        ", ifnull(jumlah_web,0) as jumlah_web, ifnull(past_jumlah_web,0) as past_jumlah_web, ifnull(jumlah_web,0) - ifnull(past_jumlah_web,0) as jumlah_web_posisi , " +
        "ifnull(jumlah_edc,0) as jumlah_edc, ifnull(past_jumlah_edc,0) as past_jumlah_edc, ifnull(jumlah_edc,0) - ifnull(past_jumlah_edc,0) as jumlah_edc_posisi from ( " +
        "select kode_kanwil, nama_kanwil, kode_cabang, nama_cabang  from dwh_branch where nama_uker1 not like 'VENDOR%' and TIPE_KANTOR = 'CABANG'  and kode_kanwil  not in ('S','T','U','V','Y') " +
        ") a " +
        "left outer join " +
        "( " +
        "select  kd_kanwil, kanwil,  kd_cabang , cabang, count(*) as jumlah_web  from   outlet_web_"+tgl+" 						 " +
        "				 group by kd_kanwil, kd_cabang , kanwil,  kd_cabang , cabang " +
        ") b on (a.kode_cabang = kd_cabang)				  " +
        "left outer join " +
        "(			  " +
        "select kd_cabang , cabang, count(*) as jumlah_edc   from tid_edc_"+tgl+"				 " +
        "				 group by kd_cabang ,  cabang " +
        ") c on (a.kode_cabang = c.kd_cabang)			 				 				  " +
        " inner join  " +
        "( " +
        "select kode_kanwil, nama_kanwil, kode_cabang, nama_cabang, sum(jumlah_web) as past_jumlah_web ,  " +
        "sum(jumlah_edc)  as past_jumlah_edc " +
        "from view_pencapaian_kanca_"+past_year+" " +
        "group by kode_kanwil, nama_kanwil	, kode_cabang, nama_cabang " +
        ")  pos_lalu " +
        "on (a.kode_cabang = pos_lalu.kode_cabang) " +
        " " +
        ")  jumlah	 group by 	 jumlah.kode_kanwil, jumlah.nama_kanwil " +
        " " +
        ") a inner join " +
        "rka_jumlah rka " +
        "on (a.kode_kanwil = rka.kd_kanwil)    inner join \n" +
        "(select kd_kanwil, count(*) as jumlah_edc_compare from tid_edc_"+tgl2+" group by kd_kanwil ) compare1\n" +
        "on (a.kode_kanwil = compare1.kd_kanwil)\n" +
        "inner join \n" +
        "(select kd_kanwil, count(*) as jumlah_web_compare from outlet_web_"+tgl2+" group by kd_kanwil ) compare2\n" +
        "on (a.kode_kanwil = compare2.kd_kanwil)";
        
     


        System.out.println(sql_pencapaian);
        return sql_pencapaian;
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


    
      
    public double getGrand_rka_jumlah() {
        return grand_rka_jumlah;
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

    public double getRka_jumlah() {
        return rka_jumlah;
    }

    public void setRka_jumlah(double rka_jumlah) {
        this.rka_jumlah = rka_jumlah;
    }

    public double getPencapaian_jumlah() {
        return pencapaian_jumlah;
    }

    public void setPencapaian_jumlah(double pencapaian_jumlah) {
        this.pencapaian_jumlah = pencapaian_jumlah;
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

    public double getGrand_pencapaian_jumlah() {
        return (grand_total_all  ) / grand_rka_jumlah * 100;
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

    public double getPast_all() {
        return past_jumlah_edc + past_jumlah_web;
    }

    public double getGrand_past_all() {
        return grand_past_jumlah_edc + grand_past_jumlah_web;
    }

    public double getJumlah_compare_web() {
        return jumlah_compare_web;
    }

    public double getDelta_compare_web() {
        return delta_compare_web;
    }

    public double getJumlah_compare_edc() {
        return jumlah_compare_edc;
    }

    public double getDelta_compare_edc() {
        return delta_compare_edc;
    }

    public double getTotal_delta_compare() {
        return total_delta_compare;
    }
    
    public double getTotal_compare() {
        return jumlah_compare_edc +  jumlah_compare_web;
    }

    public double getGrand_compare_web() {
        return grand_compare_web;
    }

    public double getGrand_delta_compare_web() {
        return   getGrand_jumlah_web() - grand_compare_web;
    }

    public double getGrand_compare_edc() {
        return grand_compare_edc;
    }

    public double getGrand_delta_compare_edc() {
        return  getGrand_jumlah_edc() - grand_compare_edc ;
    }
    
    
    

    public double getGrand_compare_all() {
        return grand_compare_all;
    }

    public double getGrand_delta_compare_all() {
        return grand_delta_compare_all;
    }
    

    
      

}

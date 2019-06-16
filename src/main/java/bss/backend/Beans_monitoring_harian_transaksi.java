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
public class Beans_monitoring_harian_transaksi {

    int no;
    
    String kode_kanwil;
    String nama_kanwil;
    double trx_edc1;
    double trx_mobile1;
    double total1;
    double trx_edc2;
    double trx_mobile2;
    double total2;
    double delta;
    double ratas1;
    double ratas2;
    String status;
   
   
    double grand_trx_edc1;
    double grand_trx_mobile1;
    double grand_total1;
    double grand_trx_edc2;
    double grand_trx_mobile2;
    double grand_total2;
    double grand_delta;
    
    
    
    
    
    Db1 db;
    J2EEConnectionPool connectionPool;


    public Collection<Beans_monitoring_harian_transaksi> getData(String tgl, String tgl2) throws SQLException {
        Collection<Beans_monitoring_harian_transaksi> dashboard_pencapaian_brilinks = new ArrayList<>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaianKanwil(tgl,tgl2));
        int i; 
        i =0;
        
        String year1 = tgl.substring(0,4);
        String month1 = tgl.substring(4,6);
        String day1 = tgl.substring(6,8);
        
        String year2 = tgl2.substring(0,4);
        String month2 = tgl2.substring(4,6);
        String day2 = tgl2.substring(6,8);
    
        while (rs.next()) {
            i++;
            Beans_monitoring_harian_transaksi dashboard_pencapaian_brilink= new Beans_monitoring_harian_transaksi
            ( i,  rs.getString("kd_kanwil"), rs.getString("kanwil"), rs.getDouble("trx_edc1"), rs.getDouble("trx_mobile1"), 
                  rs.getDouble("total1") , rs.getDouble("trx_edc2"), rs.getDouble("trx_mobile2"), rs.getDouble("total2"),rs.getDouble("delta"));
             dashboard_pencapaian_brilinks.add(dashboard_pencapaian_brilink);
          
             grand_trx_edc1 = grand_trx_edc1 + dashboard_pencapaian_brilink.getTrx_edc1();
             grand_trx_mobile1 = grand_trx_mobile1 + dashboard_pencapaian_brilink.getTrx_mobile1();
             grand_total1 = grand_total1 + dashboard_pencapaian_brilink.getTotal1();
             grand_trx_edc2 = grand_trx_edc2 + dashboard_pencapaian_brilink.getTrx_edc2();
             grand_trx_mobile2 = grand_trx_mobile2 + dashboard_pencapaian_brilink.getTrx_mobile2();
             grand_total2 = grand_total2 + dashboard_pencapaian_brilink.getTotal2();
             grand_delta = grand_delta + dashboard_pencapaian_brilink.getDelta();
             
             dashboard_pencapaian_brilink.setRatas1(dashboard_pencapaian_brilink.getTotal1() / Double.parseDouble(day1));
             dashboard_pencapaian_brilink.setRatas2(dashboard_pencapaian_brilink.getTotal2() / Double.parseDouble(day2));
            
        }
        
     
        
        
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }

    public Beans_monitoring_harian_transaksi(int no, String kode_kanwil, String nama_kanwil, double trx_edc1, double trx_mobile1, double total1, double trx_edc2, double trx_mobile2, double total2, double delta) {
        this.no = no;
        this.kode_kanwil = kode_kanwil;
        this.nama_kanwil = nama_kanwil;
        this.trx_edc1 = trx_edc1;
        this.trx_mobile1 = trx_mobile1;
        this.total1 = total1;
        this.trx_edc2 = trx_edc2;
        this.trx_mobile2 = trx_mobile2;
        this.total2 = total2;
        this.delta = delta;
    }

    
  
    public Beans_monitoring_harian_transaksi() {
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
       // tgl <- main tanggal
       // tgl2 <- compare
        
        String sql_pencapaian = "select a.kd_kanwil, a.kanwil, b.edc as trx_edc1, c.mobile as trx_mobile1, b.edc + c.mobile as total1, "
                + "d.edc as trx_edc2, e.mobile as trx_mobile2 , d.edc + e.mobile as total2, \n" +
        "( b.edc + c.mobile ) - ( d.edc + e.mobile) as delta from kanwil\n" +
        "a\n" +
        "left outer join\n" +
        "(select kd_kanwil, sum(total_transaksi) as edc from fitur_edc_"+tgl+" group by kd_kanwil)\n" +
        "b on (a.kd_kanwil = b.kd_kanwil)\n" +
        "left outer join\n" +
        "(select kd_kanwil, sum(total_transaksi) as mobile from fitur_web_"+tgl+" group by kd_kanwil)\n" +
        "c on (a.kd_kanwil = c.kd_kanwil)\n" +
        "left outer join\n" +
        "(select kd_kanwil, sum(total_transaksi) as edc from fitur_edc_"+tgl2+" group by kd_kanwil)\n" +
        "d on (a.kd_kanwil = d.kd_kanwil)\n" +
        "left outer join\n" +
        "(select kd_kanwil, sum(total_transaksi) as mobile from fitur_web_"+tgl2+" group by kd_kanwil)\n" +
        "e on (a.kd_kanwil = e.kd_kanwil)";
      
        System.out.println(sql_pencapaian);
        return sql_pencapaian;
    }
    
      public Collection<String> getListDateReady() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select  replace(a.table_name,'fitur_edc_','') as table_name from information_schema.tables a\n" +
                    "  inner join \n" +
                    " information_schema.tables b \n" +
                    "  on ( replace(a.table_name,'fitur_edc_','')  =  replace(b.table_name,'fitur_web_','') )\n" +
                    "  where  a.table_name like 'fitur_edc_%'\n" +
                    "  and b.table_name like 'fitur_web%' and length(a.table_name) >= 16\n" +
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

    public double getTrx_edc1() {
        return trx_edc1;
    }

    public void setTrx_edc1(double trx_edc1) {
        this.trx_edc1 = trx_edc1;
    }

    public double getTrx_mobile1() {
        return trx_mobile1;
    }

    public void setTrx_mobile1(double trx_mobile1) {
        this.trx_mobile1 = trx_mobile1;
    }

    public double getTotal1() {
        return total1;
    }

    public void setTotal1(double total1) {
        this.total1 = total1;
    }

    public double getTrx_edc2() {
        return trx_edc2;
    }

    public void setTrx_edc2(double trx_edc2) {
        this.trx_edc2 = trx_edc2;
    }

    public double getTrx_mobile2() {
        return trx_mobile2;
    }

    public void setTrx_mobile2(double trx_mobile2) {
        this.trx_mobile2 = trx_mobile2;
    }

    public double getTotal2() {
        return total2;
    }

    public void setTotal2(double total2) {
        this.total2 = total2;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getGrand_delta() {
        return grand_delta;
    }

    public void setGrand_delta(double grand_delta) {
        this.grand_delta = grand_delta;
    }
    
    

    public double getGrand_trx_edc1() {
        return grand_trx_edc1;
    }

    public void setGrand_trx_edc1(double grand_trx_edc1) {
        this.grand_trx_edc1 = grand_trx_edc1;
    }

    public double getGrand_trx_mobile1() {
        return grand_trx_mobile1;
    }

    public void setGrand_trx_mobile1(double grand_trx_mobile1) {
        this.grand_trx_mobile1 = grand_trx_mobile1;
    }

    public double getGrand_total1() {
        return grand_total1;
    }

    public void setGrand_total1(double grand_total1) {
        this.grand_total1 = grand_total1;
    }

    public double getGrand_trx_edc2() {
        return grand_trx_edc2;
    }

    public void setGrand_trx_edc2(double grand_trx_edc2) {
        this.grand_trx_edc2 = grand_trx_edc2;
    }

    public double getGrand_trx_mobile2() {
        return grand_trx_mobile2;
    }

    public void setGrand_trx_mobile2(double grand_trx_mobile2) {
        this.grand_trx_mobile2 = grand_trx_mobile2;
    }

    public double getGrand_total2() {
        return grand_total2;
    }

    public void setGrand_total2(double grand_total2) {
        this.grand_total2 = grand_total2;
    }

    public double getRatas1() {
        return ratas1;
    }

    public void setRatas1(double ratas1) {
        this.ratas1 = ratas1;
    }

    public double getRatas2() {
        return ratas2;
    }

    public void setRatas2(double ratas2) {
        this.ratas2 = ratas2;
    }

   
    
    


      
      

}

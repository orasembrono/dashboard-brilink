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
public class Dashboard_pencapaian_brilink {

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
    double jumlah_staging;
    double casa_web;
    double casa_edc;
    double casa_all;
    double low_performance;
    double pencapaian_bep;
    double pencapaian_fbi;
    double pencapaian_trx;
    double pencapaian_casa;
    double pencapaian_jumlah;

    
    Db1 db;
    J2EEConnectionPool connectionPool;

    public Collection<Dashboard_pencapaian_brilink> getDashboard_pencapaian_brilink() throws SQLException {
        Collection<Dashboard_pencapaian_brilink> dashboard_pencapaian_brilinks = new ArrayList<Dashboard_pencapaian_brilink>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from view_pencapaian_kanca_201801");
        int no = 0;

        while (rs.next()) {
            no++;
            Dashboard_pencapaian_brilink dashboard_pencapaian_brilink
    = new Dashboard_pencapaian_brilink(no,
            rs.getString("kode_kanwil"),
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
            rs.getDouble("jumlah_staging"),
            rs.getDouble("casa_web"),
            rs.getDouble("casa_edc"),
            rs.getDouble("casa_all"),
            rs.getDouble("low_performance"),
            rs.getDouble("pencapaian_bep"),
            rs.getDouble("pencapaian_fbi"),
            rs.getDouble("pencapaian_trx"),
            rs.getDouble("pencapaian_casa"),
            rs.getDouble("pencapaian_jumlah"));
             dashboard_pencapaian_brilinks.add(dashboard_pencapaian_brilink);

        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }
    
    
     public Collection<Dashboard_pencapaian_brilink> getDashboard_pencapaian_brilink_kanwil() throws SQLException {
        Collection<Dashboard_pencapaian_brilink> dashboard_pencapaian_brilinks = new ArrayList<Dashboard_pencapaian_brilink>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaianKanwil("201801"));
        int no = 0;

        while (rs.next()) {
            no++;
            Dashboard_pencapaian_brilink dashboard_pencapaian_brilink
    = new Dashboard_pencapaian_brilink(no,
            rs.getString("kode_kanwil"),
            rs.getString("nama_kanwil"),
            "",
            "",
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
            rs.getDouble("jumlah_staging"),
            rs.getDouble("casa_web"),
            rs.getDouble("casa_edc"),
            rs.getDouble("casa_all"),
            rs.getDouble("low_performance"),
            rs.getDouble("pencapaian_bep"),
            rs.getDouble("pencapaian_fbi"),
            rs.getDouble("pencapaian_trx"),
            rs.getDouble("pencapaian_casa"),
            rs.getDouble("pencapaian_jumlah"));
             dashboard_pencapaian_brilinks.add(dashboard_pencapaian_brilink);

        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }

    public Dashboard_pencapaian_brilink() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }

    public Dashboard_pencapaian_brilink(int no, String kode_kanwil, String nama_kanwil, String kode_cabang, String nama_cabang, double total_transaksi_web, double total_transaksi_edcf, double total_transaksi_all, double total_fee_web, double total_fee_edcf, double total_fee_all, double total_nominal_web, double total_nominal_edcf, double total_nominal_all, double edc_trx, double web_trx, double trx_all, double bep_edc, double bep_web, double bep_all, double jumlah_edc, double jumlah_web, double jumlah_all, double jumlah_edc_2017, double jumlah_web_2017, double jumlah_all_2017, double jumlah_staging, double casa_web, double casa_edc, double casa_all, double low_performance, double pencapaian_bep, double pencapaian_fbi, double pencapaian_trx, double pencapaian_casa, double pencapaian_jumlah) {
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
        this.jumlah_staging = jumlah_staging;
        this.casa_web = casa_web;
        this.casa_edc = casa_edc;
        this.casa_all = casa_all;
        this.low_performance = low_performance;
        this.pencapaian_bep = pencapaian_bep;
        this.pencapaian_fbi = pencapaian_fbi;
        this.pencapaian_trx = pencapaian_trx;
        this.pencapaian_casa = pencapaian_casa;
        this.pencapaian_jumlah = pencapaian_jumlah;
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

    public double getEdc_trx() {
        return edc_trx;
    }

    public void setEdc_trx(double edc_trx) {
        this.edc_trx = edc_trx;
    }

    public double getWeb_trx() {
        return web_trx;
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

    public double getJumlah_staging() {
        return jumlah_staging;
    }

    public void setJumlah_staging(double jumlah_staging) {
        this.jumlah_staging = jumlah_staging;
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

    public double getLow_performance() {
        return low_performance;
    }

    public void setLow_performance(double low_performance) {
        this.low_performance = low_performance;
    }

    public double getPencapaian_bep() {
        return pencapaian_bep;
    }

    public void setPencapaian_bep(double pencapaian_bep) {
        this.pencapaian_bep = pencapaian_bep;
    }

    public double getPencapaian_fbi() {
        return pencapaian_fbi;
    }

    public void setPencapaian_fbi(double pencapaian_fbi) {
        this.pencapaian_fbi = pencapaian_fbi;
    }

    public double getPencapaian_trx() {
        return pencapaian_trx;
    }

    public void setPencapaian_trx(double pencapaian_trx) {
        this.pencapaian_trx = pencapaian_trx;
    }

    public double getPencapaian_casa() {
        return pencapaian_casa;
    }

    public void setPencapaian_casa(double pencapaian_casa) {
        this.pencapaian_casa = pencapaian_casa;
    }

    public double getPencapaian_jumlah() {
        return pencapaian_jumlah;
    }

    public void setPencapaian_jumlah(double pencapaian_jumlah) {
        this.pencapaian_jumlah = pencapaian_jumlah;
    }

    private String generateSQLPencapaian(String tgl) {
        
        String sql_pencapaian = "select * from view_pencapaian_kanca_"+tgl;
        
        return sql_pencapaian;
    }
    
    
    private String generateSQLPencapaianKanwil(String tgl){
        String sql_pencapaian = "select penc.*, rj."+tgl+" as rka_jumah ,rf."+tgl+" as rka_fbi,rt.201801 as rka_trx from\n" +
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
                        "sum(edc_trx) as edc_trx,\n" +
                        "sum(web_trx) as web_trx,\n" +
                        "sum(trx_all) as trx_all,\n" +
                        "sum(bep_edc) as bep_edc,\n" +
                        "sum(bep_web) as bep_web,\n" +
                        "sum(bep_all) as bep_all,\n" +
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
                        "on (penc.kode_kanwil = rt.kd_kanwil)";
        
        System.out.println(sql_pencapaian);
        return sql_pencapaian;
    }
    
      public Collection<String> getListViewPencapaian() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select table_name from information_schema.tables where table_name like 'view_pencapaian_kanca_%' "
+ "order by table_name ";
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

}

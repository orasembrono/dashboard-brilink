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
public class Dashboard_ranking {

    int no;
    String kd_kanwil;
    String kanwil;
    String transaksi;
    String fbi;
    String nominal;
    String device_trx;
    String bep;
    String jumlah;
    String jumlah_2017;
    String staging;
    String casa;
    String rka_jumlah;
    String rka_trxfin;
    String rka_fbi;
    double pencapaian_bep;
    double pencapainan_fbi;
    double pencapaian_trx;
    double pencapaian_casa;
    double pencapaian_jumlah;
    double total;
    Image label_pencapaian;


    Image image_warning = new Image("OK", new ThemeResource("img/warning1.png"));
    Image image_danger = new Image("OK", new ThemeResource("img/danger1.png"));
    Image image_ok = new Image("OK", new ThemeResource("img/ok1.png"));

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getKd_kanwil() {
        return kd_kanwil;
    }

    public void setKd_kanwil(String kd_kanwil) {
        this.kd_kanwil = kd_kanwil;
    }

    public String getKanwil() {
        return kanwil;
    }

    public void setKanwil(String kanwil) {
        this.kanwil = kanwil;
    }

    public double getPencapaian_bep() {
        return pencapaian_bep;
    }

    public void setPencapaian_bep(double pencapaian_bep) {
        this.pencapaian_bep = pencapaian_bep;
    }

    public double getPencapainan_fbi() {
        return pencapainan_fbi;
    }

    public void setPencapainan_fbi(double pencapainan_fbi) {
        this.pencapainan_fbi = pencapainan_fbi;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Image getLabel_pencapaian() {
        return label_pencapaian;
    }

    public void setLabel_pencapaian(Image label_pencapaian) {
        this.label_pencapaian = label_pencapaian;
    }

    J2EEConnectionPool connectionPool;
    Db1 db;

    public Dashboard_ranking() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }

    public Dashboard_ranking(String kd_kanwil, String kanwil) {
        this.kd_kanwil = kd_kanwil;
        this.kanwil = kanwil;
    }

    public Dashboard_ranking(int no, String kd_kanwil, String kanwil, double pencapaian_bep, double pencapainan_fbi, double pencapaian_trx, double pencapaian_casa, double pencapaian_jumlah, double total) {
        this.no = no;
        this.kd_kanwil = kd_kanwil;
        this.kanwil = kanwil;
        this.pencapaian_bep = pencapaian_bep;
        this.pencapainan_fbi = pencapainan_fbi;
        this.pencapaian_trx = pencapaian_trx;
        this.pencapaian_casa = pencapaian_casa;
        this.pencapaian_jumlah = pencapaian_jumlah;
        this.total = total;
        if (total >= 90) {
            this.label_pencapaian = image_ok;
        } else if (total >= 70) {
            this.label_pencapaian = image_warning;
        } else {
            this.label_pencapaian = image_danger;
        }

    }

    public Collection<Dashboard_ranking> getDashboard_ranking() throws SQLException {
        Collection<Dashboard_ranking> dashboard_rankings = new ArrayList<Dashboard_ranking>();

        String sql = "select  `KD_KANWIL` , "
                + "  `NAMA_KANWIL` ,"
                + "   `TRANSAKSI` ,"
                + "   `FBI`,"
                + "  `NOMINAL` ,"
                + "  `DEVICE_TRX` ,"
                + "  `BEP` ,"
                + "  `JUMLAH` ,"
                + "  `JUMLAH_2017` ,"
                + "  `STAGING` ,"
                + "  `CASA`,"
                + "  `RKA_JUMLAH` ,"
                + "  `RKA_TRXFIN`,"
                + "  `RKA_FBI`,"
                + "  format(`PENCAPAIAN_BEP`,2) as PENCAPAIAN_BEP,"
                + "  format(`PENCAPAIAN_FBI`,2) as PENCAPAIAN_FBI,"
                + "  format(`PENCAPAIAN_TRX`,2) as PENCAPAIAN_TRX,"
                + "  format(`PENCAPAIAN_CASA`,2) as PENCAPAIAN_CASA,"
                + "  format(PENCAPAIAN_JUMLAH,2)  as PENCAPAIAN_JUMLAH,"
                + "  format(PENCAPAIAN_BEP * 0.15 +  PENCAPAIAN_FBI * 0.25+ PENCAPAIAN_TRX * 0.25 + PENCAPAIAN_CASA * 0.25 + PENCAPAIAN_JUMLAH * 0.10,2) as TOTAL"
                + "  from  "
                + "  dashboard_ranking order by (PENCAPAIAN_BEP * 0.15 +  PENCAPAIAN_FBI * 0.25+ PENCAPAIAN_TRX * 0.25 + PENCAPAIAN_CASA * 0.25 + PENCAPAIAN_JUMLAH * 0.10) desc";

        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int no = 0;
        while (rs.next()) {
            no++;
            Dashboard_ranking dashboard_ranking
                    = new Dashboard_ranking(no,
                            rs.getString("KD_KANWIL"),
                            rs.getString("NAMA_KANWIL"),
                            rs.getDouble("PENCAPAIAN_BEP"),
                            rs.getDouble("PENCAPAIAN_FBI"),
                            rs.getDouble("PENCAPAIAN_TRX"),
                            rs.getDouble("PENCAPAIAN_CASA"),
                            rs.getDouble("PENCAPAIAN_JUMLAH"),
                            rs.getDouble("TOTAL"));
            dashboard_rankings.add(dashboard_ranking);

        }
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_rankings;
    }

}

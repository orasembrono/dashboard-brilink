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
public class Beans_laporan_mingguan {

    int no;
    String kode_kanwil;
    String nama_kanwil;
    
    double total_transaksi_web;
    double total_transaksi_edcf;
    double total_transaksi_all;
    double total_transaksi_web_compare;
    double total_transaksi_edcf_compare;
    double total_transaksi_all_compare;
  
    
    double total_fee_web;
    double total_fee_edcf;
    double total_fee_all;
    double total_fee_web_compare;
    double total_fee_edcf_compare;
    double total_fee_all_compare;
  
   
    double total_nominal_web;
    double total_nominal_edcf;
    double total_nominal_all;
    double total_nominal_web_compare;
    double total_nominal_edcf_compare;
    double total_nominal_all_compare;
    
    double jumlah_edc;
    double jumlah_web;
    double jumlah_all;
    double jumlah_edc_compare;
    double jumlah_web_compare;
    double jumlah_all_compare;
 
    double casa_web;
    double casa_edc;
    double casa_all;
    double casa_web_compare;
    double casa_edc_compare;
    double casa_all_compare;
  
    double total_transaksi_rka;
    double total_fee_rka;
    double total_jumlah_rka;
    
    double grand_transaksi_web;
    double grand_transaksi_edcf;
    double grand_transaksi_all;
    double grand_transaksi_web_compare;
    double grand_transaksi_edcf_compare;
    double grand_transaksi_all_compare;

    double grand_fee_web;
    double grand_fee_edcf;
    double grand_fee_all;
    double grand_fee_web_compare;
    double grand_fee_edcf_compare;
    double grand_fee_all_compare;
   
    double grand_jumlah_edc =0.0;
    double grand_jumlah_web = 0.0;
    double grand_jumlah_all = 0.0;
    double grand_jumlah_edc_compare =0.0;
    double grand_jumlah_web_compare = 0.0;
    double grand_jumlah_all_compare = 0.0;
    
    double grand_casa_web;
    double grand_casa_edc;
    double grand_casa_all;
    double grand_casa_web_compare;
    double grand_casa_edc_compare;
    double grand_casa_all_compare;
    
    double grand_rka_jumlah;
    double grand_rka_fbi;
    double grand_rka_trx;
  
    
    Db1 db;
    J2EEConnectionPool connectionPool;

    
    public Collection<String> getListDateRka() throws SQLException {
      
        Connection con_1 = connectionPool.reserveConnection();
        Statement st_1 = con_1.createStatement();
        String sql_1 = "Select  distinct bulan from rka_all ";
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
    

    public Collection<Beans_laporan_mingguan> getData(String tgl, String compare, String rka) throws SQLException {
        Collection<Beans_laporan_mingguan> dashboard_pencapaian_brilinks = new ArrayList<Beans_laporan_mingguan>();
        Connection con = connectionPool.reserveConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(generateSQLPencapaianKanwil(tgl,compare,rka));
        int no = 0;

        
        while (rs.next()) {
            no++;
            Beans_laporan_mingguan dashboard_pencapaian_brilink= new Beans_laporan_mingguan
            (no,rs.getString("kode_kanwil"),
            rs.getString("nama_kanwil"),
                    
            rs.getDouble("total_transaksi_web"),
            rs.getDouble("total_transaksi_edcf"),
            rs.getDouble("total_transaksi_all"),
            rs.getDouble("total_transaksi_web_"+compare),
            rs.getDouble("total_transaksi_edcf_"+compare),
            rs.getDouble("total_transaksi_all_"+compare),
                    
            rs.getDouble("total_fee_web"),
            rs.getDouble("total_fee_edcf"),
            rs.getDouble("total_fee_all"),
            rs.getDouble("total_fee_web_"+compare),
            rs.getDouble("total_fee_edcf_"+compare),
            rs.getDouble("total_fee_all_"+compare),
                    
            rs.getDouble("total_nominal_web"),
            rs.getDouble("total_nominal_edcf"),
            rs.getDouble("total_nominal_all"),
            rs.getDouble("total_nominal_web_"+compare),
            rs.getDouble("total_nominal_edcf_"+compare),
            rs.getDouble("total_nominal_all_"+compare),
                    
            rs.getDouble("jumlah_edc"),
            rs.getDouble("jumlah_web"),
            rs.getDouble("jumlah_all"),
            rs.getDouble("jumlah_edc_"+compare),
            rs.getDouble("jumlah_web_"+compare),
            rs.getDouble("jumlah_all_"+compare),        
         
            rs.getDouble("casa_web"),
            rs.getDouble("casa_edc"),
            rs.getDouble("casa_all"), 
            rs.getDouble("casa_web_"+compare),
            rs.getDouble("casa_edc_"+compare),
            rs.getDouble("casa_all_"+compare), 
                    
            rs.getDouble("rka_trxfin_"+rka) ,
            rs.getDouble("rka_fee_"+rka),
            rs.getDouble("rka_jumlah_"+rka));
             dashboard_pencapaian_brilinks.add(dashboard_pencapaian_brilink);
             
             grand_jumlah_edc =  grand_jumlah_edc + dashboard_pencapaian_brilink.jumlah_edc;
             grand_jumlah_web =  grand_jumlah_web + dashboard_pencapaian_brilink.jumlah_web;
             grand_jumlah_all =  grand_jumlah_all + dashboard_pencapaian_brilink.jumlah_all;
          
             grand_transaksi_web = grand_transaksi_web + dashboard_pencapaian_brilink.total_transaksi_web;
             grand_transaksi_edcf = grand_transaksi_edcf + dashboard_pencapaian_brilink.total_transaksi_edcf;
             grand_transaksi_all = grand_transaksi_all + dashboard_pencapaian_brilink.total_transaksi_all;
             grand_transaksi_web_compare = grand_transaksi_web_compare + dashboard_pencapaian_brilink.total_transaksi_web_compare;
             grand_transaksi_edcf_compare = grand_transaksi_edcf_compare + dashboard_pencapaian_brilink.total_transaksi_edcf_compare;
             grand_transaksi_all_compare = grand_transaksi_all_compare + dashboard_pencapaian_brilink.total_transaksi_all_compare;
             
             grand_fee_web = grand_fee_web + dashboard_pencapaian_brilink.total_fee_web;
             grand_fee_edcf = grand_fee_edcf + dashboard_pencapaian_brilink.total_fee_edcf;
             grand_fee_all = grand_fee_all + dashboard_pencapaian_brilink.total_fee_all;
             grand_fee_web_compare = grand_fee_web_compare + dashboard_pencapaian_brilink.total_fee_web_compare;
             grand_fee_edcf_compare = grand_fee_edcf_compare + dashboard_pencapaian_brilink.total_fee_edcf_compare;
             grand_fee_all_compare = grand_fee_all_compare + dashboard_pencapaian_brilink.total_fee_all_compare;
             
             grand_jumlah_edc = grand_jumlah_edc + dashboard_pencapaian_brilink.jumlah_edc;
             grand_jumlah_web = grand_jumlah_web + dashboard_pencapaian_brilink.jumlah_web;
             grand_jumlah_all = grand_jumlah_all + dashboard_pencapaian_brilink.jumlah_all;
             grand_jumlah_edc_compare = grand_jumlah_edc_compare + dashboard_pencapaian_brilink.jumlah_edc_compare;
             grand_jumlah_web_compare = grand_jumlah_web_compare + dashboard_pencapaian_brilink.jumlah_web_compare;
             grand_jumlah_all_compare = grand_jumlah_all_compare + dashboard_pencapaian_brilink.jumlah_all_compare;
                
             grand_casa_web = grand_casa_web + dashboard_pencapaian_brilink.casa_web;
             grand_casa_edc = grand_casa_edc + dashboard_pencapaian_brilink.casa_edc;
             grand_casa_all = grand_casa_all + dashboard_pencapaian_brilink.casa_all;
             grand_casa_web_compare = grand_casa_web_compare + dashboard_pencapaian_brilink.casa_web_compare;
             grand_casa_edc_compare = grand_casa_edc_compare + dashboard_pencapaian_brilink.casa_edc_compare;
             grand_casa_all_compare = grand_casa_all_compare + dashboard_pencapaian_brilink.casa_all_compare;
            
             grand_rka_jumlah  =  grand_rka_jumlah + dashboard_pencapaian_brilink.total_jumlah_rka;
             grand_rka_fbi =  grand_rka_fbi + dashboard_pencapaian_brilink.total_fee_rka;
             grand_rka_trx =  grand_rka_trx + dashboard_pencapaian_brilink.total_transaksi_rka;
            
        }
        
       
        
        
        rs.close();
        con.close();
        connectionPool.releaseConnection(con);
        return dashboard_pencapaian_brilinks;
    }

    public Beans_laporan_mingguan(int no, String kode_kanwil, String nama_kanwil, double total_transaksi_web, double total_transaksi_edcf, double total_transaksi_all, double total_transaksi_web_compare, double total_transaksi_edcf_compare, double total_transaksi_all_compare, double total_fee_web, double total_fee_edcf, double total_fee_all, double total_fee_web_compare, double total_fee_edcf_compare, double total_fee_all_compare, double total_nominal_web, double total_nominal_edcf, double total_nominal_all, double total_nominal_web_compare, double total_nominal_edcf_compare, double total_nominal_all_compare, double jumlah_edc, double jumlah_web, double jumlah_all, double jumlah_edc_compare, double jumlah_web_compare, double jumlah_all_compare, double casa_web, double casa_edc, double casa_all, double casa_web_compare, double casa_edc_compare, double casa_all_compare, double total_transaksi_rka, double total_fee_rka, double total_jumlah_rka) {
        this.no = no;
        this.kode_kanwil = kode_kanwil;
        this.nama_kanwil = nama_kanwil;
        this.total_transaksi_web = total_transaksi_web;
        this.total_transaksi_edcf = total_transaksi_edcf;
        this.total_transaksi_all = total_transaksi_all;
        this.total_transaksi_web_compare = total_transaksi_web_compare;
        this.total_transaksi_edcf_compare = total_transaksi_edcf_compare;
        this.total_transaksi_all_compare = total_transaksi_all_compare;
        this.total_fee_web = total_fee_web;
        this.total_fee_edcf = total_fee_edcf;
        this.total_fee_all = total_fee_all;
        this.total_fee_web_compare = total_fee_web_compare;
        this.total_fee_edcf_compare = total_fee_edcf_compare;
        this.total_fee_all_compare = total_fee_all_compare;
        this.total_nominal_web = total_nominal_web;
        this.total_nominal_edcf = total_nominal_edcf;
        this.total_nominal_all = total_nominal_all;
        this.total_nominal_web_compare = total_nominal_web_compare;
        this.total_nominal_edcf_compare = total_nominal_edcf_compare;
        this.total_nominal_all_compare = total_nominal_all_compare;
        this.jumlah_edc = jumlah_edc;
        this.jumlah_web = jumlah_web;
        this.jumlah_all = jumlah_all;
        this.jumlah_edc_compare = jumlah_edc_compare;
        this.jumlah_web_compare = jumlah_web_compare;
        this.jumlah_all_compare = jumlah_all_compare;
        this.casa_web = casa_web;
        this.casa_edc = casa_edc;
        this.casa_all = casa_all;
        this.casa_web_compare = casa_web_compare;
        this.casa_edc_compare = casa_edc_compare;
        this.casa_all_compare = casa_all_compare;
        this.total_transaksi_rka = total_transaksi_rka;
        this.total_fee_rka = total_fee_rka;
        this.total_jumlah_rka = total_jumlah_rka;
    }

  

   
  
    
    
    public Beans_laporan_mingguan() {
        db = new Db1();
        this.connectionPool = db.getConnectionPool1();
    }

  
   
 

    
    
    private String generateSQLPencapaianKanwil(String tgl, String compare, String rka){
        String sql_pencapaian = "select data1.kode_kanwil, data1.nama_kanwil, data1.total_transaksi_web ,\n" +
"data1.total_transaksi_edcf , data1.total_transaksi_all,\n" +
"data2.total_transaksi_web as total_transaksi_web_"+compare+",\n" +
"data2.total_transaksi_edcf as total_transaksi_edcf_"+compare+" , data2.total_transaksi_all as total_transaksi_all_"+compare+",\n" +
"\n" +
"data1.total_fee_web, data1.total_fee_edcf,data1. total_fee_all,\n" +
"data2.total_fee_web as total_fee_web_"+compare+", data2.total_fee_edcf as total_fee_edcf_"+compare+" ,data2. total_fee_all as total_fee_all_"+compare+",\n" +
"\n" +
"\n" +
"data1.jumlah_web, data1.jumlah_edc, data1.jumlah_all,\n" +
" data2.jumlah_web as jumlah_web_"+compare+", data2.jumlah_edc as jumlah_edc_"+compare+", data2.jumlah_all as jumlah_all_"+compare+" ,\n" +
"data1.casa_web, data1.casa_edc, data1.casa_all,\n" +
"data2.casa_web as casa_web_"+compare+", data2.casa_edc as casa_edc_"+compare+", data2.casa_all as casa_all_"+compare+", \n" +
"\n" +"data1.total_nominal_web, data1.total_nominal_edcf, data1.total_nominal_all,"+
"data2.total_nominal_web as total_nominal_web_"+compare+", data2.total_nominal_edcf as total_nominal_edcf_"+compare+", data2.total_nominal_all as  total_nominal_all_"+compare+", "+               
"rka_j1.nominal as rka_jumlah_"+rka+", rka_j2.nominal as rka_jumlah_201812,\n" +
"\n" +
"rka_f1.nominal as rka_fee_"+rka+", rka_f2.nominal as rka_fee_201812,\n" +
"\n" +
"rka_t1.nominal as rka_trxfin_"+rka+", rka_t2.nominal as rka_trxfin_201812\n" +
"\n" +
" from (\n" +
"\n" +
"\n" +
"select  kode_kanwil, nama_kanwil, sum(total_transaksi_web) as total_transaksi_web, \n" +
"sum(total_transaksi_edcf) as total_transaksi_edcf, sum(total_transaksi_all) as total_transaksi_all, \n" +
"sum(total_fee_web) as total_fee_web, sum(total_fee_edcf) as total_fee_edcf, sum(total_fee_all) as total_fee_all,\n" +
"sum(total_nominal_web) as total_nominal_web, sum(total_nominal_edcf) as total_nominal_edcf, sum(total_nominal_all) as total_nominal_all,\n" +
"sum(jumlah_edc) as jumlah_edc, sum(jumlah_web) as jumlah_web, sum(jumlah_all) as jumlah_all, sum(jumlah_edc_bansos) as jumlah_edc_bansos,\n" +
"sum(casa_web) as casa_web, sum(casa_edc) as casa_edc, sum(casa_all) as casa_all\n" +
"\n" +
"from (\n" +
"\n" +
"\n" +
"\n" +
"select a.kode_kanwil, a.nama_kanwil as nama_kanwil, a.kode_cabang, a.nama_cabang, \n" +
"         total_transaksi_web ,    total_transaksi_edcf,           \n" +
"         total_transaksi_web + total_transaksi_edcf as total_transaksi_all,\n" +
"         total_fee_web, total_fee_edcf, \n" +
"              total_fee_web + total_fee_edcf as total_fee_all,\n" +
"              total_nominal_web, total_nominal_edcf, \n" +
"                 total_nominal_web + total_nominal_edcf as total_nominal_all,\n" +
"                                            jumlah_edc,  jumlah_web, jumlah_edc + jumlah_web as jumlah_all, jumlah_edc_bansos,   \n" +
"                \n" +
"                \n" +
"                cast(casa_web as unsigned ) as casa_web , cast(casa_edc as unsigned ) as casa_edc, \n" +
"                  cast(casa_web +  casa_edc as unsigned )  as casa_all\n" +
"            \n" +
"              \n" +
"from            \n" +
"\n" +
"(\n" +
"\n" +
"\n" +
"\n" +
"select a1.kode_kanwil, replace(a1.nama_kanwil,'KANWIL ','') as nama_kanwil, a1.kode_cabang, a1.nama_cabang ,  ifnull(total_transaksi_web,0) as total_transaksi_web , IFNULL(total_nominal_web,0) as total_nominal_web, \n" +
"ifnull(total_nominal_edcf,0) as total_nominal_edcf, ifnull(total_fee_web,0) as total_fee_web , IFNULL(total_fee_edcf,0) as total_fee_edcf, ifnull(total_transaksi_edcf,0) as total_transaksi_edcf ,\n" +
" ifnull(jumlah_edc,0) as jumlah_edc, ifnull(jumlah_web,0) as jumlah_web, ifnull(jumlah_edc_bansos,0) as jumlah_edc_bansos\n" +
"\n" +
"                \n" +
"                from ( select * from dwh_branch       where TIPE_KANTOR = 'CABANG'  and nama_uker1 not like 'VENDOR%' and kode_kanwil  not in ('S','T','U','V','Y') ) a1 \n" +
"              left outer join \n" +
"                (\n" +
"                \n" +
"                \n" +
"                select  kd_kanwil, kanwil,  kd_cabang , cabang, sum(ifnull(total_transaksi,0)) as total_transaksi_web, sum(replace(ifnull(total_nominal,0),',','')) as total_nominal_web \n" +
"                , sum(replace(ifnull(total_fee_bri,0),',','')) as total_fee_web  from   trx_web_"+tgl+"  \n" +
"            \n" +
"                 group by kd_kanwil, kd_cabang , kanwil,  kd_cabang , cabang\n" +
"                 \n" +
"                    \n" +
"                      ) a   \n" +
"                \n" +
"                 on (a1.KODE_CABANG = a.kd_cabang)\n" +
"                \n" +
"                \n" +
"             left outer join \n" +
"                  (              \n" +
"                 \n" +
"                    select kd_cabang , cabang, sum( ifnull(total_transaksi,0) ) as total_transaksi_edcf, sum(replace(total_nominal,',','')) as total_nominal_edcf,\n" +
"                 sum(replace(total_fee_bri,',','')) as total_fee_edcf   from trx_edc_fin_"+tgl+"                \n" +
"             \n" +
"                 group by kd_cabang ,  cabang\n" +
"                 \n" +
"                         \n" +
"                ) b on (a1.KODE_CABANG = b.kd_cabang) \n" +
"            left outer join             \n" +
"                (\n" +
"                select kd_cabang , count(*) as jumlah_edc  from   tid_edc_"+tgl+"             group by kd_cabang\n" +
"                ) edc on (a1.KODE_CABANG = edc.kd_cabang)     \n" +
"             left outer join \n" +
"                (\n" +
"                select kd_cabang , count(*) as jumlah_web  from   outlet_web_"+tgl+"            group by kd_cabang\n" +
"                ) web on (a1.KODE_CABANG = web.kd_cabang)\n" +
"       left outer join     \n" +
"       (\n" +
"                select kd_cabang , count(*) as jumlah_edc_bansos  from   tid_remarkbansos            group by kd_cabang\n" +
"                ) bansos on (a1.KODE_CABANG = bansos.kd_cabang)\n" +
"           \n" +
"          \n" +
"            \n" +
") a\n" +
"inner join\n" +
"(\n" +
"select a1.kode_kanwil, a1.nama_kanwil, a1.kode_cabang, a1.nama_cabang,ifnull(casa_edc,0) as casa_edc, ifnull(casa_web,0) casa_web\n" +
"    from ( select * from dwh_branch       where TIPE_KANTOR = 'CABANG' and nama_uker1 not like 'VENDOR%' and kode_kanwil  not in ('S','T','U','V','Y') ) a1 \n" +
"left outer join    \n" +
"                 (    \n" +
"                 \n" +
"                                          select a.kd_cabang,  sum( replace(`casa_(rp#)`,',','')) as casa_edc from  mid_edc_"+tgl+" a inner join  tid_edc_"+tgl+" b\n" +
"                 on (a.mid = b.mid)      group by a.kd_cabang         \n" +
"\n" +
"                )ca on (a1.KODE_CABANG =ca.kd_cabang) \n" +
"                left outer join     \n" +
"                 (                               \n" +
"                          select a.kd_cabang,  sum( replace(casa,',',''))  as casa_web from agen_web_"+tgl+" a inner join outlet_web_"+tgl+" b\n" +
"                 on (a.id_merchant = b.id_merchant)      group by a.kd_cabang    \n" +
"                )caw on (a1.KODE_CABANG =caw.kd_cabang) \n" +
"                ) b on (a.kode_cabang = b.kode_cabang)\n" +
"                \n" +
") data group by kode_kanwil, nama_kanwil\n" +
"\n" +
"\n" +
")  data1 inner join\n" +
"(select kode_kanwil, nama_kanwil \n" +
", sum(total_transaksi_web) as total_transaksi_web, \n" +
"sum(total_transaksi_edcf) as total_transaksi_edcf, sum(total_transaksi_all) as total_transaksi_all, \n" +
"sum(total_fee_web) as total_fee_web, sum(total_fee_edcf) as total_fee_edcf, sum(total_fee_all) as total_fee_all,\n" +
"sum(total_nominal_web) as total_nominal_web, sum(total_nominal_edcf) as total_nominal_edcf, sum(total_nominal_all) as total_nominal_all,\n" +
"sum(jumlah_edc) as jumlah_edc, sum(jumlah_web) as jumlah_web, sum(jumlah_all) as jumlah_all,\n" +
"sum(casa_web) as casa_web, sum(casa_edc) as casa_edc, sum(casa_all) as casa_all\n" +
"from view_pencapaian_kanca_"+compare+" group by kode_kanwil, nama_kanwil)\n" +
"data2 \n" +
"on (data1.kode_kanwil = data2.kode_kanwil)\n" +
"inner join\n" +
" ( select kd_kanwil, nominal  from rka_all where bulan = '"+rka+"' and rka ='jumlah')\n" +
" rka_j1\n" +
" on (data1.kode_kanwil = rka_j1.kd_kanwil)\n" +
" inner join\n" +
" ( select kd_kanwil, nominal  from rka_all where bulan = '201812' and rka ='jumlah')\n" +
" rka_j2\n" +
" on (data1.kode_kanwil = rka_j2.kd_kanwil)\n" +
" inner join\n" +
" ( select kd_kanwil, nominal  from rka_all where bulan = '"+rka+"' and rka ='fbi')\n" +
" rka_f1\n" +
" on (data1.kode_kanwil = rka_f1.kd_kanwil)\n" +
" inner join\n" +
" ( select kd_kanwil, nominal  from rka_all where bulan = '201812' and rka ='fbi')\n" +
" rka_f2\n" +
" on (data1.kode_kanwil = rka_f2.kd_kanwil)\n" +
" inner join\n" +
" ( select kd_kanwil, nominal  from rka_all where bulan = '"+rka+"' and rka ='trxfin')\n" +
" rka_t1\n" +
" on (data1.kode_kanwil = rka_t1.kd_kanwil)\n" +
" inner join\n" +
" ( select kd_kanwil, nominal  from rka_all where bulan = '201812' and rka ='trxfin')\n" +
" rka_t2\n" +
" on (data1.kode_kanwil = rka_t2.kd_kanwil)";
        
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

    public int getNo() {
        return no;
    }

    public String getKode_kanwil() {
        return kode_kanwil;
    }

    public String getNama_kanwil() {
        return nama_kanwil;
    }

    public double getTotal_transaksi_web() {
        return total_transaksi_web;
    }

    public double getTotal_transaksi_edcf() {
        return total_transaksi_edcf;
    }

    public double getTotal_transaksi_all() {
        return total_transaksi_all;
    }

    public double getTotal_transaksi_web_compare() {
        return total_transaksi_web_compare;
    }

    public double getTotal_transaksi_edcf_compare() {
        return total_transaksi_edcf_compare;
    }

    public double getTotal_transaksi_all_compare() {
        return total_transaksi_all_compare;
    }

    public double getTotal_fee_web() {
        return total_fee_web;
    }

    public double getTotal_fee_edcf() {
        return total_fee_edcf;
    }

    public double getTotal_fee_all() {
        return total_fee_all;
    }

    public double getTotal_fee_web_compare() {
        return total_fee_web_compare;
    }

    public double getTotal_fee_edcf_compare() {
        return total_fee_edcf_compare;
    }

    public double getTotal_fee_all_compare() {
        return total_fee_all_compare;
    }

    public double getTotal_nominal_web() {
        return total_nominal_web;
    }

    public double getTotal_nominal_edcf() {
        return total_nominal_edcf;
    }

    public double getTotal_nominal_all() {
        return total_nominal_all;
    }

    public double getTotal_nominal_web_compare() {
        return total_nominal_web_compare;
    }

    public double getTotal_nominal_edcf_compare() {
        return total_nominal_edcf_compare;
    }

    public double getTotal_nominal_all_compare() {
        return total_nominal_all_compare;
    }

    public double getJumlah_edc() {
        return jumlah_edc;
    }

    public double getJumlah_web() {
        return jumlah_web;
    }

    public double getJumlah_all() {
        return jumlah_all;
    }

    public double getJumlah_edc_compare() {
        return jumlah_edc_compare;
    }

    public double getJumlah_web_compare() {
        return jumlah_web_compare;
    }

    public double getJumlah_all_compare() {
        return jumlah_all_compare;
    }

    public double getCasa_web() {
        return casa_web;
    }

    public double getCasa_edc() {
        return casa_edc;
    }

    public double getCasa_all() {
        return casa_all;
    }

    public double getCasa_web_compare() {
        return casa_web_compare;
    }

    public double getCasa_edc_compare() {
        return casa_edc_compare;
    }

    public double getCasa_all_compare() {
        return casa_all_compare;
    }

    public double getTotal_transaksi_rka() {
        return total_transaksi_rka;
    }

    public double getTotal_fee_rka() {
        return total_fee_rka;
    }

    public double getTotal_jumlah_rka() {
        return total_jumlah_rka;
    }

    public double getGrand_transaksi_web() {
        return grand_transaksi_web;
    }

    public double getGrand_transaksi_edcf() {
        return grand_transaksi_edcf;
    }

    public double getGrand_transaksi_all() {
        return grand_transaksi_all;
    }

    public double getGrand_transaksi_web_compare() {
        return grand_transaksi_web_compare;
    }

    public double getGrand_transaksi_edcf_compare() {
        return grand_transaksi_edcf_compare;
    }

    public double getGrand_transaksi_all_compare() {
        return grand_transaksi_all_compare;
    }

    public double getGrand_fee_web() {
        return grand_fee_web;
    }

    public double getGrand_fee_edcf() {
        return grand_fee_edcf;
    }

    public double getGrand_fee_all() {
        return grand_fee_all;
    }

    public double getGrand_fee_web_compare() {
        return grand_fee_web_compare;
    }

    public double getGrand_fee_edcf_compare() {
        return grand_fee_edcf_compare;
    }

    public double getGrand_fee_all_compare() {
        return grand_fee_all_compare;
    }

    public double getGrand_jumlah_edc() {
        return grand_jumlah_edc;
    }

    public double getGrand_jumlah_web() {
        return grand_jumlah_web;
    }

    public double getGrand_jumlah_all() {
        return grand_jumlah_all;
    }

    public double getGrand_jumlah_edc_compare() {
        return grand_jumlah_edc_compare;
    }

    public double getGrand_jumlah_web_compare() {
        return grand_jumlah_web_compare;
    }

    public double getGrand_jumlah_all_compare() {
        return grand_jumlah_all_compare;
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

    public double getGrand_casa_web_compare() {
        return grand_casa_web_compare;
    }

    public double getGrand_casa_edc_compare() {
        return grand_casa_edc_compare;
    }

    public double getGrand_casa_all_compare() {
        return grand_casa_all_compare;
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

    public Db1 getDb() {
        return db;
    }

    public J2EEConnectionPool getConnectionPool() {
        return connectionPool;
    }

  
      

}

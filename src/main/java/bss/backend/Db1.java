/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend;



import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.ui.ComboBox;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author TOSHIBA
 */
public class Db1 {
    

private J2EEConnectionPool connectionPool_Brilink = null;
private J2EEConnectionPool connectionPool_Ebanking = null;


private SQLContainer container = null;
public static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();

static {
    otherSymbols.setDecimalSeparator(',');
    otherSymbols.setGroupingSeparator('.');
}

public static DecimalFormat Format = new DecimalFormat( "###,###,###,###",otherSymbols );
public static DecimalFormat Format_decimal = new DecimalFormat( "###,###,###,##0.00",otherSymbols );
public static DecimalFormat Format_decimal_percent = new DecimalFormat( "###.## '%'",otherSymbols );   

public Db1(){
    initConnectionPool();
}

private void initConnectionPool() {
    
        try {
          Context ctx = new InitialContext();
	  DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/brilink");
          connectionPool_Brilink  = new J2EEConnectionPool(ds);
          
          Context ctx2 = new InitialContext();
	  DataSource ds2 = (DataSource)ctx2.lookup("java:comp/env/jdbc/ebanking");
          connectionPool_Ebanking  = new J2EEConnectionPool(ds2);
        } catch (Exception e) {           
            e.printStackTrace();
        }
    }


 J2EEConnectionPool getConnectionPool1(){
     return connectionPool_Brilink;
 }
 


public J2EEConnectionPool getConnectionPool_OJL() {
        return connectionPool_Ebanking;
}




  public void getDataComboBox(String sql, ComboBox combo) throws SQLException{
       
        Connection c = connectionPool_Ebanking.reserveConnection();       
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
      //  combo.clear();
        while (rs.next()){
            System.out.println("SSS");
          //  combo.addItems(rs.getString(1));
        }
        c.close();
        connectionPool_Ebanking.releaseConnection(c);
    }
 
 
 
 

 
 
 
 public static String  SQL_detailfo(String fo,String branch, String pembanding, String pencapaian,String tipe){
    
     String sql = 
            "SELECT  (@curRow := @curRow + 1) AS ID, A.KANCA, A.FO, A.NO_REK, A.NAMA, B.SALDO AS `TABUNGAN_1`, A.SALDO AS `TABUNGAN_2`,\n" +
            "( case when A.SALDO is null then 0 else A.SALDO end - case when B.SALDO is null then 0 else B.SALDO end   ) AS DELTA       \n" +
            " from (\n" +
            "select a.id as ID, a.KANCA as KANCA , a.FO, a.no_rek, a.cif, a.nama, b.balance as SALDO  from djs_fo_rekening_all a inner join\n" +
            "(select * from di319 where  branch = '269'  and periode = '2015-12-31') b on (a.no_rek = b.acc_num)\n" +
            " where a.fo = '#FO' and a.tipe = 'TABUNGAN' )a\n" +
            " inner join(\n" +
            "select a.id as ID, a.KANCA as KANCA , a.FO, a.no_rek, a.cif, a.nama, b.balance as SALDO  from djs_fo_rekening_all a inner join\n" +
            "(select * from di319 where  branch = '269'  and periode = '2016-06-30') b on (a.no_rek = b.acc_num)\n" +
            " where a.fo = 'I Putu ' and a.tipe = 'TABUNGAN' )\n" +
            " b on (a.no_rek = b.no_rek)\n" +
            " INNER JOIN  (SELECT @curRow := 0) r"; 
     
     
     
     
     
     String sql1 = 
              "SELECT (@curRow := @curRow + 1) AS ID, A.* FROM ( "+
             "SELECT  (@curRow := @curRow + 1) AS ID, A.KANCA, A.FO, A.NO_REK, A.NAMA, B.SALDO AS `TABUNGAN_1`, A.SALDO AS `TABUNGAN_2`,\n" +
           " ( case when A.SALDO is null then 0 else A.SALDO end - case when B.SALDO is null then 0 else B.SALDO end   ) AS DELTA FROM \n" +
           " (\n" +
           " select a.id as ID, a.KANCA as KANCA , a.FO, a.no_rek, a.cif, a.nama, b.balance as SALDO  from djs_fo_rekening_all a inner join\n" +
           "(select * from di319 where  branch = '#BRANCH'  and periode = '#PEMBANDING') b on (a.no_rek = b.acc_num) where  \n" +
           " a.fo = '#FO' and a.tipe = '#TIPE' \n" +
           " ) A\n" +
           "left outer join\n" +
           "(\n" +
           "select a.id as ID, a.KANCA as KANCA , a.FO, a.no_rek, a.cif, a.nama, b.balance as SALDO  from djs_fo_rekening_all a inner join\n" +
           "(select * from di319 where  branch = '#BRANCH'  and periode = '#PENCAPAIAN') b on (a.no_rek = b.acc_num) where  \n" +
           " a.fo = '#FO' and a.tipe = '#TIPE' \n" +
           ") B \n" +
           "ON (A.no_rek = B.no_rek )\n" +
           "ORDER BY ( case when A.SALDO is null then 0 else A.SALDO end - case when B.SALDO is null then 0 else B.SALDO end   ) "+
            ") A INNER JOIN  (SELECT @curRow := 0) r limit 200"; 
     sql1 = sql1.replaceAll("#FO", fo);
     sql1 = sql1.replaceAll("#BRANCH", branch);
     sql1 = sql1.replaceAll("#PEMBANDING", pembanding);
     sql1 = sql1.replaceAll("#PENCAPAIAN",pencapaian);
     sql1 = sql1.replaceAll("#TIPE", tipe);
     
     
     return sql1;
 }
 
}
 
 
 
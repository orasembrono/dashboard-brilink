/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend.tools_lib;

import bss.backend.Beans_fraud_warning;
import bss.backend.Beans_laporan_bulanan_kanwil;
import bss.backend.tools_lib.AdvancedFileDownloader.AdvancedDownloaderListener;
import bss.view.Fraud_warning;
import com.vaadin.data.provider.Query;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Stream;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author IVAN
 */
public class Excel_fraud_warning {
    
    public  String path = "/WEB-INF/";
    public static String Sheet_EDC ="EDC";
    public static String Sheet_Mobile ="Mobile";
    String ExcelFile;
    String tipe_report;
   
  
    
    
    public Button createButtonExportExcel(String tipe_report){
        this.tipe_report = tipe_report;
        Button btn = new Button("Export");
        btn.setIcon(VaadinIcons.DOWNLOAD_ALT);
         ExcelFile = generatefilepath();
        Resource res = new FileResource(new File(ExcelFile));
         final AdvancedFileDownloader downloader = new AdvancedFileDownloader(res);
        downloader.addAdvancedDownloaderListener(new AdvancedDownloaderListener() {
                 @Override
            public void beforeDownload(AdvancedFileDownloader.DownloaderEvent downloadEvent) {
              createExcel();
              }
            });
        downloader.extend(btn);
        return btn;
    }
    
    public void createExcel(){
        try {
           
           
            
            Beans_fraud_warning beans_fraud_warning = new Beans_fraud_warning();
            Beans_fraud_warning beans_fraud_warning_mob = new Beans_fraud_warning();
            Collection<Beans_fraud_warning> data1 = beans_fraud_warning.getData(tipe_report,Beans_fraud_warning.EDC);
            Collection<Beans_fraud_warning> data2 = beans_fraud_warning_mob.getData(tipe_report,Beans_fraud_warning.MOBILE);
         
  
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(Sheet_EDC);  
            
            HSSFRow rowtitle = sheet.createRow((short)0);
            rowtitle.createCell(0).setCellValue("TIPE REPORT : EDC / "+tipe_report);
            
            HSSFRow rowhead = sheet.createRow((short)1);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("Tid");
            rowhead.createCell(2).setCellValue("Mid");
            rowhead.createCell(3).setCellValue("Nama Agen");
            rowhead.createCell(4).setCellValue("Kanwil");
            rowhead.createCell(5).setCellValue("Fee");
            rowhead.createCell(6).setCellValue("Transaksi");
            rowhead.createCell(7).setCellValue("Vol per Trx");  
            rowhead.createCell(8).setCellValue("Volume");
            
            short i = 1;
            for ( Beans_fraud_warning  beans_fraud_warning1 : data1){
                System.out.println(i);
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(beans_fraud_warning1.getNo());
                row.createCell(1).setCellValue(beans_fraud_warning1.getTid());
                row.createCell(2).setCellValue(beans_fraud_warning1.getMid());
                row.createCell(3).setCellValue(beans_fraud_warning1.getNama_agen());
                row.createCell(4).setCellValue(beans_fraud_warning1.getKanwil());
                row.createCell(5).setCellValue(beans_fraud_warning1.getFee());
                row.createCell(6).setCellValue(beans_fraud_warning1.getTransaksi());
                row.createCell(7).setCellValue(beans_fraud_warning1.getVol_pertrx());
                row.createCell(8).setCellValue(beans_fraud_warning1.getVolume());
            }
            
     
            HSSFSheet sheet2 = workbook.createSheet(Sheet_Mobile);  
            
            HSSFRow rowtitle2 = sheet2.createRow((short)0);
            rowtitle2.createCell(0).setCellValue("TIPE REPORT : Mobile / "+tipe_report);
          
            
            HSSFRow rowhead2 = sheet2.createRow((short)1);
            rowhead2.createCell(0).setCellValue("No.");
            rowhead2.createCell(1).setCellValue("Tid");
            rowhead2.createCell(2).setCellValue("Mid");
            rowhead2.createCell(3).setCellValue("Nama Agen");
            rowhead2.createCell(4).setCellValue("Kanwil");
            rowhead2.createCell(5).setCellValue("Fee");
            rowhead2.createCell(6).setCellValue("Transaksi");
            rowhead2.createCell(7).setCellValue("Vol per Trx");  
            rowhead2.createCell(8).setCellValue("Volume");
            
             i = 1;
            for ( Beans_fraud_warning  beans_fraud_warning2 : data2){
                System.out.println(i);
                HSSFRow row = sheet2.createRow(++i);
                row.createCell(0).setCellValue(beans_fraud_warning2.getNo());
                row.createCell(1).setCellValue(beans_fraud_warning2.getTid());
                row.createCell(2).setCellValue(beans_fraud_warning2.getMid());
                row.createCell(3).setCellValue(beans_fraud_warning2.getNama_agen());
                row.createCell(4).setCellValue(beans_fraud_warning2.getKanwil());
                row.createCell(5).setCellValue(beans_fraud_warning2.getFee());
                row.createCell(6).setCellValue(beans_fraud_warning2.getTransaksi());
                row.createCell(7).setCellValue(beans_fraud_warning2.getVol_pertrx());
                row.createCell(8).setCellValue(beans_fraud_warning2.getVolume());
            }
            

           
           

            FileOutputStream fileOut = new FileOutputStream(ExcelFile);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
        
        
    }

    
    
    
    private String generatefilepath(){
        return VaadinService.getCurrent().getBaseDirectory().
                       getAbsolutePath()+ path + "warning"+String.valueOf(System.currentTimeMillis())+".xls";
    }
     
}

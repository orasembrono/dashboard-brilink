/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend.tools_lib;

import bss.backend.Beans_laporan_harian_jumlah_kanca;
import bss.backend.Beans_laporan_harian_jumlah_kanwil;
import bss.backend.Beans_laporan_harian_kanwil;
import bss.backend.tools_lib.AdvancedFileDownloader.AdvancedDownloaderListener;
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
public class Excel_laporan_harian_jumlah_kanca {
    
    public  String path = "/WEB-INF/";
    public static String Sheet ="Sheet1";
    String ExcelFile;
    String tanggal;
    String kd_kanwil;
    String nama_kanwil;
  
    
    
    public Button createButtonExportExcel(String tanggal, String kd_kanwil, String nama_kanwil){
        this.tanggal = tanggal;
        this.kd_kanwil = kd_kanwil;
        this.nama_kanwil = nama_kanwil;
        
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
           
            if (tanggal == null ){
                throw new Exception("Tanggal belum dipilih");
            }
            
            Beans_laporan_harian_jumlah_kanca pencapaian_brilink_kanca = new Beans_laporan_harian_jumlah_kanca();
            Collection<Beans_laporan_harian_jumlah_kanca> data1 = pencapaian_brilink_kanca.getData(tanggal,kd_kanwil);
            
          //  kode_kanwil	nama_kanwil						
           //dc_trx	web_trx	trx_all	bep_edc	bep_web	bep_all	jumlah_edc	jumlah_web	jumlah_all	jumlah_edc_2017	jumlah_web_2017	jumlah_all_2017	casa_web	casa_edc	casa_all

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(Sheet);  
      
            HSSFRow rowcaption = sheet.createRow((short)0);
            rowcaption.createCell(0).setCellValue("Laporan harian kuantitas kanca : "+nama_kanwil+" posisi :"+tanggal);
            
            HSSFRow rowhead = sheet.createRow((short)1);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("Kanca");
            rowhead.createCell(2).setCellValue("Jumlah All");
            rowhead.createCell(3).setCellValue("Jumlah Brimob");
            rowhead.createCell(4).setCellValue("Delta Brimob");
            rowhead.createCell(5).setCellValue("Jumah EDC");
            rowhead.createCell(6).setCellValue("Delta EDC");
            
           
            
            short i = 1;
          
            for ( Beans_laporan_harian_jumlah_kanca  pencapaian_brilink_kanwil1 : data1){
                System.out.println(i);
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(pencapaian_brilink_kanwil1.getNo());
                row.createCell(1).setCellValue(pencapaian_brilink_kanwil1.getNama_kanca());
                row.createCell(2).setCellValue(pencapaian_brilink_kanwil1.getTotal_all() );
                row.createCell(3).setCellValue(pencapaian_brilink_kanwil1.getJumlah_web());
                row.createCell(4).setCellValue(pencapaian_brilink_kanwil1.getJumlah_web_delta());
                row.createCell(5).setCellValue(pencapaian_brilink_kanwil1.getJumlah_edc());
                row.createCell(6).setCellValue(pencapaian_brilink_kanwil1.getJumlah_edc_delta());
                
                // end of record
                if ( (i - 1) >= data1.size() ){
                    row = sheet.createRow(++i);
                    row.createCell(0).setCellValue("");
                    row.createCell(1).setCellValue("Total");
                    row.createCell(2).setCellValue(pencapaian_brilink_kanca.getGrand_total_all() );
                    row.createCell(3).setCellValue(pencapaian_brilink_kanca.getGrand_jumlah_web());
                    row.createCell(4).setCellValue(pencapaian_brilink_kanca.getGrand_jumlah_web_delta());
                    row.createCell(5).setCellValue(pencapaian_brilink_kanca.getGrand_jumlah_edc());
                    row.createCell(6).setCellValue(pencapaian_brilink_kanca.getGrand_jumlah_edc_delta());
           
                }
                
             }
                

            FileOutputStream fileOut = new FileOutputStream(ExcelFile);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
        
        
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    
    
    private String generatefilepath(){
        return VaadinService.getCurrent().getBaseDirectory().
                       getAbsolutePath()+ path + "file_kanca_jumlah"+String.valueOf(System.currentTimeMillis())+".xls";
    }
     
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend.tools_lib;

import bss.backend.Beans_laporan_harian_kanca;
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
public class Excel_laporan_harian_kanca {
    
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
            
            Beans_laporan_harian_kanca pencapaian_brilink_kanca = new Beans_laporan_harian_kanca();
            Collection<Beans_laporan_harian_kanca> data1 = pencapaian_brilink_kanca.getData(tanggal,"all");
            
          //  kode_kanca	nama_kanca						
           //dc_trx	web_trx	trx_all	bep_edc	bep_web	bep_all	jumlah_edc	jumlah_web	jumlah_all	jumlah_edc_2017	jumlah_web_2017	jumlah_all_2017	casa_web	casa_edc	casa_all

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(Sheet);  
            
            HSSFRow rowcaption = sheet.createRow((short)0);
            rowcaption.createCell(0).setCellValue("Laporan harian kanca posisi :"+tanggal);
          
      
            HSSFRow rowhead = sheet.createRow((short)1);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("kode_kanwil");
            rowhead.createCell(2).setCellValue("nama_kanwil");
            rowhead.createCell(3).setCellValue("kode_kanca");
            rowhead.createCell(4).setCellValue("Nama Kanca");
            rowhead.createCell(5).setCellValue("Transaksi EDC");
            rowhead.createCell(6).setCellValue("Transaksi Mob");
            rowhead.createCell(7).setCellValue("Transaksi All"); 
            rowhead.createCell(8).setCellValue("FBI EDC");
            rowhead.createCell(9).setCellValue("FBI Mob");
            rowhead.createCell(10).setCellValue("FBI All");  
            rowhead.createCell(11).setCellValue("Volume EDC");
            rowhead.createCell(12).setCellValue("Volume Mob");
            rowhead.createCell(13).setCellValue("Volume All");
          
            
            short i = 1;
            for ( Beans_laporan_harian_kanca  pencapaian_brilink_kanca1 : data1){
                System.out.println(i);
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(pencapaian_brilink_kanca1.getNo());
                row.createCell(1).setCellValue(pencapaian_brilink_kanca1.getKode_kanwil());
                row.createCell(2).setCellValue(pencapaian_brilink_kanca1.getNama_kanwil());
                row.createCell(3).setCellValue(pencapaian_brilink_kanca1.getKode_cabang());
                row.createCell(4).setCellValue(pencapaian_brilink_kanca1.getNama_cabang());
                row.createCell(5).setCellValue(pencapaian_brilink_kanca1.getTotal_transaksi_edcf());
                row.createCell(6).setCellValue(pencapaian_brilink_kanca1.getTotal_fee_web());
                row.createCell(7).setCellValue(pencapaian_brilink_kanca1.getTotal_transaksi_all());
                row.createCell(8).setCellValue(pencapaian_brilink_kanca1.getTotal_fee_edcf());
                row.createCell(9).setCellValue(pencapaian_brilink_kanca1.getTotal_fee_web());
                row.createCell(10).setCellValue(pencapaian_brilink_kanca1.getTotal_fee_all()); 
                row.createCell(11).setCellValue(pencapaian_brilink_kanca1.getTotal_nominal_edcf());
                row.createCell(12).setCellValue(pencapaian_brilink_kanca1.getTotal_nominal_web());
                row.createCell(13).setCellValue(pencapaian_brilink_kanca1.getTotal_nominal_all());
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
                       getAbsolutePath()+ path + "file_kanca"+String.valueOf(System.currentTimeMillis())+".xls";
    }
     
}

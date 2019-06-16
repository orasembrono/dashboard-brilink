/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend.tools_lib;

import bss.backend.Beans_laporan_bulanan_kanwil;
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
public class Excel_laporan_bulanan_kanwil {
    
    public  String path = "/WEB-INF/";
    public static String Sheet ="Sheet1";
    String ExcelFile;
    String tanggal;
  
    
    
    public Button createButtonExportExcel(String tanggal){
        this.tanggal = tanggal;
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
            
            Beans_laporan_bulanan_kanwil pencapaian_brilink_kanwil = new Beans_laporan_bulanan_kanwil();
            Collection<Beans_laporan_bulanan_kanwil> data1 = pencapaian_brilink_kanwil.getData(tanggal);
            
          //  kode_kanwil	nama_kanwil						
           //dc_trx	web_trx	trx_all	bep_edc	bep_web	bep_all	jumlah_edc	jumlah_web	jumlah_all	jumlah_edc_2017	jumlah_web_2017	jumlah_all_2017	casa_web	casa_edc	casa_all

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(Sheet);  
      
            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("kanwil");
            rowhead.createCell(2).setCellValue("Penc Jumlah");
            rowhead.createCell(3).setCellValue("Penc FBI");
            rowhead.createCell(4).setCellValue("Penc Transaksi Fin");
            rowhead.createCell(5).setCellValue("Penc BEP");
            rowhead.createCell(6).setCellValue("Penc Bertransaksi");
            rowhead.createCell(7).setCellValue("Penc Casa");
            
            rowhead.createCell(8).setCellValue("Jumlah EDC");
            rowhead.createCell(9).setCellValue("Jumlah Mob");
            rowhead.createCell(10).setCellValue("Jumlah All");
            rowhead.createCell(11).setCellValue("RKA Jumlah");
            rowhead.createCell(12).setCellValue("Transaksi EDC");
            rowhead.createCell(13).setCellValue("Transaksi Mob");
            rowhead.createCell(14).setCellValue("Transaksi All");
            rowhead.createCell(15).setCellValue("RKA transaksi");
            rowhead.createCell(16).setCellValue("FBI EDC");
            rowhead.createCell(17).setCellValue("FBI Mob");
            rowhead.createCell(18).setCellValue("FBI All");
            rowhead.createCell(19).setCellValue("RKA FBI");
            rowhead.createCell(20).setCellValue("EDC bertrx");
            rowhead.createCell(21).setCellValue("Mob bertrx");
            rowhead.createCell(22).setCellValue("EDC BEP");
            rowhead.createCell(23).setCellValue("CASA");
            
            short i = 0;
            for ( Beans_laporan_bulanan_kanwil  pencapaian_brilink_kanwil1 : data1){
                System.out.println(i);
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(pencapaian_brilink_kanwil1.getNo());
                row.createCell(1).setCellValue(pencapaian_brilink_kanwil1.getNama_kanwil());
                row.createCell(2).setCellValue(pencapaian_brilink_kanwil1.getPencapaian_jumlah());
                row.createCell(3).setCellValue(pencapaian_brilink_kanwil1.getPencapaianFBI());
                row.createCell(4).setCellValue(pencapaian_brilink_kanwil1.getPencapaian_transaksi());
                row.createCell(5).setCellValue(pencapaian_brilink_kanwil1.getPencapaian_bep());
                row.createCell(6).setCellValue(pencapaian_brilink_kanwil1.getPencapaian_bertrx());
                row.createCell(7).setCellValue(pencapaian_brilink_kanwil1.getPencapaian_casa());
                row.createCell(8).setCellValue(pencapaian_brilink_kanwil1.getJumlah_edc());
                row.createCell(9).setCellValue(pencapaian_brilink_kanwil1.getJumlah_web());
                row.createCell(10).setCellValue(pencapaian_brilink_kanwil1.getJumlah_all());
                row.createCell(11).setCellValue(pencapaian_brilink_kanwil1.getRka_jumlah());
                row.createCell(12).setCellValue(pencapaian_brilink_kanwil1.getTotal_transaksi_edcf());
                row.createCell(13).setCellValue(pencapaian_brilink_kanwil1.getTotal_fee_web());
                row.createCell(14).setCellValue(pencapaian_brilink_kanwil1.getTotal_transaksi_all());
                row.createCell(15).setCellValue(pencapaian_brilink_kanwil1.getRka_trx());
                row.createCell(16).setCellValue(pencapaian_brilink_kanwil1.getTotal_fee_edcf());
                row.createCell(17).setCellValue(pencapaian_brilink_kanwil1.getTotal_fee_web());
                row.createCell(18).setCellValue(pencapaian_brilink_kanwil1.getTotal_fee_all());
                row.createCell(19).setCellValue(pencapaian_brilink_kanwil1.getRka_fbi());
                row.createCell(20).setCellValue(pencapaian_brilink_kanwil1.getEdc_trx());
                row.createCell(21).setCellValue(pencapaian_brilink_kanwil1.getWeb_trx());
                row.createCell(22).setCellValue(pencapaian_brilink_kanwil1.getBep_edc());
                row.createCell(23).setCellValue(pencapaian_brilink_kanwil1.getCasa_all());
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
                       getAbsolutePath()+ path + "file_kanwil"+String.valueOf(System.currentTimeMillis())+".xls";
    }
     
}

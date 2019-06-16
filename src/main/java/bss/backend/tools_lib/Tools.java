/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.backend.tools_lib;

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
public class Tools {
    
    public  String path = "/WEB-INF/file1.xls";
    public static String Sheet ="Sheet1";
    
    String ExcelFile;
    
    public Button createButtonExportExcel(){
     
        Button btn = new Button("Export");
        ExcelFile = VaadinService.getCurrent().getBaseDirectory().
                       getAbsolutePath()+path;
        btn.setIcon(VaadinIcons.DOWNLOAD_ALT);
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
           
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(Sheet);  

            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("Name");
            rowhead.createCell(2).setCellValue("Address");
            rowhead.createCell(3).setCellValue("Email");

            HSSFRow row = sheet.createRow((short)1);
            row.createCell(0).setCellValue("1");
            row.createCell(1).setCellValue("ivan ari wibawa");
            row.createCell(2).setCellValue("indoensisa");
            row.createCell(3).setCellValue("ivan.ari.w@gmail.com");

            FileOutputStream fileOut = new FileOutputStream(ExcelFile);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");

        } catch ( Exception ex ) {
            System.out.println(ex);
        }
        
        
    }
    
    
      public void createExcel(Collection  col){
        try {
            System.out.println("test");
           for (Object data : col){
               System.out.println("test");
               Field [] attributes =  data.getClass().getDeclaredFields();   
             
                        for (Field field : attributes) {
                       // Dynamically read Attribute Name
                       System.out.println("ATTRIBUTE NAME: " + field.getName());

                       try {
                           // Dynamically set Attribute Value
                           PropertyUtils.setSimpleProperty(data, field.getName(), "A VALUE");
                           System.out.println("ATTRIBUTE VALUE: " + PropertyUtils.getSimpleProperty(data, field.getName()));
                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                   }
             
             
           }
            
         

        } catch ( Exception ex ) {
            System.out.println(ex);
        }
        
        
    }
    
}

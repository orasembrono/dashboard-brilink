/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Beans_fraud_warning;
import bss.backend.Beans_laporan_bulanan_kanwil;
import bss.backend.Db1;
import bss.backend.tools_lib.Excel_fraud_warning;
import bss.backend.tools_lib.Excel_laporan_bulanan_kanwil;
import static bss.view.Laporan_bulanan_kanwil.GREEN_UP;
import static bss.view.Laporan_bulanan_kanwil.ORANGE_UP;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author ivanariwibawa
 */
public class Fraud_warning extends VerticalLayout {
    
     Grid<Beans_fraud_warning> grid_edc;
     Grid<Beans_fraud_warning> grid_mobile;
     ComboBox combo_segmen;
     Button button_go;
     Component label_caption;
     public static String TIPES [] = {"FEE","TRANSAKSI","VOL_PER_TRX","VOLUME"};
     TabSheet sheet = new TabSheet();
     VerticalLayout sheetEDC = new VerticalLayout();
     VerticalLayout sheetMobile = new VerticalLayout();
     HorizontalLayout upanel = new HorizontalLayout(); 
     Button  buttonExport;
     Excel_fraud_warning excel_button;
     
  
     
     public Fraud_warning() {
        this.setSizeFull();
        
        excel_button = new Excel_fraud_warning();
        grid_edc = new Grid<Beans_fraud_warning>("");
      
        grid_edc.setSizeFull();
        grid_mobile = new Grid<Beans_fraud_warning>("");
        grid_mobile.setSizeFull();
        
        combo_segmen = new ComboBox();
        combo_segmen.setItems(TIPES);
        combo_segmen.setEmptySelectionAllowed(false);
        combo_segmen.setSelectedItem(TIPES[0]);//default
        
        button_go = new Button("Go");
         button_go.addClickListener((event) -> {
            System.out.println(combo_segmen.getValue().toString());
            refreshGridEdc(combo_segmen.getValue().toString());
            refreshGridMobile(combo_segmen.getValue().toString());
            
              showButtonExport();
        }); 
        label_caption = buildHeader("Fraud Detector");
        
        sheetEDC.setSizeFull();
        sheetMobile.setSizeFull();
        sheet.setSizeFull();
        sheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        sheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        
        
        // upper panel
        upanel = new HorizontalLayout();
        HorizontalLayout upanel1 = new HorizontalLayout();
        upanel1.addComponent(label_caption);
        upanel1.addComponent(combo_segmen);
        upanel1.addComponent(button_go);
        upanel.setSizeFull();
        upanel.addComponent(upanel1);
        upanel.setComponentAlignment(upanel1, Alignment.MIDDLE_LEFT);
        
        sheetEDC.addComponent(grid_edc);
        sheetMobile.addComponent(grid_mobile);
        sheet.addTab(sheetEDC,"EDC");
        sheet.addTab(sheetMobile,"Mobile");
        
        addComponent(upanel);
        addComponent(sheet);
        setExpandRatio(upanel, 0.7f);
        setExpandRatio(sheet, 9.3f);
        
       
    }
     
     
    
      private Component buildHeader(String label) {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        Label titleLabel = new Label(label);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H2);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        return header;
    }
      
      
       private void refreshGridEdc(String tipe_data) {
        grid_edc.removeAllColumns();
        if (grid_edc.getFooterRowCount() >= 1)
                grid_edc.removeFooterRow(0);
      
        
        Beans_fraud_warning bean_fraud_warning = new Beans_fraud_warning();

        try {
             grid_edc.setItems(bean_fraud_warning.getData(tipe_data,bean_fraud_warning.EDC));
         } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        
         grid_edc.setCaption(bean_fraud_warning.getCurrent_date());
         grid_edc.addColumn(Beans_fraud_warning::getNo).setCaption("No").
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
        grid_edc.addColumn(Beans_fraud_warning::getTid).setCaption("TID").
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("TID");
         
          grid_edc.addColumn(Beans_fraud_warning::getMid).setCaption("MID").
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("MID");
        
           grid_edc.addColumn(Beans_fraud_warning::getNama_agen).setCaption("Nama Agen").
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Nama_agen");
        
           
            grid_edc.addColumn(Beans_fraud_warning::getKanwil).setCaption("Kanwil").
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Kanwil");
        

           grid_edc.addColumn(Beans_fraud_warning::getFee, new NumberRenderer(Db1.Format)).setCaption("Fee").setWidth(150.0).
                setStyleGenerator((item) -> {
                        return "cellorange";
                }).setId("Fee");
            
          grid_edc.addColumn(Beans_fraud_warning::getTransaksi, new NumberRenderer(Db1.Format)).setCaption("Transaksi").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellorange";
           }).setId("Transaksi");
       
           
           grid_edc.addColumn(Beans_fraud_warning::getVol_pertrx, new NumberRenderer(Db1.Format)).setCaption("Vol per Trx").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellorange";
           }).setId("Volpertrx");
       
            grid_edc.addColumn(Beans_fraud_warning::getVolume, new NumberRenderer(Db1.Format)).setCaption("Volume").setWidth(250.0).
                setStyleGenerator((item) -> {
                        return "cellorange";
                }).setId("Volume");

              grid_edc.setSelectionMode(Grid.SelectionMode.NONE);
      }
       
       private void refreshGridMobile(String tipe_data) {
        grid_mobile.removeAllColumns();
        if (grid_mobile.getFooterRowCount() >= 1)
                grid_mobile.removeFooterRow(0);
      
        
        Beans_fraud_warning bean_fraud_warning = new Beans_fraud_warning();

        try {
             grid_mobile.setItems(bean_fraud_warning.getData(tipe_data,bean_fraud_warning.MOBILE));
         } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        
         grid_mobile.setCaption(bean_fraud_warning.getCurrent_date());
         grid_mobile.addColumn(Beans_fraud_warning::getNo).setCaption("No").
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
        grid_mobile.addColumn(Beans_fraud_warning::getTid).setCaption("TID (ID_OUTLET)").
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("TID");
         
          grid_mobile.addColumn(Beans_fraud_warning::getMid).setCaption("MID (ID_MERCHANT)").
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("MID");
        
           grid_mobile.addColumn(Beans_fraud_warning::getNama_agen).setCaption("Nama Agen").
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Nama_agen");
        
           
            grid_mobile.addColumn(Beans_fraud_warning::getKanwil).setCaption("Kanwil").
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Kanwil");
        

           grid_mobile.addColumn(Beans_fraud_warning::getFee, new NumberRenderer(Db1.Format)).setCaption("Fee").setWidth(150.0).
                setStyleGenerator((item) -> {
                        return "cellorange";
                }).setId("Fee");
            
          grid_mobile.addColumn(Beans_fraud_warning::getTransaksi, new NumberRenderer(Db1.Format)).setCaption("Transaksi").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellorange";
           }).setId("Transaksi");
       
           
           grid_mobile.addColumn(Beans_fraud_warning::getVol_pertrx, new NumberRenderer(Db1.Format)).setCaption("Vol per Trx").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellorange";
           }).setId("Volpertrx");
       
            grid_mobile.addColumn(Beans_fraud_warning::getVolume, new NumberRenderer(Db1.Format)).setCaption("Volume").setWidth(250.0).
                setStyleGenerator((item) -> {
                        return "cellorange";
                }).setId("Volume");
            
             grid_mobile.setSelectionMode(Grid.SelectionMode.NONE);
             
           
      }
       
       private void showButtonExport(){
        if (buttonExport != null){
            upanel.removeComponent(buttonExport);
        }
       
        buttonExport =  excel_button.createButtonExportExcel(combo_segmen.getValue().toString());
        buttonExport.setIcon(VaadinIcons.DOWNLOAD);
        upanel.addComponent(buttonExport);
        upanel.setComponentAlignment(buttonExport, Alignment.MIDDLE_RIGHT);
        
       }
       
      
}

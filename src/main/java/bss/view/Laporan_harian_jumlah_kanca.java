/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Beans_laporan_harian_jumlah_kanca;
import bss.backend.Db1;
import bss.backend.tools_lib.Excel_laporan_harian_jumlah_kanca;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;

/**
 *
 * @author IVAN
 */
public class Laporan_harian_jumlah_kanca extends VerticalLayout {

    public static double GREEN_UP = 90.0;
    public static double ORANGE_UP = 75.0;
    Grid<Beans_laporan_harian_jumlah_kanca> grid;
  
    Button buttonDownload;
   
    Excel_laporan_harian_jumlah_kanca excel_button;
    Button  buttonExport;
    // used to placed export button
    HorizontalLayout panel_upper_right;
    String kd_kanwil;
    String nama_kanwil;
    String tanggal;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Laporan_harian_jumlah_kanca(String kd_kanwil,String nama_kanwil,String tanggal) {
        this.setSizeFull();
        this.kd_kanwil = kd_kanwil;
        this.nama_kanwil = nama_kanwil;
        this.tanggal = tanggal;
       
        excel_button = new Excel_laporan_harian_jumlah_kanca();
        grid = new Grid< >("Laporan Harian Kanca ("+tanggal+")");
        grid.setSizeFull();
      
        HorizontalLayout panel_upper = new HorizontalLayout();
        panel_upper.setSizeFull();
        
      
        HorizontalLayout panel_upper_left = new HorizontalLayout();
        panel_upper_left.addComponent(buildButtonBack());
        panel_upper_left.addComponent(buildHeader());
        
        panel_upper_right = new HorizontalLayout();
        panel_upper_right.setSizeFull();
        
        panel_upper.addComponent(panel_upper_left);
        panel_upper.addComponent(panel_upper_right);
        addComponent(panel_upper);
        addComponent(grid);
        setExpandRatio(panel_upper, 0.05f);
        setExpandRatio(grid, 0.95f);
        
        refreshGrid();
    }

  

     private void refreshGrid() {
        grid.removeAllColumns();
        if (grid.getFooterRowCount() >= 1)
                grid.removeFooterRow(0);
      
        
        Beans_laporan_harian_jumlah_kanca beans_loparan_harian = new Beans_laporan_harian_jumlah_kanca();

        try {
            grid.setItems(beans_loparan_harian.getData(tanggal,kd_kanwil));
        } catch (SQLException ex) {
        }

        
         grid.addColumn(Beans_laporan_harian_jumlah_kanca::getNo).setCaption("No").setWidth(50.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
      
        grid.addColumn(Beans_laporan_harian_jumlah_kanca::getNama_kanca).setCaption("Kanca").setWidth(200.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Nama_kanwil");
      

       

      
        grid.addColumn(Beans_laporan_harian_jumlah_kanca::getTotal_all, new NumberRenderer(Db1.Format_decimal)).setCaption("Total All").setWidth(150.0).setStyleGenerator((item) -> {
             return "cellblack";
         }).setId("total_all");
  
          

        grid.addColumn(Beans_laporan_harian_jumlah_kanca::getJumlah_web, new NumberRenderer(Db1.Format)).setCaption("Jumlah Brimob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_web");
        grid.addColumn(Beans_laporan_harian_jumlah_kanca::getJumlah_web_delta, new NumberRenderer(Db1.Format)).setCaption("Delta Brimob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_web_delta");
       
        
        grid.addColumn(Beans_laporan_harian_jumlah_kanca::getJumlah_edc, new NumberRenderer(Db1.Format)).setCaption("Jumlah EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_edc");
 
        grid.addColumn(Beans_laporan_harian_jumlah_kanca::getJumlah_edc_delta, new NumberRenderer(Db1.Format)).setCaption("EDC Delta").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_edc_delta");

      
   
        
        
        FooterRow footer = grid.prependFooterRow();
        footer.getCell("Nama_kanwil").setHtml("<h3> <b> TOTAL </b> </h3>");
       
        footer.getCell("total_all").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_all())+" </b> </h3>");
      
        
        footer.getCell("jumlah_web").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_jumlah_web())+" </b> </h3>");
        footer.getCell("jumlah_web_delta").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_jumlah_web_delta())+" </b> </h3>");
        
        footer.getCell("jumlah_edc").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_jumlah_edc())+" </b> </h3>");
        footer.getCell("jumlah_edc_delta").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_jumlah_edc_delta())+" </b> </h3>");
       
        
        grid.getColumns().stream().forEach(column -> column.setHidable(true));
        grid.setFrozenColumnCount(3);
        
        showButtonExport();
    }
    
   

   
    
    private void showButtonExport(){
        if (buttonExport != null){
            panel_upper_right.removeComponent(buttonExport);
        }
        
        buttonExport = excel_button.createButtonExportExcel(tanggal, kd_kanwil, nama_kanwil);
        buttonExport.setIcon(VaadinIcons.DOWNLOAD);
        panel_upper_right.addComponent(buttonExport);
        panel_upper_right.setComponentAlignment(buttonExport, Alignment.MIDDLE_RIGHT);
    }
    
    private void showWarning(){
        Notification.show("Silahkan pilih tanggal terlebih dahulu",
                             Notification.Type.WARNING_MESSAGE).setPosition(Position.TOP_CENTER);
    }
    
    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        Label titleLabel = new Label(nama_kanwil);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H2);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        return header;
    }
    
    private Button buildButtonBack() {
        Button b = new Button();
        b.addStyleNames(ValoTheme.BUTTON_PRIMARY);
        b.setIcon(VaadinIcons.BACKWARDS);
        b.addClickListener(e -> {
             Laporan_harian_jumlah_kanwil lap =  ((MyUI) getUI()).getHome().getLaporan_harian_jumlah_kanwil();
             if (lap != null)
                  ((MyUI) getUI()).getHome().setSubContent(lap);
          
        });

        return b;
    }

}

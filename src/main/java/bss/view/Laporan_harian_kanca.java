/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Db1;
import bss.backend. Beans_laporan_harian_kanca;
import bss.backend.Beans_laporan_harian_kanwil;
import bss.backend.tools_lib.Excel_laporan_bulanan_kanca;
import bss.backend.tools_lib.Excel_laporan_harian_kanca;
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

/**
 *
 * @author IVAN
 */
public class Laporan_harian_kanca extends VerticalLayout {

    public static double GREEN_UP = 90.0;
    public static double ORANGE_UP = 75.0;
    Grid< Beans_laporan_harian_kanca> grid;
  
    Button buttonDownload;
   
    Excel_laporan_harian_kanca excel_button;
    Button  buttonExport;
    // used to placed export button
    HorizontalLayout panel_upper_right;
    String kd_kanwil;
    String nama_kanwil;
    String tanggal;
    
    public Laporan_harian_kanca(String kd_kanwil,String nama_kanwil,String tanggal) {
        this.kd_kanwil = kd_kanwil;
        this.nama_kanwil = nama_kanwil;
        this.tanggal = tanggal;
        
        setSizeFull();
        excel_button = new Excel_laporan_harian_kanca();
        grid = new Grid< Beans_laporan_harian_kanca>("Laporan Harian Kanca ("+tanggal+")");
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
      
        
         Beans_laporan_harian_kanca pencapaian_brilink_kanca = new  Beans_laporan_harian_kanca();

        try {
            grid.setItems(pencapaian_brilink_kanca.getData(tanggal,kd_kanwil));
        
        } catch (Exception ex) {
            ex.printStackTrace();
            showWarning();
            return;
        }
        
         grid.addColumn( Beans_laporan_harian_kanca::getNo).setCaption("No").setWidth(100.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
         grid.addColumn( Beans_laporan_harian_kanca::getNama_cabang).setCaption("Kanca").setWidth(200.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Nama_kanca"); 
       
        

       
        grid.addColumn( Beans_laporan_harian_kanca::getTotal_transaksi_edcf, new NumberRenderer(Db1.Format)).setCaption("Transaksi EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_edcf");
        grid.addColumn( Beans_laporan_harian_kanca::getTotal_transaksi_web, new NumberRenderer(Db1.Format)).setCaption("Transaksi Mob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_web");
        grid.addColumn( Beans_laporan_harian_kanca::getTotal_transaksi_all, new NumberRenderer(Db1.Format)).setCaption("Transaksi All").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_all");

      
        grid.addColumn( Beans_laporan_harian_kanca::getTotal_fee_edcf, new NumberRenderer(Db1.Format)).setCaption("FBI EDC").setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_edcf");;
        grid.addColumn( Beans_laporan_harian_kanca::getTotal_fee_web, new NumberRenderer(Db1.Format)).setCaption("FBI Mob").setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_web");
        grid.addColumn( Beans_laporan_harian_kanca::getTotal_fee_all, new NumberRenderer(Db1.Format)).setCaption("FBI All").setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_all");;;

        grid.addColumn(Beans_laporan_harian_kanca::getTotal_nominal_edcf, new NumberRenderer(Db1.Format)).setCaption("Volume EDC").setWidth(200.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_nominal_edcf");
        grid.addColumn(Beans_laporan_harian_kanca::getTotal_nominal_web, new NumberRenderer(Db1.Format)).setCaption("Volume Mob").setWidth(200.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_nominal_web");
        grid.addColumn(Beans_laporan_harian_kanca::getTotal_nominal_all, new NumberRenderer(Db1.Format)).setCaption("Volume All").setWidth(200.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_nominal_all");
       
        
        
        FooterRow footer = grid.prependFooterRow();
        footer.getCell("Nama_kanca").setHtml("<h3> <b> TOTAL </b> </h3>");
        
       
        footer.getCell("total_transaksi_edcf").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_transaksi_edcf())+" </b> </h3>");
        footer.getCell("total_transaksi_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_transaksi_web())+" </b> </h3>");
        footer.getCell("total_transaksi_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_transaksi_all())+" </b> </h3>");
      
        footer.getCell("total_fee_edcf").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_fee_edcf())+" </b> </h3>");
        footer.getCell("total_fee_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_fee_web())+" </b> </h3>");
        footer.getCell("total_fee_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_fee_all())+" </b> </h3>");
      
        
        footer.getCell("total_nominal_edcf").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_nominal_edcf())+" </b> </h3>");
        footer.getCell("total_nominal_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_nominal_web())+" </b> </h3>");
        footer.getCell("total_nominal_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_nominal_all())+" </b> </h3>");
        
        grid.getColumns().stream().forEach(column -> column.setHidable(true));
        grid.setFrozenColumnCount(2);
        
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
             Laporan_harian_kanwil lap =  ((MyUI) getUI()).getHome().getLaporan_harian();
             if (lap != null)
                  ((MyUI) getUI()).getHome().setSubContent(lap);
          
        });

        return b;
    }

}

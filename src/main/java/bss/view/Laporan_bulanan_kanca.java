/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Dashboard_ranking;
import bss.backend.Db1;
import bss.backend.Beans_laporan_bulanan_kanca;
import bss.backend.Beans_laporan_bulanan_kanwil;
import bss.backend.tools_lib.Excel_laporan_bulanan_kanca;
import bss.backend.tools_lib.Tools;
import static bss.view.Ranking.GREEN_UP;
import static bss.view.Ranking.ORANGE_UP;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.renderers.ImageRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IVAN
 */
public class Laporan_bulanan_kanca extends VerticalLayout {

    public static double GREEN_UP = 90.0;
    public static double ORANGE_UP = 75.0;
    Grid<Beans_laporan_bulanan_kanca> grid;
  
    Button buttonDownload;
   
    Excel_laporan_bulanan_kanca excel_button;
    Button  buttonExport;
    // used to placed export button
    HorizontalLayout panel_upper_right;
    String kd_kanwil;
    String nama_kanwil;
    String tanggal;
    
    public Laporan_bulanan_kanca(String kd_kanwil,String nama_kanwil,String tanggal) {
        this.kd_kanwil = kd_kanwil;
        this.nama_kanwil = nama_kanwil;
        this.tanggal = tanggal;
        
        setSizeFull();
        excel_button = new Excel_laporan_bulanan_kanca();
        grid = new Grid<Beans_laporan_bulanan_kanca>("Laporan Bulanan Kanca");
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
      
        
        Beans_laporan_bulanan_kanca pencapaian_brilink_kanca = new Beans_laporan_bulanan_kanca();

        try {
            grid.setItems(pencapaian_brilink_kanca.getData(tanggal,kd_kanwil));
        
        } catch (Exception ex) {
            ex.printStackTrace();
            showWarning();
            return;
        }
        
         grid.addColumn(Beans_laporan_bulanan_kanca::getNo).setCaption("No").
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
         grid.addColumn(Beans_laporan_bulanan_kanca::getNama_cabang).setCaption("Kanca").setWidth(200.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Nama_kanca"); 
       
        grid.addColumn(Beans_laporan_bulanan_kanca::getPencapaian_jumlah, new NumberRenderer(Db1.Format)).setCaption("Penc Jumlah").setWidth(150.0).
                setStyleGenerator((item) -> {
                    if (item.getPencapaian_jumlah() > 0.0) {
                        return "cellgreen";
                    }else {
                        return "cellorange";
                    }
                }).setId("pencapaian_jumlah");

        grid.addColumn(Beans_laporan_bulanan_kanca::getPencapaianFBI, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc FBI").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaianFBI() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaianFBI() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_fbi");

        grid.addColumn(Beans_laporan_bulanan_kanca::getPencapaian_transaksi, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc Transaksi").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaianFBI() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaianFBI() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_transaksi");

        grid.addColumn(Beans_laporan_bulanan_kanca::getJumlah_edc,new NumberRenderer(Db1.Format)).setCaption("Jumlah EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_edc");

        grid.addColumn(Beans_laporan_bulanan_kanca::getJumlah_web,new NumberRenderer(Db1.Format)).setCaption("Jumlah Mob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_web");
        grid.addColumn(Beans_laporan_bulanan_kanca::getJumlah_all,new NumberRenderer(Db1.Format)).setCaption("Jumlah All").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_all");

       
        grid.addColumn(Beans_laporan_bulanan_kanca::getTotal_transaksi_edcf, new NumberRenderer(Db1.Format)).setCaption("Transaksi EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_edcf");
        grid.addColumn(Beans_laporan_bulanan_kanca::getTotal_transaksi_web, new NumberRenderer(Db1.Format)).setCaption("Transaksi Mob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_web");
        grid.addColumn(Beans_laporan_bulanan_kanca::getTotal_transaksi_all, new NumberRenderer(Db1.Format)).setCaption("Transaksi All").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_all");

      
        grid.addColumn(Beans_laporan_bulanan_kanca::getTotal_fee_edcf, new NumberRenderer(Db1.Format)).setCaption("FBI EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_edcf");;
        grid.addColumn(Beans_laporan_bulanan_kanca::getTotal_fee_web, new NumberRenderer(Db1.Format)).setCaption("FBI Mob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_web");
        grid.addColumn(Beans_laporan_bulanan_kanca::getTotal_fee_all, new NumberRenderer(Db1.Format)).setCaption("FBI All").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_all");;;

          grid.addColumn(Beans_laporan_bulanan_kanca::getBep_web, new NumberRenderer(Db1.Format)).setCaption("BEP Mobile").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("bep_web");
         
         grid.addColumn(Beans_laporan_bulanan_kanca::getBep_edc, new NumberRenderer(Db1.Format)).setCaption("BEP EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("bep_edc");

        grid.addColumn(Beans_laporan_bulanan_kanca::getEdc_trx_persen, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("EDC bertrx").setWidth(150.0).
                setStyleGenerator((item) -> {
                    if (item.getEdc_trx_persen() >= GREEN_UP) {
                        return "cellgreen";
                    } else if (item.getEdc_trx_persen() >= ORANGE_UP) {
                        return "cellblack";
                    } else {
                        return "cellorange";
                    }
                }).setId("edc_trx");;;
        grid.addColumn(Beans_laporan_bulanan_kanca::getWeb_trx_persen, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Mob bertrx").setWidth(150.0).
                setStyleGenerator((item) -> {
                    if (item.getWeb_trx_persen() >= GREEN_UP) {
                        return "cellgreen";
                    } else if (item.getWeb_trx_persen() >= ORANGE_UP) {
                        return "cellblack";
                    } else {
                        return "cellorange";
                    }
                }).setId("web_trx");

        grid.addColumn(Beans_laporan_bulanan_kanca::getBep_edc_persen, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("EDC BEP").setWidth(150.0).
                setStyleGenerator((item) -> {
                    if (item.getBep_edc_persen() >= GREEN_UP) {
                        return "cellgreen";
                    } else if (item.getBep_edc_persen() >= ORANGE_UP) {
                        return "cellblack";
                    } else {
                        return "cellorange";
                    }
                }).setId("bep_edc_persen");

        grid.addColumn(Beans_laporan_bulanan_kanca::getCasa_all, new NumberRenderer(Db1.Format)).setCaption("Casa").setWidth(200.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("casa_all");
        
        
        FooterRow footer = grid.prependFooterRow();
        footer.getCell("Nama_kanca").setHtml("<h3> <b> TOTAL </b> </h3>");
        footer.getCell("jumlah_edc").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_jumlah_edc())+" </b> </h3>");
        footer.getCell("jumlah_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_jumlah_web())+" </b> </h3>");
        footer.getCell("jumlah_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_jumlah_all())+" </b> </h3>");
        
       
        footer.getCell("total_transaksi_edcf").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_transaksi_edcf())+" </b> </h3>");
        footer.getCell("total_transaksi_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_transaksi_web())+" </b> </h3>");
        footer.getCell("total_transaksi_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_transaksi_all())+" </b> </h3>");
      
        footer.getCell("total_fee_edcf").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_fee_edcf())+" </b> </h3>");
        footer.getCell("total_fee_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_fee_web())+" </b> </h3>");
        footer.getCell("total_fee_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_total_fee_all())+" </b> </h3>");
      
        footer.getCell("edc_trx").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanca.getGrand_edc_trx())+" </b> </h3>");
        footer.getCell("web_trx").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanca.getGrand_web_trx())+" </b> </h3>");
        footer.getCell("bep_edc_persen").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanca.getGrand_bep_edc_persen())+" </b> </h3>");
        footer.getCell("casa_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_casa_all())+" </b> </h3>");
        
        
        footer.getCell("bep_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_bep_web())+" </b> </h3>");
        footer.getCell("bep_edc").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_bep_edc())+" </b> </h3>");
     
        
        footer.getCell("pencapaian_jumlah").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanca.getGrand_pencapaian_jumlah())+" </b> </h3>");
        footer.getCell("pencapaian_transaksi").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanca.getGrand_pencapaian_trx())+" </b> </h3>");
        footer.getCell("pencapaian_fbi").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanca.getGrand_pencapaian_fbi())+" </b> </h3>");
        
        
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
             Laporan_bulanan_kanwil lap =  ((MyUI) getUI()).getHome().getLaporan_bulanan();
             if (lap != null)
                 System.out.println("owek");
            
                  ((MyUI) getUI()).getHome().setSubContent(lap);
                  System.out.println("owek1"); 
        });

        return b;
    }

}

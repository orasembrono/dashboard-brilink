/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Dashboard_ranking;
import bss.backend.Db1;
import bss.backend.Beans_laporan_bulanan_kanwil;
import bss.backend.tools_lib.Excel_laporan_bulanan_kanwil;
import bss.backend.tools_lib.Tools;
import static bss.view.Ranking.GREEN_UP;
import static bss.view.Ranking.ORANGE_UP;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
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
public class Laporan_bulanan_kanwil extends VerticalLayout {

    public static double GREEN_UP = 90.0;
    public static double ORANGE_UP = 75.0;
    Grid<Beans_laporan_bulanan_kanwil> grid;
    ComboBox comboTanggal;
    Button buttonDownload;
    Button buttonGo;
    Excel_laporan_bulanan_kanwil excel_button;
    Button  buttonExport;
    // used to placed export button
    HorizontalLayout panel_upper_right;
    String selectedTanggal;
    
    public Laporan_bulanan_kanwil() {
     
        setSizeFull();
        excel_button = new Excel_laporan_bulanan_kanwil();
        grid = new Grid<Beans_laporan_bulanan_kanwil>("Laporan Bulanan Kanwil");
        grid.setSizeFull();
        buttonGo = buildButtonGo();
        comboTanggal = buildComboDate();
        HorizontalLayout panel_upper = new HorizontalLayout();
        panel_upper.setSizeFull();
        HorizontalLayout panel_upper_left = new HorizontalLayout();
        panel_upper_right = new HorizontalLayout();
        panel_upper_left.addComponent(comboTanggal);
        panel_upper_left.addComponent(buttonGo);
        panel_upper_right.setSizeFull();     
        panel_upper.addComponent(panel_upper_left);
        panel_upper.addComponent(panel_upper_right);
        
       
        addComponent(panel_upper);
        addComponent(grid);
        setExpandRatio(panel_upper, 0.05f);
        setExpandRatio(grid, 0.95f);
    }

    private Button buildButtonGo() {
        Button b = new Button("Go");
        b.addStyleNames(ValoTheme.BUTTON_PRIMARY);
        b.setIcon(VaadinIcons.PLAY);
        b.addClickListener(e -> {
            refreshGridDashboard_ranking();
        });

        return b;
    }

    private ComboBox buildComboDate() {
        ComboBox<String> combo = new ComboBox();
        Beans_laporan_bulanan_kanwil dpb = new Beans_laporan_bulanan_kanwil();
        Collection<String> data1;
        try {
            data1 = dpb.getListViewPencapaian();
        } catch (SQLException ex) {
            data1 = new ArrayList();
        }
        combo.setItems(data1);
        return combo;
    }

   

    private void refreshGridDashboard_ranking() {
        grid.removeAllColumns();
        if (grid.getFooterRowCount() >= 1)
                grid.removeFooterRow(0);
      
        
        Beans_laporan_bulanan_kanwil pencapaian_brilink_kanwil = new Beans_laporan_bulanan_kanwil();

        try {
            selectedTanggal = comboTanggal.getValue().toString();
            grid.setItems(pencapaian_brilink_kanwil.getData(selectedTanggal));
        
        } catch (Exception ex) {
            ex.printStackTrace();
            showWarning();
            return;
        }
        
         grid.addColumn(Beans_laporan_bulanan_kanwil::getNo).setCaption("No").
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
        grid.addColumn(Beans_laporan_bulanan_kanwil::getNama_kanwil).setCaption("Kanwil").setWidth(150.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Nama_kanwil");
         
        
        grid.addComponentColumn((item) -> {
            Button bkanwil = new Button(){
                @Override
                public String toString() {
                    return this.getCaption();
                }
            };
            
            bkanwil.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
            bkanwil.setIcon(VaadinIcons.ANGLE_DOUBLE_RIGHT);
            bkanwil.setHeight("25px");
            bkanwil.addClickListener(e->{
                    Laporan_bulanan_kanca laporan_bulanan_kanca = new Laporan_bulanan_kanca(item.getKode_kanwil(),item.getNama_kanwil(),selectedTanggal);
                    ((MyUI) getUI()).getHome().setSubContent(laporan_bulanan_kanca);
                 });
            
            return bkanwil; //To change body of generated lambdas, choose Tools | Templates.
        }).setCaption("Action");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getPencapaian_jumlah, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc Jumlah").setWidth(150.0).
                setStyleGenerator((item) -> {
                    if (item.getPencapaian_jumlah() >= GREEN_UP) {
                        return "cellgreen";
                    } else if (item.getPencapaian_jumlah() >= ORANGE_UP) {
                        return "cellblack";
                    } else {
                        return "cellorange";
                    }
                }).setId("pencapaian_jumlah");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getPencapaianFBI, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc FBI").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaianFBI() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaianFBI() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_fbi");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getPencapaian_transaksi, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc Transaksi").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaian_transaksi() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaian_transaksi() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_transaksi");
        
         grid.addColumn(Beans_laporan_bulanan_kanwil::getPencapaian_bep, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc BEP").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaian_bep() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaian_bep() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_bep");
         
          grid.addColumn(Beans_laporan_bulanan_kanwil::getPencapaian_bertrx, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc BerTrx").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaian_bertrx() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaian_bertrx() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_bertrx");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getPencapaian_casa, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc Casa").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaian_casa()>= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaian_bertrx() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_casa");

            
        grid.addColumn(Beans_laporan_bulanan_kanwil::getJumlah_edc, new NumberRenderer(Db1.Format)).setCaption("Jumlah EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_edc");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getJumlah_web, new NumberRenderer(Db1.Format)).setCaption("Jumlah Mob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_web");
        grid.addColumn(Beans_laporan_bulanan_kanwil::getJumlah_all, new NumberRenderer(Db1.Format)).setCaption("Jumlah All").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_all");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getRka_jumlah, new NumberRenderer(Db1.Format)).setCaption("Rka Jumlah").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("rka_jumlah");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getTotal_transaksi_edcf, new NumberRenderer(Db1.Format)).setCaption("Transaksi EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_edcf");
        grid.addColumn(Beans_laporan_bulanan_kanwil::getTotal_transaksi_web, new NumberRenderer(Db1.Format)).setCaption("Transaksi Mob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_web");
        grid.addColumn(Beans_laporan_bulanan_kanwil::getTotal_transaksi_all, new NumberRenderer(Db1.Format)).setCaption("Transaksi All").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_all");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getRka_trx, new NumberRenderer(Db1.Format)).setCaption("Transaksi Rka").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("rka_trx");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getTotal_fee_edcf, new NumberRenderer(Db1.Format)).setCaption("FBI EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_edcf");
        grid.addColumn(Beans_laporan_bulanan_kanwil::getTotal_fee_web, new NumberRenderer(Db1.Format)).setCaption("FBI Mob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_web");
        grid.addColumn(Beans_laporan_bulanan_kanwil::getTotal_fee_all, new NumberRenderer(Db1.Format)).setCaption("FBI All").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_all");

        grid.addColumn(Beans_laporan_bulanan_kanwil::getRka_fbi, new NumberRenderer(Db1.Format)).setCaption("FBI Rka").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("rka_fbi");
        
         
           grid.addColumn(Beans_laporan_bulanan_kanwil::getEdc_trx, new NumberRenderer(Db1.Format)).setCaption("EDC bertrx").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("edc_trx");
           
     
               grid.addColumn(Beans_laporan_bulanan_kanwil::getWeb_trx, new NumberRenderer(Db1.Format)).setCaption("Mob bertrx").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("web_trx");
        
       
         grid.addColumn(Beans_laporan_bulanan_kanwil::getTrx_all, new NumberRenderer(Db1.Format)).setCaption("All ber Trx").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("trx_all");
                 
               

          grid.addColumn(Beans_laporan_bulanan_kanwil::getBep_edc, new NumberRenderer(Db1.Format)).setCaption("EDC BEP").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("bep_edc_persen");
         
         
           grid.addColumn(Beans_laporan_bulanan_kanwil::getBep_web, new NumberRenderer(Db1.Format)).setCaption("MOB BEP").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("bep_web_persen");
          
       
             grid.addColumn(Beans_laporan_bulanan_kanwil::getBep_all, new NumberRenderer(Db1.Format)).setCaption("BEP ALL").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("bep_all_persen");
          
       
        
          
          
        grid.addColumn(Beans_laporan_bulanan_kanwil::getCasa_all, new NumberRenderer(Db1.Format)).setCaption("Casa").setWidth(200.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("casa_all");
        
        
        FooterRow footer = grid.prependFooterRow();
        footer.getCell("Nama_kanwil").setHtml("<h3> <b> TOTAL </b> </h3>");
        footer.getCell("jumlah_edc").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_jumlah_edc())+" </b> </h3>");
        footer.getCell("jumlah_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_jumlah_web())+" </b> </h3>");
        footer.getCell("jumlah_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_jumlah_all())+" </b> </h3>");
        footer.getCell("rka_jumlah").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_rka_jumlah())+" </b> </h3>");
        
       
        footer.getCell("total_transaksi_edcf").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_total_transaksi_edcf())+" </b> </h3>");
        footer.getCell("total_transaksi_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_total_transaksi_web())+" </b> </h3>");
        footer.getCell("total_transaksi_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_total_transaksi_all())+" </b> </h3>");
        footer.getCell("rka_trx").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_rka_trx())+" </b> </h3>");

        footer.getCell("total_fee_edcf").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_total_fee_edcf())+" </b> </h3>");
        footer.getCell("total_fee_web").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_total_fee_web())+" </b> </h3>");
        footer.getCell("total_fee_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_total_fee_all())+" </b> </h3>");
        footer.getCell("rka_fbi").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_rka_fbi())+" </b> </h3>");

        
                
        footer.getCell("edc_trx").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_edc_trx())+" </b> </h3>");
        footer.getCell("web_trx").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_web_trx())+" </b> </h3>");
        footer.getCell("trx_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_trx_all())+" </b> </h3>");
       
        
        footer.getCell("bep_edc_persen").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_bep_edc_persen())+" </b> </h3>");
        footer.getCell("bep_web_persen").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_bep_web_persen())+" </b> </h3>");
        footer.getCell("bep_all_persen").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_bep_all_persen())+" </b> </h3>");
       
        footer.getCell("casa_all").setHtml("<h3> <b> "+Db1.Format.format(pencapaian_brilink_kanwil.getGrand_casa_all())+" </b> </h3>");

        footer.getCell("pencapaian_jumlah").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanwil.getGrand_pencapaian_jumlah())+" </b> </h3>");
        footer.getCell("pencapaian_transaksi").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanwil.getGrand_pencapaian_trx())+" </b> </h3>");
        footer.getCell("pencapaian_fbi").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanwil.getGrand_pencapaian_fbi())+" </b> </h3>");
        footer.getCell("pencapaian_bep").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanwil.getGrand_pencapaian_bepall())+" </b> </h3>");
        footer.getCell("pencapaian_bertrx").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanwil.getGrand_pencapaian_bertrxall())+" </b> </h3>");
        footer.getCell("pencapaian_casa").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(pencapaian_brilink_kanwil.getGrand_pencapaian_casa())+" </b> </h3>");
      
        
        grid.getColumns().stream().forEach(column -> column.setHidable(true));
        grid.setFrozenColumnCount(3);
        
        showButtonExport();
    }
    
    private void showButtonExport(){
        if (buttonExport != null){
            panel_upper_right.removeComponent(buttonExport);
        }
        
        buttonExport = excel_button.createButtonExportExcel(comboTanggal.getValue().toString());
        buttonExport.setIcon(VaadinIcons.DOWNLOAD);
        panel_upper_right.addComponent(buttonExport);
        panel_upper_right.setComponentAlignment(buttonExport, Alignment.MIDDLE_RIGHT);
    }
    
    private void showWarning(){
        Notification.show("Silahkan pilih tanggal terlebih dahulu",
                             Notification.Type.WARNING_MESSAGE).setPosition(Position.TOP_CENTER);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Db1;
import bss.backend.Beans_laporan_harian_kanwil;
import bss.backend.tools_lib.Excel_laporan_harian_kanwil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author IVAN
 */
public class Laporan_harian_kanwil extends VerticalLayout {

    public static double GREEN_UP = 90.0;
    public static double ORANGE_UP = 75.0;
    Grid<Beans_laporan_harian_kanwil> grid;
    ComboBox comboTanggal;
    Button buttonDownload;
    Button buttonGo;
    Excel_laporan_harian_kanwil excel_button;
    Button  buttonExport;
    // used to placed export button
    HorizontalLayout panel_upper_right;
    String selectedTanggal;
    
    
    private void prepared(){
        setSizeFull();
        excel_button = new Excel_laporan_harian_kanwil();
        grid = new Grid<>();
        grid.setCaption("Laporan Harian Transaksi");
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
    
    public Laporan_harian_kanwil() {
       prepared();
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
        Beans_laporan_harian_kanwil dpb = new Beans_laporan_harian_kanwil();
        Collection<String> data1;
        try {
            data1 = dpb.getListDateReady();
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
      
        
        Beans_laporan_harian_kanwil beans_loparan_harian = new Beans_laporan_harian_kanwil();

        try {
            selectedTanggal = comboTanggal.getValue().toString();
            grid.setItems(beans_loparan_harian.getData(selectedTanggal));
        
        } catch (SQLException ex) {
            showWarning();
            return;
        }
        
         grid.addColumn(Beans_laporan_harian_kanwil::getNo).setCaption("No").
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
        grid.addColumn(Beans_laporan_harian_kanwil::getNama_kanwil).setCaption("Kanwil").setWidth(150.0).
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
                    Laporan_harian_kanca laporan_harian_kanca = new Laporan_harian_kanca(item.getKode_kanwil(),item.getNama_kanwil(),selectedTanggal);
                    ((MyUI) getUI()).getHome().setSubContent(laporan_harian_kanca);
                 });
            
            return bkanwil; //To change body of generated lambdas, choose Tools | Templates.
        }).setCaption("Action");

       

        grid.addColumn(Beans_laporan_harian_kanwil::getPencapaianFBI, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc FBI").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaianFBI() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaianFBI() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_fbi");

        grid.addColumn(Beans_laporan_harian_kanwil::getPencapaian_transaksi, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc Transaksi").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaian_transaksi() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaian_transaksi() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_transaksi");

        

        grid.addColumn(Beans_laporan_harian_kanwil::getTotal_transaksi_edcf, new NumberRenderer(Db1.Format)).setCaption("Transaksi EDC").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_edcf");
        grid.addColumn(Beans_laporan_harian_kanwil::getTotal_transaksi_web, new NumberRenderer(Db1.Format)).setCaption("Transaksi Mob").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_web");
        grid.addColumn(Beans_laporan_harian_kanwil::getTotal_transaksi_all, new NumberRenderer(Db1.Format)).setCaption("Transaksi All").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_transaksi_all");

        grid.addColumn(Beans_laporan_harian_kanwil::getRka_trx, new NumberRenderer(Db1.Format)).setCaption("Transaksi Rka").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("rka_trx");

        grid.addColumn(Beans_laporan_harian_kanwil::getTotal_fee_edcf, new NumberRenderer(Db1.Format)).setCaption("FBI EDC").setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_edcf");
        grid.addColumn(Beans_laporan_harian_kanwil::getTotal_fee_web, new NumberRenderer(Db1.Format)).setCaption("FBI Mob").setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_web");
        grid.addColumn(Beans_laporan_harian_kanwil::getTotal_fee_all, new NumberRenderer(Db1.Format)).setCaption("FBI All").setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_fee_all");

        grid.addColumn(Beans_laporan_harian_kanwil::getRka_fbi, new NumberRenderer(Db1.Format)).setCaption("FBI Rka").setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("rka_fbi");

        
         grid.addColumn(Beans_laporan_harian_kanwil::getTotal_nominal_edcf, new NumberRenderer(Db1.Format)).setCaption("Volumw EDC").setWidth(200.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_nominal_edcf");
        grid.addColumn(Beans_laporan_harian_kanwil::getTotal_nominal_web, new NumberRenderer(Db1.Format)).setCaption("Volume Mob").setWidth(200.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_nominal_web");
        grid.addColumn(Beans_laporan_harian_kanwil::getTotal_nominal_all, new NumberRenderer(Db1.Format)).setCaption("Volume All").setWidth(200.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("total_nominal_all");
        
        
        FooterRow footer = grid.prependFooterRow();
        footer.getCell("Nama_kanwil").setHtml("<h3> <b> TOTAL </b> </h3>");
       
       
        footer.getCell("total_transaksi_edcf").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_transaksi_edcf())+" </b> </h3>");
        footer.getCell("total_transaksi_web").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_transaksi_web())+" </b> </h3>");
        footer.getCell("total_transaksi_all").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_transaksi_all())+" </b> </h3>");
        footer.getCell("rka_trx").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_rka_trx())+" </b> </h3>");

        footer.getCell("total_fee_edcf").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_fee_edcf())+" </b> </h3>");
        footer.getCell("total_fee_web").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_fee_web())+" </b> </h3>");
        footer.getCell("total_fee_all").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_fee_all())+" </b> </h3>");
        footer.getCell("rka_fbi").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_rka_fbi())+" </b> </h3>");

        footer.getCell("total_nominal_edcf").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_nominal_edcf())+" </b> </h3>");
        footer.getCell("total_nominal_web").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_nominal_web())+" </b> </h3>");
        footer.getCell("total_nominal_all").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_nominal_all())+" </b> </h3>");
        
        
        footer.getCell("pencapaian_transaksi").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(beans_loparan_harian.getGrand_pencapaian_trx())+" </b> </h3>");
        footer.getCell("pencapaian_fbi").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(beans_loparan_harian.getGrand_pencapaian_fbi())+" </b> </h3>");
        
        
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

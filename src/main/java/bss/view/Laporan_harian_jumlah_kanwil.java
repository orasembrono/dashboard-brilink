/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Beans_laporan_harian_jumlah_kanwil;
import bss.backend.Db1;
import bss.backend.tools_lib.Excel_laporan_harian_jumlah_kanwil;
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
public class Laporan_harian_jumlah_kanwil extends VerticalLayout {

    public static double GREEN_UP = 90.0;
    public static double ORANGE_UP = 75.0;
    Grid<Beans_laporan_harian_jumlah_kanwil> grid;
    ComboBox comboTanggal;
    ComboBox comboTanggal2;
    Button buttonDownload;
    Button buttonGo;
    Excel_laporan_harian_jumlah_kanwil excel_button;
    Button  buttonExport;
    // used to placed export button
    HorizontalLayout panel_upper_right;
    String selectedTanggal;
    String selectedTanggal2;
    
    
    private void prepared(){
        setSizeFull();
        excel_button = new Excel_laporan_harian_jumlah_kanwil();
        grid = new Grid<>();
        grid.setCaption("Laporan Harian Jumlah");
        grid.setSizeFull();
        buttonGo = buildButtonGo();
        comboTanggal = buildComboDate("Main date");
        comboTanggal2 = buildComboDate("Compare date");
        HorizontalLayout panel_upper = new HorizontalLayout();
        panel_upper.setSizeFull();
        HorizontalLayout panel_upper_left = new HorizontalLayout();
        panel_upper_right = new HorizontalLayout();
        panel_upper_left.addComponent(comboTanggal);
        panel_upper_left.addComponent(comboTanggal2);
        
        
        panel_upper_left.addComponent(buttonGo);
        panel_upper_left.setComponentAlignment(buttonGo, Alignment.BOTTOM_CENTER);
        panel_upper_right.setSizeFull();     
        panel_upper.addComponent(panel_upper_left);
        panel_upper.addComponent(panel_upper_right);
        
       
        addComponent(panel_upper);
        addComponent(grid);
        setExpandRatio(panel_upper, 0.1f);
        setExpandRatio(grid, 0.9f);
    }
    
    public Laporan_harian_jumlah_kanwil() {
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

    private ComboBox buildComboDate(String caption) {
        ComboBox<String> combo = new ComboBox(caption);
        Beans_laporan_harian_jumlah_kanwil dpb = new Beans_laporan_harian_jumlah_kanwil();
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
      
        
        Beans_laporan_harian_jumlah_kanwil beans_loparan_harian = new Beans_laporan_harian_jumlah_kanwil();

        try {
            selectedTanggal = comboTanggal.getValue().toString();
            selectedTanggal2 = comboTanggal2.getValue().toString(); 
            grid.setItems(beans_loparan_harian.getData(selectedTanggal,selectedTanggal2));
        
        } catch (SQLException ex) {
            ex.printStackTrace();
            showWarning();
            return;
        }
        
         grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getNo).setCaption("No").setWidth(50.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getNama_kanwil).setCaption("Kanwil").setWidth(150.0).
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
                    Laporan_harian_jumlah_kanca laporan_harian_jumlah_kanca = new Laporan_harian_jumlah_kanca(item.getKode_kanwil(),item.getNama_kanwil(),selectedTanggal);
                    ((MyUI) getUI()).getHome().setSubContent(laporan_harian_jumlah_kanca);
                 });
            
            return bkanwil; //To change body of generated lambdas, choose Tools | Templates.
        }).setWidth(100.0).setCaption("Action");

       

        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getPencapaian_jumlah, new NumberRenderer(Db1.Format_decimal_percent)).setCaption("Penc Jumlah").setWidth(150.0).setStyleGenerator((item) -> {
            if (item.getPencapaian_jumlah() >= GREEN_UP) {
                return "cellgreen";
            } else if (item.getPencapaian_jumlah() >= ORANGE_UP) {
                return "cellblack";
            } else {
                return "cellorange";
            }
        }).setId("pencapaian_jumlah");

        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getTotal_all, new NumberRenderer(Db1.Format)).setCaption(selectedTanggal).setWidth(150.0).setStyleGenerator((item) -> {
             return "cellblack";
         }).setId("total_all");
        
         grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getTotal_compare, new NumberRenderer(Db1.Format)).setCaption(selectedTanggal2).setWidth(150.0).setStyleGenerator((item) -> {
             return "cellblack";
         }).setId("total_compare");
        
        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getTotal_delta_compare, new NumberRenderer(Db1.Format)).setCaption("Delta").setWidth(150.0).setStyleGenerator((item) -> {
             return "cellblack";
         }).setId("delta_compare");

          grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getRka_jumlah, new NumberRenderer(Db1.Format)).setCaption("RKA Jumlah").setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack"; 
        }).setId("rka_jumlah");
          
          

        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getJumlah_web, new NumberRenderer(Db1.Format)).setCaption("BM "+selectedTanggal).setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_web");
        
          grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getJumlah_compare_web, new NumberRenderer(Db1.Format)).setCaption("BM "+selectedTanggal2).setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_compare_web");
        
        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getDelta_compare_web, new NumberRenderer(Db1.Format)).setCaption("Delta BM").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("delta_compare_web");
       
        
        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getJumlah_edc, new NumberRenderer(Db1.Format)).setCaption("EDC "+selectedTanggal).setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_edc");
        
        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getJumlah_compare_edc, new NumberRenderer(Db1.Format)).setCaption("EDC "+selectedTanggal2).setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("jumlah_compare_edc");
 
        grid.addColumn(Beans_laporan_harian_jumlah_kanwil::getDelta_compare_edc, new NumberRenderer(Db1.Format)).setCaption("EDC Delta").setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("delta_compare_edc");

      
   
        
        
        FooterRow footer = grid.prependFooterRow();
        footer.getCell("Nama_kanwil").setHtml("<h3> <b> TOTAL </b> </h3>");
       
       
        footer.getCell("pencapaian_jumlah").setHtml("<h3> <b> "+Db1.Format_decimal_percent.format(beans_loparan_harian.getGrand_pencapaian_jumlah())+" </b> </h3>");
        footer.getCell("total_all").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_total_all())+" </b> </h3>");
        footer.getCell("total_compare").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_compare_all())+" </b> </h3>");
        footer.getCell("delta_compare").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_delta_compare_all())+" </b> </h3>");
        
        footer.getCell("rka_jumlah").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_rka_jumlah())+" </b> </h3>");
        
        footer.getCell("jumlah_web").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_jumlah_web())+" </b> </h3>");
        footer.getCell("jumlah_compare_web").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_compare_web())+" </b> </h3>");
        footer.getCell("delta_compare_web").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_delta_compare_web())+" </b> </h3>");
        
        footer.getCell("jumlah_edc").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_jumlah_edc())+" </b> </h3>");
        footer.getCell("jumlah_compare_edc").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_compare_edc())+" </b> </h3>");
        footer.getCell("delta_compare_edc").setHtml("<h3> <b> "+Db1.Format.format(beans_loparan_harian.getGrand_delta_compare_edc())+" </b> </h3>");
       
        
        grid.getColumns().stream().forEach(column -> column.setHidable(true));
        grid.setFrozenColumnCount(3);
        
        showButtonExport();
    }
    
    private void showButtonExport(){
        if (buttonExport != null){
            panel_upper_right.removeComponent(buttonExport);
        }
        
        buttonExport = excel_button.createButtonExportExcel(comboTanggal.getValue().toString(),comboTanggal2.getValue().toString());
        buttonExport.setIcon(VaadinIcons.DOWNLOAD);
        panel_upper_right.addComponent(buttonExport);
        panel_upper_right.setComponentAlignment(buttonExport, Alignment.MIDDLE_RIGHT);
    }
    
    private void showWarning(){
        Notification.show("Silahkan pilih tanggal terlebih dahulu",
                             Notification.Type.WARNING_MESSAGE).setPosition(Position.TOP_CENTER);
    }
   
}

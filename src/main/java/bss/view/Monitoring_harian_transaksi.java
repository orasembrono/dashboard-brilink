/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Beans_laporan_harian_jumlah_kanwil;
import bss.backend.Beans_monitoring_harian_transaksi;
import bss.backend.Db1;
import bss.backend.tools_lib.Excel_laporan_harian_jumlah_kanwil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
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
public class Monitoring_harian_transaksi extends VerticalLayout {

    public static double GREEN_UP = 90.0;
    public static double ORANGE_UP = 75.0;
    Grid<Beans_monitoring_harian_transaksi> grid;
    ComboBox comboTanggal; // <- main tanggal
    ComboBox comboTanggal2; // <- compare tanggal
    Button buttonDownload;
    Button buttonGo;
    Excel_laporan_harian_jumlah_kanwil excel_button;
    Button  buttonExport;
    // used to placed export button
    HorizontalLayout panel_upper_right;
    String selectedTanggal;
    String selectedTanggal2;
    TabSheet sheet = new TabSheet();
    VerticalLayout sheetPerKanwil = new VerticalLayout();
    VerticalLayout sheetPerFitur = new VerticalLayout();
    
    
   
    private VerticalLayout buildSearchPanel(){
        buttonGo = buildButtonGo();
        comboTanggal = buildComboDate("Main date");
        comboTanggal2 = buildComboDate("Compare date");
        VerticalLayout panel_main = new VerticalLayout();
        panel_main.setSizeUndefined();
        HorizontalLayout panel_upper_1 = new HorizontalLayout();
        panel_upper_1.addComponent(comboTanggal);
        panel_upper_1.addComponent(comboTanggal2);
       
        panel_main.addComponent(panel_upper_1);
        panel_main.addComponent(buttonGo);
        panel_main.setComponentAlignment(buttonGo, Alignment.BOTTOM_RIGHT);
        return panel_main;
       
    }
    
    private void buildSearchWindow(){
         final Window win = new Window(" Brilink");
        win.setWidth("450px");
        win.addStyleName("win1");
        win.setClosable(false);
        win.setResizable(false);
        win.setIcon(FontAwesome.MAP_O);   
        VerticalLayout winlayout = new VerticalLayout();
        win.setContent(buildSearchPanel());
      
        getUI().getCurrent().addWindow(win);
        win.setPosition(20, 100);
        win.focus();
    }
    
    private void buildGrid(){
        
        sheetPerKanwil.setSizeFull();
        sheetPerFitur.setSizeFull();
        sheet.setSizeFull();
        sheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        
                
        setSizeFull();
        excel_button = new Excel_laporan_harian_jumlah_kanwil();
        grid = new Grid<>();
        grid.setCaption("Monitoring Harian Transaksi Finansial");
        grid.setSizeFull();
        
        panel_upper_right = new HorizontalLayout();
        panel_upper_right.setSizeFull();
        addComponentsAndExpand(panel_upper_right);
                
        addComponentsAndExpand(sheet);
        setExpandRatio(panel_upper_right, 0.02f);
        setSpacing(false);
        panel_upper_right.setSpacing(false);
        setExpandRatio(sheet, 0.98f);
        
        sheetPerKanwil.addComponent(grid);
        
        sheet.addTab(sheetPerKanwil,"Perkanwil");
        sheet.addTab(sheetPerFitur,"PerFitur");
    }
    
    public Monitoring_harian_transaksi() {
       buildSearchWindow();
       buildGrid();
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
        Beans_monitoring_harian_transaksi dpb = new Beans_monitoring_harian_transaksi();
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
      
        
        Beans_monitoring_harian_transaksi beans_monitoring_harian_transaksi = new Beans_monitoring_harian_transaksi();

        try {
            selectedTanggal = comboTanggal.getValue().toString();
            selectedTanggal2 = comboTanggal2.getValue().toString(); 
            grid.setItems(beans_monitoring_harian_transaksi.getData(selectedTanggal,selectedTanggal2));
        
        } catch (SQLException ex) {
            ex.printStackTrace();
            showWarning();
            return;
        }
        
         grid.addColumn(Beans_monitoring_harian_transaksi::getNo).setCaption("No").setWidth(50.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                });
         
        grid.addColumn(Beans_monitoring_harian_transaksi::getNama_kanwil).setCaption("Kanwil").setWidth(150.0).
                setStyleGenerator((item) -> {
                        return "cellblack";
                }).setId("Nama_kanwil");
         
        
       
           
       

        grid.addColumn(Beans_monitoring_harian_transaksi::getTrx_edc2, new NumberRenderer(Db1.Format)).setCaption("EDC "+selectedTanggal2).setWidth(150.0).setStyleGenerator((item) -> {
              return "cellblack";  
        }).setId("EDC "+selectedTanggal2);

        grid.addColumn(Beans_monitoring_harian_transaksi::getTrx_mobile2, new NumberRenderer(Db1.Format)).setCaption("Mobile "+selectedTanggal2).setWidth(150.0).setStyleGenerator((item) -> {
             return "cellblack";
         }).setId("Mobile "+selectedTanggal2);
        
         grid.addColumn(Beans_monitoring_harian_transaksi::getTotal2, new NumberRenderer(Db1.Format)).setCaption("Total "+selectedTanggal2).setWidth(150.0).setStyleGenerator((item) -> {
             return "cellblack";
         }).setId("Total "+selectedTanggal2);
        
        grid.addColumn(Beans_monitoring_harian_transaksi::getTrx_edc1, new NumberRenderer(Db1.Format)).setCaption("EDC "+selectedTanggal).setWidth(150.0).setStyleGenerator((item) -> {
             return "cellblack";
         }).setId("EDC_"+selectedTanggal);

          grid.addColumn(Beans_monitoring_harian_transaksi::getTrx_mobile1, new NumberRenderer(Db1.Format)).setCaption("Mobile "+selectedTanggal).setWidth(170.0).setStyleGenerator((item) -> {
            return "cellblack"; 
        }).setId("Mobile_"+selectedTanggal);
 
        grid.addColumn(Beans_monitoring_harian_transaksi::getTotal1, new NumberRenderer(Db1.Format)).setCaption("Total "+selectedTanggal).setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("Total_"+selectedTanggal);
        
          grid.addColumn(Beans_monitoring_harian_transaksi::getDelta, new NumberRenderer(Db1.Format)).setCaption("Delta").setWidth(150.0).setStyleGenerator((item) -> {
             if (item.getRatas1() > item.getRatas2()) {
                return "cellgreen";
            }  else {
                return "cellorange";
            }
        }).setId("Delta");
        
      /*  
      grid.addColumn(Beans_monitoring_harian_transaksi::getRatas1, new NumberRenderer(Db1.Format)).setCaption("Total "+selectedTanggal).setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("ratas1");
      
      grid.addColumn(Beans_monitoring_harian_transaksi::getRatas2, new NumberRenderer(Db1.Format)).setCaption("Total "+selectedTanggal).setWidth(150.0).setStyleGenerator((item) -> {
            return "cellblack";
        }).setId("ratas2");
        
      */
        
        
        FooterRow footer = grid.prependFooterRow();
        footer.getCell("Nama_kanwil").setHtml("<h3> <b> TOTAL </b> </h3>");
       
       
        footer.getCell("EDC "+selectedTanggal2).setHtml("<h3> <b> "+Db1.Format_decimal.format(beans_monitoring_harian_transaksi.getGrand_trx_edc2())+" </b> </h3>");
        footer.getCell("Mobile "+selectedTanggal2).setHtml("<h3> <b> "+Db1.Format_decimal.format(beans_monitoring_harian_transaksi.getGrand_trx_mobile2())+" </b> </h3>");
        footer.getCell("Total "+selectedTanggal2).setHtml("<h3> <b> "+Db1.Format_decimal.format(beans_monitoring_harian_transaksi.getGrand_total2())+" </b> </h3>");
        footer.getCell("EDC_"+selectedTanggal).setHtml("<h3> <b> "+Db1.Format_decimal.format(beans_monitoring_harian_transaksi.getGrand_trx_edc1())+" </b> </h3>");
        footer.getCell("Mobile_"+selectedTanggal).setHtml("<h3> <b> "+Db1.Format_decimal.format(beans_monitoring_harian_transaksi.getGrand_trx_mobile1())+" </b> </h3>");
        footer.getCell("Total_"+selectedTanggal).setHtml("<h3> <b> "+Db1.Format_decimal.format(beans_monitoring_harian_transaksi.getGrand_total1())+" </b> </h3>");
        footer.getCell("Delta").setHtml("<h3> <b> "+Db1.Format_decimal.format(beans_monitoring_harian_transaksi.getGrand_delta())+" </b> </h3>");
        
        
        
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

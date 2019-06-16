/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author IVAN
 */
public class Home extends VerticalLayout implements View {

    public static String NAME = "home";
    Panel subContent;
    MapB map;
    Chart chart;
    Ranking ranking;
    Laporan_bulanan_kanwil laporan_bulanan_kanwil;
    Laporan_harian_kanwil laporan_harian_kanwil;
    Laporan_harian_jumlah_kanwil laporan_harian_jumlah_kanwil;
    Monitoring_harian_transaksi monitoring_harian_transaksi;
    Profile_agen profile_agen;
    Fraud_warning fraud_warning;
    Data_problem_log  data_problem_log;
    
    VerticalLayout maincontent;
    VerticalLayout imagecontent;
   
    
    public  Home() {
        setSizeFull();
        setMargin(false);
        
        
        
        maincontent = new VerticalLayout();
        maincontent.setSizeFull();
        maincontent.setSpacing(false);
        subContent = new Panel();
        subContent.setSizeFull();
        HorizontalLayout menubar = buildMenuBar();
        maincontent.addComponent(menubar);
        maincontent.setExpandRatio(menubar, 0.07f);
        maincontent.addComponent(subContent);
        maincontent.setExpandRatio(subContent, 0.93f);
        maincontent.setMargin(false);
        
        
        
       /* imagecontent = new VerticalLayout();
        Image image = new Image(null,
                new ThemeResource("img/background_1.png"));
        imagecontent.setSizeFull();
        imagecontent.addComponent(image);
        imagecontent.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        
        subContent.setContent(imagecontent);
       */
        addComponent(maincontent);
        
        setContentDefault();
    }
    
    private void setContentDefault(){
         chart = new Chart();
         subContent.setContent(chart);
         System.out.println("chart");
    }
    
   
    
     private void onSelectMenu(MenuBar.MenuItem selectedItem) throws SQLException {
        MyUI mainUI = (MyUI) getUI();
        mainUI.removeAllWindow();
        MenuBar.MenuItem mitem = selectedItem.getParent();
        List<MenuBar.MenuItem> items = mitem.getChildren();
        for (MenuBar.MenuItem item : items) {
            item.setChecked(false);
        }
        selectedItem.setChecked(true);

        if (selectedItem.getText().equals("Maps")) {
            map = new MapB();
            subContent.setContent(map);
            System.out.println("ok");

        }  else if (selectedItem.getText().equals("Chart")) {
             chart = new Chart();
             subContent.setContent(chart);
            
        }else if (selectedItem.getText().startsWith("Lap Bulanan")) {
             laporan_bulanan_kanwil = new Laporan_bulanan_kanwil();
             subContent.setContent(laporan_bulanan_kanwil);
             
             System.out.println("laporan_harian_kanwil");   
        } else if (selectedItem.getText().startsWith("Lap Harian - Kualitas")) {
             laporan_harian_kanwil = new Laporan_harian_kanwil();
             subContent.setContent(laporan_harian_kanwil);
           
             System.out.println("laporan_harian_kanwil");   
        }else if (selectedItem.getText().startsWith("Lap Harian - Kuantitas")) {
             laporan_harian_jumlah_kanwil = new Laporan_harian_jumlah_kanwil();
             subContent.setContent(laporan_harian_jumlah_kanwil);
             System.out.println("laporan_harian_jumlah_kanwil");  
           
        }else if (selectedItem.getText().startsWith("Monitoring Harian")) {
             monitoring_harian_transaksi = new Monitoring_harian_transaksi();
             subContent.setContent(monitoring_harian_transaksi);
              
        } else if (selectedItem.getText().startsWith("Fraud Early")) {
          
             fraud_warning = new Fraud_warning();
             subContent.setContent(fraud_warning);
            
             System.out.println("Fraud Warning");  
        }else if (selectedItem.getText().equals("Logout")) {
             getUI().getNavigator().removeView(Home.NAME);
             VaadinSession.getCurrent().setAttribute("user", null);
             Page.getCurrent().setUriFragment("");
        }else if (selectedItem.getText().equals("Profile Agen")) {
             profile_agen = new Profile_agen();
             subContent.setContent(profile_agen);
             System.out.println("profile agen"); 
           
        }else if (selectedItem.getText().equals("Problem Log")) {
             data_problem_log = new Data_problem_log();
             subContent.setContent(data_problem_log);
             System.out.println("Data problem log"); 
           
        }
        else {
            
           
            System.out.println("nothing");
            //subContent.setContent(imagecontent);
        }

        cekSessionValid();
        System.out.println(selectedItem.getText());
    }

    private HorizontalLayout buildMenuBar() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setStyleName("headercolor");
        layout.setSizeFull();
        layout.setSpacing(false);
        layout.setMargin(false);

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand;
        mycommand = (MenuBar.MenuItem selectedItem) -> {
            try {
                onSelectMenu(selectedItem);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        };

        MenuBar barmenu = new MenuBar();
        
        HorizontalLayout layoutbarmenu = new HorizontalLayout();
        layoutbarmenu.addComponent(barmenu);
         
        layout.addComponent(layoutbarmenu);
        layout.setComponentAlignment(layoutbarmenu, Alignment.BOTTOM_LEFT);
        
        
        
        Image image = new Image(null,
                new ThemeResource("img/brilink_1.png"));
        layout.addComponent(image);
        layout.setComponentAlignment(image, Alignment.MIDDLE_RIGHT);
        MenuBar.MenuItem mitem = barmenu.addItem("Menu", VaadinIcons.MENU, null);

        MenuBar.MenuItem mchart = mitem.addItem("Chart", VaadinIcons.CHART_TIMELINE, mycommand);
        mchart.setCheckable(
                true);
       
        mitem.addSeparator();
        MenuBar.MenuItem Lapbulanan = mitem.addItem("Lap Bulanan", VaadinIcons.CALENDAR, mycommand);

        Lapbulanan.setCheckable(
                true);
        
        mitem.addSeparator();
        MenuBar.MenuItem lapHarian1 = mitem.addItem("Lap Harian - Kualitas", VaadinIcons.CHART_LINE, mycommand);
        lapHarian1.setCheckable(
                true);
        
        mitem.addSeparator();
        MenuBar.MenuItem lapHarian2 = mitem.addItem("Lap Harian - Kuantitas", VaadinIcons.CHART_3D, mycommand);
        lapHarian2.setCheckable(
                true);
        
        mitem.addSeparator();
        MenuBar.MenuItem monitoirng_harian = mitem.addItem("Monitoring Harian", VaadinIcons.ACADEMY_CAP, mycommand);
        monitoirng_harian.setCheckable(
                true);
        
        
         mitem.addSeparator();
        MenuBar.MenuItem lap_warehouse = mitem.addItem("Fraud Early Warning", VaadinIcons.CLOUD_DOWNLOAD, mycommand);
        lapHarian2.setCheckable(
                true);
        
        
        mitem.addSeparator();
        MenuBar.MenuItem map = mitem.addItem("Maps", VaadinIcons.MAP_MARKER, mycommand);
        map.setCheckable(
                true);
        
        mitem.addSeparator();
        MenuBar.MenuItem profile_agen = mitem.addItem("Profile Agen", VaadinIcons.USER_CHECK, mycommand);
        map.setCheckable(
                true);
        
        mitem.addSeparator();
        MenuBar.MenuItem problem_brilink = mitem.addItem("Problem Log", VaadinIcons.AMBULANCE, mycommand);
        map.setCheckable(
                true);
        
        
         mitem.addSeparator();
        MenuBar.MenuItem logout = mitem.addItem("Logout", VaadinIcons.OUT, mycommand);
        
        
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
      
    }
    
    
    private void cekSessionValid(){
           if(getSession().getAttribute("user") == null){
               Page.getCurrent().setUriFragment("");
            }
    }
    
    
    public void setSubContent(Component c){
      
        subContent.setContent(c);
    }
    
    public Laporan_bulanan_kanwil getLaporan_bulanan(){
        return laporan_bulanan_kanwil;
    }
    
     public Laporan_harian_kanwil getLaporan_harian(){
        return laporan_harian_kanwil;
    }

    public Laporan_harian_jumlah_kanwil getLaporan_harian_jumlah_kanwil() {
        return laporan_harian_jumlah_kanwil;
    }
     
     
    
}

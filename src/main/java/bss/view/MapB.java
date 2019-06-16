/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Longlat;
import bss.backend.Tid_edc;
import bss.backend.Kanwil;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;


/**
 *
 * @author IVAN
 */
public class MapB extends VerticalLayout {

   
    ComboBox comboRegion;
    GoogleMap googleMap;
    Longlat longlat ;

    public MapB() throws SQLException {

      
       
         longlat = new Longlat();
         googleMap = new GoogleMap("AIzaSyBIGt991AOJgtSlv4Ntm2Faj3GE8ffVsys", null, "english");
         MyUI mainUI = (MyUI) getUI();
            
        // add marker
        googleMap.setCenter(new LatLon(
                -1.285481, 119.957469));
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);
        googleMap.setZoom(5);
        googleMap.setSizeFull();   
        TabSheet tabs = new TabSheet();
        tabs.setSizeFull();
        tabs.addTab(googleMap, "Maps");
        Panel panelSetting = new Panel();
        tabs.addTab(panelSetting, "Setup");
        this.setSizeFull();
        this.addComponent(googleMap);
       // mainUI.removeAllWindow();             
        final Window win = new Window(" Brilink");
        win.setWidth("300px");
        win.addStyleName("win1");
        win.setClosable(false);
        win.setResizable(false);
        win.setIcon(FontAwesome.MAP_O);   
        VerticalLayout winlayout = new VerticalLayout();
        win.setContent(buildPanelRegionSelector());
      
        getUI().getCurrent().addWindow(win);
        win.setPosition(getUI().getCurrent().getPage().getBrowserWindowWidth() - (int) win.getWidth(), 100);
        win.focus();
    }
    
  

    private VerticalLayout buildPanelRegionSelector() {
        VerticalLayout layout = new VerticalLayout();
        comboRegion = builComboRegion();
        layout.addComponent(comboRegion);
        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidth("100%");
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        Label footerText = new Label("");
        footerText.setSizeUndefined();
        Button ok = new Button("OK");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        footer.addComponents(footerText, ok);
        footer.setExpandRatio(footerText, 1);
        layout.addComponent(footer);
        ok.addClickListener(e->{
           Optional o = comboRegion.getSelectedItem();
           Kanwil kanwil = (Kanwil) o.get();
       
           addMarkerGoogleMap(kanwil.getKd_kanwil());
           
        });
        return layout;
    }
    
    private ComboBox builComboRegion(){
        Kanwil kanwil = new Kanwil();
        Collection<Kanwil> kanwils = new ArrayList<Kanwil>();
        try {
            kanwils = kanwil.getAllKanwil();
        } catch (SQLException ex) {
        }
        ComboBox combo = new ComboBox("Region", kanwils);
        combo.setEmptySelectionCaption("Select Region");
        ItemCaptionGenerator icap = new ItemCaptionGenerator() {
            @Override
            public String apply(Object item) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                Kanwil object = (Kanwil) item;
                return object.getKanwil();
            }
        };
   
        IconGenerator t = new IconGenerator() {
             @Override
             public Resource apply(Object item) {
                 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 Kanwil object = (Kanwil) item;
                // return new ThemeResource("img/pin/"+object.getKd_kanwil()+".png");
                 return new ThemeResource("img/pin/placeholder.png");
             }
         };
        combo.setItemCaptionGenerator(icap);
        combo.setItemIconGenerator(t);
        return combo;
    }
    
    private void addMarkerGoogleMap(String region){
        
        ArrayList<Object[]> array = new ArrayList<Object[]>();
        try {
            array = longlat.getLonglat(region);
        } catch (SQLException ex) {
          
        }
        // remove first current markets
        // not work.. error.. working by clearmarkers..hehe
        
        /*Collection<GoogleMapMarker> markers = googleMap.getMarkers();
        for (GoogleMapMarker marker  : markers ){
             googleMap.removeMarker(marker);
           System.out.println(marker.getCaption());
         }
        */
        googleMap.clearMarkers();     
        //  TID, NAMA_AGEN, LOKASI, KD_KANWIL, NAMA_KANWIL, longitude, latitude          
        for (Object[] data1 : array) {
            String tid = data1[0].toString();
            String nama_agen = data1[1].toString();
            String lokasi = data1[2].toString();
            String kd_kanwil = data1[3].toString().trim();
            String kanwil = data1[4].toString();
            String longitude = data1[5].toString();
            String latitude = data1[6].toString();
            double lats = 0;
            double longs = 0;       
            try {
                lats = Double.valueOf(latitude);
                longs = Double.valueOf(longitude);
                String caption = ""+kanwil+" "+tid+"/"+nama_agen+"";
              /*  googleMap.addMarker(caption, new LatLon(
                        lats, longs), false, "VAADIN/themes/mytheme/img/pin/" + kd_kanwil + ".png");
                        */
               googleMap.addMarker(caption, new LatLon(
                        lats, longs), false, "VAADIN/themes/mytheme/img/pin/pin.png");
                //  ex.printStackTrace();}
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}

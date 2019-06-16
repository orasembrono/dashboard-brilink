/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.backend.Dashboard_ranking;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.renderers.ImageRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author IVAN
 */
public class Ranking extends VerticalLayout {

    public static double GREEN_UP = 90.0;
    public static double ORANGE_UP = 75.0;
    
    
    Grid<Dashboard_ranking> grid;
   
    
    public  Ranking() {
        setSizeFull();
        grid = buildGridDashboard_ranking();
        grid.setSizeFull();
        //addComponent(buildButtonDownload());
        
        addComponent(grid);
        
    }
    
    
    
    private Button buildButtonDownload(){
        Button exportButton = new Button("Export");
   
           exportButton.addClickListener(e -> {
            });

        return exportButton;        
    }
    
     private Grid buildGridDashboard_ranking(){ 

        grid = new Grid<Dashboard_ranking>("Pencapaian BriLink Kanwil");
        Dashboard_ranking dashboard_ranking = new Dashboard_ranking();
        
        
        try {
            grid.setItems(dashboard_ranking.getDashboard_ranking());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

       // grid.addColumn("kd_kanwil");
       // grid.addColumn("kanwil");
        ComponentRenderer b = new ComponentRenderer();
        ImageRenderer imagerenderer = new ImageRenderer();
        
       // grid.addColumn(Dashboard_ranking::getLabel_pencapaian,b).setCaption("-").setWidth(50);
        grid.addColumn(Dashboard_ranking::getNo).setCaption("No").setWidth(100);
       // grid.addColumn(Dashboard_ranking::getKanwil).setCaption("Kanwil ");
        grid.addComponentColumn((item) -> {
            Button bkanwil = new Button(item.getKanwil());
            bkanwil.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
            bkanwil.setHeight("20px");
            return bkanwil; //To change body of generated lambdas, choose Tools | Templates.
        }).setCaption("Kanwil");
        grid.addColumn(Dashboard_ranking::getTotal).setCaption("TOTAL (%)").setStyleGenerator((item) -> {
                  if ( item.getTotal()>= GREEN_UP )  
                    return "cellgreen";
                  else if ( item.getTotal() >= ORANGE_UP )  
                    return "cellblack";
                  else 
                     return "cellorange"; 
                });
        grid.addColumn(Dashboard_ranking::getPencapaian_bep).setCaption("BEP (%)").setStyleGenerator((item) -> {
                  if ( item.getPencapaian_bep() >= GREEN_UP )  
                    return "cellgreen";
                  else if ( item.getPencapaian_bep() >= ORANGE_UP )  
                    return "cellblack";
                  else 
                     return "cellorange"; 
                });
        grid.addColumn(Dashboard_ranking::getPencapaian_casa).setCaption("CASA (%)").setStyleGenerator((item) -> {
                  if ( item.getPencapaian_casa() >= GREEN_UP )  
                    return "cellgreen";
                  else if ( item.getPencapaian_casa() >= ORANGE_UP )  
                    return "cellblack";
                  else 
                     return "cellorange"; 
                });
        grid.addColumn(Dashboard_ranking::getPencapaian_trx).setCaption("TRANSAKSI (%)").setStyleGenerator((item) -> {
                  if ( item.getPencapaian_trx() >= GREEN_UP )  
                    return "cellgreen";
                  else if ( item.getPencapaian_trx() >= ORANGE_UP )  
                    return "cellblack";
                  else 
                     return "cellorange"; 
                });
        grid.addColumn(Dashboard_ranking::getPencapainan_fbi).setCaption("FBI (%)").setStyleGenerator((item) -> {
                  if ( item.getPencapainan_fbi() >= GREEN_UP )  
                    return "cellgreen";
                  else if ( item.getPencapainan_fbi() >= ORANGE_UP )  
                    return "cellblack";
                  else 
                     return "cellorange"; 
                });
        Column c = grid.addColumn(Dashboard_ranking::getPencapaian_jumlah).setCaption("JUMLAH (%)")
                .setStyleGenerator((item) -> {
                  if ( item.getPencapaian_jumlah() >= GREEN_UP )  
                    return "cellgreen";
                  else if ( item.getPencapaian_jumlah() >= ORANGE_UP )  
                    return "cellblack";
                  else 
                     return "cellorange"; 
                });
      
                // Allow column reordering
        grid.setColumnReorderingAllowed(true);
        
     
        FooterRow footer = grid.prependFooterRow();
        c.setId("coba");
         System.out.println(c.getId());
       // footer.getCell(c)
       footer.getCell("coba").setText("coba");
        
        // Allow column hiding
        grid.getColumns().stream().forEach(column -> column.setHidable(true));
 
        return grid;
    }
     
     
     
       private Button buildDetailButton(String kanwil) {
        Button button = new Button(kanwil,VaadinIcons.CLOSE);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        //button.addClickListener(e -> {});
        return button;
      } 

}

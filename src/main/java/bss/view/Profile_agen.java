/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.backend.Beans_profile_agen;
import bss.backend.tools_lib.Excel_laporan_harian_kanwil;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivanariwibawa
 */
public class Profile_agen extends VerticalLayout {

   
    Panel Panel_result_edc = new Panel();
    Panel Panel_result_mobile = new Panel();    
    TextField Tf_inputsearch_edc = new TextField();
    ComboBox comboSearch_edc = new ComboBox();
    TextField Tf_inputsearch_mob = new TextField();
    ComboBox comboSearch_mob = new ComboBox();
    TabSheet sheet = new TabSheet();
    
    VerticalLayout sheetEDC = new VerticalLayout();
    VerticalLayout sheetMobile = new VerticalLayout();
  
    
    public Profile_agen() {
        this.setSizeFull();
        sheet.setSizeFull();
        sheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        sheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
       // sheet.setHeight(100.0f, Unit.PERCENTAGE);
        sheet.addTab(buildSheetEDC(),"EDC");
        sheet.addTab(buildSheetMobile(),"Mobile");
        addComponent(sheet);
      
    }
    
    
    private VerticalLayout buildSheetEDC(){
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        HorizontalLayout panel_upper = new HorizontalLayout();
        panel_upper.setSizeFull();
        HorizontalLayout panel_upper_left = new HorizontalLayout();
        HorizontalLayout panel_upper_right = new HorizontalLayout();

        panel_upper_left.addComponent(buildComboCriteria_edc());

        panel_upper_right.setSizeFull();
        panel_upper.addComponent(panel_upper_left);
        panel_upper.addComponent(panel_upper_right);
        
        
        layout.addComponent(panel_upper);
        layout.setExpandRatio(panel_upper, 0.1f);
        layout.addComponent(Panel_result_edc);
        layout.setExpandRatio(Panel_result_edc, 0.9f);
        
        return layout;
    }

    private VerticalLayout buildSheetMobile(){
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        HorizontalLayout panel_upper = new HorizontalLayout();
        panel_upper.setSizeFull();
        HorizontalLayout panel_upper_left = new HorizontalLayout();
        HorizontalLayout panel_upper_right = new HorizontalLayout();

        panel_upper_left.addComponent(buildComboCriteria_mob());

        panel_upper_right.setSizeFull();
        panel_upper.addComponent(panel_upper_left);
        panel_upper.addComponent(panel_upper_right);
        
        
        layout.addComponent(panel_upper);
        layout.setExpandRatio(panel_upper, 0.1f);
        layout.addComponent(Panel_result_mobile);
        layout.setExpandRatio(Panel_result_mobile, 0.9f);
        
        return layout;
    }
   
      
    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        Label titleLabel = new Label("");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H2);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        return header;
    }

    private Button buildButtonGo_edc() {
        Button b = new Button("Find");
        b.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        b.setIcon(VaadinIcons.SEARCH_PLUS);
        b.addClickListener(e -> {
           if (comboSearch_edc.getValue().equals("TID")) 
           setPanelResultbyTid_edc(Tf_inputsearch_edc.getValue());
        });

        return b;
    }

   

    private HorizontalLayout buildComboCriteria_edc() {
     
        ArrayList<String> array = new ArrayList<>();
        array.add("TID");
        array.add("Nama");
      //  array.add("ID Agen");
        comboSearch_edc.setItems(array);
        comboSearch_edc.setSelectedItem("TID");

        HorizontalLayout form = new HorizontalLayout();
        form.addComponent(comboSearch_edc);
        form.addComponent(Tf_inputsearch_edc);
        form.addComponent(buildButtonGo_edc());

        return form;
    }
    
    
     private Button buildButtonGo_mob() {
        Button b = new Button("Find");
        b.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        b.setIcon(VaadinIcons.SEARCH_PLUS);
        b.addClickListener(e -> {
           if (comboSearch_mob.getValue().equals("TID")) 
           setPanelResultbyTid_edc(Tf_inputsearch_mob.getValue());
        });

        return b;
    }

   

    private HorizontalLayout buildComboCriteria_mob() {
     
        ArrayList<String> array = new ArrayList<>();
        array.add("TID");
        array.add("Nama");
      //  array.add("ID Agen");
        comboSearch_mob.setItems(array);
        comboSearch_mob.setSelectedItem("TID");

        HorizontalLayout form = new HorizontalLayout();
        form.addComponent(comboSearch_mob);
        form.addComponent(Tf_inputsearch_mob);
        form.addComponent(buildButtonGo_mob());

        return form;
    }
    
    
    private void setPanelResultbyTid_edc(String tid){
       if (tid.equals("") || tid == null){
           Tf_inputsearch_edc.setComponentError(new UserError("Tidak boleh kosong !"));
           return;
       } 
           Tf_inputsearch_edc.setComponentError(null);
        
       Beans_profile_agen beans1 = null;
        try {
           Beans_profile_agen beans_profile_agen = new Beans_profile_agen();
           beans1 = beans_profile_agen.getDataByTid(tid);
        } catch (SQLException ex) {
            Logger.getLogger(Profile_agen.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        FormLayout form = new FormLayout();
        form.setSpacing(true);
        form.setMargin(true);
         
        Label section = new Label("Search Result");
        section.addStyleName("h2");
        section.addStyleName("colored"); //this will just make it colored
       
        
        // if no data found
        if (beans1 == null){
            section.setValue("Not Found");
            form.addComponent(section);
            Panel_result_edc.setContent(form);
            return;
        }
        
        form.addComponent(section);
        
        Label label1 = new Label(beans1.getId_agen());
        label1.setCaption("ID Agen");
        
        Label label2 = new Label(beans1.getNama_agen());
        label2.setCaption("Nama Agen");
        
        Label label3 = new Label(beans1.getMid());
        label3.setCaption("MID");
        
        Label label4 = new Label(beans1.getAlamat());
        label4.setCaption("Alamat");
        
        Label label5 = new Label(beans1.getKanwil() + "  "+beans1.getNama_cabang());
        label5.setCaption("Uker ");
        
        Label label6 = new Label(beans1.getTangga_pks());
        label6.setCaption("Tgl");
        
        Label label7 = new Label(beans1.getJenis_usaha());
        label7.setCaption("Jenis Usaha");
        
        
        
        form.addComponent(label1);
        form.addComponent(label2);
        form.addComponent(label3);
        form.addComponent(label4);
        form.addComponent(label5);
        form.addComponent(label6);
        form.addComponent(label7);
        
        Panel_result_edc.setContent(form);
    }
    
      private void showWarning(String message){
        Notification.show(message,
                             Notification.Type.ERROR_MESSAGE).setPosition(Position.BOTTOM_CENTER);
    }

}

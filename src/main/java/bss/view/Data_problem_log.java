/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.MyUI;
import bss.backend.Beans_brilink_problem;
import bss.backend.Beans_laporan_bulanan_kanca;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;





/**
 *
 * @author ivanariwibawa
 */
public class Data_problem_log extends VerticalLayout {

    Grid<Beans_brilink_problem> g_problem;
    public static String STATUS[] = {"open", "close"};

    public Data_problem_log() {

        setSizeFull();

        // panel left
        HorizontalLayout panel_upper = new HorizontalLayout();
        panel_upper.setSizeFull();

        Button b_tambah = new Button("Add");
        b_tambah.setIcon(VaadinIcons.PLUS_CIRCLE_O);
        b_tambah.addStyleNames(ValoTheme.BUTTON_PRIMARY);
        panel_upper.addComponent(b_tambah);
        panel_upper.setComponentAlignment(b_tambah, Alignment.TOP_RIGHT);

        g_problem = new Grid<Beans_brilink_problem>("List Problem");
        g_problem.setSizeFull();

        addComponent(panel_upper);
        addComponent(g_problem);
        setExpandRatio(panel_upper, 0.05f);
        setExpandRatio(g_problem, 0.95f);

        refreshGrid();

        b_tambah.addClickListener((event) -> {
            add();
        });
    }

    private void refreshGrid() {
        g_problem.removeAllColumns();
        if (g_problem.getFooterRowCount() >= 1) {
            g_problem.removeFooterRow(0);
        }

        Beans_brilink_problem brilink_problem = new Beans_brilink_problem();

        try {
            g_problem.setItems(brilink_problem.getData());

        } catch (Exception ex) {
            ex.printStackTrace();
            //   showWarning();
            return;
        }

        g_problem.addColumn(Beans_brilink_problem::getNo).setCaption("No").
                setStyleGenerator((item) -> {
                    return "cellblack";
                });

        g_problem.addColumn(Beans_brilink_problem::getTanggal).setCaption("Tanggal").
                setStyleGenerator((item) -> {
                    return "cellblack";
                });
        g_problem.addColumn(Beans_brilink_problem::getProblem).setCaption("Problem").
                setStyleGenerator((item) -> {
                    return "cellblack";
                });

        g_problem.addColumn(Beans_brilink_problem::getDetail_problem).setCaption("Detail").
                setStyleGenerator((item) -> {
                    return "cellblack";
                });

        g_problem.addColumn(Beans_brilink_problem::getStatus).setCaption("Status").
                setStyleGenerator((item) -> {
                    return "cellblack";
                });

        g_problem.addColumn(Beans_brilink_problem::getDate_close).setCaption("Date Close").
                setStyleGenerator((item) -> {
                    return "cellblack";
                });

        g_problem.addComponentColumn((item) -> {
            Button bkanwil = new Button() {
                @Override
                public String toString() {
                    return this.getCaption();
                }
            };

            bkanwil.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
            bkanwil.setIcon(VaadinIcons.EDIT);
            bkanwil.setHeight("25px");
            bkanwil.addClickListener(e -> {
                update(item);
            });

            return bkanwil; //To change body of generated lambdas, choose Tools | Templates.
        }).setCaption("Action").setWidth(100.0);

    }

    void add() {
        Window subWindow = new Window("Add");
        FormLayout subContent = new FormLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        VerticalLayout frame1 = new VerticalLayout();
        frame1.addComponent(subContent);
        subWindow.setContent(frame1);

        // Put some components in it
        DateField df_tanggal = new DateField("Tanggal");
        df_tanggal.setDateFormat("yyyy-MM-dd");
        df_tanggal.setValue(LocalDate.now());
        TextField tf_problem = new TextField("Problem");
        TextArea ta_detail_problem = new TextArea("Detail Problem");

        subContent.addComponent(df_tanggal);
        subContent.addComponent(tf_problem);
        subContent.addComponent(ta_detail_problem);

        // Center it in the browser window
        subWindow.setClosable(false);
        subWindow.setDraggable(false);
        subWindow.setModal(true);
        subWindow.setResizable(false);

        // Trivial logic for closing the sub-window
        Button ok = new Button("Save");
        ok.addStyleNames(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener((event) -> {

            System.out.println(df_tanggal.getValue().toString());

            Beans_brilink_problem brilink_problem = new Beans_brilink_problem(0,
                    df_tanggal.getValue().toString(), tf_problem.getValue(), ta_detail_problem.getValue(), null, null, 0);

            try {
                brilink_problem.insert();
                refreshGrid();
                 Notification.show("Add", "Data Berhasil Ditambahkan",
                             Notification.Type.HUMANIZED_MESSAGE).setPosition(Position.BOTTOM_CENTER);
                  
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(Data_problem_log.class.getName()).log(Level.SEVERE, null, ex);
            }

            subWindow.close();
        });

        Button cancel = new Button("Cancel");
        cancel.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        cancel.addClickListener((event) -> {
            subWindow.close();
        });

        HorizontalLayout layout_button = new HorizontalLayout();
        layout_button.addComponent(cancel);
        layout_button.addComponent(ok);
        subContent.addComponent(layout_button);

        subWindow.center();

        // Open it in the UI
        getUI().addWindow(subWindow);
    }

   
     
    void update(Beans_brilink_problem beans_brilink_problem) {
        Window subWindow = new Window("Update / Delete");
        FormLayout subContent = new FormLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        VerticalLayout frame1 = new VerticalLayout();
        frame1.addComponent(subContent);
        subWindow.setContent(frame1);

        // Put some components in it
        DateField df_tanggal = new DateField("Tanggal");
        df_tanggal.setDateFormat("yyyy-MM-dd");
        LocalDate date = LocalDate.of(Integer.valueOf(beans_brilink_problem.getTanggal().substring(0, 4)),
                Integer.valueOf(beans_brilink_problem.getTanggal().substring(5, 7)),
                Integer.valueOf(beans_brilink_problem.getTanggal().substring(8, 10)));
        df_tanggal.setValue(date);

        TextField tf_problem = new TextField("Problem");
        tf_problem.setValue(beans_brilink_problem.getProblem());

        TextArea ta_detail_problem = new TextArea("Detail Problem");
        ta_detail_problem.setValue(beans_brilink_problem.getDetail_problem());

        ComboBox c_status = new ComboBox();
        c_status.setItems(STATUS);
        c_status.setEmptySelectionAllowed(false);
        c_status.setSelectedItem(beans_brilink_problem.getStatus());

        subContent.addComponent(df_tanggal);
        subContent.addComponent(tf_problem);
        subContent.addComponent(ta_detail_problem);
        subContent.addComponent(c_status);

        // Center it in the browser window
        subWindow.setClosable(false);
        subWindow.setDraggable(false);
        subWindow.setModal(true);
        subWindow.setResizable(false);

        // Trivial logic for closing the sub-window
        Button ok = new Button("Save");
        ok.addStyleNames(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener((event) -> {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date_now = new Date();
            String date_close = null;
            // open -> close
            if ( beans_brilink_problem.getStatus().equals(STATUS[0]) && c_status.getValue().equals(STATUS[1]) ){
                System.out.println("open - close");
                date_close =    dateFormat.format(date_now);
             // close -> open
            }else if ( beans_brilink_problem.getStatus().equals(STATUS[1]) && c_status.getValue().equals(STATUS[0]) ){
              date_close = null;  
            } else{
              date_close = beans_brilink_problem.getDate_close();
            }
                    
                    
                    
            Beans_brilink_problem brilink_problem = new Beans_brilink_problem(0,
                    df_tanggal.getValue().toString(), tf_problem.getValue(), ta_detail_problem.getValue(),
                    c_status.getValue().toString(), date_close, beans_brilink_problem.getId());

            try {
                brilink_problem.update();
                refreshGrid();
                 Notification.show("Update", "Data Berhasil Diupdate",
                             Notification.Type.HUMANIZED_MESSAGE).setPosition(Position.BOTTOM_CENTER);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(Data_problem_log.class.getName()).log(Level.SEVERE, null, ex);
            }

            subWindow.close();
        });

        Button cancel = new Button("Cancel");
        cancel.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        cancel.addClickListener((event) -> {
            subWindow.close();
        });
        
        HorizontalLayout layout_button = new HorizontalLayout();
        HorizontalLayout layout_confirm = new HorizontalLayout();
         

        Button delete = new Button("Delete");
        delete.addStyleNames(ValoTheme.BUTTON_DANGER);
        delete.addClickListener((event) -> {
            
            subContent.removeComponent(layout_button);
            subContent.addComponent(layout_confirm);
        });
        
        
        Button confirm_delete = new Button("Yes");
        confirm_delete.addStyleNames(ValoTheme.BUTTON_DANGER);
        Button confirm_no = new Button("No");
        confirm_no.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
         
        confirm_no.addClickListener((event) -> {
            
            subContent.removeComponent(layout_confirm);
            subContent.addComponent(layout_button);
        });
         
        confirm_delete.addClickListener((event) -> {
            
            Beans_brilink_problem brilink_problem = new Beans_brilink_problem(0,
                    df_tanggal.getValue().toString(), tf_problem.getValue(), ta_detail_problem.getValue(),
                    c_status.getValue().toString(), null, beans_brilink_problem.getId());
            
           try {
               brilink_problem.delete();
               refreshGrid();
               
                Notification.show("Delete", "Data Berhasil Dihapus",
                             Notification.Type.HUMANIZED_MESSAGE).setPosition(Position.BOTTOM_CENTER);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(Data_problem_log.class.getName()).log(Level.SEVERE, null, ex);
            }
           subWindow.close();
        });

      
        layout_button.addComponent(delete);
        layout_button.addComponent(cancel);
        layout_button.addComponent(ok);
        subContent.addComponent(layout_button);
        
       
        layout_confirm.addComponent(new Label("Are You Sure ??"));    
        layout_confirm.addComponent(confirm_no);
        layout_confirm.addComponent(confirm_delete);
     
        subWindow.center();

        // Open it in the UI
        getUI().addWindow(subWindow);
    }
}

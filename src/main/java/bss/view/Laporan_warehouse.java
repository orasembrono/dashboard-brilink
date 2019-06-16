/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.backend.Beans_laporan_bulanan_kanwil;
import bss.backend.Beans_laporan_harian_kanwil;
import bss.backend.Beans_laporan_mingguan;
import bss.backend.Beans_laporan_mingguanx;
import bss.backend.Beans_profile_agen;
import bss.backend.tools_lib.AdvancedFileDownloader;
import static bss.backend.tools_lib.Excel_laporan_bulanan_kanwil.Sheet;
import bss.backend.tools_lib.Excel_laporan_harian_kanwil;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
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
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author ivanariwibawa
 */
public class Laporan_warehouse extends VerticalLayout {

    ComboBox combo_jenis;
    ComboBox combo_tanggal;
    ComboBox combo_pembanding;
    ComboBox combo_rka;

    Button button_go;
    FormLayout form1;

    public String path = "/WEB-INF/";
    public static String Sheet = "Sheet1";
    String ExcelFile;
    String tanggal;
    String compare;
    String rka;

    public Laporan_warehouse() {
        //this.setSizeFull();
        this.addComponent(buildHeader());

        combo_jenis = new ComboBox("Type");
        combo_jenis.setWidth("300px");
        combo_tanggal = buildComboTanggal("Tanggal");
        combo_pembanding = buildComboCompare("Compare");
        combo_rka = buildComboRka("RKA");

        setCombo_jenis();

        button_go = createButtonExportExcel();
        //  button_go.setIcon(VaadinIcons.DOWNLOAD);
        //  button_go.addStyleNames(ValoTheme.BUTTON_PRIMARY);
        form1 = new FormLayout();
        form1.addComponent(combo_jenis);
        form1.addComponent(combo_pembanding);
        form1.addComponent(combo_tanggal);
        form1.addComponent(combo_rka);
        form1.addComponent(button_go);
        form1.setComponentAlignment(button_go, Alignment.MIDDLE_RIGHT);

        this.addComponent(form1);
    }

    private ComboBox buildComboCompare(String title) {
        ComboBox<String> combo = new ComboBox(title);
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

    private ComboBox buildComboTanggal(String title) {
        ComboBox<String> combo = new ComboBox(title);
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

    private ComboBox buildComboRka(String title) {
        ComboBox<String> combo = new ComboBox(title);
        Beans_laporan_mingguanx dpb = new Beans_laporan_mingguanx();
        Collection<String> data1;
        try {
            data1 = dpb.getListDateRka();
        } catch (SQLException ex) {
            data1 = new ArrayList();
        }
        combo.setItems(data1);
        return combo;
    }

    private void setCombo_jenis() {
        ArrayList<String> array = new ArrayList<>();
        array.add("Mingguan perKanwil");
        array.add("Mingguan perKanca");
        combo_jenis.setItems(array);
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setSizeUndefined();
        header.addStyleName("viewheader");
        Label titleLabel = new Label("Download Report");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H2);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        return header;
    }

    private String generatefilepath() {
        return VaadinService.getCurrent().getBaseDirectory().
                getAbsolutePath() + path + "mingguan_" + String.valueOf(System.currentTimeMillis()) + ".xls";
    }

    public Button createButtonExportExcel() {

        Button btn = new Button("Export");
        btn.setIcon(VaadinIcons.DOWNLOAD_ALT);
        ExcelFile = generatefilepath();
        Resource res = new FileResource(new File(ExcelFile));
        final AdvancedFileDownloader downloader = new AdvancedFileDownloader(res);
        downloader.addAdvancedDownloaderListener(new AdvancedFileDownloader.AdvancedDownloaderListener() {
            @Override
            public void beforeDownload(AdvancedFileDownloader.DownloaderEvent downloadEvent) {
                createExcel();
            }
        });
        downloader.extend(btn);
        return btn;
    }

    public void createExcel() {
        try {

            tanggal = combo_tanggal.getValue().toString();
            rka = combo_rka.getValue().toString();
            compare = combo_pembanding.getValue().toString();

            Beans_laporan_mingguan laporan_mingguan = new Beans_laporan_mingguan();
            Collection<Beans_laporan_mingguan> data1 = laporan_mingguan.getData(tanggal, compare, rka);

            //  kode_kanwil	nama_kanwil						
            //dc_trx	web_trx	trx_all	bep_edc	bep_web	bep_all	jumlah_edc	jumlah_web	jumlah_all	jumlah_edc_2017	jumlah_web_2017	jumlah_all_2017	casa_web	casa_edc	casa_all
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Jumlah");

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("kanwil");
            rowhead.createCell(2).setCellValue("Kode Kanwil.");
            rowhead.createCell(3).setCellValue("Mobile "+tanggal);
            rowhead.createCell(4).setCellValue("EDC "+tanggal);
            rowhead.createCell(5).setCellValue("All "+tanggal);
            rowhead.createCell(6).setCellValue("Mobile "+compare);
            rowhead.createCell(7).setCellValue("EDC "+compare);
            rowhead.createCell(8).setCellValue("All "+compare);
            rowhead.createCell(9).setCellValue("RKA "+rka);

            short i = 0;
            for (Beans_laporan_mingguan pencapaian_brilink_kanwil1 : data1) {
                System.out.println(i);
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(pencapaian_brilink_kanwil1.getNo());
                row.createCell(1).setCellValue(pencapaian_brilink_kanwil1.getNama_kanwil());
                row.createCell(2).setCellValue(pencapaian_brilink_kanwil1.getKode_kanwil());
                row.createCell(3).setCellValue(pencapaian_brilink_kanwil1.getJumlah_web());
                row.createCell(4).setCellValue(pencapaian_brilink_kanwil1.getJumlah_edc());
                row.createCell(5).setCellValue(pencapaian_brilink_kanwil1.getJumlah_all());
                row.createCell(6).setCellValue(pencapaian_brilink_kanwil1.getJumlah_edc_compare());
                row.createCell(7).setCellValue(pencapaian_brilink_kanwil1.getJumlah_web_compare());
                row.createCell(8).setCellValue(pencapaian_brilink_kanwil1.getJumlah_all_compare());
                row.createCell(9).setCellValue(pencapaian_brilink_kanwil1.getTotal_jumlah_rka());

            }

            // sheet 2
           
            HSSFSheet sheet_fbi = workbook.createSheet("FBI");

            HSSFRow rowhead_fbi = sheet_fbi.createRow((short) 0);
            rowhead_fbi.createCell(0).setCellValue("No.");
            rowhead_fbi.createCell(1).setCellValue("kanwil");
            rowhead_fbi.createCell(2).setCellValue("Kode Kanwil.");
            rowhead_fbi.createCell(3).setCellValue("Mobile "+tanggal);
            rowhead_fbi.createCell(4).setCellValue("EDC "+tanggal);
            rowhead_fbi.createCell(5).setCellValue("All "+tanggal);
            rowhead_fbi.createCell(6).setCellValue("Mobile "+compare);
            rowhead_fbi.createCell(7).setCellValue("EDC "+compare);
            rowhead_fbi.createCell(8).setCellValue("All "+compare);
            rowhead_fbi.createCell(9).setCellValue("RKA "+rka);

            short i_fbi = 0;
            for (Beans_laporan_mingguan pencapaian_brilink_kanwil1 : data1) {
                
                HSSFRow row = sheet_fbi.createRow(++i_fbi);
                row.createCell(0).setCellValue(pencapaian_brilink_kanwil1.getNo());
                row.createCell(1).setCellValue(pencapaian_brilink_kanwil1.getNama_kanwil());
                row.createCell(2).setCellValue(pencapaian_brilink_kanwil1.getKode_kanwil());
                row.createCell(3).setCellValue(pencapaian_brilink_kanwil1.getTotal_fee_web());
                row.createCell(4).setCellValue(pencapaian_brilink_kanwil1.getJumlah_edc());
                row.createCell(5).setCellValue(pencapaian_brilink_kanwil1.getJumlah_all());
                row.createCell(6).setCellValue(pencapaian_brilink_kanwil1.getJumlah_edc_compare());
                row.createCell(7).setCellValue(pencapaian_brilink_kanwil1.getJumlah_web_compare());
                row.createCell(8).setCellValue(pencapaian_brilink_kanwil1.getJumlah_all_compare());
                row.createCell(9).setCellValue(pencapaian_brilink_kanwil1.getTotal_jumlah_rka());

            }

            FileOutputStream fileOut = new FileOutputStream(ExcelFile);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void showWarning(String message) {
        Notification.show(message,
                Notification.Type.ERROR_MESSAGE).setPosition(Position.BOTTOM_CENTER);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bss.view;

import bss.backend.Chart_data;
import bss.backend.Chart_timeseries;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;

import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.Series;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.MeterGaugeRenderer;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;
import org.vaadin.highcharts.HighChart;

/**
 *
 * @author IVAN
 */
public class Chart extends Panel {

    public Chart() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(false);
        content.addStyleName("chartcontent");
        Component header = buildHeader();
        content.addComponent(header);
        content.setExpandRatio(header, 0.05f);

        CssLayout layout_chart = new CssLayout();

        content.addComponent(layout_chart);

        layout_chart.setSizeFull();
        content.setExpandRatio(layout_chart, 0.95f);
        layout_chart.addComponent(buildChartEdcd_Brimob());
       // layout_chart.addComponent(buildChartTrxFeeJml());
       // layout_chart.addComponent(buildChartCountMonthly());
        
                
        layout_chart.addComponent(buildChartFBI_EDCToday());
        layout_chart.addComponent(buildChartFBI_WEBToday());
       
      //  layout_chart.addComponent(buildChartNPE());
      //  layout_chart.addComponent(buildChartTransaksi());
        layout_chart.addComponent(buildActivationData());
        this.setContent(content);

      
    }

    private Component buildChartEdcd_Brimob() {

        DataSeries dataSeries = new DataSeries();

        // date, keterangan, value 
        Chart_data chartdata = new Chart_data();
        ArrayList<Map> array;
        String date ="";

        try {
            array = chartdata.getChartPieEDC_Mobile();

            int i = 0;
            for (Map data1 : array) {
                date = data1.get("date").toString();
                String keterangan = data1.get("keterangan").toString();
                keterangan = keterangan.replaceAll("jumlah_brilink_", "");
                Integer value = Integer.valueOf(data1.get("value").toString());

                dataSeries.newSeries().add(keterangan, value);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.PIE)
                .setRendererOptions(
                        new PieRenderer()
                                .setShowDataLabels(true));

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setShowTooltip(true)
                .setTooltipAlwaysVisible(true)
                .setKeepTooltipInsideChart(true)
                .setTooltipLocation(TooltipLocations.NORTH)
                .setTooltipAxes(TooltipAxes.XY_BAR);

        Legend legend = new Legend()
                .setShow(true)
                .setPlacement(LegendPlacements.OUTSIDE_GRID);

        Options options = new Options()
                .setSeriesDefaults(seriesDefaults)
                .setLegend(legend)
                .setHighlighter(highlighter);

        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        Component layout = buildPanelChart("EDC Brilink vs EDC Mobile ("+date+")", chart, "color3");
        return layout;
    }

    private Component buildChartTransaksi() {
        DataSeries dataSeries = new DataSeries();

        // tahun 2015
        Chart_timeseries timeseries = new Chart_timeseries();
        ArrayList<Map> array;
        String[] tahuns = null;
        Integer[] jumlah_brilinks;
        Integer[] beps;
        Integer[] agen_transaksis;
        try {
            array = timeseries.getTimeseries();
            tahuns = new String[array.size()];
            jumlah_brilinks = new Integer[array.size()];
            beps = new Integer[array.size()];
            agen_transaksis = new Integer[array.size()];
            int i = 0;
            for (Map data1 : array) {
                String tahun = data1.get("tahun").toString();
                Integer jumlah_brilink = Integer.valueOf(data1.get("jumlah_brilink").toString());
                Integer bep = Integer.valueOf(data1.get("bep").toString());
                Integer agen_transaksi = Integer.valueOf(data1.get("agen_transaksi").toString());

                System.out.println(jumlah_brilink + " " + bep + " " + agen_transaksi);

                jumlah_brilinks[i] = jumlah_brilink;
                beps[i] = bep;
                agen_transaksis[i] = agen_transaksi;

                tahuns[i++] = tahun;
            }

            dataSeries.add(jumlah_brilinks);
            dataSeries.add(beps);
            dataSeries.add(agen_transaksis);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setFillToZero(true)
                .setRenderer(SeriesRenderers.BAR);

        Series series = new Series()
                .addSeries(
                        new XYseries()
                                .setLabel("JUMLAH"))
                .addSeries(
                        new XYseries()
                                .setLabel("BEP"))
                .addSeries(
                        new XYseries()
                                .setLabel("TRANSAKSI"));

        Legend legend = new Legend()
                .setShow(true)
                .setPlacement(LegendPlacements.OUTSIDE_GRID);

        Axes axes = new Axes()
                .addAxis(
                        new XYaxis()
                                .setRenderer(AxisRenderers.CATEGORY)
                                .setTicks(
                                        new Ticks()
                                                .add(tahuns)))
                .addAxis(
                        new XYaxis(XYaxes.Y)
                                .setPad(1.05f)
                                .setTickOptions(
                                        new AxisTickRenderer()
                                                .setFormatString("%d")));

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setShowTooltip(true)
                .setTooltipAlwaysVisible(true)
                .setKeepTooltipInsideChart(true)
                .setTooltipLocation(TooltipLocations.NORTH)
                .setTooltipAxes(TooltipAxes.XY_BAR);

        Options options = new Options()
                .setSeriesDefaults(seriesDefaults)
                .setSeries(series)
                .setLegend(legend)
                .setHighlighter(highlighter)
                .setAxes(axes);

        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        Component layout = buildPanelChart("Time Series Brilink", chart, "color3");
        return layout;
    }
    
    private Component buildChartTrxFeeJml() {
        DataSeries dataSeries = new DataSeries();

        // tahun 2015
        Chart_timeseries timeseries = new Chart_timeseries();
        ArrayList<Map> array;
        String[] tahuns = null;
        Double[] jumlah;
        Double[] transaksi;
        Double[] fee;
        try {
            array = timeseries.getTimeseriesMtoM();
            tahuns = new String[array.size()];
            jumlah = new Double[array.size()];
            transaksi = new Double[array.size()];
            fee = new Double[array.size()];
            int i = 0;
            for (Map data1 : array) {
                String tahun = data1.get("bulan").toString();
                Double jumlah1 = Double.valueOf(data1.get("jumlah").toString());
                Double transaksi1 = Double.valueOf(data1.get("transaksi").toString()) / 1000.0;
                Double fee1 = Double.valueOf(data1.get("fee").toString()) / 1000000.0;

                System.out.println(jumlah1 + " " + transaksi1 + " " + fee1);

                jumlah[i] = jumlah1;
                transaksi[i] = transaksi1;
                fee[i] = fee1;

                tahuns[i++] = tahun;
            }

           
            dataSeries.add(transaksi);
            dataSeries.add(fee);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // jumlah not used, becouse not so good view for graph
        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setFillToZero(true)
                .setRenderer(SeriesRenderers.BAR);

        Series series = new Series()
             
                .addSeries(
                        new XYseries()
                                .setLabel("TRANSAKSI"))
                .addSeries(
                        new XYseries()
                                .setLabel("FEE"));

        Legend legend = new Legend()
                .setShow(true)
                .setPlacement(LegendPlacements.OUTSIDE_GRID);

        Axes axes = new Axes()
                .addAxis(
                        new XYaxis()
                                .setRenderer(AxisRenderers.CATEGORY)
                                .setTicks(
                                        new Ticks()
                                                .add(tahuns)))
                .addAxis(
                        new XYaxis(XYaxes.Y)
                                .setPad(1.05f)
                                .setTickOptions(
                                        new AxisTickRenderer()
                                                .setFormatString("%d")));

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setShowTooltip(true)
                .setTooltipAlwaysVisible(true)
                .setKeepTooltipInsideChart(true)
                .setTooltipLocation(TooltipLocations.NORTH)
                .setTooltipAxes(TooltipAxes.XY_BAR);

        Options options = new Options()
                .setSeriesDefaults(seriesDefaults)
                .setSeries(series)
                .setLegend(legend)
                .setHighlighter(highlighter)
                .setAxes(axes);

        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        Component layout = buildPanelChartDouble("Monthly Transaction/Feebased Brilink", chart, "color3");
        return layout;
    }


    private Component buildChartCountMonthly() {

        // tahun 2015
        Chart_timeseries timeseries = new Chart_timeseries();
        ArrayList<Map> array;

        DataSeries dataSeries = new DataSeries()
                .newSeries();

        try {
            array = timeseries.getCountMonthly();

            int i = 0;
            for (Map data1 : array) {
                String date1 = data1.get("bulan").toString();
                Double value = Double.valueOf(data1.get("jumlah").toString());
                
                int tahun = Integer.valueOf(date1.substring(0, 4));
                int bulan = Integer.valueOf(date1.substring(4, 6));
                int tanggal = 1;

                    GregorianCalendar g = new GregorianCalendar();
                    g.set(tahun, bulan - 1, tanggal);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                    String date2 = sdf.format(g.getTime());
                    
                    
                System.out.println("x "+date2);
                
                dataSeries.add(date2, value);
          }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        SeriesDefaults seriesDefaults = new SeriesDefaults();
        Axes axes = new Axes()
                .addAxis(
                        new XYaxis()
                                .setRenderer(AxisRenderers.DATE)
                                .setTickOptions(
                                        new AxisTickRenderer().setFormatString("%m-%Y"))
                                .setNumberTicks(5))
                .addAxis(
                        new XYaxis(XYaxes.Y)
                                .setTickOptions(
                                        new AxisTickRenderer()
                                                .setFormatString("%d")));

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setSizeAdjust(10)
                .setTooltipLocation(TooltipLocations.NORTH)
                .setTooltipAxes(TooltipAxes.Y)
                .setTooltipFormatString("<b><i><span style='color:red;'>Jumlah :%d </span></i></b> ")
                .setUseAxesFormatters(false);

        Options options = new Options()
                .addOption(seriesDefaults)
                .addOption(axes)
                .addOption(highlighter);

        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        Component layout = buildPanelChart("Amount ", chart, "color2");
        return layout;
    }
       
       
    private Component buildChartFBI_EDCToday() {

        // tahun 2015
        Chart_timeseries timeseries = new Chart_timeseries();
        ArrayList<Map> array;

        DataSeries dataSeries = new DataSeries()
                .newSeries();

        try {
            array = timeseries.getFBITimeSeriesEDC();

            int i = 0;
            for (Map data1 : array) {
                String datekey = "";
                for (Object key : data1.keySet()) {
                    datekey = key.toString();
                    long value = (long) Double.valueOf(data1.get(datekey).toString()).doubleValue() / 1000000;

                    String datakeyname = datekey.replaceAll("fee_bri_", "");
                    // dataSeries.add(datakeyname, value);

                    int tahun = Integer.valueOf(datakeyname.substring(0, 4));
                    int bulan = Integer.valueOf(datakeyname.substring(4, 6));
                    int tanggal = 1 ;//Integer.valueOf(datakeyname.substring(6, 8));

                    GregorianCalendar g = new GregorianCalendar();
                    g.set(tahun, bulan - 1, tanggal);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                    String date1 = sdf.format(g.getTime());
                    System.out.println(date1 + " " + value);
                    dataSeries.add(date1, value);
                }
                //  String keterangan = data1.get("keterangan").toString();
                // Integer value = Integer.valueOf(data1.get("value").toString());

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        SeriesDefaults seriesDefaults = new SeriesDefaults();
        Axes axes = new Axes()
                .addAxis(
                        new XYaxis()
                                .setRenderer(AxisRenderers.DATE)
                                .setTickOptions(
                                        new AxisTickRenderer().setFormatString("%m-%Y"))
                                .setNumberTicks(5))
                .addAxis(
                        new XYaxis(XYaxes.Y)
                                .setTickOptions(
                                        new AxisTickRenderer()
                                                .setFormatString("%d")));

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setSizeAdjust(10)
                .setTooltipLocation(TooltipLocations.NORTH)
                .setTooltipAxes(TooltipAxes.Y)
                .setTooltipFormatString("<b><i><span style='color:red;'>FBI :%d </span></i></b> ")
                .setUseAxesFormatters(false);

        Options options = new Options()
                .addOption(seriesDefaults)
                .addOption(axes)
                .addOption(highlighter);

        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        Component layout = buildPanelChart("FBI EDC Perbulan (dalam jutaan) ", chart, "color2");
        return layout;
    }
    
    private Component buildChartFBI_WEBToday() {

        // tahun 2015
        Chart_timeseries timeseries = new Chart_timeseries();
        ArrayList<Map> array;

        DataSeries dataSeries = new DataSeries()
                .newSeries();

        try {
            array = timeseries.getFBITimeSeriesMobile();

            int i = 0;
            for (Map data1 : array) {
                String datekey = "";
                for (Object key : data1.keySet()) {
                    datekey = key.toString();
                    long value = (long) Double.valueOf(data1.get(datekey).toString()).doubleValue() / 1000000;

                    String datakeyname = datekey.replaceAll("fee_bri_", "");
                    // dataSeries.add(datakeyname, value);

                    int tahun = Integer.valueOf(datakeyname.substring(0, 4));
                    int bulan = Integer.valueOf(datakeyname.substring(4, 6));
                    int tanggal = 1;// Integer.valueOf(datakeyname.substring(6, 8));

                    GregorianCalendar g = new GregorianCalendar();
                    g.set(tahun, bulan - 1, tanggal);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                    String date1 = sdf.format(g.getTime());
                    System.out.println(date1 + " " + value);
                    dataSeries.add(date1, value);
                }
                //  String keterangan = data1.get("keterangan").toString();
                // Integer value = Integer.valueOf(data1.get("value").toString());

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        SeriesDefaults seriesDefaults = new SeriesDefaults();
        Axes axes = new Axes()
                .addAxis(
                        new XYaxis()
                                .setRenderer(AxisRenderers.DATE)
                                .setTickOptions(
                                        new AxisTickRenderer().setFormatString("%m-%Y"))
                                .setNumberTicks(5))
                .addAxis(
                        new XYaxis(XYaxes.Y)
                                .setTickOptions(
                                        new AxisTickRenderer()
                                                .setFormatString("%d")));

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setSizeAdjust(10)
                .setTooltipLocation(TooltipLocations.NORTH)
                .setTooltipAxes(TooltipAxes.Y)
                .setTooltipFormatString("<b><i><span style='color:red;'>FBI :%d </span></i></b> ")
                .setUseAxesFormatters(false);

        Options options = new Options()
                .addOption(seriesDefaults)
                .addOption(axes)
                .addOption(highlighter);

        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        Component layout = buildPanelChart("FBI Mobile Perbulan (dalam jutaan) ", chart, "color1");
        return layout;
    }
    
    private Component buildActivationData(){
         ArrayList<Map> array;

        Chart_data chartdata = new Chart_data();
        int non_active=0;
        int active=0;
        int total=0;
        float percent =0.0f;

        try {
            array = chartdata.getDataActivated();
            
              for (Map data1 : array) {
             
                 non_active = Integer.parseInt(data1.get("BELUM AKTIF").toString());
                 active = Integer.parseInt(data1.get("AKTIF").toString());
                 total = non_active + active;
                 percent = Math.round( ((float)active/(float)total )* 100.0f);
            }
              
        }catch (Exception e){
            e.printStackTrace();
        }
       
        Locale locale  = new Locale("in","ID");
        DecimalFormat decimalFormat = (DecimalFormat)
        NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern("###,###.##");        
        
        VerticalLayout panel1 = new VerticalLayout();
        Label ltotal = new Label("Total Mobile : "+decimalFormat.format(total));
        ltotal.setStyleName("label1");
        
        Label lactive = new Label("Sudah Aktivasi : "+decimalFormat.format(active));
        lactive.setStyleName("label1");
        
        Label lnonactive = new Label("Belum Aktivasi : "+decimalFormat.format(non_active));
        lnonactive.setStyleName("label1");
        
        Label lpercent = new Label("Percent Aktif: "+decimalFormat.format(percent)+" %");
        lpercent.setStyleName("label1");
        
        panel1.addComponent(ltotal);
        panel1.addComponent(lactive); 
        panel1.addComponent(lnonactive);
        panel1.addComponent(lpercent);
        Component layout = buildPanelChart("Activation Mobile :", panel1, "color5");
      
           
        return layout;
    }

    private Component buildChartStaging() {
        DataSeries dataSeries = new DataSeries();

        // date, keterangan, value 
        Chart_data chartdata = new Chart_data();
        ArrayList<Map> array;

        try {
            array = chartdata.getChartPieStaging();

            int i = 0;
            for (Map data1 : array) {
                String date = data1.get("date").toString();
                String keterangan = data1.get("keterangan").toString();

                Integer value = Integer.valueOf(data1.get("value").toString());

                dataSeries.add(value);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.METER_GAUGE)
                .setRendererOptions(
                        new MeterGaugeRenderer()
                                .setMin(1000)
                                .setMax(20000)
                                .setIntervals(1000, 5000, 10000, 15000, 20000)
                                .setIntervalColors("#66cc66", "#93b75f", "#E7E658", "#d67124", "#cc6666"));

        Options options = new Options()
                .setSeriesDefaults(seriesDefaults);

        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        Component layout = buildPanelChart2("Jumlah Staging  ", chart, "color5");

        return layout;
    }

    private Component buildChartNPE() {
        DataSeries dataSeries = new DataSeries();

        // date, keterangan, value 
        Chart_data chartdata = new Chart_data();
        ArrayList<Map> array;

        try {
            array = chartdata.getChartNonPerformance();

            int i = 0;
            for (Map data1 : array) {
                String date = data1.get("date").toString();
                String keterangan = data1.get("keterangan").toString();

                Integer value = Integer.valueOf(data1.get("value").toString());

                dataSeries.add(value);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.METER_GAUGE)
                .setRendererOptions(
                        new MeterGaugeRenderer()
                                .setMin(0)
                                .setMax(60000)
                                .setIntervals(0, 10000, 20000, 40000, 50000, 60000)
                                .setIntervalColors("#66cc66", "#93b75f", "#E7E658", "#d67124", "#cc6666", "#cc6666"));

        Options options = new Options()
                .setSeriesDefaults(seriesDefaults);

        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        Component layout = buildPanelChart2("Jumlah EDC Non-Performance  ", chart, "color5");
        return layout;
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        Label titleLabel = new Label("Dashboard");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        return header;
    }

    public Component buildPanelChart(String title, Component c, String theme) {

        Panel panel = new Panel(title);
        panel.setIcon(FontAwesome.BAR_CHART_O);

        panel.setContent(c);
        c.setWidth("400px");
        panel.addStyleName(theme);   
        panel.addStyleName("chart");
        panel.setWidth("500px");

        VerticalLayout v = new VerticalLayout();
        v.setSizeFull();
        v.setMargin(false);
        v.addStyleName("chartcontent");
        v.addComponent(c);

        panel.setContent(v);
        return panel;
    }

    public Component buildPanelChart2(String title1, Component c1, String theme) {

        c1.setWidth("300px");
        Panel panel1 = new Panel(title1);
        panel1.addStyleName("chart2");
        panel1.setIcon(FontAwesome.CALENDAR_TIMES_O);
        panel1.setContent(c1);
        panel1.addStyleName(theme);
        panel1.setWidth("500px");
        c1.setHeight("300px");
        return panel1;
    }
    
    public Component buildPanelChartDouble(String title, Component c1, String theme) {

      
        Panel panel = new Panel(title);
        panel.setIcon(FontAwesome.BAR_CHART_O);
        panel.setContent(c1);
        c1.setWidth("450px");
        panel.addStyleName(theme);
        panel.addStyleName("chart");
        panel.setWidth("500px");
        VerticalLayout v = new VerticalLayout();
        v.setSizeFull();
        v.setMargin(false);
        v.addStyleName("chartcontent");
        v.addComponent(c1);

        panel.setContent(v);
        return panel;
    }
    
    public void testchart(){
        
        HighChart chart = new HighChart();
        
        
    }
}



package bss;

import bss.view.Chart;
import bss.view.Home;
import bss.view.LoginView;
import bss.view.MapB;
import bss.view.Ranking;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Title("Dashboard Brilink")
@Viewport("user-scalable=no,initial-scale=0.9")
public class MyUI extends UI {
   
    VerticalLayout layout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Locale.setDefault(Locale.UK);
        // getPage().getJavaScript().execute("document.head.innerHTML += '<meta name=\"viewport\" content=\"initial-scale = 0.9,maximum-scale = 0.9\">'");
       Notification notification = new Notification(
                "Welcome to Dashboard Brilink");
        notification
                .setDescription("<span Selamat datang di Dashboard Brilink.</span> <span> Dashboard ini ditujukan untuk keperluan monitoring internal Divisi JRM <b>Silahkan login untuk melanjutkan</b> .</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(5000);
        notification.show(Page.getCurrent());
        
        
        new Navigator(this, this);
        getNavigator().addView(LoginView.NAME, LoginView.class);
         
        getNavigator().setErrorView(LoginView.class);
        Page.getCurrent().addUriFragmentChangedListener(new UriFragmentChangedListener() {
            @Override
            public void uriFragmentChanged(Page.UriFragmentChangedEvent event) {
                router(event.getUriFragment());
            }
        }); 
    }

    private void router(String route) {
        if(getSession().getAttribute("user") != null){
             getNavigator().addView(Home.NAME, Home.class);
	    getNavigator().navigateTo(Home.NAME);
        }else{
            getNavigator().navigateTo(LoginView.NAME);
            }  
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    private CssLayout testResponsive() {
        CssLayout layout = new CssLayout();
        layout.setWidth("100%");
        layout.addStyleName("flexwrap");

        Responsive.makeResponsive(layout);

        Label title = new Label("Space is big, really big");
        title.addStyleName("title");
        layout.addComponent(title);

        Label description = new Label("This is a "
                + "long description of the image shown "
                + "on the right or below, depending on the "
                + "screen width. The text here could continue long.");
        description.addStyleName("itembox");
        description.setSizeUndefined();
        layout.addComponent(description);

        Image image = new Image(null,
                new ThemeResource("img/background.png"));
        image.addStyleName("itembox");
        //layout.addComponent(image);
        return layout;
    }

    public void removeAllWindow() {
        Collection<Window> wins = getUI().getWindows();

        for (Window win1 : wins) {
            getUI().removeWindow(win1);
        }
    }

    public void loginSuccess() {
        setContent(layout);
    }

    public Home getHome() {
        // all novigation is home (after login)
        return  (Home) getNavigator().getCurrentView();
    }
    
    
    public void showNotificationWarning(String message){
         Notification notification = new Notification(
                "Warning");
        notification
                .setDescription("<span Dashboard Brilink.</span> <span>"+message+".</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
       
        notification.show(Page.getCurrent());
    }
    

}

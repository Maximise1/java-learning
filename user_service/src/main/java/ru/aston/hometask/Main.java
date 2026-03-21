package ru.aston.hometask;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ru.aston.hometask.config.AppConfig;
import ru.aston.hometask.config.PersistenceConfig;

public class Main {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(0);
        tomcat.setBaseDir("temp");
        tomcat.getConnector();
        Context context = tomcat.addContext("", new java.io.File(".").getAbsolutePath());

        AnnotationConfigWebApplicationContext appContext =
                new AnnotationConfigWebApplicationContext();
        appContext.register(
                AppConfig.class,
                PersistenceConfig.class
        );
        appContext.setServletContext(context.getServletContext());
        appContext.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        Tomcat.addServlet(context, "dispatcher", dispatcherServlet);
        context.addServletMappingDecoded("/", "dispatcher");

        tomcat.start();
        int port = tomcat.getConnector().getLocalPort();
        System.out.println("Server started at http://localhost:" + port);
        tomcat.getServer().await();
    }
}

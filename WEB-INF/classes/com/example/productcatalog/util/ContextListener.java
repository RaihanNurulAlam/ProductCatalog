package com.example.productcatalog.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String jdbcURL = sce.getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = sce.getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = sce.getServletContext().getInitParameter("jdbcPassword");
        DBUtil.init(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup resources if needed
    }
}

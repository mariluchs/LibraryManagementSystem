package iu.piisj.librarymanagementsystem.library.bootstrap;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class LibraryDataSeederListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(">>> [Listener] contextInitialized");
        new LibraryDataSeeder().seed();
    }
}

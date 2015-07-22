/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker;

import TVShowTimelineMaker.ui.LogWindow;
import TVShowTimelineMaker.ui.StartWindow;
import TVShowTimelineMaker.util.XML.TopLevelXMLWriter;
import com.civprod.io.DirectoryWalkers.LoadingDirectoryWalker;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steven Owens
 */
public class Main {

    public static Show myShow;
    public static TopLevelXMLWriter xmlWriter;
    public static final Random rnd = new Random();
    public static AtomicBoolean loadClasses = new AtomicBoolean(false);
    
    public static final int YearRange = 3000;
    
    public static final Logger AppGlobalLogger = java.util.logging.Logger.getLogger("TVShowTimelineMaker");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LogWindow.getGobalWindow().setVisible(true);
        AppGlobalLogger.setLevel(Level.ALL);
        AppGlobalLogger.addHandler(LogWindow.getGobalWindow().getLogHandler());
        Logger.getLogger(Main.class.getName()).setParent(Logger.getGlobal());
        StartWindow newStartWindow = new StartWindow();
        newStartWindow.setVisible(true);
        final String pathJar;
        if (args.length >= 1) {
                pathJar = args[0];
        } else {
            pathJar = "build\\classes";
        }
        new Thread(() -> {
            java.util.Collection<Class<?>> classes = new java.util.ArrayList<>(1);
            LoadingDirectoryWalker mWalker = new LoadingDirectoryWalker("TVShowTimelineMaker");
            try {
                mWalker.run(new File(pathJar), classes);
                com.civprod.dynamicClassLoading.Initializer.initialize(classes);
                loadClasses.set(true);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        /*File mlpXmlFile = new File(path);
        xmlWriter = new TopLevelXMLWriter(mlpXmlFile);
        myShow = null;
        if (mlpXmlFile.exists()) {
            try {
                myShow = xmlWriter.readXMLFile();
            } catch (JDOMException | IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (myShow == null) {
            myShow = new Show();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new shutDownWriter()));
        TimeLineEditor newTimeLineEditor = new TimeLineEditor();
        ShowEditor newShowEditor = new ShowEditor();
        newTimeLineEditor.setVisible(true);
        newShowEditor.setVisible(true);*/
    }

    public static class shutDownWriter implements java.lang.Runnable {

        @Override
        public void run() {
            try {
                xmlWriter.writeXMLFile(myShow);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

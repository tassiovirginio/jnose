package br.ufba.jnose;

import br.ufba.jnose.core.CSVCore;
import br.ufba.jnose.pages.HomePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import java.io.File;

public class WicketApplication extends WebApplication {

    public static boolean COBERTURA_ON = false;

    public static String USERHOME;

    public static String JNOSE_PROJECTS_FOLDER;

    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {

        USERHOME = System.getProperty("user.home");
        System.out.println("OS current user home directory is " + USERHOME);

        String strTmp = System.getProperty("java.io.tmpdir");
        System.out.println("OS current temporary directory: " + strTmp);
        System.out.println("OS Name: " + System.getProperty("os.name"));
        System.out.println("OS Version: " + System.getProperty("os.version"));
        System.out.println("OS user home directory is " + USERHOME);

        JNOSE_PROJECTS_FOLDER = USERHOME+ File.separator + ".jnose_projects" + File.separator;
        System.out.println("OS JNose Projects folder: " + JNOSE_PROJECTS_FOLDER);

        super.init();
        this.getMarkupSettings().setStripWicketTags(true);
        this.getDebugSettings().setAjaxDebugModeEnabled(false);
        CSVCore.load(this);

        File file = new File(JNOSE_PROJECTS_FOLDER);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}

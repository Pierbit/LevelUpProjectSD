package model.storage;

import java.io.File;

public abstract class Upload {
    public static String getUploadPath() {
        return System.getenv("CATALINA_HOME") + File.separator + "uploads" + File.separator;
    }
}

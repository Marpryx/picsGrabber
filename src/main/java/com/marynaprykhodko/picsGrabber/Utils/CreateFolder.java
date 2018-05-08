package com.marynaprykhodko.picsGrabber.Utils;

import com.marynaprykhodko.picsGrabber.Controller.GrabberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class CreateFolder {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(GrabberController.class);


    /**
     * Detects user's OS.
     * The System class maintains a Properties object that describes the configuration of
     * the current working environment.
     * The key "os.name" means - Operating system name.
     * According to https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
     */

    public static String detectOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "Windows";
        } else if (os.contains("nux") || os.contains("nix")) {
            return "Linux";
        } else if (os.contains("mac")) {
            return "Mac";
        } else {
            return "Other";
        }
    }


    /**
     * Returns path to the user's home directory.
     * Must be crossplatform.
     * According to http://stackoverflow.com/a/586345
     *
     * The System class maintains a Properties object that describes the configuration of
     * the current working environment.
     * The key "user.home" means - User home directory
     *  According to https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
     *
     * @return
     */
    public static String getUserDesktopDirPath() {
        // To get user's Desktop's path
        File desktop = new File(System.getProperty("user.home"), "Desktop");
        LOG.info("Desktop path: " + desktop.getPath());
        return desktop.getPath();
    }

    /**
     * Makes a full directory path by concatenating parent path with the folder's
     * name. It also detects the user's OS to specify the correct path.
     *
     * @param parentPath
     * @param folderName
     * @return
     */
    private static String createFullPath(String parentPath, String folderName) {
        String directoryName;

        LOG.info(detectOS());
        switch (detectOS()) {
            case "Mac":
                directoryName = parentPath.concat("/" + folderName);
                break;
            case "Windows":
                directoryName = parentPath.concat("\\" + folderName);
                break;
            // here, in case of the issues with 2 above cases we just concatenate
            // desired folder name to provided path. Note, there is no slash at the
            // beginning of the folder name for purpose no to breake parent path.
            default:
                directoryName = parentPath.concat("_" + folderName);
        }
        return directoryName;
    }


}

package com.blogspot.qbeukes.cubes.util;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 8:18:41 PM
 */
public class AppUtil {
  public static final String TITLE = "CubeTimer";

  /**
   * Name of directory under the user's home directory where files are stored.
   */
  private static final String STORAGE_DIRECTORY_NAME = ".cubetimer";

  /**
   * Directory where files will be stored
   */
  private static File storageDirectory;

  /**
   * User's home directory
   */
  private static String homeDirectory;

  /**
   * Operating system's file path separator
   */
  private static String filePathSeparator;

  /**
   * Show an error and exit.
   * @param message
   */
  public static void fatalError(String message) {
    MessageUtil.showError(TITLE, message);
    System.exit(1);
  }

  /**
   * Performs the necessary steps to display a dialog. This must be invoked from a non-AWT event dispatch thread.
   * This method will block until the dialog is dismissed.
   *
   * @param dialog Dialog to display
   * @param locationRelativeTo The dialog will be centered around this component. If null it will be centered on screen.
   */
  public static void showDialog(final JDialog dialog, final Component locationRelativeTo) {
    try {
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {
          dialog.pack();
          dialog.setLocationRelativeTo(locationRelativeTo);
        }
      });

      dialog.setVisible(true);
    }
    catch (InvocationTargetException e) {
      String msg = "Error occurred while displaying the '" + dialog.getTitle() + "' dialog.";
      MessageUtil.showError("Profiles", msg);
      System.err.println(msg);
      e.printStackTrace(System.err);
    }
    catch (InterruptedException e) {
      String msg = "Interrupted while displaying the '" + dialog.getTitle() + "' dialog.";
      MessageUtil.showError("Profiles", msg);
      System.err.println(msg);
      e.printStackTrace(System.err);
    }
  }

  /**
   * Get the base directory where all files related to the application will be stored. This is a directory named
   * {@link #STORAGE_DIRECTORY_NAME} inside the user's home directory. If it doesn't exist this method will create it.
   *
   * @return Reference to storage directory
   */
  public static File getStorageDirectory() {
    if (storageDirectory == null) {
      String home = AppUtil.getHomeDirectory();
      String separator = AppUtil.getFilePathSeparator();

      storageDirectory = new File(home + separator + STORAGE_DIRECTORY_NAME);

      try {
        storageDirectory.mkdirs();
      }
      catch (SecurityException e) {
        throw new SecurityException("Unable to create base directory where data will be stored to/loaded from: " + storageDirectory, e);
      }
    }

    return storageDirectory;
  }

  /**
   * Determine the user's home directory. Any error will show an error dialog.
   *
   * @return User's home directory.
   */
  public static String getHomeDirectory() {
    if (homeDirectory == null) {
      homeDirectory = System.getProperty("user.home");
      if (homeDirectory == null) {
        MessageUtil.showError(AppUtil.TITLE, "Cannot determine user home directory. Saving/loading data will not work.");
      }
    }

    return homeDirectory;
  }

  /**
   * Determine the operating system's file path separator (for example / on Unix based systems). Any error will show an error dialog.
   *
   * @return File path separator
   */
  public static String getFilePathSeparator() {
    if (filePathSeparator == null) {
      filePathSeparator = System.getProperty("file.separator");
      if (filePathSeparator == null) {
        MessageUtil.showError(AppUtil.TITLE, "Cannot determine platform path separator. Saving/loading data will not work.");
      }
    }

    return filePathSeparator;
  }
}

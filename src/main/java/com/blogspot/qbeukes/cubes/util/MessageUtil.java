package com.blogspot.qbeukes.cubes.util;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 1:28:10 PM
 */
public class MessageUtil {
  public static void showError(String title, String message) {
    showMessage(JOptionPane.ERROR_MESSAGE, title, message);
  }

  public static void showWarn(String title, String message) {
    showMessage(JOptionPane.WARNING_MESSAGE, title, message);
  }

  private static void showMessage(final int type, final String title, final String message) {
    if (SwingUtilities.isEventDispatchThread()) {
      JOptionPane.showMessageDialog(null, message, title, type);
    }
    else {
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {
            JOptionPane.showMessageDialog(null, message, title, type);
          }
        });
      }
      catch (InvocationTargetException e) {
        System.err.println("Failed to display message.");
        e.printStackTrace(System.err);
      }
      catch (InterruptedException e) {
        System.err.println("Interrupted while displaying message.");
        e.printStackTrace(System.err);
        
      }
    }
  }

  /**
   * Shows a YES/NO confirm dialog.
   *
   * @param title
   * @param message
   * @return Either {@link javax.swing.JOptionPane#YES_OPTION} or {@link javax.swing.JOptionPane#NO_OPTION}.
   */
  public static int showYesNoConfirmDialog(String title, String message) {
    return showYesNoConfirmDialog(null, title, message);
  }

  /**
   * Shows a YES/NO confirm dialog.
   *
   * @param parent Parent component
   * @param title
   * @param message
   * @return Either {@link javax.swing.JOptionPane#YES_OPTION} or {@link javax.swing.JOptionPane#NO_OPTION}.
   */
  public static int showYesNoConfirmDialog(Component parent, String title, String message) {
    return JOptionPane.showConfirmDialog(parent, message, title,
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
  }
}

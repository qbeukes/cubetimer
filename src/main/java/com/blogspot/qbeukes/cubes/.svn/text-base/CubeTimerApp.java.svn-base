package com.blogspot.qbeukes.cubes;

import com.blogspot.qbeukes.cubes.countdown.PlaySound;
import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.scores.ScoreManager;
import com.blogspot.qbeukes.cubes.ui.CubeTimer;
import com.blogspot.qbeukes.cubes.updates.UpdateException;
import com.blogspot.qbeukes.cubes.updates.UpdateManager;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 15 Nov 2010
 * Time: 12:26:28 AM
 */
public class CubeTimerApp {
  public static void main(String[] args) throws Exception {
    setLookAndFeel();
    Config.initialize();
    performUpdates();
    
    PlaySound.initialize(); // to ensure 0 delay
    ScoreManager.initialize();

    final CubeTimer dialog = new CubeTimer();
    SwingUtilities.invokeAndWait(new Runnable() {
      public void run() {
        dialog.setTitle("CubeTimer");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
      }
    });

    dialog.setVisible(true);
    System.exit(0);
  }

  private static void performUpdates() throws UpdateException {
    UpdateManager updateManager = new UpdateManager();
    updateManager.performUpdates();
  }

  private static void setLookAndFeel() {
    if (tryLookAndFeel(UIManager.getSystemLookAndFeelClassName())) {
      return;
    }

    if (tryLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())) {
      return;
    }
    
    if (tryLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")) {
      return;
    }

    if (tryLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")) {
      return;
    }

    if (tryLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel")) {
      return;
    }
  }

  private static boolean tryLookAndFeel(String lnf) {
    try {
      UIManager.setLookAndFeel(lnf);
      System.out.println("Using look and feel: " + lnf);
      return true;
    }
    catch (Exception e) {
      System.err.println("Failed to set look and feel: " + lnf);
      return false;
    }
  }
}

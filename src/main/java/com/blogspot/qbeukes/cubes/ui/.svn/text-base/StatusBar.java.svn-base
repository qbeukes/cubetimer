package com.blogspot.qbeukes.cubes.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 9:41:34 PM
 */
public class StatusBar {
  public static List<WeakReference<StatusBar>> instances = new ArrayList<WeakReference<StatusBar>>();

  private static String currentStatus = "Ready... set... GO!";

  private static Color currentColor = Color.BLACK;
  
  private JPanel statusPanel;
  private JLabel statusLabel;

  public StatusBar() {
    synchronized (StatusBar.class) {
      instances.add(new WeakReference(this));
    }
    updateDisplay();
  }

  public void updateDisplay() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        statusLabel.setText(currentStatus);
        statusLabel.setForeground(currentColor);
      }
    });
  }

  public static void publishStatus(String text) {
    publishStatus(text, false);
  }

  public static void publishStatus(String text, boolean error) {
    if (error) {
      publishStatus(text, Color.RED);
    }
    else {
      publishStatus(text, Color.BLACK);
    }
  }

  public synchronized static void publishStatus(String text, Color color) {
    currentStatus = text;
    currentColor = color;

    Iterator<WeakReference<StatusBar>> iter = instances.iterator();

    while (iter.hasNext()) {
      WeakReference<StatusBar> reference = iter.next();

      StatusBar statusBar = (StatusBar)reference.get();

      if (statusBar == null) {
        iter.remove();
      }
      else {
        statusBar.updateDisplay();
      }
    }
  }
}

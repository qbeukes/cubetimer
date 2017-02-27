package com.blogspot.qbeukes.cubes.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
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
    } else {
      publishStatus(text, Color.BLACK);
    }
  }

  public synchronized static void publishStatus(String text, Color color) {
    currentStatus = text;
    currentColor = color;

    Iterator<WeakReference<StatusBar>> iter = instances.iterator();

    while (iter.hasNext()) {
      WeakReference<StatusBar> reference = iter.next();

      StatusBar statusBar = (StatusBar) reference.get();

      if (statusBar == null) {
        iter.remove();
      } else {
        statusBar.updateDisplay();
      }
    }
  }

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    statusPanel = new JPanel();
    statusPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
    statusLabel = new JLabel();
    statusLabel.setText("Status Label");
    statusPanel.add(statusLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JSeparator separator1 = new JSeparator();
    statusPanel.add(separator1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return statusPanel;
  }
}

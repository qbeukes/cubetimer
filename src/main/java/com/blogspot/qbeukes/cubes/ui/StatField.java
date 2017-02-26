package com.blogspot.qbeukes.cubes.ui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 9:15:02 PM
 */
public class StatField {
  private JTextField statText;
  private JPanel statField;

  private String stat;

  public void setStat(long stat) {
    setStat(Long.toString(stat));
  }

  public void setStat(String stat) {
    this.stat = stat;

    if (SwingUtilities.isEventDispatchThread()) {
      displayStat();
    }
    else {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          displayStat();
        }
      });
    }
  }

  private void displayStat() {
    statText.setText(stat);
  }
}

package com.blogspot.qbeukes.cubes.ui;

import com.blogspot.qbeukes.cubes.util.TimeUtil;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 12:47:05 PM
 */
public class TimeField {
  private JPanel timeFieldPanel;
  private JTextField timeText;
  private JLabel timeLabel;

  private long milliseconds;

  public void setTime(long milliseconds) {
    this.milliseconds = milliseconds;

    if (SwingUtilities.isEventDispatchThread()) {
      displayTime();
    }
    else {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          displayTime();
        }
      });
    }
  }

  private void displayTime() {
    String time = TimeUtil.formatTime(milliseconds);
    timeText.setText(time);
  }
}

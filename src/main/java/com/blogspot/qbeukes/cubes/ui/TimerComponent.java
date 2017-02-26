package com.blogspot.qbeukes.cubes.ui;

import com.blogspot.qbeukes.cubes.countdown.CountdownSound;
import com.blogspot.qbeukes.cubes.countdown.PlaySound;
import com.blogspot.qbeukes.cubes.util.MessageUtil;
import com.blogspot.qbeukes.cubes.util.TimeUtil;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 1:21:45 PM
 */
public class TimerComponent implements Runnable {
  /**
   * At what count down value should the stepping sound start.
   */
  private static final int SOUND_START = 3;

  private JPanel timerPanel;
  private JLabel timerLabel;

  private volatile boolean running = false;

  private int countDown;

  private long startTime;

  private long lastTime;

  private boolean started = false;

  public void run() {
    running = true;
    lastTime = 0;
    started = false;

    int count = countDown;
    int previousCount = 0;

    startTime = System.currentTimeMillis();

    while (running) {
      if (count > 0) {
        if (countDown == count) {
          StatusBar.publishStatus("Count down...", Color.BLUE);
        }

        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        count = (int)(countDown - elapsed);

        updateDisplay(Integer.toString(count), Color.RED);
        
        if (previousCount != count && count != 0) {
          doSound(count);
        }

        previousCount = count;
      }
      else {
        if (count == 0) {
          doSound(0);
          count = -1;

          StatusBar.publishStatus("Solve started!", new Color(0x00, 0x94, 0x00));

          startTime = System.currentTimeMillis();
          started = true;
        }

        lastTime = System.currentTimeMillis();

        String time = TimeUtil.formatTime(lastTime - startTime);
        updateDisplay(time, Color.BLACK);
      }

      try {
        Thread.sleep(1);
      }
      catch (InterruptedException e) {
        break;
      }
    }

    StatusBar.publishStatus("Solve stopped.");
  }

  private void doSound(int count) {
    if (count == 0) {
      PlaySound.playSound(CountdownSound.FINAL);
    }
    else if (count <= SOUND_START) {
      PlaySound.playSound(CountdownSound.STEP);
    }
  }

  public void startTimer(int countDown) {
    if (running) {
      MessageUtil.showError("Timer", "A timer is already running!");
      return;
    }
    this.countDown = countDown;
    new Thread(this).start();
  }

  public void stopTimer() {
    running = false;
  }

  private void updateDisplay(final String text, final Color color) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        timerLabel.setText(text);
        timerLabel.setForeground(color);
      }
    });
  }

  /**
   * Returns the elapsed time.
   * 
   * @return The current value of the timer.
   */
  public long getDuration() {
    return lastTime - startTime;
  }

  /**
   * @return The start time of the current timer
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * @return The countdown used in this timer instance.
   */
  public int getCountDown() {
    return countDown;
  }

  /**
   * @return True if the countdown completed and the timing started
   */
  public boolean countDownCompleted() {
    return started;
  }
}
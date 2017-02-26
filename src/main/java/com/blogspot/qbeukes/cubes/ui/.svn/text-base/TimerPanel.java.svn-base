package com.blogspot.qbeukes.cubes.ui;

import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.Profile;
import com.blogspot.qbeukes.cubes.model.config.ProfileListener;
import com.blogspot.qbeukes.cubes.model.config.ProfileManager;
import com.blogspot.qbeukes.cubes.model.scores.ScoreManager;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 4:58:11 PM
 */
public class TimerPanel implements ProfileListener {
  private JPanel timerPanel;
  private TimerComponent timer;
  private JButton startStopButton;
  private JButton acceptButton;
  private JButton discardButton;
  private JButton popButton;
  private JComboBox countDownList;

  private TimerMode currentMode = TimerMode.NEUTRAL;

  private ShortcutKeyAdaptor shortcutKeyAdaptor = new ShortcutKeyAdaptor();

  private static Profile getActiveProfile() {
    return getProfileManager().getActiveProfile();
  }

  private static ProfileManager getProfileManager() {
    return Config.getConfig().getProfileManager();
  }

  public TimerPanel() {
    // shortcut keys
    timerPanel.addKeyListener(shortcutKeyAdaptor);
    startStopButton.addKeyListener(shortcutKeyAdaptor);
    acceptButton.addKeyListener(shortcutKeyAdaptor);

    // actions
    startStopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
          startTimer();
        }
        else {
          stopTimer();
        }
      }
    });
    acceptButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        acceptScore();
      }
    });
    discardButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        discardScore();
      }
    });
    popButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        popScore();
      }
    });

    countDownList.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            if (!countDownList.isPopupVisible()) {
              countDownTimeSelected();
            }
          }
        });
      }
    });
    countDownList.addPopupMenuListener(new PopupMenuListener() {
      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
      }

      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        countDownTimeSelected();
      }

      public void popupMenuCanceled(PopupMenuEvent e) {
        countDownTimeSelected();
      }
    });

    getProfileManager().addProfileListener(this);

    updateSelectedCountDownTime();
    setMode(TimerMode.NEUTRAL);
  }

  private void updateSelectedCountDownTime() {
    int countDownTime = getActiveProfile().getCountdownTime();
    countDownList.setSelectedItem(Integer.toString(countDownTime));
  }

  /**
   * Will apply the focus according to the current timer "mode"
   */
  public void reapplyModeFocus() {
    if (currentMode.acceptFocus) {
      acceptButton.requestFocus();
    }
    else {
      startStopButton.requestFocus();
    }
  }

  /**
   * A new count down time was selected from the combo box
   */
  private void countDownTimeSelected() {
    reapplyModeFocus();

    // update and save the profile
    int newTime = getCountDown();
    if (newTime != getActiveProfile().getCountdownTime()) {
      getActiveProfile().setCountdownTime(newTime);
      Config.getConfig().saveConfig();
    }
  }

  private void stopTimer() {
    timer.stopTimer();

    if (timer.countDownCompleted()) {
      setMode(TimerMode.STOPPED);
    }
    else {
      setMode(TimerMode.NEUTRAL);
    }
  }

  private void startTimer() {
    setMode(TimerMode.STARTED);
    timer.startTimer(getCountDown());
  }

  private int getCountDown() {
    String countDownStr = countDownList.getSelectedItem().toString();
    return Integer.parseInt(countDownStr);
  }

  private void popScore() {
    setMode(TimerMode.NEUTRAL);

    ScoreManager.getInstance().recordPop(timer.getStartTime(), timer.getDuration(), timer.getCountDown());
    StatusBar.publishStatus("POP!", new Color(0x8E, 0x68, 0x00));
  }

  private void discardScore() {
    setMode(TimerMode.NEUTRAL);

    ScoreManager.getInstance().recordDiscard(timer.getStartTime(), timer.getDuration(), timer.getCountDown());
    StatusBar.publishStatus("Score discarded!", Color.MAGENTA);
  }

  private void acceptScore() {
    setMode(TimerMode.NEUTRAL);

    ScoreManager.getInstance().recordAccept(timer.getStartTime(), timer.getDuration(), timer.getCountDown());

    StatusBar.publishStatus("Score accepted.");
  }

  private void setMode(final TimerMode mode) {
    currentMode = mode;

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        startStopButton.setText(mode.startStopAction);
        startStopButton.setActionCommand(mode.startStopAction);
        startStopButton.setEnabled(mode.startStopEnabled);
        acceptButton.setEnabled(mode.recordScoreEnabled);
        discardButton.setEnabled(mode.recordScoreEnabled);
        popButton.setEnabled(mode.recordScoreEnabled);

        countDownTimeSelected();
      }
    });
  }

  @Override
  public void activeProfileChanged() {
    updateSelectedCountDownTime();
  }

  @Override
  public void profilesChanged() {
  }

  /**
   * Class for shortcut keys
   */
  private class ShortcutKeyAdaptor extends KeyAdapter {
    @Override
    public void keyReleased(KeyEvent e) {
      int c = (e.getKeyChar() | 0x20);
      
      if (c == 's') {
        startStopButton.doClick();
      }
      else if (c == 'd') {
        discardButton.doClick();
      }
      else if (c == 'a') {
        acceptButton.doClick();
      }
      else if (c == 'p') {
        popButton.doClick();
      }
    }
  }

  /**
   * Solve modes
   */
  private enum TimerMode {
    STARTED("Stop", true, false, false),
    STOPPED("Start", false, true, true),
    NEUTRAL("Start", true, false, false);

    private String startStopAction;
    
    private boolean startStopEnabled;

    private boolean recordScoreEnabled;

    /**
     * If true, the Accept button will have focus, else the start/stop button will have focus
     */
    private boolean acceptFocus;

    private TimerMode(String startStopAction, boolean startStopEnabled, boolean recordScoreEnabled, boolean acceptFocus) {
      this.startStopAction = startStopAction;
      this.startStopEnabled = startStopEnabled;
      this.recordScoreEnabled = recordScoreEnabled;
      this.acceptFocus = acceptFocus;
    }
  }
}

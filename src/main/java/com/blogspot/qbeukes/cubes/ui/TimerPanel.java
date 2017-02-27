package com.blogspot.qbeukes.cubes.ui;

import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.Profile;
import com.blogspot.qbeukes.cubes.model.config.ProfileListener;
import com.blogspot.qbeukes.cubes.model.config.ProfileManager;
import com.blogspot.qbeukes.cubes.model.scores.ScoreManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
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
        } else {
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
    } else {
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
    } else {
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
    timerPanel = new JPanel();
    timerPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
    timer = new TimerComponent();
    timerPanel.add(timer.$$$getRootComponent$$$(), new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
    timerPanel.add(panel1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    startStopButton = new JButton();
    startStopButton.setText("Start");
    startStopButton.setMnemonic('S');
    startStopButton.setDisplayedMnemonicIndex(0);
    panel1.add(startStopButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    acceptButton = new JButton();
    acceptButton.setEnabled(false);
    acceptButton.setText("Accept");
    acceptButton.setMnemonic('A');
    acceptButton.setDisplayedMnemonicIndex(0);
    panel1.add(acceptButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    discardButton = new JButton();
    discardButton.setEnabled(false);
    discardButton.setText("Discard");
    discardButton.setMnemonic('D');
    discardButton.setDisplayedMnemonicIndex(0);
    panel1.add(discardButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    popButton = new JButton();
    popButton.setEnabled(false);
    popButton.setText("Pop");
    popButton.setMnemonic('P');
    popButton.setDisplayedMnemonicIndex(0);
    panel1.add(popButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    countDownList = new JComboBox();
    final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
    defaultComboBoxModel1.addElement("0");
    defaultComboBoxModel1.addElement("3");
    defaultComboBoxModel1.addElement("5");
    defaultComboBoxModel1.addElement("7");
    defaultComboBoxModel1.addElement("10");
    defaultComboBoxModel1.addElement("15");
    defaultComboBoxModel1.addElement("20");
    defaultComboBoxModel1.addElement("25");
    defaultComboBoxModel1.addElement("30");
    defaultComboBoxModel1.addElement("35");
    defaultComboBoxModel1.addElement("40");
    defaultComboBoxModel1.addElement("45");
    defaultComboBoxModel1.addElement("50");
    defaultComboBoxModel1.addElement("55");
    defaultComboBoxModel1.addElement("60");
    defaultComboBoxModel1.addElement("90");
    defaultComboBoxModel1.addElement("120");
    countDownList.setModel(defaultComboBoxModel1);
    panel2.add(countDownList, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label1 = new JLabel();
    label1.setText("Count Down");
    panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final Spacer spacer1 = new Spacer();
    timerPanel.add(spacer1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    final Spacer spacer2 = new Spacer();
    timerPanel.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return timerPanel;
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
      } else if (c == 'd') {
        discardButton.doClick();
      } else if (c == 'a') {
        acceptButton.doClick();
      } else if (c == 'p') {
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

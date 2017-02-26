package com.blogspot.qbeukes.cubes.ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class CubeTimer extends JDialog {
  private JPanel contentPane;
  private StatusBar statusBar;
  private Toolbar toolbar;
  private TimerPanel timerPanel;
  private JButton buttonOK;

  public CubeTimer() {
    super(null, ModalityType.TOOLKIT_MODAL);
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    addWindowFocusListener(new WindowAdapter() {
      @Override
      public void windowGainedFocus(WindowEvent e) {
        reapplyModeFocus();
      }
    });
  }

  private void createUIComponents() {
    statusBar = new StatusBar();
    toolbar = new Toolbar(this);
  }

  private void reapplyModeFocus() {
    timerPanel.reapplyModeFocus();
  }
}

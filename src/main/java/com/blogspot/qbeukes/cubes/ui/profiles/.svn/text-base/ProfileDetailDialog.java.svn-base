package com.blogspot.qbeukes.cubes.ui.profiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProfileDetailDialog extends JDialog {
  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JTextField profileName;
  private boolean cancelled;

  public ProfileDetailDialog() {
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {
          public void run() {
            onOK();
          }
        }).start();
      }
    });

    buttonCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    });

// call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

// call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }

  public void setProfileName(String name) {
    profileName.setText(name);
  }

  public String getProfileName() {
    return profileName.getText();
  }

  public boolean isCancelled() {
    return cancelled;
  }

  private void onOK() {
    if (validateInput()) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          dispose();
        }
      });
    }
  }

  private boolean validateInput() {
    return true;
  }

  private void onCancel() {
    cancelled = true;
    dispose();
  }
}

package com.blogspot.qbeukes.cubes.ui;

import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.ProfileListener;
import com.blogspot.qbeukes.cubes.model.config.ProfileManager;
import com.blogspot.qbeukes.cubes.ui.profiles.ManageProfilesDialog;
import com.blogspot.qbeukes.cubes.util.AppUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 20 Nov 2010
 * Time: 5:52:45 PM
 */
public class Toolbar implements ProfileListener {
  private JTextField activeProfileText;
  private JButton manageProfilesButton;
  private JPanel toolbarPanel;

  private ManageProfilesDialog manageProfilesDialog;

  private CubeTimer mainWindow;

  public Toolbar(CubeTimer mainWindow) {
    this.mainWindow = mainWindow;

    getProfileManager().addProfileListener(this);
    activeProfileText.setText(getProfileManager().getActiveProfile().getName());
    manageProfilesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showManageProfilesDialog();
      }
    });
  }

  private synchronized void showManageProfilesDialog() {
    if (manageProfilesDialog == null) {
      manageProfilesDialog = new ManageProfilesDialog(mainWindow);
    }

    new Thread(new Runnable() {
      public void run() {
        AppUtil.showDialog(manageProfilesDialog, mainWindow);
      }
    }).start();
  }

  private static ProfileManager getProfileManager() {
    return Config.getConfig().getProfileManager();
  }

  @Override
  public void activeProfileChanged() {
    activeProfileText.setText(getProfileManager().getActiveProfile().getName());
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
    toolbarPanel = new JPanel();
    toolbarPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
    final JLabel label1 = new JLabel();
    label1.setText("Active Profile");
    toolbarPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    activeProfileText = new JTextField();
    activeProfileText.setEditable(false);
    activeProfileText.setEnabled(true);
    toolbarPanel.add(activeProfileText, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    manageProfilesButton = new JButton();
    manageProfilesButton.setText("Manage Profiles");
    toolbarPanel.add(manageProfilesButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return toolbarPanel;
  }
}

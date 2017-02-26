package com.blogspot.qbeukes.cubes.ui;

import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.ProfileListener;
import com.blogspot.qbeukes.cubes.model.config.ProfileManager;
import com.blogspot.qbeukes.cubes.ui.profiles.ManageProfilesDialog;
import com.blogspot.qbeukes.cubes.util.AppUtil;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
}

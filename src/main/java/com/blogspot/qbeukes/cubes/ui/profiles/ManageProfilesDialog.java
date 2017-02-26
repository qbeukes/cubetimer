package com.blogspot.qbeukes.cubes.ui.profiles;

import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.ConfigException;
import com.blogspot.qbeukes.cubes.model.config.ProfileListener;
import com.blogspot.qbeukes.cubes.model.config.ProfileManager;
import com.blogspot.qbeukes.cubes.ui.CubeTimer;
import com.blogspot.qbeukes.cubes.util.AppUtil;
import com.blogspot.qbeukes.cubes.util.MessageUtil;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

public class ManageProfilesDialog extends JDialog implements ProfileListener {
  private JPanel contentPane;
  private JButton buttonOK;
  private JButton addProfileButton;
  private JButton removeProfileButton;
  private JButton setActiveButton;
  private JList profilesList;
  private ProfilesListModel profilesListModel;

  private CubeTimer mainWindow;

  private static ProfileManager getProfileManager() {
    return Config.getConfig().getProfileManager();
  }

  public ManageProfilesDialog(CubeTimer mainWindow) {
    super(null, ModalityType.TOOLKIT_MODAL);

    this.mainWindow = mainWindow;
    
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);
    setTitle("Profiles");

    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

// call onOK() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onOK();
      }
    });

// call onOK() on ESCAPE
    contentPane.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    addProfileButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addNewProfile();
      }
    });
    
    removeProfileButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        removeSelectedProfile();
      }
    });
    
    setActiveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setSelectedProfileActive();
        onOK();
      }
    });

    profilesList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2) {
          setSelectedProfileActive();
          onOK();
        }
      }
    });

    profilesList.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          setSelectedProfileActive();
          onOK();
        }
      }
    });
  }

  private void addNewProfile() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        ProfileDetailDialog profileDetail = new ProfileDetailDialog();
        AppUtil.showDialog(profileDetail, mainWindow);

        if (!profileDetail.isCancelled()) {
          String profileName = profileDetail.getProfileName();

          try {
            getProfileManager().registerProfile(profileName);
          }
          catch (ConfigException e) {
            MessageUtil.showError("Profiles", "Error adding profile: " + e.getMessage());
          }
        }
      }
    }).start();
  }

  private void removeSelectedProfile() {
    String profile = getSelectedProfile();

    int option = MessageUtil.showYesNoConfirmDialog(this, "Profiles", "Are you sure you want to remove profile: " + profile + "?");

    if (option == JOptionPane.YES_OPTION) {
      getProfileManager().removeProfile(profile);
    }
  }

  private void setSelectedProfileActive() {
    String profile = getSelectedProfile();
    
    try {
      getProfileManager().setActiveProfile(profile);
    }
    catch (ConfigException e) {
      MessageUtil.showError("Profiles", "Cannot set active profile: " + e.getMessage());
      System.err.println("Error adding profile: " + e.getMessage());
      e.printStackTrace(System.err);
    }
  }

  private String getSelectedProfile() {
    int index = profilesList.getSelectedIndex();
    return profilesListModel.getProfileAt(index);
  }

  private void onOK() {
// add your code here
    dispose();
  }

  private void createUIComponents() {
    profilesListModel = new ProfilesListModel();
    profilesList = new JList(profilesListModel);
    profilesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  @Override
  public void activeProfileChanged() {
  }

  @Override
  public void profilesChanged() {
    // only enabled when more than one profile
    removeProfileButton.setEnabled(profilesListModel.getSize() > 1);
  }
}

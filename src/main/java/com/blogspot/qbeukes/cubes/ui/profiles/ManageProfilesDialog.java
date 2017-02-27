package com.blogspot.qbeukes.cubes.ui.profiles;

import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.ConfigException;
import com.blogspot.qbeukes.cubes.model.config.ProfileListener;
import com.blogspot.qbeukes.cubes.model.config.ProfileManager;
import com.blogspot.qbeukes.cubes.ui.CubeTimer;
import com.blogspot.qbeukes.cubes.util.AppUtil;
import com.blogspot.qbeukes.cubes.util.MessageUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
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

    $$$setupUI$$$();
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
          } catch (ConfigException e) {
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
    } catch (ConfigException e) {
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

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    createUIComponents();
    contentPane = new JPanel();
    contentPane.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    contentPane.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
    final Spacer spacer1 = new Spacer();
    panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    buttonOK = new JButton();
    buttonOK.setText("OK");
    panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(271, 24), null, 0, false));
    final JScrollPane scrollPane1 = new JScrollPane();
    panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    scrollPane1.setViewportView(profilesList);
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
    contentPane.add(panel4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    addProfileButton = new JButton();
    addProfileButton.setText("Add Profile");
    panel4.add(addProfileButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    removeProfileButton = new JButton();
    removeProfileButton.setText("Remove Profile");
    panel4.add(removeProfileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final Spacer spacer2 = new Spacer();
    panel4.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    setActiveButton = new JButton();
    setActiveButton.setText("Set Active");
    panel4.add(setActiveButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return contentPane;
  }
}

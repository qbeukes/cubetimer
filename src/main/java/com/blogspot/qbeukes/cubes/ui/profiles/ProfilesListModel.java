package com.blogspot.qbeukes.cubes.ui.profiles;

import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.Profile;
import com.blogspot.qbeukes.cubes.model.config.ProfileListener;
import com.blogspot.qbeukes.cubes.model.config.ProfileManager;

import javax.swing.AbstractListModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A profiles list model for a JList. Takes a snapshot of the profiles at construction time and registers against
 * the profile manager for "profile changed" events. Will update whenever a profile is added/removed/changed.
 * <p>
 * With a profile change, or on every construction/display of the list you would need to create a new list model.
 * <p>
 * The active profile will be displayed with a "(Active)" suffix.
 * <p>
 * To query for the selected profile, you need to get the selected index and then query the list model for the profile
 * at that index using the {#getProfileAt(int)} method. 
 *  
 * User: quintin
 * Date: 21 Nov 2010
 * Time: 10:03:12 PM
 */
public class ProfilesListModel extends AbstractListModel implements ProfileListener {
  private List<Profile> profiles;

  private int activeProfileIndex;

  public ProfilesListModel() {
    initializeProfileList();
    registerForProfileEvents();
  }

  private static ProfileManager getProfileManager() {
    return Config.getConfig().getProfileManager();
  }

  /**
   * Adds the list model as a profile events listener.
   */
  private void registerForProfileEvents() {
    getProfileManager().addProfileListener(this);
  }

  /**
   * Initializes the profile list model
   */
  private void initializeProfileList() {
    profiles = new ArrayList<Profile>(getProfileManager().getProfiles());

    Collections.sort(profiles, new Comparator<Profile>() {
      public int compare(Profile o1, Profile o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    findActiveProfileIndex();
  }

  private void findActiveProfileIndex() {
    activeProfileIndex = profiles.indexOf(getProfileManager().getActiveProfile());
  }

  @Override
  public void activeProfileChanged() {
    int oldActive = activeProfileIndex;
    findActiveProfileIndex();
    fireContentsChanged(this, oldActive, oldActive);
    fireContentsChanged(this, activeProfileIndex, activeProfileIndex);
  }

  @Override
  public void profilesChanged() {
    int oldSize = profiles.size();
    initializeProfileList();
    fireContentsChanged(this, 0, Math.max(oldSize, profiles.size()));
  }

  public int getSize() {
    return profiles.size();
  }

  public String getProfileAt(int index) {
    return profiles.get(index).getName();
  }

  public Object getElementAt(int index) {
    Profile item = profiles.get(index);

    if (index == activeProfileIndex) {
      return item.getName() + " (Active)";
    }
    else {
      return item.getName();
    }
  }
}

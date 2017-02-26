package com.blogspot.qbeukes.cubes.model.config;

import com.blogspot.qbeukes.cubes.model.storage.SerializedStorageStrategy;
import com.blogspot.qbeukes.cubes.model.storage.StorageStrategy;
import com.blogspot.qbeukes.cubes.util.AppUtil;
import com.blogspot.qbeukes.cubes.util.MessageUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/**
 * Application configuration and persistent state
 * User: quintin
 * Date: 20 Nov 2010
 * Time: 8:53:06 PM
 */
public class Config implements Serializable, ProfileListener {
  /**
   * Version number for the serializable class structure. This needs to match the version variable
   * for data to be deserialized successfully. The update manager should bring the version variable and persistent
   * data up to date to match this version.
   */
  public static final int CURRENT_VERSION = 1;
  
  private static Config config;

  private static final StorageStrategy<Config> storageStrategy = new SerializedStorageStrategy<Config>("config.dat");

  private int version;

  private ProfileManager profileManager;

  public Config() {
    profileManager = new ProfileManager();
  }

  /**
   * Get the singleton configuration instance
   */
  public static Config getConfig() {
    if (config == null) {
      throw new IllegalStateException("Configuration not initialized.");
    }
    return config;
  }

  /**
   * Loads/initializes the configuration
   */
  public static void initialize() throws ConfigException {
    try {
      config = storageStrategy.load();
    }
    catch (FileNotFoundException e) {
      config = new Config();
      config.saveConfig();
    }
    catch (IOException e) {
      System.err.println("Failed to load saved configuration/stage.");
      e.printStackTrace(System.err);
      AppUtil.fatalError("Error loading configuration: " + e.getMessage());
      return;
    }

    config.profileManager.addProfileListener(config);
  }

  /**
   * Save this config instance.
   */
  public void saveConfig() {
    try {
      storageStrategy.save(this);
      System.out.println("Config/state saved successfully.");
    }
    catch (IOException e) {
      MessageUtil.showError(AppUtil.TITLE, "Error saving configuration: " + e.getMessage());

      System.err.println("Error saving configuration.");
      e.printStackTrace(System.err);
    }
  }

  /**
   * @return the profile manager
   */
  public ProfileManager getProfileManager() {
    return profileManager;
  }

  public int getVersion() {
    return version;
  }

  /**
   * Updates the version number and saves the configuration. This should only be called from the {@link com.blogspot.qbeukes.cubes.updates.UpdateManager}
   * classes. 
   * @param version
   */
  public void setVersion(int version) {
    this.version = version;
    saveConfig();
  }

  @Override
  public void activeProfileChanged() {
    saveConfig();
  }

  @Override
  public void profilesChanged() {
    saveConfig();
  }
}

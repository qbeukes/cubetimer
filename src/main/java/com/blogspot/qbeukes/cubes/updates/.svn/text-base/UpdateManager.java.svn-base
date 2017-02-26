package com.blogspot.qbeukes.cubes.updates;

import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.updates.upgrades.Upgrade0to1;
import com.blogspot.qbeukes.cubes.util.AppUtil;
import com.blogspot.qbeukes.cubes.util.StreamUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Manages updates of the persistent data.
 * 
 * User: quintin
 * Date: 22 Nov 2010
 * Time: 12:00:04 AM
 */
public class UpdateManager {
  private static final String BACKUP_DIRECTORY_NAME = "backups";

  private File backupDirectory;
  
  /**
   * List of available upgrades
   */
  private List<? extends Upgrade> upgradesList = Arrays.asList(
      new Upgrade0to1()
  );

  public UpdateManager() {

  }

  /**
   * Performs the upgrades until the version from {@link com.blogspot.qbeukes.cubes.model.config.Config#CURRENT_VERSION}
   * is equal to the version returned by {@link com.blogspot.qbeukes.cubes.model.config.Config#getVersion()}
   */
  public void performUpdates() throws UpdateException {
    validateUpgrades();

    Config config = Config.getConfig();

    printVersion();

    prepareBackupDirectory();

    int upgradeCount = 0;
    int currentVersion = config.getVersion();
    while (currentVersion < Config.CURRENT_VERSION) {
      for (Upgrade upgrade : upgradesList) {
        if (currentVersion == upgrade.getFromVersion()) {
          int newVersion = upgrade.perform(this);
          config.setVersion(newVersion);

          System.out.println("Performed upgrade from version " + currentVersion + " to " + newVersion);

          ++upgradeCount;
          break;
        }
      }

      // version is still the same. This indicates a very bad error.
      if (currentVersion == config.getVersion()) {
        throw new UpdateException("Upgrading from version " + currentVersion + " failed.");
      }

      currentVersion = config.getVersion();
    }

    if (upgradeCount > 0) {
      System.out.println("Successfully applied " + upgradeCount + " upgrades.");
      printVersion();
    }
  }

  private void printVersion() {
    int version = Config.getConfig().getVersion();
    String baseMsg = "Current data version: " + version;

    if (version == Config.CURRENT_VERSION) {
      System.out.println(baseMsg + " (Up to Date)");
    }
    else {
      System.out.println(baseMsg);
    }
  }

  /**
   * Validate the list of upgrades.
   * <p>
   * Validations include:
   * <ol>
   *  <li>Only one update for each version exists</li>
   * </ol>
   */
  private void validateUpgrades() throws UpdateException {
    // check unique versions
    for (Upgrade upgrade1 : upgradesList) {
      for (Upgrade upgrade2 : upgradesList) {
        if (upgrade1 != upgrade2 && upgrade1.getFromVersion() == upgrade2.getFromVersion()) {
          throw new UpdateException("Two upgrades handle the same version: " + upgrade1.getClass().getName() + " and " + upgrade2.getClass().getName());
        }
      }
    }
  }

  /**
   * Prepares the backup directory and {@link #backupDirectory} variable.
   *
   * @see #BACKUP_DIRECTORY_NAME
   */
  private void prepareBackupDirectory() {
    backupDirectory = new File(AppUtil.getStorageDirectory(), BACKUP_DIRECTORY_NAME);
    backupDirectory.mkdirs();
  }

  /**
   * Back a file. It will be copied in to {@link #backupDirectory} with a unique name. The name will then be logged
   * to STDERR.
   * <p>
   * The name is based on the source file, the version being upgraded, and a UUID to ensure uniqueness.
   *
   * @see #prepareBackupDirectory()
   * @param sourceFile File name relative to the storage directory
   * @param upgrade The upgrade requesting the backup
   */
  public void backupFile(File sourceFile, Upgrade upgrade) throws UpdateException {
    String backupFileName = "v" + upgrade.getFromVersion() + "-" + UUID.randomUUID().toString() + "-" + sourceFile.getName().replaceAll("[^-_.a-zA-Z0-9]+", "_") + ".bak";

    File destFile = new File(backupDirectory, backupFileName);

    InputStream in = null;
    OutputStream out = null;
    try {
      // open the streams
      try {
        InputStream fileIn = new FileInputStream(sourceFile);
        in = new BufferedInputStream(fileIn);
      }
      catch (FileNotFoundException e) {
        throw new UpdateException("Backup of file '" + sourceFile + "' can not complete because file doesn't not exist: " + sourceFile.getAbsolutePath());
      }

      try {
        OutputStream fileOut = new FileOutputStream(destFile);
        out = new BufferedOutputStream(fileOut);
      }
      catch (IOException e) {
        throw new UpdateException("Failed to open backup destination.", e);
      }

      try {
        StreamUtils.transferStream(in, out);
      }
      catch (IOException e) {
        throw new UpdateException("Failed to backup file from '" + sourceFile + "' to '" + destFile + "'.", e);
      }

      System.out.println("Backed up file '" + sourceFile.getAbsolutePath() + "' to '" + destFile + "'.");
    }
    finally {
      try {
        if (in != null) {
          in.close();
        }
      }
      catch (IOException e) {
        System.err.println("Closing input stream from '" + sourceFile + "' failed.");
        e.printStackTrace(System.err);
      }

      try {
        if (out != null) {
          out.close();
        }
      }
      catch (IOException e) {
        System.err.println("Closing output stream to '" + destFile + "' failed.");
        e.printStackTrace(System.err);
      }
    }
  }
}

package com.blogspot.qbeukes.cubes.updates.upgrades;

import com.blogspot.qbeukes.cubes.model.ScoreData;
import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.Profile;
import com.blogspot.qbeukes.cubes.model.storage.SerializedStorageStrategy;
import com.blogspot.qbeukes.cubes.updates.UpdateException;
import com.blogspot.qbeukes.cubes.updates.UpdateManager;
import com.blogspot.qbeukes.cubes.updates.Upgrade;
import com.blogspot.qbeukes.cubes.util.AppUtil;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 22 Nov 2010
 * Time: 12:03:33 AM
 */
public class Upgrade0to1 implements Upgrade {
  @Override
  public int getFromVersion() {
    return 0;
  }

  @Override
  public int perform(UpdateManager manager) throws UpdateException {
    File oldScoresFile = new File(AppUtil.getStorageDirectory(), "scores.dat");

    if (oldScoresFile.exists()) {
      upgradeScoresFile(manager, oldScoresFile);
    }

    return 1;
  }

  private void upgradeScoresFile(UpdateManager manager, File oldScoresFile) throws UpdateException {
    Profile activeProfile = Config.getConfig().getProfileManager().getActiveProfile();

    SerializedStorageStrategy<ScoreData> storageStrategy;
    if (activeProfile.getScoreStorageStrategy() instanceof SerializedStorageStrategy) {
      storageStrategy = (SerializedStorageStrategy<ScoreData>)activeProfile.getScoreStorageStrategy();
    }
    else {
      throw new UpdateException("Storage strategy not instance of SerializedStorageStrategy.");
    }

    File newScoresFile = storageStrategy.getSaveLocation();
    if (newScoresFile.exists()) {
      manager.backupFile(newScoresFile, this);
    }

    newScoresFile.delete();
    oldScoresFile.renameTo(newScoresFile);

    System.out.println("Move old scores file '" + oldScoresFile.getAbsolutePath() + "' to new location '" + newScoresFile.getAbsolutePath() + "'.");

    // scores shouldn't be loaded at this stage, so we don't call "reload". This is because updates happen just after
    // config was loaded.
    //ScoreManager.getInstance().loadScoreData();
  }
}

package com.blogspot.qbeukes.cubes.model.scores;

import com.blogspot.qbeukes.cubes.model.Outcome;
import com.blogspot.qbeukes.cubes.model.ScoreData;
import com.blogspot.qbeukes.cubes.model.SolveScore;
import com.blogspot.qbeukes.cubes.model.config.Config;
import com.blogspot.qbeukes.cubes.model.config.Profile;
import com.blogspot.qbeukes.cubes.model.config.ProfileListener;
import com.blogspot.qbeukes.cubes.model.storage.StorageException;
import com.blogspot.qbeukes.cubes.model.storage.StorageStrategy;
import com.blogspot.qbeukes.cubes.ui.StatusBar;
import com.blogspot.qbeukes.cubes.util.AppUtil;
import com.blogspot.qbeukes.cubes.util.MessageUtil;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 7:39:05 PM
 */
public class ScoreManager implements ProfileListener {
  private static final String DEFAULT_SAVE_FILE = "scores.dat";
  
  private static ScoreManager instance = new ScoreManager();

  private ScoreData data;

  private List<ScoreListener> listeners = new CopyOnWriteArrayList<ScoreListener>();

  private boolean saveDisabled = false;

  public static ScoreManager getInstance() {
    return instance;
  }

  /**
   * Just a dummy method for creating a new instance so data is loaded.
   */
  public static void initialize() {
    Config.getConfig().getProfileManager().addProfileListener(instance);
  }

  public ScoreManager() {
    loadScoreData();
  }

  /**
   * @return The currently active profile's storage strategy
   */
  private static StorageStrategy<ScoreData> getActiveStorageStrategy() {
    return getActiveProfile().getScoreStorageStrategy();
  }

  /**
   * @return The currently active profile
   */
  private static Profile getActiveProfile() {
    return Config.getConfig().getProfileManager().getActiveProfile();
  }

  public void loadScoreData() {
    if (saveDisabled) {
      System.err.println("Saving is disabled. Not saving score data.");
      return;
    }

    try {
      data = getActiveStorageStrategy().load();

      StatusBar.publishStatus("Scores loaded successfully.");
    }
    catch (FileNotFoundException e) {
      data = new ScoreData();

      saveScoreData();

      String msg = "No score data file exists. Starting with clean slate for '" + getActiveProfile().getName() + "'.";
      StatusBar.publishStatus(msg, true);
    }
    catch (IOException e) {
      MessageUtil.showError(AppUtil.TITLE, "Error loading saved data: " + e.getMessage());

      System.err.println("Failed to load saved data.");
      e.printStackTrace(System.err);
    }
    catch (StorageException e) {
      MessageUtil.showError(AppUtil.TITLE, "Error loading saved data: " + e.getMessage());

      System.err.println("Failed to load saved data.");
      e.printStackTrace(System.err);
    }

    if (data == null) {
      String msg = "Score loading failed. Running without save/load functionality.";
      MessageUtil.showWarn(AppUtil.TITLE, msg);
      saveDisabled = true;

      data = new ScoreData();

      StatusBar.publishStatus(msg, true);
    }

    fireScoresUpdated();
  }

  private void saveScoreData() {
    if (saveDisabled) {
      System.err.println("Saving is disabled. Not saving score data.");
      return;
    }

    try {
      getActiveStorageStrategy().save(data);
      
      System.out.println("Score data saved.");
      StatusBar.publishStatus("Score data saved at " + new Date().toString());
    }
    catch (IOException e) {
      MessageUtil.showError(AppUtil.TITLE, "Error saving score data: " + e.getMessage());

      System.err.println("Failed to save score data.");
      e.printStackTrace(System.err);
    }
  }

  public void recordAccept(long startTime, long duration, int countDown) {
    SolveScore score = new SolveScore(startTime, duration, countDown, Outcome.ACCEPTED);
    data.addScore(score);
    saveScoreData();
    fireScoresUpdated();
  }

  public void recordDiscard(long startTime, long duration, int countDown) {
    SolveScore score = new SolveScore(startTime, duration, countDown, Outcome.DISCARDED);
    data.addScore(score);
    saveScoreData();
    fireScoresUpdated();
  }

  public void recordPop(long startTime, long duration, int countDown) {
    SolveScore score = new SolveScore(startTime, duration, countDown, Outcome.POP);
    data.addScore(score);
    saveScoreData();
    fireScoresUpdated();
  }

  public ScoreData getScoreData() {
    return data;
  }

  public boolean addScoreListener(ScoreListener scoreListener) {
    return listeners.add(scoreListener);
  }

  public boolean removeScoreListener(Object o) {
    return listeners.remove(o);
  }

  private void fireScoresUpdated() {
    for (ScoreListener listener : listeners) {
      listener.scoresUpdated();
    }
  }

  public void activeProfileChanged() {
    loadScoreData();
  }

  @Override
  public void profilesChanged() {
  }
}

package com.blogspot.qbeukes.cubes.model.config;

import com.blogspot.qbeukes.cubes.model.ScoreData;
import com.blogspot.qbeukes.cubes.model.storage.SerializedStorageStrategy;
import com.blogspot.qbeukes.cubes.model.storage.StorageStrategy;

import java.io.Serializable;
import java.util.UUID;

/**
 * Player profile.
 * 
 * User: quintin
 * Date: 20 Nov 2010
 * Time: 8:57:40 PM
 */
public class Profile implements Serializable {
  private static final long serialVersionUID = -812756052545519663L;
  
  private static final int DEFAULT_COUNTDOWN_TIME = 10;

  private static final String ILLEGAL_FILENAME_CHARACTERS_REGEX = "[^-_a-zA-Z0-9]+";
                    
  private String id;

  private String name;

  private int countdownTime;

  private StorageStrategy<ScoreData> scoreStorageStrategy;

  /**
   * Create a new profile with the specified name.
   * @param name
   */
  public Profile(String name) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.countdownTime = DEFAULT_COUNTDOWN_TIME;
    scoreStorageStrategy = new SerializedStorageStrategy<ScoreData>(prepareFileName(name));
  }

  /**
   * Creates a file name from the specified profile name. This replaces all spaces and illegal characters with an underscore,
   * makes the string lower case, prefixes with "scores-" and the unique ID, and finally adds a .dat extension.
   * @param name
   * @return Prepared file name
   */
  private String prepareFileName(String name) {
    String cleaned = name.replaceAll(ILLEGAL_FILENAME_CHARACTERS_REGEX, "_");
    return "scores-" + cleaned.toLowerCase() + "-" + id + ".dat";
  }

  public StorageStrategy<ScoreData> getScoreStorageStrategy() {
    return scoreStorageStrategy;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCountdownTime() {
    return countdownTime;
  }

  public void setCountdownTime(int countdownTime) {
    this.countdownTime = countdownTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Profile profile = (Profile) o;

    if (id != profile.id) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}

package com.blogspot.qbeukes.cubes.model.config;

/**
 * Listens for profile changes.
 * 
 * User: quintin
 * Date: 20 Nov 2010
 * Time: 11:34:29 PM
 */
public interface ProfileListener {
  /**
   * Active profile has changed to the specified.
   */
  public void activeProfileChanged();

  /**
   * The list of profiles changed, ie. a profile was added/removed or one's details changed.
   */
  public void profilesChanged();
}

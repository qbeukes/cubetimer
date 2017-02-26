package com.blogspot.qbeukes.cubes.updates;

/**
 * Interface to an individual upgrade.
 * User: quintin
 * Date: 22 Nov 2010
 * Time: 12:01:04 AM
 */
public interface Upgrade {
  /**
   * @return The version number this upgrade will work from.
   */
  public int getFromVersion();

  /**
   * Performs the upgrade.
   *
   * @param manager
   * @return New version number
   */
  public int perform(UpdateManager manager) throws UpdateException;
}

package com.blogspot.qbeukes.cubes.util;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 12:54:42 PM
 */
public class TimeUtil {
  /**
   * Format a millisecond time into MM:SS.mmm
   *
   * @param milliseconds
   */
  public static String formatTime(long milliseconds) {
    long ms = milliseconds % 1000;
    long x = (milliseconds - ms) / 1000;
    long sec = x % 60;
    long min = (x - sec) / 60;

    return String.format("%02d:%02d.%03d", min, sec, ms);
  }
}

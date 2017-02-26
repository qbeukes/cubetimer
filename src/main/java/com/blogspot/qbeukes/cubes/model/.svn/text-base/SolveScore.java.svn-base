package com.blogspot.qbeukes.cubes.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 5:51:58 PM
 */
public class SolveScore implements Comparable<SolveScore>, Serializable {
  private static final long serialVersionUID = 7210550785352374822L;

  private Date startTime;

  private long duration;

  private int countDown;

  private Outcome outcome;

  public SolveScore(Date startTime, long duration, int countDown, Outcome outcome) {
    if (outcome == null) {
      throw new IllegalArgumentException("Outcome may not be null.");
    }

    if (startTime == null) {
      throw new IllegalArgumentException("Start time may not be null.");
    }

    this.startTime = startTime;
    this.duration = duration;
    this.countDown = countDown;
    this.outcome = outcome;
  }

  public SolveScore(long startTime, long duration, int countDown, Outcome outcome) {
    this(new Date(startTime), duration, countDown, outcome);
  }

  public boolean isAccept() {
    return outcome == Outcome.ACCEPTED;
  }

  public int compareTo(SolveScore o) {
    return startTime.compareTo(o.startTime);
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public int getCountDown() {
    return countDown;
  }

  public void setCountDown(int countDown) {
    this.countDown = countDown;
  }

  public Outcome getOutcome() {
    return outcome;
  }

  public void setOutcome(Outcome outcome) {
    this.outcome = outcome;
  }
}

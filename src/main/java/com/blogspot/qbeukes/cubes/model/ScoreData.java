package com.blogspot.qbeukes.cubes.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 5:45:00 PM
 */
public class ScoreData implements Serializable {
  private static final long serialVersionUID = 1891911175401857195L;

  private Set<SolveScore> scores = new TreeSet<SolveScore>();

  /**
   * Total score durations included in average. Used to optimize the calculation.
   */
  private long totalScore;

  private int totalAccepted;

  private int totalDiscarded;

  private int totalPopped;

  /**
   * Average time
   */
  private long averageTime;

  /**
   * Best score
   */
  private SolveScore bestScore;

  /**
   * Worst score
   */
  private SolveScore worstScore;

  public ScoreData() {

  }

  public synchronized void addScore(SolveScore score) {
    scores.add(score);
    calculateStatsOptimized(score);
  }

  public synchronized void recalculateStats() {
    for (SolveScore score : scores) {
      calculateStatsOptimized(score);
    }
  }

  private synchronized void calculateStatsOptimized(SolveScore score) {
    switch (score.getOutcome()) {
      case ACCEPTED:
        ++totalAccepted;
        
        totalScore += score.getDuration();

        averageTime = totalScore / totalAccepted;

        if (bestScore == null || score.getDuration() < bestScore.getDuration()) {
          bestScore = score;
        }

        if (worstScore == null || score.getDuration() > worstScore.getDuration()) {
          worstScore = score;
        }
        
        break;

      case DISCARDED:
        ++totalDiscarded;
        break;

      case POP:
        ++totalPopped;
        break;
    }
  }

  public synchronized long getAverageTime() {
    return averageTime;
  }

  public synchronized SolveScore getBestScore() {
    return bestScore;
  }

  public synchronized SolveScore getWorstScore() {
    return worstScore;
  }

  public int getTotalAccepted() {
    return totalAccepted;
  }

  public int getTotalDiscarded() {
    return totalDiscarded;
  }

  public int getTotalPopped() {
    return totalPopped;
  }

  public int getTotalSolves() {
    return scores.size();
  }
}

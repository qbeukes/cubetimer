package com.blogspot.qbeukes.cubes.ui;

import com.blogspot.qbeukes.cubes.model.ScoreData;
import com.blogspot.qbeukes.cubes.model.SolveScore;
import com.blogspot.qbeukes.cubes.model.scores.ScoreListener;
import com.blogspot.qbeukes.cubes.model.scores.ScoreManager;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 1:01:27 PM
 */
public class SummaryPanel implements ScoreListener {
  private JPanel summaryPanel;
  private TimeField bestTime;
  private TimeField averageTime;
  private TimeField worstTime;
  private StatField totalAccepted;
  private StatField totalDiscarded;
  private StatField totalPopped;
  private StatField totalSolves;

  public SummaryPanel() {
    ScoreManager.getInstance().addScoreListener(this);
    displayScoreData();
  }

  private ScoreData getScoreData() {
    return ScoreManager.getInstance().getScoreData();
  }

  public void scoresUpdated() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        displayScoreData();
      }
    });
  }

  private void displayScoreData() {
    ScoreData data = getScoreData();

    bestTime.setTime(getTime(data.getBestScore()));
    worstTime.setTime(getTime(data.getWorstScore()));
    averageTime.setTime(data.getAverageTime());

    totalAccepted.setStat(data.getTotalAccepted());
    totalDiscarded.setStat(data.getTotalDiscarded());
    totalPopped.setStat(data.getTotalPopped());
    totalSolves.setStat(data.getTotalSolves());
  }

  private long getTime(SolveScore score) {
    if (score == null) {
      return 0;
    }
    else {
      return score.getDuration();
    }
  }
}

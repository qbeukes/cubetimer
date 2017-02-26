package com.blogspot.qbeukes.cubes.countdown;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 14 Nov 2010
 * Time: 11:52:15 PM
 */
public class PlaySound implements Runnable {
  private static final int BUFFER_SIZE = 128000;

  private static Map<CountdownSound, byte[]> sounds = new EnumMap<CountdownSound, byte[]>(CountdownSound.class);

  private static final Object playLock = new Object();

  private static PlaySound player;

  private boolean running = true;

  private CountdownSound targetSound;

  public static void initialize() {
    if (player != null) {
      return;
    }

    // start player thread
    player = new PlaySound();
    new Thread(player).start();

    // load sounds
    for (CountdownSound s : CountdownSound.values()) {
      InputStream input = PlaySound.class.getResourceAsStream(s.getResourceName());

      if (input == null) {
        System.err.println("Invalid sound file resource: " + s.getResourceName());
        continue;
      }

      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

      byte[] buf = new byte[256];
      int read;
      try {
        while ((read = input.read(buf)) != -1) {
          byteOut.write(buf, 0, read);
        }

        input.close();
      }
      catch (IOException e) {
        System.err.println("Failed to load sound: " + s);
        e.printStackTrace(System.err);
        continue;
      }

      byte[] data = byteOut.toByteArray();
      sounds.put(s, data);
    }

    // play silence to initialize the sound system
//    PlaySound.playSound(CountdownSound.SILENCE);

    System.out.println(sounds.size() + " sounds loaded successfully.");
  }

  private static void findMixer() {
//    Mixer.Info[] mixers = AudioSystem.getMixerInfo();
//    mixer = mixers[2];

//    System.out.println(mixers.length + " mixers available:");
//    for (int i = 0; i < mixers.length; ++i) {
//      System.out.println(" " + mixers[i]);
//      Mixer mixer = AudioSystem.getMixer(mixers[i]);
//
//      System.out.println("  Source lines:");
//      Line.Info[] lines = mixer.getSourceLineInfo();
//      for (int j = 0; j < lines.length; ++j)
//        System.out.println("   " + lines[j]);
//      if (lines.length == 0)
//        System.out.println("   [none]");
//      System.out.println(" Target lines:");
//      lines = mixer.getTargetLineInfo();
//      for (int k = 0; k < lines.length; ++k)
//        System.out.println("   " + lines[k]);
//      if (lines.length == 0)
//        System.out.println("   [none]");
//      System.out.println();
//    }
  }

  private static AudioInputStream getSound(CountdownSound sound) {
    if (sounds.size() == 0) {
      initialize();
    }

    try {
      byte[] data = sounds.get(sound);

      if (data == null) {
        System.err.println("Sound file wasn't loaded: " + sound);
        return null;
      }

      InputStream byteIn = new ByteArrayInputStream(data);
      return AudioSystem.getAudioInputStream(byteIn);
    }
    catch (Exception e) {
      System.err.println("Failed to load sound: " + sound);
      e.printStackTrace(System.err);
    }

    return null;
  }

  /**
   * Instructs the player to start a sound
   *
   * @param sound
   */
  public static void playSound(CountdownSound sound) {
    player.targetSound = sound;

    synchronized (playLock) {
      playLock.notifyAll();
    }
  }

  private void play(CountdownSound sound) {
    if (sound == null) {
      System.err.println("Sound play invoked with null sound specified.");
      return;
    }

    AudioInputStream audioStream = getSound(sound);

    if (audioStream == null) {
      return;
    }

    AudioFormat audioFormat = audioStream.getFormat();

    SourceDataLine sourceLine;
    try {
      sourceLine = AudioSystem.getSourceDataLine(audioFormat);
      sourceLine.open(audioFormat);
    }
    catch (LineUnavailableException e) {
      System.err.println("Source line unavailable.");
      e.printStackTrace(System.err);
      return;
    }
    catch (Exception e) {
      System.err.println("Unknown error opening source line.");
      e.printStackTrace(System.err);
      return;
    }

    sourceLine.start();

    int nBytesRead = 0;
    byte[] abData = new byte[BUFFER_SIZE];
    while (nBytesRead != -1) {
      try {
        nBytesRead = audioStream.read(abData, 0, abData.length);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      if (nBytesRead >= 0) {
        @SuppressWarnings("unused")
        int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
      }
    }

    sourceLine.drain();
    sourceLine.close();
  }

  public void run() {
    try {
      while (running) {
        if (targetSound == null) {
          synchronized (playLock) {
            playLock.wait();
          }

          continue;
        }

        CountdownSound s = targetSound;
        targetSound = null;
        play(s);
      }
    }
    catch (InterruptedException e) {
      System.err.println("Player interrupted!");
      e.printStackTrace(System.err);
    }
  }
}

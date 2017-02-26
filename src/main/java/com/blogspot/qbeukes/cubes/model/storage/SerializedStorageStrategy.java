package com.blogspot.qbeukes.cubes.model.storage;

import com.blogspot.qbeukes.cubes.util.AppUtil;

import java.io.*;

/**
 * Saves to a .cubetimer directory inside the home directory, in a Serialized format through ObjectOutputStream.
 *
 * User: quintin
 * Date: 20 Nov 2010
 * Time: 7:32:01 PM
 */
public class SerializedStorageStrategy<T> implements StorageStrategy<T>, Serializable {
  private static final long serialVersionUID = -3000963317437716311L;

  /**
   * Initializes the base directory where files will be stored.
   */
  private static void initialize() {
  }

  /**
   * Builds the save location to/from which the data will be serialized.
   * @param file
   * @return Save location as a File instance
   */
  private static File buildSaveLocation(String file) {
    initialize();
    return new File(AppUtil.getStorageDirectory(), file);
  }

  /**
   * File to which data will be serialized.
   */
  private File saveLocation;

  public SerializedStorageStrategy(String saveLocation) {
    this.saveLocation = buildSaveLocation(saveLocation);
  }

  public void save(T object) throws IOException {
    boolean success = false;
    OutputStream output = null;
    try {
      OutputStream fileOut = new FileOutputStream(saveLocation);
      output = new BufferedOutputStream(fileOut);

      ObjectOutputStream objectOut = new ObjectOutputStream(output);
      objectOut.writeObject(object);

      success = true;
    }
    finally {
      try {
        if (output != null) {
          output.close();
        }
      }
      catch (IOException e) {
        // only throw the exception if another hasn't already been raised
        if (success) {
          throw e;
        }
      }
    }
  }

  public T load() throws IOException {
    boolean success = true;
    InputStream input = null;
    try {
      InputStream fileIn = new FileInputStream(saveLocation);
      input = new BufferedInputStream(fileIn);

      ObjectInputStream objectIn = new ObjectInputStream(input);
      T result = (T)objectIn.readObject();

      success = true;

      return result;
    }
    catch (ClassNotFoundException e) {
      throw new StorageException("Class not found referenced in serialized storage.", e);
    }
    finally {
      try {
        if (input != null) {
          input.close();
        }
      }
      catch (IOException e) {
        // only throw if we haven't already raised an exception
        if (success) {
          throw e;
        }
      }
    }
  }

  public File getSaveLocation() {
    return saveLocation;
  }
}

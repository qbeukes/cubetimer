package com.blogspot.qbeukes.cubes.model.storage;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 20 Nov 2010
 * Time: 7:27:42 PM
 */
public interface StorageStrategy<T> {
  public void save(T object) throws IOException;

  public T load() throws IOException;
}

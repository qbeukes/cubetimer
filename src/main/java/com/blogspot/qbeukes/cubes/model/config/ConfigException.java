package com.blogspot.qbeukes.cubes.model.config;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 20 Nov 2010
 * Time: 9:03:47 PM
 */
public class ConfigException extends Exception {
  public ConfigException() {
    super();
  }

  public ConfigException(String message) {
    super(message);
  }

  public ConfigException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigException(Throwable cause) {
    super(cause);
  }
}

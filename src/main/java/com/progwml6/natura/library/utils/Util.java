package com.progwml6.natura.library.utils;

import com.progwml6.natura.Natura;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
  /** Gets a logger for the given name */
  public static Logger getLogger(String type) {
    return LogManager.getLogger(Natura.MOD_ID + "-" + type);
  }

}

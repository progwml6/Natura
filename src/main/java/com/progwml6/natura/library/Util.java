package com.progwml6.natura.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public class Util {
  public static final String MODID = "natura";
  public static final String RESOURCE = MODID.toLowerCase(Locale.US);

  public static Logger getLogger(String type) {
    return LogManager.getLogger(MODID + "-" + type);
  }
}

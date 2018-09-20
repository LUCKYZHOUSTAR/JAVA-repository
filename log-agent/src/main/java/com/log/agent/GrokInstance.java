package com.log.agent;

import io.thekraken.grok.api.Grok;
import io.thekraken.grok.api.Match;
import io.thekraken.grok.api.exception.GrokException;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午4:04 2018/9/20
 */
public class GrokInstance {

  private static Grok grok;

  private GrokInstance() {

  }

  public static Grok getGrokInstance(String grokPatternPath) {
    if (grok == null) {
      try {
        grok = Grok.create(grokPatternPath);
      } catch (GrokException e) {
        e.printStackTrace();
      }
    }
    return grok;
  }


  public static Match getMatch(String pattern, String message) {
    Match match = null;
    try {
      grok.compile(pattern);
      match = grok.match(message);
      match.captures();
    } catch (GrokException e) {
      e.printStackTrace();
      match = null;
    }
    return match;
  }


  public static Map<String, Object> toMap(String pattern, String message) {
    Match match = getMatch(pattern, message);
    if (match != null) {
      return match.toMap();
    }
    return null;
  }

  public static String toJson(String pattern, String message) {
    Match match = getMatch(pattern, message);
    if (match != null) {
      return match.toJson();
    }
    return null;
  }


}

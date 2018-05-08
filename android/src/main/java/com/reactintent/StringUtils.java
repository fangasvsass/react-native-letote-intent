package com.reactintent;

/**
 * Created by okct on 08/05/2018.
 */

public class StringUtils {
  /**
   * 判断字符串是否不为空
   *
   * @param string 指定字符串
   * @return 不为 null 且 长度大于 0 返回 true, 否则返回 false
   */
  public static boolean notEmpty(CharSequence string) {
    return string != null && string.length() > 0;
  }
}

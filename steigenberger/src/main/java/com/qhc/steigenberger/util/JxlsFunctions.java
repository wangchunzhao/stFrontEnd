package com.qhc.steigenberger.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Excel 模板导出公共辅助方法类.
 * <p>
 * 
 * @author suzu
 */
public class JxlsFunctions {
  /**
   * 
   * if判断，如果b为true，则返回o1，否则返回o2.
   * 
   * @param b
   * @param o1
   * @param o2
   * @return o1 or o2
   */
  public Object ifelse(boolean b, Object o1, Object o2) {
    return b ? o1 : o2;
  }
  /**
   * 
   * 日期格式化.
   * 
   * @param date
   * @param fmt
   * @return 格式化后的字符串
   */
  public String dateFmt(Date date, String fmt) {
    if (date == null) {
      return "";
    }
    try {
      SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
      return dateFmt.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}

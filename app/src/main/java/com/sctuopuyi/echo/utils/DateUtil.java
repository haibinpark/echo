package com.sctuopuyi.echo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

/**
 * 日期扩展类
 */

public class DateUtil {
    public static final String TIME_REG_YMD = "yyyy年MM月dd日";
    public static final String TIME_REG_YMD2 = "yyyy/MM/dd";
    public static final String TIME_REG_YMD3 = "yyyy.MM.dd";
    public static final String TIME_REG_YMD4 = "yyyy-MM-dd";
    public static final String TIME_REG_YM = "yyyy年MM月";
    public static final String TIME_REG_YM1 = "yyyy-MM";
    public static final String TIME_REG_MD = "MM月dd日";
    public static final String TIME_REG_MDHM = "MM-dd HH:mm";
    public static final String TIME_REG_YMDHM = "yyyy年MM月dd HH:mm";
    public static final String TIME_REG_YMDHM2 = "yyyy-MM-dd HH:mm";
    public static final String TIME_REG_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_REG_YMDHMS2 = "yyyy.MM.dd\r\nHH:mm:ss";
    public static final String TIME_REG_HMS = "HH:mm:ss";
    public static final String TIME_REG_HM = "HH:mm";


    //region base other to strReg

    /**
     * Date  转为  自定义时间样式
     */
    public static String dateToStrReg(Date data, String reg) {
        String result = "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat(reg);
            result = sf.format(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 时间戳Long  转为  自定义时间样式
     */
    public static String stampLongToStrReg(long l, String reg) {
        Date data = new Date(l);
        return dateToStrReg(data, reg);
    }

    /**
     * 时间戳Str  转为  自定义时间样式
     */
    public static String stampStrToStrReg(String time, String reg) {
        String result = "";
        if (!TextUtils.isEmpty(time)) {
            try {
                long l = Long.parseLong(time);
                Date data = new Date(l);
                result = dateToStrReg(data, reg);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 时间戳转为当日凌晨零点的时间戳
     */
    public static String stampStrToDayStampStr(String time) {
        return strRegToStampStr(stampStrToStrReg(time, TIME_REG_YMD2), TIME_REG_YMD2);
    }

    //endregion

    //region base strReg to other

    /**
     * 自定义时间样式  转为  Date
     */
    public static Date strRegToDate(String time, String reg) {
        Date date = new Date(0);
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat sf = new SimpleDateFormat(reg);
            try {
                date = sf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 自定义时间样式  转为  时间戳long
     */
    public static long strRegToStampLong(String time, String reg) {
        return strRegToDate(time, reg).getTime();
    }

    /**
     * 自定义时间样式  转为  时间戳Str
     */
    public static String strRegToStampStr(String time, String reg) {
        String result = "";
        SimpleDateFormat sf = new SimpleDateFormat(reg);
        if (!TextUtils.isEmpty(time)) {
            try {
                Date date = sf.parse(time);
                result = date.getTime() + "";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 自定义时间样式  转换  自定义时间样式
     */
    public static String StrRegToStrReg(String reg1, String reg2, String time) {
        SimpleDateFormat sf2 = new SimpleDateFormat(reg2);
        String result = sf2.format(new Date(strRegToStampLong(time, reg1)));
        return result;
    }

    //endregion

    //region get currentTime

    /**
     * 当前时间  转为  yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrent_yMdHms() {
        return dateToStrReg(new Date(), TIME_REG_YMDHMS);
    }

    /**
     * 当前时间  转为  yyyy年MM月dd HH:mm
     */
    public static String getCurrent_yMdHm() {
        return dateToStrReg(new Date(), TIME_REG_YMDHM);
    }

    /**
     * 当前时间  转为  yyyy-MM-dd HH:mm
     */
    public static String getCurrent_yMdHm2() {
        return dateToStrReg(new Date(), TIME_REG_YMDHM2);
    }

    /**
     * 当前时间  转为  yyyy年MM月dd日
     */
    public static String getCurrent_yMd() {
        return dateToStrReg(new Date(), TIME_REG_YMD);
    }

    /**
     * 当前时间  转为  yyyy/MM/dd
     */
    public static String getCurrent_yMd2() {
        return dateToStrReg(new Date(), TIME_REG_YMD2);
    }

    /**
     * 当前时间  转为  yyyy.MM.dd
     */
    public static String getCurrent_yMd3() {
        return dateToStrReg(new Date(), TIME_REG_YMD3);
    }

    /**
     * 当前时间  转为  yyyy-MM-dd
     */
    public static String getCurrent_yMd4() {
        return dateToStrReg(new Date(), TIME_REG_YMD4);
    }

    /**
     * 当前时间  转为  yyyy年MM月
     */
    public static String getCurrent_yM() {
        return dateToStrReg(new Date(), TIME_REG_YM);
    }


    /**
     * 当前时间  转为  yyyy-MM
     */
    public static String getCurrent_yM1() {
        return dateToStrReg(new Date(), TIME_REG_YM1);
    }

    /**
     * 当前时间  转为  MM-dd HH:mm
     */
    public static String getCurrent_MdHm() {
        return dateToStrReg(new Date(), TIME_REG_MDHM);
    }

    /**
     * 当前时间  转为  时间戳Long
     */
    public static long getCurrentStampLong() {
        boolean who = true;
        if (who) {
            return System.currentTimeMillis();
        } else {
            return new Date().getTime();
        }
    }

    /**
     * 当前时间  转为  时间戳Str
     */
    public static String getCurrentStampStr() {
        return String.valueOf(getCurrentStampLong());
    }

    /**
     * 当前时间 - 未来（过去）几天  转为  时间戳Str
     */
    public static String getCurrentAddDaysStampStr(long days) {
        long dayL = 864 * 100000 * days;
        long result = getCurrentStampLong() + dayL;
        return String.valueOf(result);
    }

    /**
     * 当前时间  转时  时间戳Str(整日)
     */
    public static String getCurrentStampStrByDay() {
        return stampStrToDayStampStr(getCurrentStampStr());
    }

    /**
     * 当前时间 转为  星期几
     */
    public static String getCurrentWeek() {
//        Calendar calendar = Calendar.getInstance();
//        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        String timeNow = getCurrentStampStr();
        return DateUtil.stampStrToWeek(timeNow);
    }


    /**
     * 当前时间  转为  MM-dd HH:mm
     */
    public static String getCurrent_Hm() {
        return dateToStrReg(new Date(), TIME_REG_HM);
    }


    /**
     * 时间戳转时与分
     *
     * @param timestamp
     * @return
     */
    public static String stampStrToHm(String timestamp) {
        return stampStrToStrReg(timestamp, TIME_REG_HM);
    }


    //endregion

    //region timeStamp to other method

    /**
     * 时间戳Str 转为  yyyy-MM-dd
     */
    public static String stampStrToyMd(String time) {
        return stampStrToStrReg(time, TIME_REG_YMD4);
    }

    /**
     * 时间戳Str  转为  MM月dd日
     */
    public static String stampStrToMd(String time) {
        return stampStrToStrReg(time, TIME_REG_MD);
    }

    /**
     * 时间戳Str  转为  MM-dd HH:mm
     */
    public static String stampStrToMDHM(String time) {
        return stampStrToStrReg(time, TIME_REG_MDHM);
    }

    /**
     * 时间戳Str  转为  yyyy-MM-dd HH:mm:ss
     */
    public static String stampStrToYMDHMS(String time) {
        return stampStrToStrReg(time, TIME_REG_YMDHMS);
    }

    /**
     * 时间戳Str  转为  yyyy-MM-dd HH:mm
     */
    public static String stampStrToYMDHM(String time) {
        return stampStrToStrReg(time, TIME_REG_YMDHM);
    }

    /**
     * 时间戳Str  转为  yyyy-MM-dd
     */
    public static String stampStrToYMD(String time) {
        return stampStrToStrReg(time, TIME_REG_YMD4);
    }

    /**
     * 时间戳Str  转为  yyyy-MM-dd HH:mm（如果是今年，去掉yy-, 如果是今天，去掉MM-dd）
     */
    public static String stampStrToYMDHM2(String time) {
        String result = stampStrToStrReg(time, TIME_REG_YMDHM2);
        String today = getCurrent_yMdHm2();
        if (!TextUtils.isEmpty(result)) {
            String[] splResult_Y = result.split("-");
            String[] splToday_Y = today.split("-");
            if (splResult_Y[0].equals(splToday_Y[0])) {//如果是今年，去掉yy-
                result = result.substring(5);
                today = today.substring(5);

                String[] splResult_MD = result.split(" ");
                String[] splToday_MD = today.split(" ");
                if (splResult_MD[0].equals(splToday_MD[0])) {//如果是今天，去掉MM-dd
                    result = splResult_MD[1];
                }
            }
        }
        return result;
    }

    public static String stampStrToYMDHMSNotNull(String time) {
        if (time.isEmpty() || time.length() == 0 || time.equals("0")) {
            return "无";
        }
        return stampStrToStrReg(time, TIME_REG_YMDHMS);
    }

    /**
     * 时间戳Str  转为  MM-dd HH:mm（如果是今天，MM-dd替换为今天）
     */
    public static String stampStrToMDHM2(String time) {
        String result = stampStrToStrReg(time, TIME_REG_MDHM);
        String today = getCurrent_MdHm();
        if (!TextUtils.isEmpty(result)) {
            String[] splResult = result.split(" ");
            String[] splToday = today.split(" ");
            if (splResult[0].endsWith(splToday[0])) {
                result = "今天 " + splResult[1];
            }
        }
        return result;
    }

    /**
     * 时间戳Str  转为  MM-dd HH:mm（如果是今天，MM-dd去掉）
     */
    public static String stampStrToMDHM3(String time) {
        String result = stampStrToStrReg(time, TIME_REG_MDHM);
        String today = getCurrent_MdHm();
        if (!TextUtils.isEmpty(result)) {
            String[] splResult = result.split(" ");
            String[] splToday = today.split(" ");
            if (splResult[0].equals(splToday[0])) {
                result = splResult[1];
            }
        }
        return result;
    }

    /**
     * 时间戳Str 转为  MM.dd —  MM.dd (eg:10.03 — 10.08)
     */
    public static String stampStrToMdMd(String time) {
        String result = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(Long.parseLong(time)));
            int today = calendar.get(Calendar.DAY_OF_MONTH);
            int toMonth = calendar.get(Calendar.MONTH) + 1;
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            int endDay = calendar.get(Calendar.DAY_OF_MONTH);
            int endMonth = calendar.get(Calendar.MONTH) + 1;

            result = toMonth + "." + today + "  —  " + endMonth + "." + endDay;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 时间戳Str  转为 星期几
     */
    public static String stampStrToWeek(String str) {
        String Week = "周";
        Calendar c = Calendar.getInstance();
        if (!TextUtils.isEmpty(str)) {
            try {
                c.setTime(new Date(Long.parseLong(str)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i = c.get(Calendar.DAY_OF_WEEK);
        Week = intToWeek(i);
        return Week;
    }

    /**
     * int  转为 星期几
     */
    public static String intToWeek(int i) {
        String Week = "周";
        if (i == Calendar.SUNDAY) {
            Week += "日";
        }
        if (i == Calendar.MONDAY) {
            Week += "一";
        }
        if (i == Calendar.TUESDAY) {
            Week += "二";
        }
        if (i == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (i == Calendar.THURSDAY) {
            Week += "四";
        }
        if (i == Calendar.FRIDAY) {
            Week += "五";
        }
        if (i == Calendar.SATURDAY) {
            Week += "六";
        }
        return Week;
    }

    //endregion

    //region other to timeStamp method

    /**
     * yyyy年MM月dd日  转为  时间戳Str
     */
    public static String yMdToStampStr(String time) {
        return strRegToStampLong(time, TIME_REG_YMD) + "";
    }

    /**
     * yyyy-MM-dd  转为  时间戳long
     */
    public static long yMdToStampLong(String time) {
        return strRegToStampLong(time, TIME_REG_YMD4);
    }

    /**
     * yyyy/MM/dd 转为  时间戳Long
     */
    public static long yMdToStampLong2(String time) {
        return strRegToStampLong(time, TIME_REG_YMD2);
    }

    //endregion

    //region other method

    /**
     * 为日期添加前缀
     */
    public static String addPrefixForDate(String date) {
        if (date.length() < 2) {
            return "0" + date;
        }
        return date;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }


    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }


    public static int getDay() {
        return Calendar.DAY_OF_MONTH;
    }

    /**
     * 把年月拼成时间戳
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取一个月的第几天
     */
    public static int getDayOfMonth(String dateString, String reg) {
        SimpleDateFormat sf = new SimpleDateFormat(reg);
        int result = -1;
        if (!TextUtils.isEmpty(dateString)) {
            try {
                Date date = sf.parse(dateString);
                result = date.getDate();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 计算两个时间戳之间的秒数
     *
     * @param roomPlayTime
     * @param newTime
     * @return
     */
    public static int spaceFromNowTime(String roomPlayTime, String newTime) {
        int result = 0;

        Long value = null;
        try {
            Long playTime = Long.parseLong(roomPlayTime);
            Long endTime = Long.parseLong(newTime);
            value = (endTime - playTime) / 1000;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value.intValue();
    }

    /**
     * 计算距离（x天x时x分x秒前）
     *
     * @param startTimestampStr
     * @return
     */
    public static String spaceFromNowToSpecTime(long startTimestampStr) {
        StringBuffer spaceTime = new StringBuffer();
        Long currentTimestamp = currentTimeMillis();
        Long startTimestamp = startTimestampStr;
        Long distance = currentTimestamp - startTimestamp;
        if (distance == 0L)
            return "";
        Long distanceDaysL = distance / (60 * 60 * 24 * 1000);
        int distanceDays = distanceDaysL.intValue();
        if (distanceDays > 0) {
            spaceTime.append(distanceDays);
            spaceTime.append("天");
        }
        distance = distance - distanceDays * (60 * 60 * 24 * 1000);
        if (distance == 0L) {
            spaceTime.append("前");
            return spaceTime.toString();
        }
        Long distanceHoursL = distance / (60 * 60 * 1000);
        int distanceHours = distanceHoursL.intValue();
        if (distanceHours > 0) {
            spaceTime.append(distanceHours);
            spaceTime.append("时");
        }
        distance = distance - distanceHours * (60 * 60 * 1000);
        if (distance == 0L) {
            spaceTime.append("前");
            return spaceTime.toString();
        }
        Long distancesML = distance / (60 * 1000);
        int distancesM = distancesML.intValue();
        if (distancesM > 0) {
            spaceTime.append(distancesM);
            spaceTime.append("分");
        }
        distance = distance - distancesML * (60 * 1000);
        if (distance == 0L) {
            spaceTime.append("前");
            return spaceTime.toString();
        }
        Long distancesSL = distance / (1000);
        int distancesS = distancesSL.intValue();
        if (distancesS > 0) {
            spaceTime.append(distancesS);
            spaceTime.append("秒");
        }
        spaceTime.append("前");
        return spaceTime.toString();
    }

    /**
     * 计算距离（x天x时）
     *
     * @param startTimestampStr
     * @return
     */
    public static String spaceFromNowToSpecTime2(long startTimestampStr) {
        StringBuffer spaceTime = new StringBuffer();
        Long currentTimestamp = currentTimeMillis();
        Long startTimestamp = startTimestampStr;
        Long distance = currentTimestamp - startTimestamp;
        if (distance == 0L) return "";
        Long distanceDaysL = distance / (60 * 60 * 24 * 1000);
        int distanceDays = distanceDaysL.intValue();
        if (distanceDays > 0) {
            spaceTime.append(distanceDays);
            spaceTime.append("天");
        }
        distance = distance - distanceDays * (60 * 60 * 24 * 1000);
        if (distance == 0L) {
            return spaceTime.toString();
        }
        Long distanceHoursL = distance / (60 * 60 * 1000);
        int distanceHours = distanceHoursL.intValue();
        spaceTime.append(distanceHours);
        spaceTime.append("时");
        return spaceTime.toString();
    }


    /**
     * 将时分秒转为秒数
     */
    public static int HMSToSecond(String time) {
        int result = 0;
        if (!TextUtils.isEmpty(time)) {
            int index1 = time.indexOf(":");
            int index2 = time.indexOf(":", index1 + 1);
            int hh = Integer.parseInt(time.substring(0, index1));
            int mi = Integer.parseInt(time.substring(index1 + 1, index2));
            int ss = Integer.parseInt(time.substring(index2 + 1));
            result = hh * 60 * 60 + mi * 60 + ss;
        }
        return result;
    }

    /**
     * 将秒数转为时分秒
     */
    public static String secondToHMS(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        String mao1 = ":";
        String mao2 = ":";
        if (d < 10) mao1 = ":0";
        if (s < 10) mao2 = ":0";
        return h + mao1 + d + mao2 + s;
    }


    public static Boolean is24Hour(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    //endregion
}

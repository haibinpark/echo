package com.sctuopuyi.echo.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ChenGY on 2018/1/19.
 */
public class InputFilterUtil {

    private final static String emojiRegex = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
    private final static String chineseRegex = "[\u4e00-\u9fa5]";
    private final static String emojiChineseRegex = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\u4e00-\u9fa5]";
    //    private final static String letterAndNumberRegex = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
    private final static String letterAndNumberRegex = "^(?:(?i)[a-zA-Z0-9]+)$";
    private final static Pattern emojiPattern = Pattern.compile(emojiRegex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    private final static Pattern chinesePattern = Pattern.compile(chineseRegex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    private final static Pattern emojiChinesePattern = Pattern.compile(emojiChineseRegex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    private final static Pattern letterAndNumberPattern = Pattern.compile(letterAndNumberRegex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);


    /**
     * 获取EmojiInputFilter
     */
    private static InputFilter getEmojiInputFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            Matcher matcher = emojiPattern.matcher(source);
            if (matcher.find()) {
                ToastUtil.shortShow("不支持输入表情");
                return "";
            }
            return null;
        };
    }

    /**
     * 获取ChineseInputFilter
     */
    private static InputFilter getChineseInputFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            Matcher matcher = chinesePattern.matcher(source);
            if (matcher.find()) {
                ToastUtil.shortShow("不支持输入中文");
                return "";
            }
            return null;
        };
    }

    /**
     * 获取EmojiChineseInputFilter
     */
    private static InputFilter getEmojiChineseInputFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            Matcher matcher = emojiChinesePattern.matcher(source);
            if (matcher.find()) {
                ToastUtil.shortShow("不支持输入中文和表情");
                return "";
            }
            return null;
        };
    }


    /**
     * getWithOutLetterAndNumberInputFilter
     *
     * @return
     */
    private static InputFilter getWithOutLetterAndNumberInputFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            if (source.length() == 0)
                return null;
            Matcher matcher = letterAndNumberPattern.matcher(source);
            if (!matcher.find()) {
                ToastUtil.shortShow("只能够输入英文与数字");
                return "";
            }
            return null;
        };
    }

    /**
     * 限制小数位数
     */
    public static void limitXDecimal(EditText et, int place) {
        et.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s == null) return;
                        String str = s.toString();
                        if (TextUtils.isEmpty(str)) return;
                        String strBehind = StrNumUtil.splitBehind(str, "\\.");
                        if (strBehind.length() <= place) return;
                        ToastUtil.shortShow("最多输入" + place + "位小数！");
                        et.setText(str.substring(0, str.length() - (strBehind.length() - place)));
                    }
                }
        );
    }

    /**
     * 限制emoji和字数
     */
    public static InputFilter[] getEmojiFilter(int lengh) {
        return new InputFilter[]{new InputFilter.LengthFilter(lengh), getEmojiInputFilter()};
    }

    /**
     * 限制emoji
     */
    public static InputFilter[] getEmojiFilter() {
        return new InputFilter[]{getEmojiInputFilter()};
    }

    /**
     * 限制中文和字数
     */
    public static InputFilter[] getChineseFilter(int lengh) {
        return new InputFilter[]{new InputFilter.LengthFilter(lengh), getChineseInputFilter()};
    }

    /**
     * 限制中文
     */
    public static InputFilter[] getChineseFilter() {
        return new InputFilter[]{getChineseInputFilter()};
    }

    /**
     * 限制中文、emoji和字数
     */
    public static InputFilter[] getEmojiChineseFilter(int lengh) {
        return new InputFilter[]{new InputFilter.LengthFilter(lengh), getEmojiChineseInputFilter()};
    }

    /**
     * 限制中文、emoji
     */
    public static InputFilter[] getEmojiChineseFilter() {
        return new InputFilter[]{getEmojiChineseInputFilter()};
    }


    public static InputFilter[] getWithOutLetterAndNumberFilter() {
        return new InputFilter[]{getWithOutLetterAndNumberInputFilter()};
    }

    /**
     * 限制8位小数
     */
    public static void limitEightDecimal(EditText et) {
        limitXDecimal(et, 8);
    }

}
package com.xbstar.esl.util;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: kira
 * @Date: 2020/2/1 11:13
 * @Description:
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null == str || str.equals("")) {
            return true;
        }
        return false;
    }

    public static String trim(String str) {
        if (null == str) {
            str = "";
        }

        return str.trim();
    }

    public static String trim(Object str) {
        if (null == str) {
            str = "";
        }

        return trim(str.toString());
    }

    public static String toStr(Object obj) {
        return (null == obj) ? "" : obj.toString();
    }

    /**
     * 字符串转换为整形值,异常时使用默认值
     *
     * @param src
     * @param defaultValue
     * @return
     */
    public static Integer toIntDefValue(String src, Integer defaultValue) {
        int intValue = 0;
        try {
            intValue = Integer.parseInt(src);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return intValue;
    }

    /**
     * 字符串转换为长整形值,异常时使用默认值
     *
     * @param src
     * @param defaultValue
     * @return
     */
    public static Long toLongDefValue(String src, Long defaultValue) {
        Long intValue = 0l;
        try {
            intValue = Long.parseLong(src);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return intValue;
    }

    public static Double toDoubleDefValue(String src, Double defaultValue) {
        Double dvalue = 0d;
        try {
            dvalue = Double.parseDouble(src);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return dvalue;
    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Title: isEq
     * @Description: 比较两个字符串是否相等
     * @author kira
     * @date 2015年7月17日 下午5:33:08
     */
    public static boolean isEq(String a, String b) {
        if (null == a || null == b) {
            return false;
        }
        return a.equalsIgnoreCase(b);
    }

    /**
     * 转化
     *
     * @author kira
     * @创建时间 2016年9月28日 下午3:36:06
     * @备注 TODO
     */
    public static String streamToStr(InputStream is) {
        StringBuffer json = new StringBuffer();
        try {
            byte[] bytes = new byte[1024];
            if (is == null) {
                return "";
            }
            int len = 0;
            while ((len = is.read(bytes)) > 0) {
                json.append(new String(bytes, 0, len));
            }


        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return json.toString();
    }

    public static String decode(String str) {
        if (!StringUtil.isEmpty(str)) {
            try {
                return URLDecoder.decode(str, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String encode(String str) {
        if (!StringUtil.isEmpty(str)) {
            try {
                return URLEncoder.encode(str, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public static double mul(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (v2 == 0) {
            return v2;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static String getStrKeyJson(JSONObject json, String key) {
        if (json.containsKey(key)) {
            return json.get(key).toString();
        }
        return null;
    }

    public static long getLongKeyJson(JSONObject json, String key) {
        if (json.containsKey(key)) {
            return json.getLong(key);
        }
        return 0;
    }

    public static int getIntKeyJson(JSONObject json, String key) {
        if (json.containsKey(key)) {
            return json.getInt(key);
        }
        return 0;
    }

    public static String getPar(String urlString, String key) {
        String[] queryStringSplit = urlString.split("&");
        Map<String, String> queryStringMap = new HashMap<String, String>(queryStringSplit.length);
        String[] queryStringParam;
        for (String qs : queryStringSplit) {
            queryStringParam = qs.split("=");
            queryStringMap.put(queryStringParam[0], queryStringParam[1]);
        }

        if (queryStringMap.containsKey(key)) {
            return queryStringMap.get(key);
        }
        return "";
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 返回指定范围内的随机数
     *
     * @param num 指定范围
     * @return
     */
    public static int random(int num) {
        if (num == 0) {
            return num;
        }
        Random random = new Random();
        return random.nextInt(num);
    }

    public static String replaceWordToNum(String str) {
        String word = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        String[] wordArr = word.split(",");
        String[] imeiArr = str.split("");
        StringBuffer replaceBuffer = new StringBuffer();
        for (String a : imeiArr) {
            for (int i = 0; i < wordArr.length; i++) {
                if (a.equalsIgnoreCase(wordArr[i])) {
                    a = "" + (i + 1) % 10;
                    break;
                }
            }
            replaceBuffer.append(a);
        }
        return replaceBuffer.toString();
    }

    public static int lastImei(String imei) {
        int sum1 = 0, sum2 = 0, temp = 0, total = 0, lastNum = 0;
        String[] imeiArr = imei.split("");
        for (int i = 0; i < 14; i++) {
            if ((i % 2) == 0) {//奇数位
                sum1 = sum1 + StringUtil.toIntDefValue(imeiArr[i], 0);
            } else {//偶数位
                temp = (StringUtil.toIntDefValue(imeiArr[i], 0)) * 2;
                if (temp < 10) {
                    sum2 = sum2 + temp;
                } else {
                    sum2 = sum2 + 1 + temp - 10;
                }
            }
        }
        total = sum1 + sum2;
        //获取个位数
        if ((total % 10) == 0) {
            lastNum = 0;
        } else {
            lastNum = total % 10;
        }
        return lastNum;
    }

    public static String generateImei(String originIMei, String androidIdMd5) {
        if (!isEmpty(androidIdMd5)) {
            StringBuffer imeiBuffer = new StringBuffer("86");
            StringBuffer beforeReplaceWord = new StringBuffer();
            if (isEmpty(originIMei) || originIMei.length() < 15) {
                beforeReplaceWord.append(androidIdMd5.substring(0, 12));
            } else {
                beforeReplaceWord.append(originIMei.substring(2, 12)).append(androidIdMd5.substring(0, 2));
            }
            imeiBuffer.append(replaceWordToNum(beforeReplaceWord.toString()));

            return imeiBuffer.append(lastImei(imeiBuffer.toString())).toString();
        }
        return "";
    }

    public static boolean matchRegex(String str, String regex) {

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isNotBlank(String[] strs) {
        String[] arrayOfString = strs;
        int j = strs.length;
        for (int i = 0; i < j; i++) {
            String str = arrayOfString[i];
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成编号
     * @param lastNumber
     * @return
     */
    public static String genEventNumber(String lastNumber) {
        String s = lastNumber.substring(8);
        int beginNum = Integer.valueOf(s);
        int endNum = ++beginNum;
        String s1 = StringUtil.toStr(endNum);
        int num = s.length() - s1.length();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < num; i++) {
            buffer.append(0);
        }
        return buffer.append(s1).toString();
    }

    public static String getRandomNum(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }
}

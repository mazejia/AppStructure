
package test.mzj.com.appstructureproject.utils;

import java.util.HashMap;

/**
 * @author jialei 对指定关键字进行过滤
 */
public class KeyWordsFilter {
    private int maxLength;

    private HashMap<String, String> filterStrs = new HashMap<String, String>();

    public static String doFilter(String mStr) {
        KeyWordsFilter mKeyWords = new KeyWordsFilter();
        mKeyWords.put("官方版");
        mKeyWords.put("官方现场版");
        mKeyWords.put("官方歌词版");
        mKeyWords.put("正式版");
        mKeyWords.put("第二版");
        mKeyWords.put("完整版");
        mKeyWords.put("现场版");
        mKeyWords.put("独唱版");
        mKeyWords.put("电影版");
        mKeyWords.put("电视剧版");
        mKeyWords.put("电影剪辑版");
        mKeyWords.put("MTV国语版");
        mKeyWords.put("舞蹈模仿版");
        mKeyWords.put("翻唱版");
        mKeyWords.put("人声版");
        mKeyWords.put("最终版");
        mKeyWords.put("红军版");
        mKeyWords.put("手语版");
        mKeyWords.put("中文版");
        mKeyWords.put("英语版");
        mKeyWords.put("日语版");
        mKeyWords.put("中日字幕版");
        mKeyWords.put("末日版");
        mKeyWords.put("结局版");
        mKeyWords.put("爵士版");
        mKeyWords.put("葫芦丝版");
        mKeyWords.put("七龙珠版");
        mKeyWords.put("1982年版");
        String mAfterFilterName = mKeyWords.filter(mStr);
        return mAfterFilterName;
    }

    /**
     * str-被过滤得字符串 s-需要过滤得字符串
     */

    private StringBuffer getFilterStr(StringBuffer sb, String str, int start, String s) {
        if (start != 0) {
            sb.append(str.substring(0, start));
        }
        return sb;
    }

    /**
     * str-被过滤的字符串 过滤，并组合过滤后的字符串
     */
    public String filter(String str) {
        StringBuffer resultStr = new StringBuffer();
        for (int start = 0; start < str.length(); start++) {
            for (int end = start + 1; end <= str.length() && end <= start + maxLength; end++) {
                String s = str.substring(start, end);
                if (filterStrs.containsKey(s)) {
                    resultStr = getFilterStr(resultStr, str, start, s);
                    return resultStr.toString();
                }
            }
        }
        return str;
    }

    public void put(String key) {
        int keyLength = key.length();
        filterStrs.put(key, "");
        if (keyLength > this.maxLength)
            maxLength = keyLength;
    }
}

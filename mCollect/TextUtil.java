package com.iue.pocketdoc.utilities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Paint;

/**
 * 
 * @author SYC
 *
 * @date 2015年4月21日
 */
public class TextUtil {
	public static boolean isValidate(String content) {
		if (content != null && !"".equals(content.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isValidate(String... contents) {
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] == null || "".equals(contents[i].trim())) {
				return false;
			}
		}
		return true;
	}
	public static boolean isValidateSec(String... contents) {
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] == null || "".equals(contents[i].trim())||contents[i].trim().startsWith("请输入")) {
				return false;
			}
		}
		return true;
	}

	public static boolean isValidate(ArrayList<?> list) {
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isValidPassword(String content) {
		if (content != null && content.length() >= 6 && content.length() <= 20) {
			return true;
		}
		return false;
	}

	

	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();

	}

	public static boolean isChinese(String password) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(password);
		while (m.find()) {
			return true;
		}
		return false;

	}

	/**
	 * 自动分割文本
	 * 
	 * @param content
	 *            需要分割的文本
	 * @param p
	 *            画笔，用来根据字体测量文本的宽度
	 * @param width
	 *            最大的可显示像素（一般为控件的宽度）
	 * @return 一个字符串数组，保存每行的文本
	 */
	public static String[] autoSplit(String content, Paint p, float width) {
		int length = content.length();
		float textWidth = p.measureText(content);
		if (textWidth <= width) {
			return new String[] { content };
		}

		int start = 0, end = 1, i = 0;
		int lines = (int) Math.ceil(textWidth / width); // 计算行数
		String[] lineTexts = new String[lines];
		while (start < length) {
			if (p.measureText(content, start, end) > width) { // 文本宽度超出控件宽度时
				lineTexts[i++] = (String) content.subSequence(start, end);
				start = end;
			}
			if (end == length) { // 不足一行的文本
				lineTexts[i] = (String) content.subSequence(start, end);
				break;
			}
			end += 1;
		}
		return lineTexts;
	}

	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 去除特殊字符或将所有中文标号替换为英文标号
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 检测是否有emoji表情
	 *
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是Emoji
	 *
	 * @param codePoint
	 *            比较的单个字符
	 * @return
	 */
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
}

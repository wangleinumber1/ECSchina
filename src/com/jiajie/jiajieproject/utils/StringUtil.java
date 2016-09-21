package com.jiajie.jiajieproject.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	// 判断字符串的合法性
	public static boolean checkStr(String str) {
		if (null == str) {
			return false;
		}
		if ("".equals(str)) {
			return false;
		}
		if ("".equals(str.trim())) {
			return false;
		}
		if ("null".equals(str)) {
			return false;
		}
		return true;
	}

	// 正则判断一个字符串是否全是数字
	public static boolean isNumeric(String str) {
		if (!checkStr(str))
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	// 判断电话号码格式是否正确
	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern

		.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		if (!checkStr(mobiles))
			return false;
		// Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(mobiles);

		return m.matches() & mobiles.trim().length() == 11;

	}

	// 判断密码格式是否正确
	public static boolean isPassword(String password) {
		if (password.length() >= 6) {
			return true;
		}
		if (password.length() <= 13) {
			return true;
		}
		return false;

	}

	// <!-- 吐司提示 -->
	// <string name="acountnull">号码不能为空</string>
	// <string name="passwordnull">密码不能为空</string>
	// <string name="acountformat">号码格式有误,请输入正确的电话号码</string>
	// <string name="passwordformat">密码格式有误,请输入6到13位数字</string>

	// 判断密码账号格式正确性
	// public static boolean checkPassword(String password, Context context) {
	// if (!StringUtil.checkStr(password)) {
	// ToastUtil.showToast(context, R.string.passwordnull, null, false);
	// return false;
	// } else if (!StringUtil.isPassword(password)) {
	// ToastUtil.showToast(context, R.string.passwordformat, null, false);
	// return false;
	// }
	//
	// return true;
	//
	// }

	// public static boolean checkAcount(String acount, Context context) {
	// if (!StringUtil.checkStr(acount)) {
	// ToastUtil.showToast(context, R.string.acountnull, null, false);
	// return false;
	// } else if (!StringUtil.isMobileNO(acount)) {
	// ToastUtil.showToast(context, R.string.acountformat, null, false);
	// return false;
	// }
	// return true;
	//
	// }

}

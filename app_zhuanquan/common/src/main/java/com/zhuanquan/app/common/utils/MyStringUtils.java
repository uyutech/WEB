package com.zhuanquan.app.common.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class MyStringUtils {

	public static String join(List<String> array, String tag) {

		if (CollectionUtils.isEmpty(array)) {
			return null;
		}

		int size = array.size();
		StringBuilder sb = new StringBuilder();

		int index = 0;
		for (String str : array) {
			sb.append(str);

			if (index < size - 1) {
				sb.append(tag);
			}

			index++;
		}

		return sb.toString();

	}

}
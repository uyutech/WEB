package com.zhuanquan.app.common.component.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSON;

public class SessionExcludeLoader implements ApplicationContextAware {

	private final Logger logger = LoggerFactory.getLogger(SessionExcludeLoader.class);

	private Map<String, Object> excludeValidateMap;
	
	

	public Map<String, Object> getExcludeValidateMap() {
		return excludeValidateMap;
	}

	public void setExcludeValidateMap(Map<String, Object> excludeValidateMap) {
		this.excludeValidateMap = excludeValidateMap;
	}



	/**
	 * 按照全路径匹配的map, 比如 /register/regisrer.htm
	 */
	private Map<String, String> wholePathMap = new HashMap<String, String>();

	/**
	 * 按照 部分path匹配的，比如 /test/ , /openapi/这种
	 */
	private List<String> packageMatchList = new ArrayList<String>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}

	public void init() throws Exception {

		if (MapUtils.isEmpty(excludeValidateMap)) {

			logger.info("SessionExcludeLoader loader exit!excludeValidateMap is null ");

			return;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) excludeValidateMap.get("session_validate_exclude");

		if (MapUtils.isEmpty(map)) {

			logger.info("SessionExcludeLoader loader exit!session_validate_exclude is null ");
		}

		// 全路径规则
		loadWholePathList(map);

		// 包路径规则
		loadPackagePathList(map);

	}

	/**
	 * 加载全路径匹配的规则
	 * 
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	private void loadWholePathList(Map<String, Object> map) {

		List<String> wholePathList = (ArrayList<String>) map.get("rule_whole_path");

		//
		if (CollectionUtils.isEmpty(wholePathList)) {
			logger.info("SessionExcludeLoader loader role_whole_path exit!role_whole_path is null ");

		} else {

			for (String path : wholePathList) {
				// 转成map是因为map匹配性能更好
				if (path.startsWith("/")) {
					wholePathMap.put(path, path);
				} else {
					wholePathMap.put("/" + path, path);
				}
			}

			logger.info("SessionExcludeLoader loader role_whole_path success !role_whole_path length: "
					+ wholePathList.size());

		}
	}

	/**
	 * 加载根据包路径匹配
	 * 
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	private void loadPackagePathList(Map<String, Object> map) {

		List<String> packagePathList = (ArrayList<String>) map.get("rule_package_match");

		if (CollectionUtils.isEmpty(packagePathList)) {
			logger.info("SessionExcludeLoader loader role_whole_path exit!role_whole_path is null ");

		} else {

			for (String path : packagePathList) {

				packageMatchList.add(path);

			}

			logger.info("SessionExcludeLoader loader loadPackagePathrule success !role_package_path length: "
					+ packagePathList.size());

		}

	}
	
	
	/**
	 * 检测url是否在免过滤里面
	 * @param url
	 * @return
	 */
	public boolean isSessionCheckExclude(String url) {
		
		
		if(StringUtils.isEmpty(url)) {
			return false;
		}
		
		//包名匹配理应支持正则校验，现在先做个最简单的
		boolean isExclude = matchPackgeRule(url);
		
		if(isExclude) {
			return true;
		}
		
		//全路径匹配
		isExclude = matchWholePathRule(url);
		
		return isExclude;
	}
	
	
	/**
	 * 包名匹配，包名匹配理应支持正则校验，现在先做个最简单的
	 * @param url
	 * @return
	 */
	private boolean matchPackgeRule(String url) {

		for(String path:packageMatchList) {
			
			if(url.indexOf(path)>-1) {
				return true;
			}
		}
		
		return false;
		
	}
	
	
	
	/**
	 *  全路径匹配
	 * @param url
	 * @return
	 */
	private boolean matchWholePathRule(String url) {

		Object obj = wholePathMap.get(url);
		
		return obj == null?false:true;
		
	}

}
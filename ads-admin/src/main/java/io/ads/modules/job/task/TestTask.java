/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.ads.modules.job.task;

import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component("testTask")
public class TestTask implements ITask{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String params){
		// 将字符串转换为JSONObject
		JSONObject jsonObject = new JSONObject(params);

		// 获取属性值
		String host = jsonObject.get("host", String.class);
		String port = jsonObject.get("port", String.class);

		// 打印获取的值
		System.out.println("Host: " + host);
		System.out.println("Port: " + port);
		logger.debug("TestTask定时任务正在执行，参数为：{}", params);
	}
}

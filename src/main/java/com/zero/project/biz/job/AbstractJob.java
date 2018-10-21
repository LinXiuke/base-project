package com.zero.project.biz.job;

import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 抽象job
 * @Author: Hogwarts
 * @Date: 2018/10/14
 */
public abstract class AbstractJob {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ExecutorService executorService = Executors.newFixedThreadPool(20);

	/**
	 * 异步执行
	 * @param jobName
	 */
	public void asyncRun(String jobName) {
		XxlJobLogger.log("begin：" + jobName);

		try {
			executorService.execute(() -> execute());
		} catch (Exception e) {
			XxlJobLogger.log("fail：" + jobName, e);
		} finally {
			XxlJobLogger.log("end：" + jobName);
		}
	}

	/**
	 * 同步执行
	 * @param jobName
	 */
	public void syncRun(String jobName) {
		XxlJobLogger.log("begin：" + jobName);

		try {
			execute();
		} catch (Exception e) {
			XxlJobLogger.log("fail：" + jobName, e);
		} finally {
			XxlJobLogger.log("end：" + jobName);
		}
	}

	protected abstract void execute();

}

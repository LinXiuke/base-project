package com.zero.project.biz.job.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.zero.common.core.biz.ApplicationContextHelper;
import com.zero.project.biz.job.AbstractJob;
import org.springframework.stereotype.Component;



@JobHandler(value="CommonJobHandler")
@Component
public class CommonJobHandler extends IJobHandler {

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("XXL-JOB：" + param);
		
		AbstractJob job = (AbstractJob) ApplicationContextHelper.getBean(param);

		job.syncRun(param);
		
		return SUCCESS;
	}

}

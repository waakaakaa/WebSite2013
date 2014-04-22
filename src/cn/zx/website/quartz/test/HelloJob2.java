package cn.zx.website.quartz.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.zx.website.util.HttpUtils;

public class HelloJob2 implements Job {
	public HelloJob2() {
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		HttpUtils.get("http://localhost:8080/weather/add");
	}
}
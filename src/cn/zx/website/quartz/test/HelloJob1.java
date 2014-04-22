package cn.zx.website.quartz.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.zx.website.util.HttpUtils;

public class HelloJob1 implements Job {
	public HelloJob1() {
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		HttpUtils.get("http://localhost:8080/work88/add");
	}
}
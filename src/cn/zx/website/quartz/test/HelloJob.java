package cn.zx.website.quartz.test;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {
	public HelloJob() {
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(new Date() + ": Hello!  HelloJob is executing.");
	}
}
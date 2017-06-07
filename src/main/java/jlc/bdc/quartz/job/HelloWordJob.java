package jlc.bdc.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloWordJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String name = (String)jobDataMap.get("name");
		System.out.println(String.format("Hello Word: %s" , name));
	}
}

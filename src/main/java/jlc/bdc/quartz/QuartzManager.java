package jlc.bdc.quartz;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.util.CollectionUtils;

public class QuartzManager {

	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	/** 
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
	 * @param jobName 任务名 
	 * @param cls 任务 
	 * @param time 时间设置，参考quartz说明文档 
	 * @version V2.0 
	 */
	public static void addJob(String jobName, Class<? extends Job> cls, String time, Map<String, String> param) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			// 任务名，任务组，任务执行类
			JobDetail job = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
			JobDataMap jobDataMap = job.getJobDataMap();
			if (!CollectionUtils.isEmpty(param)) {
				for (String key : param.keySet()) {
					jobDataMap.put(key, param.get(key));
				}
			}
			// 触发器
			TriggerBuilder<Trigger> newTrigger = TriggerBuilder.newTrigger();
			// 触发器名,触发器组
			newTrigger.withIdentity(jobName, TRIGGER_GROUP_NAME);
			// 触发器时间设定
			CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(time);
			newTrigger.withSchedule(cronSchedule);
			Trigger trigger = newTrigger.build();
			sched.scheduleJob(job, trigger);
			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 
	 * 添加一个定时任务 
	 * @param jobName 任务名 
	 * @param jobGroupName 任务组名 
	 * @param triggerName 触发器名 
	 * @param triggerGroupName 触发器组名 
	 * @param jobClass 任务 
	 * @param time 时间设置，参考quartz说明文档 
	 * @version V2.0 
	 */
	public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
			// 触发器
			TriggerBuilder<Trigger> newTrigger = TriggerBuilder.newTrigger();
			// 触发器名,触发器组
			newTrigger.withIdentity(jobName, triggerGroupName);
			// 触发器时间设定
			CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(time);
			newTrigger.withSchedule(cronSchedule);
			Trigger trigger = newTrigger.build();
			sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
	 * @param jobName 
	 * @param time 
	 * @version V2.0 
	 */
	public static void modifyJobTime(String jobName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
			// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			sched.rescheduleJob(triggerKey, trigger);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 
	 * 修改一个任务的触发时间 
	 * @param triggerName 
	 * @param triggerGroupName 
	 * @param time 
	 */
	public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				// 修改时间  - 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
				trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 重启触发器
				sched.resumeTrigger(triggerKey);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
	 * @param jobName 
	 */
	public static void removeJob(String jobName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			
			JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
			// 停止触发器
			sched.pauseTrigger(triggerKey);
			// 移除触发器
			sched.unscheduleJob(triggerKey);
			// 删除任务
			sched.deleteJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 
	 * 移除一个任务 
	 * @param jobName 
	 * @param jobGroupName 
	 * @param triggerName 
	 * @param triggerGroupName 
	 */
	public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			// 停止触发器
			sched.pauseTrigger(triggerKey);
			// 移除触发器
			sched.unscheduleJob(triggerKey);
			// 删除任务
			sched.deleteJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 
	 * 启动所有定时任务 
	 */
	public static void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 
	 * 关闭所有定时任务 
	 */
	public static void shutdownJobs() {
	try {
		Scheduler sched = gSchedulerFactory.getScheduler();
		if (!sched.isShutdown()) {
			sched.shutdown();
		}
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	}
}

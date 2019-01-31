package cn.unis.gmweb.websocket;
/**
 * 通过配置监听器，利用qutiz，定时执行任务
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class WebSocketServerJobListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("实例化websocket监听器");
        JobDetail jobDetail = JobBuilder.newJob(WebSocketJob.class).withIdentity("webSocketJob","group").build();
        JobDetail jobDetail2 = JobBuilder.newJob(WebSocketJob2.class).withIdentity("webSocketJob2","group2").build();
        try {

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("webSocketJob", "group")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule("/10 * * * * ?"))//更改时间
                    .build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
        	
        	Trigger trigger2 = TriggerBuilder
        			.newTrigger()
        			.withIdentity("webSocketJob2", "group2")
        			.withSchedule(
        					CronScheduleBuilder.cronSchedule("/20 * * * * ?"))//更改时间
        			.build();
        	Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
        	scheduler2.start();
        	scheduler2.scheduleJob(jobDetail2, trigger2);
        	
        } catch (SchedulerException e) {
        	e.printStackTrace();
        }
	}

}

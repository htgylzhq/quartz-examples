package org.quartz.examples.example3;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This Example will demonstrate all of the basics of scheduling capabilities of Quartz using Cron
 * Triggers.
 *
 * @author Bill Kratzer
 */
public class CronTriggerExample {

  private static final Logger logger = LoggerFactory.getLogger(CronTriggerExample.class);

  public void run() throws Exception {
    logger.info("------- Initializing -------------------");

    // First we must get a reference to a scheduler
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler scheduler = sf.getScheduler();

    logger.info("------- Initialization Complete --------");

    logger.info("------- Scheduling Jobs ----------------");

    // jobs can be scheduled before scheduler.start() has been called

    // job 1 will run every 20 seconds
    JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();

    CronTrigger trigger =
        newTrigger()
            .withIdentity("trigger1", "group1")
            .withSchedule(cronSchedule("0/20 * * * * ?"))
            .build();

    Date ft = scheduler.scheduleJob(job, trigger);
    logger.info(
        job.getKey()
            + " has been scheduled to run at: "
            + ft
            + " and repeat based on expression: "
            + trigger.getCronExpression());

    // job 2 will run every other minute (at 15 seconds past the minute)
    job = newJob(SimpleJob.class).withIdentity("job2", "group1").build();

    trigger =
        newTrigger()
            .withIdentity("trigger2", "group1")
            .withSchedule(cronSchedule("15 0/2 * * * ?"))
            .build();

    ft = scheduler.scheduleJob(job, trigger);
    logger.info(
        job.getKey()
            + " has been scheduled to run at: "
            + ft
            + " and repeat based on expression: "
            + trigger.getCronExpression());

    // job 3 will run every other minute but only between 8am and 5pm
    job = newJob(SimpleJob.class).withIdentity("job3", "group1").build();

    trigger =
        newTrigger()
            .withIdentity("trigger3", "group1")
            .withSchedule(cronSchedule("0 0/2 8-17 * * ?"))
            .build();

    ft = scheduler.scheduleJob(job, trigger);
    logger.info(
        job.getKey()
            + " has been scheduled to run at: "
            + ft
            + " and repeat based on expression: "
            + trigger.getCronExpression());

    // job 4 will run every three minutes but only between 5pm and 11pm
    job = newJob(SimpleJob.class).withIdentity("job4", "group1").build();

    trigger =
        newTrigger()
            .withIdentity("trigger4", "group1")
            .withSchedule(cronSchedule("0 0/3 17-23 * * ?"))
            .build();

    ft = scheduler.scheduleJob(job, trigger);
    logger.info(
        job.getKey()
            + " has been scheduled to run at: "
            + ft
            + " and repeat based on expression: "
            + trigger.getCronExpression());

    // job 5 will run at 10am on the 1st and 15th days of the month
    job = newJob(SimpleJob.class).withIdentity("job5", "group1").build();

    trigger =
        newTrigger()
            .withIdentity("trigger5", "group1")
            .withSchedule(cronSchedule("0 0 10am 1,15 * ?"))
            .build();

    ft = scheduler.scheduleJob(job, trigger);
    logger.info(
        job.getKey()
            + " has been scheduled to run at: "
            + ft
            + " and repeat based on expression: "
            + trigger.getCronExpression());

    // job 6 will run every 30 seconds but only on Weekdays (Monday through Friday)
    job = newJob(SimpleJob.class).withIdentity("job6", "group1").build();

    trigger =
        newTrigger()
            .withIdentity("trigger6", "group1")
            .withSchedule(cronSchedule("0,30 * * ? * MON-FRI"))
            .build();

    ft = scheduler.scheduleJob(job, trigger);
    logger.info(
        job.getKey()
            + " has been scheduled to run at: "
            + ft
            + " and repeat based on expression: "
            + trigger.getCronExpression());

    // job 7 will run every 30 seconds but only on Weekends (Saturday and Sunday)
    job = newJob(SimpleJob.class).withIdentity("job7", "group1").build();

    trigger =
        newTrigger()
            .withIdentity("trigger7", "group1")
            .withSchedule(cronSchedule("0,30 * * ? * SAT,SUN"))
            .build();

    ft = scheduler.scheduleJob(job, trigger);
    logger.info(
        job.getKey()
            + " has been scheduled to run at: "
            + ft
            + " and repeat based on expression: "
            + trigger.getCronExpression());

    logger.info("------- Starting Scheduler ----------------");

    // All of the jobs have been added to the scheduler, but none of the jobs
    // will run until the scheduler has been started
    scheduler.start();

    logger.info("------- Started Scheduler -----------------");

    logger.info("------- Waiting five minutes... ------------");
    try {
      // wait five minutes to show jobs
      TimeUnit.MINUTES.sleep(5);
      // executing...
    } catch (Exception e) {
      //
    }

    logger.info("------- Shutting Down ---------------------");

    scheduler.shutdown(true);

    logger.info("------- Shutdown Complete -----------------");

    SchedulerMetaData metaData = scheduler.getMetaData();
    logger.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
  }

  public static void main(String[] args) throws Exception {
    CronTriggerExample example = new CronTriggerExample();
    example.run();
  }
}

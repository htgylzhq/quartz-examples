package org.quartz.examples.example1;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This Example will demonstrate how to start and shutdown the Quartz scheduler and how to schedule
 * a job to run in Quartz.
 *
 * @author Bill Kratzer
 */
public class SimpleExample {

  private static final Logger logger = LoggerFactory.getLogger(SimpleExample.class);

  public void run() throws Exception {
    logger.info("------- Initializing ----------------------");

    // First we must get a reference to a scheduler
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler scheduler = sf.getScheduler();

    logger.info("------- Initialization Complete -----------");

    // computer a time that is on the next round minute
    Date runTime = evenMinuteDate(new Date());

    logger.info("------- Scheduling Job  -------------------");

    // define the job and tie it to our HelloJob class
    JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();

    // Trigger the job to run on the next round minute
    Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

    // Tell quartz to schedule the job using our trigger
    scheduler.scheduleJob(job, trigger);
    logger.info(job.getKey() + " will run at: " + runTime);

    // Start up the scheduler (nothing can actually run until the
    // scheduler has been started)
    scheduler.start();

    logger.info("------- Started Scheduler -----------------");

    // wait long enough so that the scheduler as an opportunity to
    // run the job!
    logger.info("------- Waiting 65 seconds... -------------");
    try {
      // wait 65 seconds to show job
      TimeUnit.SECONDS.sleep(65);
      // executing...
    } catch (Exception e) {
      //
    }

    // shut down the scheduler
    logger.info("------- Shutting Down ---------------------");
    scheduler.shutdown(true);
    logger.info("------- Shutdown Complete -----------------");
  }

  public static void main(String[] args) throws Exception {
    SimpleExample example = new SimpleExample();
    example.run();
  }
}

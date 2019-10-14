package org.quartz.examples.example3;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * This is just a simple job that gets fired off many times by example 3
 *
 * @author Bill Kratzer
 */
public class SimpleJob implements Job {

  private static final Logger logger = LoggerFactory.getLogger(SimpleJob.class);

  /**
   * Empty constructor for job initialization
   */
  public SimpleJob() {
  }

  /**
   * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}
   * </code> fires that is associated with the <code>Job</code>.
   *
   * @throws JobExecutionException if there is an exception while executing the job.
   */
  public void execute(JobExecutionContext context) throws JobExecutionException {

    // This job simply prints out its job name and the
    // date and time that it is running
    JobKey jobKey = context.getJobDetail().getKey();
    logger.info("SimpleJob says: " + jobKey + " executing at " + new Date());
  }
}

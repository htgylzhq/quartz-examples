package org.quartz.examples.example1;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * This is just a simple job that says "Hello" to the world.
 *
 * @author Bill Kratzer
 */
public class HelloJob implements Job {

  private static Logger logger = LoggerFactory.getLogger(HelloJob.class);

  /**
   * Empty constructor for job initialization
   *
   * <p>Quartz requires a public empty constructor so that the scheduler can instantiate the class
   * whenever it needs.
   */
  public HelloJob() {}

  /**
   * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}
   * </code> fires that is associated with the <code>Job</code>.
   *
   * @throws JobExecutionException if there is an exception while executing the job.
   */
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    // Say Hello to the World and display the date/time
    logger.info("Hello World! - " + new Date());
  }
}

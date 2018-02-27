package com.safeway.titan.dug.configuration.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.safeway.titan.dug.batch.service.Processor;
import com.safeway.titan.dug.batch.service.Reader;
import com.safeway.titan.dug.batch.service.Writer;
import com.safeway.titan.dug.service.BatchJobCompletionListener;

@Configuration
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	JobLauncher jobLauncher;
 
	@Autowired
	Job processJob;

	@Bean
	@Scheduled(fixedRate = 5000)
	public Job job() {
		return jobBuilderFactory.get("processJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
	
//	@Scheduled(fixedRate = 5000)
//	public void printMessage() {
//		try {
//			JobParameters jobParameters = new JobParametersBuilder().addLong(
//					"time", System.currentTimeMillis()).toJobParameters();
//			jobLauncher.run(processJob, jobParameters);
//			System.out.println("I have been scheduled with Spring scheduler");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	

	@Bean
	public Step step1() {

		return stepBuilderFactory.get("step1").<String, String>chunk(1).reader(new Reader()).processor(new Processor())
				.writer(new Writer()).build();
	}
	
	@Bean
	public JobExecutionListener listener() {
		return new BatchJobCompletionListener();
	}

	@Bean
	public ResourcelessTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

}

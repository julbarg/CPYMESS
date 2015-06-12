package com.claro.cpymes.service.starup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.claro.cpymes.services.remote.ProcessNitsService;


@Configuration
@EnableScheduling
public class LoadNitsOnix implements InitializingBean {

   private static Logger LOGGER = LogManager.getLogger(LoadNitsOnix.class.getName());

   @Autowired
   private ProcessNitsService processNitsService;

   @Override
   @Scheduled(cron = "51 18 * * * *")
   public void afterPropertiesSet() throws Exception {
      load();
   }

   public void load() {
      LOGGER.info("START JOB NITS");
      processNitsService.processNits();
      LOGGER.info("END JOB NITS");
   }

}

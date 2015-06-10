package com.claro.cpymes.service.starup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.claro.cpymes.services.remote.ProcessNitsService;


@Configuration
@EnableScheduling
@EnableAsync
public class LoadNitsOnix implements InitializingBean {

   private static Logger LOGGER = LogManager.getLogger(LoadNitsOnix.class.getName());

   @Autowired
   private ProcessNitsService processNitsService;

   @Override
   @Scheduled(fixedDelay = DateUtil.DAY_MILLISECONDS)
   public void afterPropertiesSet() throws Exception {
      load();
   }

   @Async
   public void load() {
      LOGGER.info("START JOB NITS");
      processNitsService.processNits();
      LOGGER.info("END JOB NITS");
   }

}

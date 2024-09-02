package com.xbstar.esl.config;

import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xbstar.esl.controller.ESL;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	@Value("${host}")
	private String host;
	@Value("${port}")
	private int port;
	@Value("${password}")
	private String password;

    @Scheduled(fixedRate = 60000)
    public void runTaskAtFixedRate() {
//        logger.info("Fixed rate task - {}", System.currentTimeMillis());
    }

    @Scheduled(fixedDelay = 5000)
    public void runTaskAtFixedDelay() {
//        logger.info("Fixed delay task - {}", System.currentTimeMillis());
    }

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void runTaskWithInitialDelay() {
//        logger.info("Task with initial delay - {}", System.currentTimeMillis());
    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void runTaskAtNoon() {
//        logger.info("Noon task - {}", System.currentTimeMillis());
    }
    
//    @Scheduled(cron = "* */1 * * * ?")
//    public void checkESLConnect() {
//    	boolean canSend = ESL.client.canSend();
//    	if(canSend) {
//    		logger.info("【FS SOCKET】:"+"连接正常...");
//    	}else{
//    		try {
//				ESL.client.connect(host, port, password, 20);
//			} catch (InboundConnectionFailure e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		logger.info("【FS SOCKET】:"+"连接失败，正在重连...");
//
//    	}
//    }
    
    
}

package com.lion.main;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MainApp {
 
	private static Logger log = LoggerFactory.getLogger(MainApp.class);
	
	public static void main(String[] args) {
 
		if(args.length == 0){
	        ApplicationContext context = new FileSystemXmlApplicationContext("/config/spring/applicationContext-common.xml");
	        log.info("期货行情Client启动成功....");
		}else{
			PropertyConfigurator.configure(args[1] + "log4j.properties");
			log.info("loading spring xml : " + args[1]);
			ApplicationContext context = new FileSystemXmlApplicationContext(args[1] + "spring/applicationContext-common.xml");
	        log.info("期货行情Client启动成功....");
		}
        
    }
}
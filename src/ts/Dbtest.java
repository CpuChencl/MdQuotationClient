package ts;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Dbtest {
	
	public static void main(String[] args){
		
		 ApplicationContext context = new ClassPathXmlApplicationContext("/spring/applicationContext-quartz.xml");
	     System.out.println("loading....");
	}
	
}
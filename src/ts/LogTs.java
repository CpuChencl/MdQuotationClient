package ts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/applicationContext-common.xml")
public class LogTs extends AbstractJUnit4SpringContextTests  {
	
	@Test
    public void saveTest() {
		logger.info("...start test...");
		logger.info("...end test...");
		
	}
}
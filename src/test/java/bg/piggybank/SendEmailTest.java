package bg.piggybank;
import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.piggybank.model.DBConnection;
import bg.piggybank.model.SendEmails;
import bg.piggybank.model.configurations.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= Config.class)
public class SendEmailTest {
	
	@Test
	public void testSendEmail() {
		SendEmails sender = new SendEmails();
		String email = sender.lastEmail();
		assertNotNull(email);
	}

}
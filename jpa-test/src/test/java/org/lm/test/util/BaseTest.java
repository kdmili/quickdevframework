package org.lm.test.util;

import org.junit.runner.RunWith;
import org.lm.jpa.Booter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Booter.class})
@WebAppConfiguration
public abstract class BaseTest {
	
}

package com.key4dream.sun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author wangmingqiang
 *
 */
@ActiveProfiles("dev")   //dev
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class BaseTest {
	@Test
	public void test() {
		System.out.println("test");
	}
}

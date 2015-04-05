package com.test.twitter;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import twitter4j.Status;

import com.study.twitter.service.TwitterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/mvc-config.xml"})

public class TestTweets {
	
	
	@Autowired
	TwitterService twitterService;
	
	@Test
	public void findTweets(){
		System.out.println("TweetTest statrs ====================================================");
		try {
			Thread.sleep(2 * 60 * 1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Status> tweets = twitterService.getTweets();
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		String result = "";

		try {
			result = mapper.writeValueAsString(tweets);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		System.out.println(result);
		System.out.println("TweetTest ends ====================================================");
	}

}

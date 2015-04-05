package com.study.twitter.controller;

/**
 * @author Arun.Chaudhary
 * This code should be used by others with consent of the author.
 * 
 * This class is the main controller.
 */

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Status;

import com.study.twitter.service.TwitterService;


@Controller
@RequestMapping("/twitter")
public class MainController {
	
	private static final Logger logger = Logger.getLogger(MainController.class);


	@Autowired
	TwitterService twitterService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getPages() {

		ModelAndView model = new ModelAndView("map");
		return model;

	}
	
	@RequestMapping(value = "/getTweets", method = RequestMethod.GET)
	public @ResponseBody String getTweetsJsonFormat() {

		ObjectMapper mapper = new ObjectMapper();

		List<Status> tweets = twitterService.getTweets();

		String result = "";

		try {
			result = mapper.writeValueAsString(tweets);
		} catch (Exception e) {

			logger.error("Exception: "+e, e);
		}

		return result;

	}

}

package com.study.twitter.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter4j.GeoLocation;
import twitter4j.Status;

import com.study.twitter.constants.Constants;
import com.study.twitter.search.TweetManager;
import com.study.twitter.search.TweetObserver;
import com.study.twitter.search.TweetPuller;
import com.study.twitter.utils.Utils;

@Service
public class TwitterService {

	@Autowired
	TweetManager tweetManager;
	
	@Autowired
	LocationService locationService;
	
	TweetObserver tweetObserver;
	
	private static final Logger logger = Logger.getLogger(TwitterService.class);

	@PostConstruct
	public void init(){

		
		GeoLocation geoLocation = new GeoLocation(Constants.LATTITUDE, Constants.LONGITUED);
		String ip = Utils.getMyIP();
		if(ip != null){
			GeoLocation location = locationService.getLocation(ip);
			if(location != null){
				geoLocation = location;
			}
		}
		TweetPuller tweetPuller = new TweetPuller(tweetManager,geoLocation);
		tweetObserver = new TweetObserver(tweetManager);
		logger.info("Starting tweetPuller.");
		new Thread(tweetPuller).start();
	}
	
	
	public List<Status> getTweets(){
		List<Status> tweets = tweetObserver.getTweetList();
		
		return tweets;
	}
	
}

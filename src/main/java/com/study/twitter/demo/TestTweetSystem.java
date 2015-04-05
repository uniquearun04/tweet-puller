package com.study.twitter.demo;

import twitter4j.GeoLocation;

import com.study.twitter.constants.Constants;
import com.study.twitter.search.TweetManager;
import com.study.twitter.search.TweetObserver;
import com.study.twitter.search.TweetPuller;

public class TestTweetSystem {

	public static void main(String[] args) {

		GeoLocation geoLocation = new GeoLocation(Constants.LATTITUDE, Constants.LONGITUED);		TweetManager tweetManager = new TweetManager();
		TweetPuller tweetPuller = new TweetPuller(tweetManager,geoLocation);
		
		TweetObserver tweetObserver = new TweetObserver(tweetManager);
		
		System.out.println("Starting test.");
		new Thread(tweetPuller).start();
		
		
		
	}

}

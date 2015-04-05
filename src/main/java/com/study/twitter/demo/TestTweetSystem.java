package com.study.twitter.demo;

import com.study.twitter.search.TweetManager;
import com.study.twitter.search.TweetObserver;
import com.study.twitter.search.TweetPuller;

public class TestTweetSystem {

	public static void main(String[] args) {

		TweetManager tweetManager = new TweetManager();
		TweetPuller tweetPuller = new TweetPuller(tweetManager);
		
		TweetObserver tweetObserver = new TweetObserver(tweetManager);
		
		System.out.println("Starting test.");
		new Thread(tweetPuller).start();
		
		
		
	}

}

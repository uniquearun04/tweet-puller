package com.study.twitter.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter4j.Status;

import com.study.twitter.search.TweetManager;
import com.study.twitter.search.TweetObserver;
import com.study.twitter.search.TweetPuller;

@Service
public class TwitterService {

	@Autowired
	TweetManager tweetManager;
	
	TweetObserver tweetObserver;
	
	@PostConstruct
	public void init(){
		TweetPuller tweetPuller = new TweetPuller(tweetManager);
		tweetObserver = new TweetObserver(tweetManager);
		System.out.println("Starting tweetPuller.");
		new Thread(tweetPuller).start();
	}
	
	
	public List<Status> getTweets(){
		List<Status> tweets = tweetObserver.getTweetList();
		
		return tweets;
	}
	
}

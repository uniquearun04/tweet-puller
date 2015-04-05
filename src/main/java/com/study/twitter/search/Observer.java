package com.study.twitter.search;

import java.util.List;

import twitter4j.Status;

public interface Observer {
	
//	public TweetManager tweetManager;
	
	public void update(List<Status> statusList);
	public void update(Status status);
}

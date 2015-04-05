package com.study.twitter.search;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import twitter4j.Status;

//@Component
public class TweetObserver implements Observer {

	public LinkedList<Status> getTweetList() {
		return tweetList;
	}

	private LinkedList<Status> tweetList;
	private final int tweetLimit = 100;
	private final int removeTweetItems = 10;
	
	private Subject tweetManager;
	
	public TweetObserver(Subject tweetManager) {
		tweetList = new LinkedList<Status>();
		this.tweetManager = tweetManager;
		tweetManager.register(this);
	}
	
	@Override
	public void update(List<Status> statusList) {
		tweetList.addAll(statusList);
		maintainList();
//		printTweets();
	}

	@Override
	public void update(Status status) {
		tweetList.addLast(status);
		maintainList();
//		printTweets();
	}

	
	private void maintainList(){
		for(Iterator<Status> it = tweetList.iterator(); it.hasNext();){
			Status next = it.next();
			if(next.getGeoLocation() == null){
				it.remove();
			}
		}
		if(tweetList.size() > tweetLimit){
			for(int i = 0; i < removeTweetItems; i++){
				tweetList.remove();
			}
		}
	}
	
	private void printTweets(){
		for(Status status : tweetList){
			System.out.println(status.getId()+", "+status.getText()+", "+status.getUser());
		}
	}
}

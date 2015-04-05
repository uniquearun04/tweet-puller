package com.study.twitter.search;

/**
 * @author Arun.Chaudhary
 * This code should be used by others with consent of the author.
 * 
 * This class manages the tweets.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import twitter4j.Status;

@Component
public class TweetManager implements Subject {

	private List<Observer> observers;

	private LinkedList<Status> tweetList;


	public TweetManager() {
		observers  = new ArrayList<Observer>();
		tweetList = new LinkedList<Status>();
	}
	
	@Override
	public void register(Observer o) {
		observers.add(o);
	}


	@Override
	public void unregister(Observer o) {
		observers.remove(o);
	}


	@Override
	public void notifyObservers(Status status) {
		
		for(Observer o : observers){
			o.update(status);
		}
	}

	@Override
	public void notifyObservers(List<Status> statusList) {
		
		for(Observer o : observers){
			o.update(statusList);
		}
	}
	
	public void addStatus(Status status){
		this.tweetList.add(status);
		notifyObservers(status);
	}
	
	
	public void addStatus(List<Status> statusList){
		this.tweetList.addAll(statusList);
		notifyObservers(statusList);
	}
	
	
	public List<Status> getTweets(){
		return new ArrayList<Status>(tweetList);
	}
	
	
}

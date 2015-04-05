package com.study.twitter.search;

/**
 * @author Arun.Chaudhary
 * This code should be used by others with consent of the author.
 * 
 * This class maintains the current tweets with geolocation property, which are used to draw on the map.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import twitter4j.Status;

//@Component
public class TweetObserver implements Observer {

	private static final Logger logger = Logger.getLogger(TweetObserver.class);

	public List<Status> getTweetList() {
		List<Status> tweetList = new ArrayList<Status>();
		for(int i = 0; i < tweetQueue.size(); i++){
			try {
				tweetList.add(tweetQueue.take());
			} catch (InterruptedException e) {
				logger.error(e,e);
			}
		}
		return tweetList;
	}

//	private LinkedList<Status> tweetList;

	private BlockingQueue<Status> tweetQueue;
	private final int tweetLimit = 100;
	private final int removeTweetItems = 10;
	
	private Subject tweetManager;
	
	public TweetObserver(Subject tweetManager) {
//		tweetList = new LinkedList<Status>();
		tweetQueue = new LinkedBlockingQueue<Status>();
		this.tweetManager = tweetManager;
		tweetManager.register(this);
	}
	
	@Override
	public void update(List<Status> statusList) {
		for(Status status : statusList){
			if(status.getGeoLocation() != null){
//				tweetList.addLast(status);
				try {
					tweetQueue.put(status);
				} catch (InterruptedException e) {
					logger.error(e, e);
				}
			}
		}
//		maintainList();
//		printTweets();
	}

	@Override
	public void update(Status status) {
		if(status.getGeoLocation() != null){
//			tweetList.addLast(status);
			try {
				tweetQueue.put(status);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}
//		maintainList();
//		printTweets();
	}

	
//	private void maintainList(){
//		if(tweetList.size() > tweetLimit){
//			for(int i = 0; i < removeTweetItems; i++){
//				tweetList.remove();
//			}
//		}
//	}
//	
//	private void printTweets(){
//		for(Status status : tweetList){
//			logger.info(status.getId()+", "+status.getText()+", "+status.getUser());
//		}
//	}
}

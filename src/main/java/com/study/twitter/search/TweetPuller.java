package com.study.twitter.search;

import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.study.twitter.constants.Constants;

public class TweetPuller implements Runnable {

	private TweetManager tweetManger;
	
	private long sinceId = 0;
	
	public TweetPuller(TweetManager tweetManger) {
		this.tweetManger = tweetManger;
	}

	
	@Override
	public void run() {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(Constants.CONSUMER_KEY);
		builder.setOAuthConsumerSecret(Constants.CONSUMER_SECRET);
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		
//		Twitter twitter = TwitterFactory.getSingleton();

//		twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		AccessToken accessToken = null;
		accessToken = new AccessToken(Constants.OAUTH_TOKEN, Constants.OAUTH_TOKEN_SECRET, Long.parseLong(Constants.OWNER_ID));
		twitter.setOAuthAccessToken(accessToken);
		GeoLocation myLocation  = new GeoLocation(28.5355, 77.391);//Noida
		
		
		Query query = new Query();
		query.setGeoCode(myLocation, 100, Query.MILES);
		query.count(100);

		while(!Thread.currentThread().interrupted()){

			query.setSinceId(sinceId);
			QueryResult result =  null;
			try {
				System.out.println("Pulling tweets since id: "+sinceId);
				result = twitter.search(query);
				List <Status> tweets = result.getTweets();
				
				for(Status status : tweets){
					if(status.getId() > sinceId){
						sinceId = status.getId();
					}
				}
				tweetManger.addStatus(tweets);
				
				try {
					Thread.sleep(1000 * 60 * 3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	    
	}

}

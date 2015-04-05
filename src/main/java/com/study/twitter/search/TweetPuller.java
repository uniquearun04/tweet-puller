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
	private GeoLocation geoLocation;
	
	private long sinceId = 0;
	
	public TweetPuller(TweetManager tweetManger, GeoLocation geolocation) {
		this.tweetManger = tweetManger;
		this.geoLocation = geolocation;
	}

	
	@Override
	public void run() {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(Constants.CONSUMER_KEY);
		builder.setOAuthConsumerSecret(Constants.CONSUMER_SECRET);
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		
		AccessToken accessToken = null;
		accessToken = new AccessToken(Constants.OAUTH_TOKEN, Constants.OAUTH_TOKEN_SECRET, Long.parseLong(Constants.OWNER_ID));
		twitter.setOAuthAccessToken(accessToken);
		
		Query query = new Query();
		query.setGeoCode(geoLocation, 100, Query.MILES);
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

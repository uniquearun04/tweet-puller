package com.study.twitter;

/**
 * @author Arun.Chaudhary
 * This code should be used by others with consent of the author.
 * 
 * This class is a simple pojo, to be used later. 
 */
import twitter4j.GeoLocation;

public class Tweet {
	
	private GeoLocation geoLocation;
	private String text;
	
	@Override
	public String toString() {
		return "Tweet [geoLocation=" + geoLocation + ", text=" + text + "]";
	}
	public GeoLocation getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}

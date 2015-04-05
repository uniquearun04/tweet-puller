package com.study.twitter.search;

import java.util.List;

import twitter4j.Status;

public interface Subject {

	public void register(Observer o);
	public void unregister(Observer o);
	public void notifyObservers(Status status);
	public void notifyObservers(List<Status> statusList);
}

package com.demux.eventus;

public class Consumer {
	private EventMapper em;

	public Consumer(EventMapper em) {
		this.em = em;
	}

	public void on(String eventName, Event e) {
		em.on(eventName, e);
	}
}
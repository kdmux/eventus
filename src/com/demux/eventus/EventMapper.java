package com.demux.eventus;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class EventMapper {

	private Map<String, Set<Event>> eventMap;
	private Timer timer = new Timer();

	public EventMapper() {
		eventMap = new HashMap<>();
		
	}
	
	protected synchronized void stopTasks(){
		timer.cancel();
		timer.purge();
		timer = new Timer();
	}
	protected synchronized void create(String eventName) {
		eventMap.put(eventName, new HashSet<Event>());
	}

	protected synchronized void destroy(String eventName) {
		if (eventMap.containsKey(eventName))
			eventMap.remove(eventName);
	}

	protected synchronized void on(String event, Event e) {
		if (eventMap.containsKey(event))
			eventMap.get(event).add(e);
	}

	protected synchronized void emit(String event) {
		StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
		if (eventMap.containsKey(event))
			for (Event e : eventMap.get(event))
				e.onEvent(new EventArgs(caller, eventMap.get(event).size()));
	}

	protected synchronized void emitScheduled(String event, Date dueDate) {
		timer.schedule(new EventTimer(this, event), dueDate);
	}

	protected synchronized void emitPeriodic(String event, long delay) {
		timer.schedule(new EventTimer(this, event), delay, delay);
	}

	protected synchronized CompletableFuture<String> emitAsync(String event) {
		return CompletableFuture.supplyAsync(() -> {
			emit(event);
			return event;
		});
	}

	public synchronized Consumer getConsumer() {
		return new Consumer(this);
	}

	public synchronized Producer getProducer() {
		return new Producer(this);
	}

	class EventTimer extends TimerTask {

		private String eventName;
		private EventMapper em;

		public EventTimer(EventMapper em, String eventName) {
			this.eventName = eventName;
			this.em = em;
		}

		@Override
		public void run() {
			em.emit(eventName);
		}
	}
}

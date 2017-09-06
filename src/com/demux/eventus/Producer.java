package com.demux.eventus;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class Producer {
	private EventMapper em;

	protected Producer(EventMapper em) {
		this.em = em;
	}

	public void create(String eventName) {
		em.create(eventName);
	}

	public void emit(String eventName) {
		em.emit(eventName);
	}

	public CompletableFuture<String> emitAsync(String eventName) {
		return em.emitAsync(eventName);
	}

	public void destroy(String eventName) {
		em.destroy(eventName);
	}

	public void emitScheduled(String eventName, Date dueDate) {
		em.emitScheduled(eventName, dueDate);
	}

	public void emitPeriodic(String eventName, long delay) {
		em.emitPeriodic(eventName, delay);
	}

	public void stopTasks() {
		em.stopTasks();
	}

}

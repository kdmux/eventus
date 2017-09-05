package com.demux.eventus;

public class EventArgs {
	private StackTraceElement caller;
	private long callTime;
	private long numConsumers;

	public EventArgs(StackTraceElement caller, long numConsumers) {
		this.caller = caller;
		this.numConsumers = numConsumers;
		callTime = System.currentTimeMillis();
	}

	public StackTraceElement getCaller() {
		return caller;
	}

	public long getCallTime() {
		return callTime;
	}

	public long getNumConsumers() {
		return numConsumers;
	}

}

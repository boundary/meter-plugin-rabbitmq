package com.boundary.plugin.rabbitmq;

// Create a whole load of nothing using many threads


abstract class Define {

	final static String QUEUE_NAME = "boundary test 2";

	public static final int THREADS = 13;

	public static final int MESSAGES = 400000; // Number of messages to be
												// processed
	public static final int IGNOREFIRST = 0; // Allow system to stabilise after
												// n messages before checking
												// for delay
	public static final int INTERVAL = 40; // These determine the rate that
											// messages are sent
	public static final int INTERVALBASE = 40; // each 100 sent is delayed by an
												// amount of milliseconds

	public static final int PREFETCH = 1;
	public static final int MAINTHREADTIMEOUT = 720; // Seconds - used as a
														// fail-safe. Main
														// thread will stop all
														// running threads on
														// timeout.
	public static final int COUNTDOWN = 5;
	public static final int MESSAGETIMEOUTS = 2;
}

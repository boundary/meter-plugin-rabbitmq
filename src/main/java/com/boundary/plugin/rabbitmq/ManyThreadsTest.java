package com.boundary.plugin.rabbitmq;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ManyThreadsTest {

	public static void main(String args[]) throws InterruptedException {

		AtomicInteger shared = new AtomicInteger(0);
		int threads = Define.THREADS;
		int messages = Define.MESSAGES;

		int mainThreadTimeout = Define.MAINTHREADTIMEOUT; // Seconds

		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
			switch (args[i]) {
			case "-t": {
				threads = Integer.parseInt(args[i + 1]);
				break;
			}
			case "-m": {
				messages = Integer.parseInt(args[i + 1]);
				break;
			}

			case "-w": {
				mainThreadTimeout = Integer.parseInt(args[i + 1]);
				break;
			}

			}
		}

		CountDownLatch latch = new CountDownLatch(threads);

		boolean latchState = true;

		System.out.println(System.currentTimeMillis()
				+ "\tMain thread started and creating " + threads + " threads "
				+ messages + " messagess. Main Thread Timeout "
				+ mainThreadTimeout);

		MessageReader[] threadList = new MessageReader[threads];

		for (int i = 0; i < threads; i++) {
			threadList[i] = new MessageReader(i, latch, shared, messages); // create
																			// a
																			// new
																			// thread
																			// but
																			// do
																			// not
																			// start
		}
		// Create the message writer thread
		MessageWriter writer = new MessageWriter(messages);

		for (int i = Define.COUNTDOWN; i > 0; i--) {
			System.out.println(System.currentTimeMillis()
					+ "\tMain thread countdown: " + i);
			Thread.sleep(1000);
		}
		long now = System.currentTimeMillis();

		for (int i = 0; i < threads; i++) {
			threadList[i].t.start(); // Start your engines!
		}

		writer.t.start(); // This should end before the others end as it writes
							// the last message before it is read

		System.out.println(System.currentTimeMillis()
				+ "\tMain thread - all threads started. Waiting on latch.");

		try {
			latchState = latch.await(mainThreadTimeout, TimeUnit.SECONDS);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long then = System.currentTimeMillis();
		if (latchState) {
			System.out.println(then + "\tMain thread exiting. Run time for "
					+ threads + " threads " + messages + " messages: "
					+ Long.toString(then - now) + " milliseconds.");
		} else {
			System.out.println(then
					+ "\tMain thread timed out waiting for child threads. "
					+ Long.toString(then - now) + " milliseconds.");
			for (int i = 0; i < threads; i++) {
				if (threadList[i].t.isAlive()) {
					System.out
							.println(Long.toString(System.currentTimeMillis())
									+ " Closing down thread: " + i);
					threadList[i].myStop = true;
					// threadList[i].t.interrupt();
				}
			}
			if (writer.t.isAlive()) {
				writer.t.interrupt();
			}

			for (int i = 0; i < threads; i++) {
				threadList[i].t.join(); // Make sure that all child threads
										// stopped before stopping main thread
				writer.t.join();
			}
			System.out.println(Long.toString(then)
					+ "\tMain thread terminating after timeout. "
					+ Long.toString(then - now) + " milliseconds.");

		}
		System.exit(0);
	}

}
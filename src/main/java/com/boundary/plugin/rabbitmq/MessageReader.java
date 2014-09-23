package com.boundary.plugin.rabbitmq;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;


// Read a lot of messages
class MessageReader implements Runnable {

	private final AtomicInteger messageCounter;
	Thread t;
	int myId;
	CountDownLatch myLatch;
	int myMessages;

	boolean myStop = false;

	MessageReader(int id, CountDownLatch latch, AtomicInteger messageCounter,
			int messages) {
		t = new Thread(this, "Message Reader");
		t.setPriority(Thread.NORM_PRIORITY);
		System.out.println(Long.toString(System.currentTimeMillis())
				+ " Message Reader (" + id + ") Created." + " Priority: "
				+ Thread.NORM_PRIORITY);

		this.messageCounter = messageCounter;
		myLatch = latch;
		myMessages = messages;
		myId = id;

	}

	// This is the entry point for the thread
	public void run() {

		// System.out.println(Long.toString(System.currentTimeMillis()) +
		// " Thread # " + t.getId() + " Running.");

		while (myStop == false) {
			// System.out.println(Long.toString(System.currentTimeMillis()) +
			// "\tThread:\t" + String.format("%1$" + 4 + "s", myId) +
			// " Requesting work " + myRepeats +"\t" + myCount);
			try {
				doWork();

			} catch (Exception e1) {
				System.err.println(Long.toString(System.currentTimeMillis())
						+ "\tThread:" + String.format("%1$" + 4 + "s", myId)
						+ " Exception recovery from error: "
						+ e1.getClass().toString());
			}
		}
		// We have finished - decrement the latch by 1
		try {
			myLatch.countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doWork() throws java.io.IOException,
			java.lang.InterruptedException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(Define.QUEUE_NAME, true, false, false, null);
		channel.basicQos(Define.PREFETCH);

		QueueingConsumer consumer = new QueueingConsumer(channel);

		channel.basicConsume(Define.QUEUE_NAME, false, consumer);

		boolean messagesFlag = true;
		int messageTimeOuts = 0;

		long avgDelay = 0;
		long avgDelayCount = 0;
		double totalDelay = 0;
		long delay = 0;

		while (messagesFlag) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery(50);
			if (delivery != null) {
				messageTimeOuts = 0;
				int counter = messageCounter.incrementAndGet();
				String message = new String(delivery.getBody());

				Long readTime = System.currentTimeMillis();
				String msgTime = message.substring(0, 13);
				delay = readTime - Long.parseLong(msgTime);

				totalDelay = totalDelay + delay;
				avgDelayCount++;
				avgDelay = (long) (totalDelay / avgDelayCount);

				if (counter % 5000 == 0) {

					System.out.println(readTime + " Reader(" + myId
							+ ") Msg: [" + message + "] Delay [" + delay
							+ "] Msg count: " + counter);
				}

				// Do some fake work
				for (double i = 0; i < 300000.0; i++) {
					double j = i * 4; // just a multiplication
					j = j / 4;
				}

				// Do some fake waiting
				Thread.sleep(5);

				// Acknowledge the message so that it can be dropped from the
				// queue
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

			} else {
				messageTimeOuts++;
				if (messageTimeOuts == Define.MESSAGETIMEOUTS) {
					messagesFlag = false;
					myStop = true;
					System.out
							.println(Long.toString(System.currentTimeMillis())
									+ " Message Counter "
									+ messageCounter.get() + " Reader(" + myId
									+ ") No more messages - shutting down.");
				} else {
					// System.out.println(Long.toString(System.currentTimeMillis())
					// + " Thread # " + t.getId() +
					// " No message to get, sleeping.");
					Thread.sleep(100);
				}
				;
			}
			if (messageCounter.get() > Define.MESSAGES - 1) {
				myStop = true;
				messagesFlag = false;
				System.out.println(Long.toString(System.currentTimeMillis())
						+ " Reader(" + myId + ")" + " Quitting."
						+ " Msg count: " + messageCounter.get()
						+ " Avg. Delay: " + avgDelay);

			}
		}

	}
}

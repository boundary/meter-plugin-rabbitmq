package com.boundary.plugin.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 *
 */
class MessageWriter implements Runnable {
	int messages;
	Thread t;

	MessageWriter(int messages) {
		t = new Thread(this, "Message Writer");
		// probably not needed now that RMQ flow control is understood
		t.setPriority(Thread.MAX_PRIORITY); 
		System.out.println(Long.toString(System.currentTimeMillis())
				+ " Message Writer Created." + " Priority "
				+ Thread.MAX_PRIORITY);
		this.messages = messages;
	}

	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection;
		
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.basicQos(Define.PREFETCH);
			// Create a queue that is durable, non-exclusive, does not delete, with no arguments
			channel.queueDeclare(Define.QUEUE_NAME, true, false, false, null);

			for (int i = 0; i < messages; i++) {
				String payload = System.currentTimeMillis() + " message number " + i;
				channel.basicPublish("",Define.QUEUE_NAME,null,payload.getBytes());

				// Work out how long to wait based on how close to the middle of the series
				if (i % 100 == 0) {
					double distanceFromMiddle = (messages / 2) - i;
					if (distanceFromMiddle < 0) {
						distanceFromMiddle = distanceFromMiddle * -1;
					}

					double sleepTime = distanceFromMiddle/(messages / 2)
							* Define.INTERVAL + Define.INTERVALBASE;
					if (i % 5000 == 0) {
						System.out.println(System.currentTimeMillis()
								+ " Message Writer delay: " + (int) sleepTime
								+ " messages sent: " + i);
					}
					Thread.sleep((long) sleepTime);
				}

				// System.out.println(" [x] Sent '" + payload + "'");
			}

			channel.close();
			connection.close();
		} catch (IOException e1) {
			System.out.println("oops!");
			e1.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("oops!");
			e.printStackTrace();
		}
		System.out.println(Long.toString(System.currentTimeMillis()) + " Message Writer Finished.");
	}

}

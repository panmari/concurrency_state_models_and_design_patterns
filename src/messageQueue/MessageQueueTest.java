package messageQueue;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MessageQueueTest {

	@Test
	public void test() throws InterruptedException {
		int threadCount = 10;
		final MessageQueue mq = new MessageQueue(5);
		List<Thread> threads = new ArrayList<>();
		for (int i=0; i < threadCount; i++) {
			threads.add(new MessageProducer(mq, 100));
			threads.add(new MessageConsumer(mq, 100));
		}
		for (Thread thread: threads) { thread.start(); }
		
		for (Thread thread: threads) { thread.join(); }
		
		assertEquals(0, mq.size());
	}

}

class MessageProducer extends Thread {

	private final MessageQueue mq;
	private final int nrMessages;

	MessageProducer(MessageQueue mq, int nrMessages) {
		this.mq = mq;
		this.nrMessages = nrMessages;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < nrMessages; i++) {
			try {
				mq.add("Message " + i + " from thread " + this.getName());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class MessageConsumer extends Thread {
	
	private final MessageQueue mq;
	private final int nrMessages;

	MessageConsumer(MessageQueue mq, int nrMessages) {
		this.mq = mq;
		this.nrMessages = nrMessages;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < nrMessages; i++) {
			try {
				mq.remove();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
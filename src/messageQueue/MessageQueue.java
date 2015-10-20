package messageQueue;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
	private final int size;
	private Queue<String> messageQueue;

	public MessageQueue(final int size) {
		this.size = size;
		messageQueue = new LinkedList<String>();
	}

	public synchronized void add(String message) throws InterruptedException {
		while (messageQueue.size() == size) {
			this.wait();
		}
		messageQueue.add(message);
		// Wake up other threads that may be waiting for a message.
		notifyAll();
	}

	public synchronized String remove() throws InterruptedException {
		while (messageQueue.size() == 0) {
			this.wait();
		}
		String removedMessage = messageQueue.remove();
		// Wake up other threads, since there is space again.
		notifyAll();
		return removedMessage;
	}
	
	public int size() {
		return messageQueue.size();
	}
}

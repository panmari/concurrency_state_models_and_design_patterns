package serie07.ghosts;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Center extends RunnablePerson {

	private int killedGhosts;
	private Date timeAtLastCheck;
	private List<FriendDoor> friends;
	private List<ComAnswer> comAnswers = new ArrayList<>();
	private boolean doorsClosed;
	private int currentlyLivingGhosts = 0;
	
	
	public Center(int n, ConcurrentLinkedQueue<Ghost> ghosts, LinkedList<FriendDoor> friends) {
		killedGhosts = 0;
		this.n = n;
		this.ghosts = ghosts;
		alive = true;
		timeAtLastCheck = new Date();
		this.friends = friends;
		this.doorsClosed = false;
	}
	
	@Override
	public void run() {
		while(alive)
		{
			if (!comAnswers.isEmpty()) {
				try {
					collectAnswersFromCom();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			double rnd = Math.random();
			if(rnd < 0.4) {
				killGhost();
			}
			if((System.currentTimeMillis() - timeAtLastCheck.getTime())/1000 > 2) {
				callFriendsOnCom();
				timeAtLastCheck.setTime(System.currentTimeMillis());
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void collectAnswersFromCom() throws ExecutionException, InterruptedException {
		boolean allAnswersAvailable = true;
		for (ComAnswer f: comAnswers) {
			if (!f.isDone()) {
				allAnswersAvailable = false;
				System.out.println("Answer missing from " + f.speaker);
				break;
			}
		}
		if (allAnswersAvailable) {
			int sumOfGhosts = 0;
			for (Future<Integer> f: comAnswers) {
				sumOfGhosts += f.get();
			}
			// Clean up list.
			comAnswers.clear();
			System.out.println("Total ghosts entered = " + sumOfGhosts);
			currentlyLivingGhosts = sumOfGhosts - killedGhosts;
			System.out.println("currently alive ghosts = " + currentlyLivingGhosts);
			if(currentlyLivingGhosts > n) {
				System.out.println("Closing doors...");
				for(FriendDoor friend: friends) {
					friend.closeDoor();
				}
				doorsClosed = true;
			}
			else if(currentlyLivingGhosts < n/2 && doorsClosed) {
				System.out.println("Opening doors...");
				for(FriendDoor friend: friends) {
					friend.openDoor();
				}
				doorsClosed = false;
			}
		} else {
			System.out.println("Answers not yet available, continue to remove ghosts...");
		}
	}

	private void callFriendsOnCom() {
		System.out.println("Calling friends on com... GhostsKilled: "+ killedGhosts);
		for(FriendDoor friend: friends) {
			comAnswers.add(friend.tryToCallOnCom());
		}
	}

	private void killGhost() {
		if(currentlyLivingGhosts > 0)
		{
			ghosts.poll();
			killedGhosts++;
			currentlyLivingGhosts--;
		}
	}

}

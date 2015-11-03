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
	private List<Future<Integer>> comAnswers = new ArrayList<>();
	private boolean doorsClosed;
	
	
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
		for (Future<Integer> f: comAnswers) {
			if (!f.isDone()) {
				allAnswersAvailable = false;
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
			System.out.println("SumOfGhosts: "+sumOfGhosts);
			int currentlyLivingGhosts = sumOfGhosts - killedGhosts;
			System.out.println("difference=living ghosts: " + currentlyLivingGhosts);
			if(currentlyLivingGhosts > n) {
				for(FriendDoor friend: friends) {
					friend.closeDoor();
				}
				doorsClosed = true;
			}
			else if(currentlyLivingGhosts < n/2 && doorsClosed) {
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
			comAnswers.add(friend.getNrOfGhostsEntered());
		}
	}

	private void killGhost() {
		if(ghosts.size() > 0)
		{
			ghosts.poll();
			killedGhosts++;
		}
	}

}

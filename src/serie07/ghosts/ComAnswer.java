package serie07.ghosts;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ComAnswer implements Future<Integer> {

    public final FriendDoor speaker;

    public ComAnswer(FriendDoor fd) {
        this.speaker = fd;
    }

    private Integer answer = null;

    public void speak(Integer answer) {
        this.answer = answer;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return answer != null;
    }

    @Override
    public Integer get() throws InterruptedException, ExecutionException {
        if (answer == null)
            throw new ExecutionException(new Exception("Called to early, check first with isDone()."));
        return answer;
    }

    @Override
    public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (answer == null)
            throw new ExecutionException(new Exception("Called to early, check first with isDone()."));
        return answer;
    }
}

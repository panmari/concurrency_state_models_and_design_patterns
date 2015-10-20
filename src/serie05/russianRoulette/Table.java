package serie05.russianRoulette;

public class Table {

    private Gun gun;
    private Player victim;

    public Table(Gun gun) {
        this.gun = gun;
    }

    public synchronized void placeGun(Gun gun) {
        this.gun = gun;
        notifyAll();
    }

    public synchronized Gun takeGun(Player player) throws InterruptedException {
        // This is not fair, since a player might immediately repick the gun.
        while (this.gun == null) {
            wait();
        }
        Gun returnedGun = gun;
        this.gun = null;
        return returnedGun;
    }

    // TODO: fix this.
    public synchronized Gun takeGunFairly(Player player) throws InterruptedException {
        this.victim = player;
        // This is fair, since the players always alternate
        while (this.gun == null && victim == player) {
            wait();
        }
        Gun returnedGun = gun;
        this.gun = null;
        this.victim = player;
        return returnedGun;
    }

}

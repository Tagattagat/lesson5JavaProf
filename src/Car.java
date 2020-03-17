import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static boolean winner;
    private static Lock win = new ReentrantLock(); // ReentrantLock из пакета java.util.concurrent.locks. Данный класс реализует интерфейс Lock.

    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    private static synchronized void checkWinner(Car c) {
        if (!winner) {
            System.out.println(c.name + " - WIN");
            winner = true;
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            MainClass.startFirstStage.countDown();
            MainClass.startFirstStage.await();
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        checkWinner(this);
        try {
            MainClass.road.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (BrokenBarrierException ex) {
            ex.printStackTrace();
        }
    }
}
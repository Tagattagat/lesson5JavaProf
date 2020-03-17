import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;
    static CountDownLatch startFirstStage = new CountDownLatch(CARS_COUNT); // CountDownLatch в Java - это тип синхронизатора, который позволяет одному Thread ожидать один или несколько Thread , прежде чем он начнет обрабатывать.
    static CyclicBarrier road = new CyclicBarrier(CARS_COUNT); // CyclicBarrier позволяет определить объект синхронизации, который приостанавливается до тех пор, пока определенное количество потоков исполнения не достигнет некоторой барьерной точки.
    static Semaphore tunnel = new Semaphore(CARS_COUNT/2, true); // Semaphore управляет доступом к общему ресурсу с помощью счетчика. Если же присвоить параметру способ логическое значение true, то тем самым можно гарантировать, что разрешения будут предоставляться ожидающим потокам исполнения в том порядке, в каком они запрашивали доступ.
    static CountDownLatch finishLastStage = new CountDownLatch(CARS_COUNT);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        while (startFirstStage.getCount() > 0) // проверяем готовность всех участников
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        while (finishLastStage.getCount() > 0) // проверяем прохождение дистанции
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
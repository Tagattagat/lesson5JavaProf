import java.util.concurrent.BrokenBarrierException;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            MainClass.road.await(); // когда метод await() будет вызван во всех остальных потоках исполнения произойдет возврат из метода.
            System.out.println(c.getName() + " закончил этап: " + description);
            if (this.length == 40){
                MainClass.finishLastStage.countDown(); // Чтобы известить о событии, следует вызвать метод countDown().
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

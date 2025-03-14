package iplm.utility;

import java.util.concurrent.*;

public class ThreadUtility {
    private static ThreadUtility INSTANCE;
    private ExecutorService executor_service;
    private BlockingQueue<Runnable> work_queue;

    public ThreadUtility(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        work_queue = new LinkedBlockingQueue<>();
        this.executor_service = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, work_queue);
    }

    public ThreadUtility() {
        work_queue = new LinkedBlockingQueue<>();
        this.executor_service = null;
    }

    public void init(int core_pool_size, int max_pool_size, long keep_alive_time, TimeUnit unit) {
        this.executor_service = new ThreadPoolExecutor(core_pool_size, max_pool_size, keep_alive_time, unit, work_queue);
    }

    // Метод для добавления задачи в пул
    public void execute(Runnable task) {
        executor_service.execute(task);
    }

    // Метод для добавления задачи с возвратом результата
    public <T> void submit(Runnable task) {
        executor_service.submit(task);
    }

    // Метод для завершения работы пула
    public void shutdown() {
        executor_service.shutdown();
    }

    // Метод для проверки завершения работы пула
    public boolean isTerminated() {
        return executor_service.isTerminated();
    }

    public static synchronized ThreadUtility getInstance() {
        if (INSTANCE == null) INSTANCE = new ThreadUtility();
        return INSTANCE;
    }

    // Пример использования
//    public static void main(String[] args) {
//        ThreadPoolWrapper threadPool = new ThreadPoolWrapper(
//                2, 5, 60, TimeUnit.SECONDS
//        );
//
//        for (int i = 0; i < 10; i++) {
//            final int taskId = i;
//            threadPool.execute(() -> {
//                System.out.println("Task " + taskId + " is being executed by " + Thread.currentThread().getName());
//                try {
//                    Thread.sleep(1000); // Симуляция работы
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            });
//        }
//
//        threadPool.shutdown();
//        try {
//            if (!threadPool.isTerminated()) {
//                threadPool.executorService.awaitTermination(1, TimeUnit.MINUTES);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
}

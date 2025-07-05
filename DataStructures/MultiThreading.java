package DataStructures;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 June 2025

We can create multiple asynchronous threads in Java using:
1. Thread class
2. Runnable interface
3. ExecutorService class
4. CompletableFuture
5. ThreadPoolExecutor
6. ScheduledExecutorService
7. Fork/Join framework -- Java 21

NOTE:
1. In Runnable Functional Interface and Thread constructor, we override run() method but we can execute it using start() or run().
2. Don't Use .run() instead of .start() ----> because t1.run(); // ❌ runs synchronously in the current thread, That’s not parallel
    it just runs like a normal method call.
    new Thread(r1);	Creates a thread (but does nothing)
    thread.start();	Starts the thread (runs r1.run() in a new thread)
    r1.run();	Just runs run() in the current thread (no multithreading)
3. run() == start() + join()
4. run() or start()+join() in Java is the same as Async Await in JavaScript
5. We cannot start a thread twice in Java. Once a thread has completed execution, it cannot be restarted.
    If you attempt to call start() on a thread that has already been started, Java throws an IllegalThreadStateException.
    This restriction exists because allowing a thread to restart would complicate the thread lifecycle and potentially lead to inconsistent state.
    If you need to execute the same task multiple times, you should create a new thread instance or use "executor services" 🔥 which are designed for repeated task execution.
6. thread.stop(); // ❌ DEPRECATED and dangerous
    It was unsafe because it could leave shared resources (locks, files, memory) in an inconsistent state.
    It could kill a thread midway through critical code, breaking invariants.
7. We can create a thread inside another thread
8. The custom wait, notify and notifyAll is defined in Object Class and not on Thread class in Java
    because the wait-notify mechanism operates on object monitors (intrinsic locks), not on threads themselves.
    Every object in Java has an associated monitor that can be used for synchronization.
    A thread doesn't wait on another thread; it waits on an object's monitor until notified by another thread.
    Since any object can serve as a synchronization point, these methods belong in Object class
9. Different ways to achieve synchronization in Java:
    1) Synchronized Method: "public synchronized void criticalMethod() {}"
    2) Synchronized Block: "public void method() { synchronized (lockObject) {} }"
    3) Explicit locks (ReentrantLock): private final Lock lock = new ReentrantLock(); public void criticalMethod() { lock.lock(); ... lock.unlock(); }
    4) Atomic variables: private AtomicInteger counter = new AtomicInteger(0); public void increment() {counter.incrementAndGet(); // Atomic operation }
    5) Thread-safe collections: Collections.synchronizedList(new ArrayList<>()); private Map<String, String> map = new ConcurrentHashMap<>(); public void update(String key, String value) {map.put(key, value); // Thread-safe operation}
10. Fork/Join framework different from traditional thread pools
    1) Work-stealing algorithm
    2) Divide-and-conquer paradigm
    3) ForkJoinPool class
    4) Recursive task decomposition
    5) Dynamic load balancing
11. CountDownLatch and CyclicBarrier in Java --> both are synchronization aids, used to coordinate the execution of multiple threads
    CountDownLatch acts as a one-time gate that remains closed until a count reaches zero. Threads can decrement the counter, and any thread can wait for the count to reach zero.
    CyclicBarrier makes a set of threads wait for each other to reach a common barrier point, and can be reused after all threads are released.
12. Handle thread interruption in Java
    1) Checking for interruption status: if (Thread.currentThread().isInterrupted()) { ... }
    2) Handling InterruptedException from blocking methods: try { Thread.sleep(1000); ... } catch (InterruptedException e) { ... Thread.currentThread().interrupt(); }
    3) Propagating interruption in methods that can't handle it: public void myMethod() throws InterruptedException { while (!Thread.currentThread().isInterrupted()) { Thread.sleep(100); ... } }
13. Check if a Thread holds a lock or not
    Object lockObject = new Object();
    synchronized (lockObject) {
    // Inside synchronized block
    boolean hasLock = Thread.holdsLock(lockObject); // Returns true
    System.out.println("Current thread holds lock: " + hasLock);
    }
    // Outside synchronized block
    boolean hasLock = Thread.holdsLock(lockObject); // Returns false
14. To get a thread dump in Java
    1) Using jstack tool: jstack <pid>
    2) From the command line for the running JVM: On Windows: Press Ctrl + Break in the console On Unix/Linux: Send SIGQUIT signal with kill -3 <pid>
    3) Using Java Management Extensions (JMX):
    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    threadMXBean.dumpAllThreads(true, true);
15. Fair locking: To implement fair locking where threads acquire the lock in first-come, first-served order, use ReentrantLock with fairness enabled:
    ReentrantLock fairLock = new ReentrantLock(true);
    fairLock.lock();
16. volatile keyword: To make a variable visible to all threads and all threads can change that volatile variable value, use the volatile keyword:
    int count = 0;
    volatile int count = 0;
17. visibility and atomicity in multithreading:
    Visibility concerns whether changes made by one thread are visible to other threads
    Atomicity ensures that operations are indivisible
18. concurrency and parallelism: Concurrency is about structure and design and  parallelism is about execution
19. Virtual Threads are introduced in Java 19 and stable in Java 21, Lightweight threads managed by the JVM, not the OS.
    You can create millions of them without exhausting system threads.
    Backed by the ForkJoinPool (but you don’t feel it).
    Designed for scalable, blocking code (e.g. networking, DB).
    Thread.startVirtualThread(() -> sout("Hello from virtual thread"))
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    scope.fork(() -> someBlockingCall());
    scope.join();
    scope.throwIfFailed();
    }




THREAD CLASS METHODS:
1. start() -- to start the thread
2. join() -- to wait for the thread to complete
3. run() -- to run the thread
4. sleep() -- to sleep the thread
5. yield() -- to yield the thread
6. interrupt() -- to interrupt the thread
7. setName() -- to set the name of the thread
8. getState() -- to get the state of the thread
9. isAlive() -- to check if the thread is alive
10. isDaemon() -- to check if the thread is a daemon
11. setDaemon() -- to set the thread as a daemon
12. getPriority() -- to get the priority of the thread
13. setPriority() -- to set the priority of the thread
14. getId() -- to get the id of the thread
15. getName() -- to get the name of the thread
16. getStackTrace() -- to get the stack trace of the thread
17. getThreadGroup() -- to get the thread group of the thread


RUNNABLE INTERFACE METHODS:
1. run() -- to run the thread


EXECUTOR SERVICE CLASS:
    We can create ExecutorService class objects using Executors class like this:
    1) ExecutorService executorService = Executors.newFixedThreadPool(3);
    2) ExecutorService executorService = Executors.newSingleThreadExecutor();
    3) ExecutorService executorService = Executors.newCachedThreadPool();
    4) ExecutorService executorService = Executors.newScheduledThreadPool(3);
    5) ExecutorService executorService = Executors.newWorkStealingPool();
    6) ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor(); // Introduced in Java 19 and stabled in Java 21
    7) ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    8) ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();




DIFFERENT .SUBMIT() METHODS IN EXECUTOR SERVICE CLASS:
    In ExecutorService.submit() method, we can pass Runnable or Callable
    <T> Future<T> submit(Callable<T> task);
    Future<?> submit(Runnable task
    <T> Future<T> submit(Runnable task, T result);
    public void execute(Runnable command){}

    .submit(Runnable task) — Fire and Track
    .execute(Runnable command) — Fire and Forget


EXECUTOR SERVICE CLASS METHODS:
     1. submit() -- to submit the thread
     2. shutdown() -- to shutdown the thread
     3. shutdownNow() -- to shutdown the thread
     4. isShutdown() -- to check if the thread is shutdown
     5. isTerminated() -- to check if the thread is terminated
     6. awaitTermination(long timeout, TimeUnit unit) -- to wait for the thread to terminate
     7. invokeAll(Collection<? extends Callable<?>> tasks) -- to invoke all the threads -----> will wait for all the threads to complete just like awaitTermination() and futureResult.get()
     8. invokeAny(Collection<? extends Callable<?>> tasks) -- to invoke any of the threads
     9. invokeAny(Collection<? extends Callable<?>> tasks, long timeout, TimeUnit unit) -- to invoke any of the threads
     10. execute(Runnable command) -- to execute the thread
     11. schedule(Runnable command, long delay, TimeUnit unit) -- to schedule the thread
     12. scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) -- to schedule the thread
     13. scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) -- to schedule the thread
     15. scheduleWithFixedDelay(Runnable command, long delay, long period, TimeUnit unit, Executor executor) -- to schedule the thread
     17. scheduleWithFixedDelay(Runnable command, long delay, long period, TimeUnit unit, ScheduledExecutorService executor) -- to schedule the thread
     18. In ExecutorService.shutdown() method, we can shutdown the thread
     19. In ExecutorService.shutdownNow() method, we can shutdown the thread
     20. Future<?> futureResult = executorService.submit();
         futureResult.get(); ----> will wait for the thread to complete, just like join() in Thread class 🔥




 COMPLETABLE FUTURE CLASS:
    Java 8 introduced CompletableFuture class, which is used to handle asynchronous operations in a non-blocking way.

COMPLETABLE FUTURE CLASS METHODS:
    1. supplyAsync() -- to supply the thread
    2. runAsync() -- to run the thread
    3. thenApply() -- to then apply the thread
    4. thenAccept() -- to then accept the thread
    5. thenRun() -- to then run the thread
    6. thenCompose() -- to then compose the thread
    7. thenAcceptEither() -- to then accept the thread
    8. thenRunEither() -- to then run the thread
    9. thenAcceptBoth() -- to then accept the thread
    10. thenRunBoth() -- to then run the thread
    11. exceptionally() -- to handle the exception
 */
public class MultiThreading {
    public static void main(String[] args) {

        // Thread class
        java.lang.Thread t1 = new Thread(() -> System.out.println("Thread 1"));
        Thread t2 = new Thread(() -> System.out.println("Thread 2"));
        Thread t3 = new Thread(() -> System.out.println("Thread 3"));


        // Runnable interface
        Runnable r1 = () -> System.out.println("Runnable 1");
        Runnable r2 = () -> System.out.println("Runnable 2");
        Runnable r3 = () -> System.out.println("Runnable 3");


         /*
         ExecutorService class
         .submit() and .invokeAll() will execute the thread
         */
        java.util.concurrent.ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(3);



        /*
        CompletableFuture
        No need of .start() or .run() here
        future.join(); // blocks and waits

        supplyAsync() to run a something asynchronously --- Supplier<>
        runAsync to run something asynchronously --- Runnable
         */
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {return "Hello, Java!"; });
        Supplier<String> supplier = () -> "Hello, Java!";
        CompletableFuture<String> future1a = CompletableFuture.supplyAsync(supplier);
        CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> {System.out.println("Hello, Java!"); return null; });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Hello, Java!", executorService);
        String str = CompletableFuture.supplyAsync(() -> 5)
                .thenApply(n -> n * 2) // Chaining Futures
                .thenApply(n -> "Result: " + n).join();
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> "https://example.com")
                .thenCompose(url -> CompletableFuture.supplyAsync(() -> "Fetched: " + url));

        CompletableFuture<Void> future5 = CompletableFuture.runAsync(() -> System.out.println("Hello, Java!"));
        Runnable runnable = () -> System.out.println("Hello, Java!");
        CompletableFuture<Void> future5a = CompletableFuture.runAsync(runnable);
        CompletableFuture<Void> future6 = CompletableFuture.runAsync(() -> System.out.println("Hello, Java!"), executorService);
        var userFuture = CompletableFuture.supplyAsync(() -> "Ravi");
        var emailFuture = CompletableFuture.supplyAsync(() -> "ravi@example.com");
        var result = userFuture.thenCombine(emailFuture, (name, email) -> name + " - " + email); // COMBINE TWO FUTURES
        System.out.println(result.join());


        // ThreadPoolExecutor
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.submit(() -> System.out.println("Thread 1"));
        threadPoolExecutor.submit(() -> System.out.println("Thread 2"));
        threadPoolExecutor.submit(() -> System.out.println("Thread 3"));
        threadPoolExecutor.execute(() -> System.out.println("Thread 4"));
        threadPoolExecutor.execute(() -> System.out.println("Thread 5"));
        threadPoolExecutor.execute(() -> System.out.println("Thread 6"));







        System.out.println("\n\nExecute Thread class objects synchronously");
        try {
            t1.start();
            t1.join();  // Main thread waits for t1 to complete
            t2.start();
            t2.join(); // Main thread waits for t2 to complete
            t3.start();
            t3.join(); // -- No need to join t3 as we don't need to wait for anything after it

            /*
             OR
             t1.run();
             t2.run();
             t3.run();

             because run() method executes all the threads synchronously i.e one after another
             so, run() == start()+join()
            */
        } catch (InterruptedException e) {
            // .join() and .sleep() can throw InterruptedException
            e.printStackTrace();
        }


        System.out.println("\n\nExecute Thread class objects asynchronously");
        t1 = new Thread(() -> System.out.println("Thread 1"));
        t2 = new Thread(() -> System.out.println("Thread 2"));
        t3 = new Thread(() -> System.out.println("Thread 3"));
        try {
            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();
            System.out.println("All async threads finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        System.out.println("\n\nExecute Runnable interface objects synchronously");
        r1.run();
        r2.run();
        r3.run();
        /*
         or
        Thread runnableT1 = new Thread(r1);
        Thread runnableT2 = new Thread(r2);
        Thread runnableT3 = new Thread(r3);
        try {
            runnableT1.start();
            runnableT1.join();
            runnableT2.start();
            runnableT2.join();
            runnableT3.start();
            runnableT3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */




        System.out.println("\n\nExecute Runnable interface objects asynchronously");
        Thread runnableT1 = new Thread(r1);
        Thread runnableT2 = new Thread(r2);
        Thread runnableT3 = new Thread(r3);

        runnableT1.start();
        runnableT2.start();
        runnableT3.start();
        try {
            runnableT1.join();
            runnableT2.join();
            runnableT3.join();
            System.out.println("All async threads finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }








        System.out.println("\n\nExecute ExecutorService class objects synchronously"); // curr submit() + curr futureResult.get() will wait for the thread to complete
        Future<?> esFutureResult1 = executorService.submit(r1); // RUNNABLE INTERFACE
        try {System.out.println(esFutureResult1.get());} catch (InterruptedException | ExecutionException e) {e.printStackTrace();}

        Future<?> esFutureResult2 = executorService.submit(() -> System.out.println("ExecutorService 2 - with custom override Runnable run()")); // RUNNABLE INTERFACE
        try {System.out.println(esFutureResult2.get());} catch (InterruptedException | ExecutionException e) {e.printStackTrace();}

        Callable<String> callable = () -> { // Callable functional interface
            System.out.println("ExecutorService 3 - with custom override Callable call()");
            return "Returned from Callable ExecutorService 3";
        };
        Future<String> esFutureResult3 = executorService.submit(callable);
        try {System.out.println(esFutureResult3.get());} catch (InterruptedException | ExecutionException e) {e.printStackTrace();}








        System.out.println("\n\nExecute ExecutorService class objects asynchronously"); // all submit()s + and then all futureResult.get()
        esFutureResult1 = executorService.submit(r1);
        esFutureResult2 = executorService.submit(() -> System.out.println("ExecutorService 2 - with custom override Runnable run()"));
        esFutureResult3 = executorService.submit(callable);
        try {
            System.out.println(esFutureResult1.get());
            System.out.println(esFutureResult2.get());
            System.out.println(esFutureResult3.get());
        } catch (InterruptedException | ExecutionException e) {e.printStackTrace();}
        System.out.println("All async threads finished in ExecutorService using ---- submit() + futureResult.get()");

        // or submit() + shutdown() + awaitTermination()
        executorService.submit(r1);
        executorService.submit(() -> System.out.println("ExecutorService 2 - with custom override Runnable run()"));
        executorService.submit(callable);
        executorService.shutdown(); // disallow new tasks
        try {
            boolean isTerminated = executorService.awaitTermination(1, TimeUnit.MINUTES); // wait for all to finish. ⚠️ Only works after shutdown(). Use if you're done submitting and just want to wait.
        } catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("All async threads finished in ExecutorService using ---- submit() + shutdown() + awaitTermination()");

        // or invokeAll()
        if (executorService.isShutdown()) executorService = Executors.newFixedThreadPool(3); // once shutdown(), can't be reused. So, create a new one
        List<Callable<Void>> tasks = List.of(
                // Just run logic inside the call(), no new Thread() needed
            () -> { r1.run(); return null; },
            () -> { r2.run(); return null; },
            () -> { r3.run(); return null; }
        );
        List<Callable<Void>> tasks2 = List.of(
            Executors.callable(r1, null),
            Executors.callable(r2, null),
            Executors.callable(r3, null)
        );
        try {
            executorService.invokeAll(tasks);
//            executorService.invokeAll(tasks2);
        } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("All async threads finished in ExecutorService using ---- invokeAll()");

        executorService.shutdown();


        // or from java 21 and it is a preview API and may be removed in a future release
//        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//            StructuredTaskScope.Subtask subtask1 = scope.fork(() -> { r1.run(); return null; });
//            StructuredTaskScope.Subtask subtask2 = scope.fork(() -> { r2.run(); return null; });
//            StructuredTaskScope.Subtask subtask3 = scope.fork(() -> { r3.run(); return null; });
//
//            scope.join(); // blocks like join()
//            scope.throwIfFailed();
//        } catch (ExecutionException | InterruptedException e) {e.printStackTrace();}
//        System.out.println("All async threads finished in ExecutorService using ---- try (var scope = new StructuredTaskScope.ShutdownOnFailure()) + fork() + join()");
    }
}

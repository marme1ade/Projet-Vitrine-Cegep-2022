package org.vitrine.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PeriodicTasks {
    private static final ScheduledExecutorService executorTimer = Executors.newSingleThreadScheduledExecutor();

    public static void start() {
        executorTimer.scheduleWithFixedDelay(new Runnable() {
            public void run() {

            }
        }, 30, 30, TimeUnit.MINUTES);
    }
}

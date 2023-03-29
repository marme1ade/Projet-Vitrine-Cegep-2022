package org.vitrine.core;

import org.vitrine.common.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PeriodicTasks {
    private static final ScheduledExecutorService executorTimer = Executors.newSingleThreadScheduledExecutor();

    /**
     * Start the scheduled periodic tasks
     */
    public static void start() {
        /* Every 5s */
        executorTimer.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                Main.tv.checkInactivity();
            }
        }, 5, 5, TimeUnit.SECONDS);

        /* Every 30s */
        executorTimer.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                Main.tv.generateTotp();
            }
        }, 30, 30, TimeUnit.SECONDS);
    }
}

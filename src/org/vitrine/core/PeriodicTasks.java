package org.vitrine.core;

import org.vitrine.common.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PeriodicTasks {
    private static final ScheduledExecutorService executorTimer = Executors.newSingleThreadScheduledExecutor();

    public static void start() {
        executorTimer.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                Utils.debugGenerateTotp();
            }
        }, 30, 30, TimeUnit.MINUTES);
    }
}

package io.subs.data.executor;

import android.support.annotation.NonNull;
import io.subs.domain.executor.IThreadExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 */
@Singleton public class JobExecutor implements IThreadExecutor {
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private final ThreadPoolExecutor threadPoolExecutor;

    @Inject JobExecutor() {
        this.threadPoolExecutor =
                new ThreadPoolExecutor(NUMBER_OF_CORES * 2, NUMBER_OF_CORES * 2, 10L,
                        TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                        new JobThreadFactory());
    }

    @Override public void execute(@NonNull Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}

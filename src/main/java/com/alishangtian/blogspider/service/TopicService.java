package com.alishangtian.blogspider.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description TopicService
 * @Date 2020/4/28 下午2:25
 * @Author maoxiaobing
 **/
@Service
@Log4j2
public class TopicService {
    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        AtomicLong num = new AtomicLong();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "topic-schedule-thread-" + num.getAndIncrement());
        }
    });

    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleWithFixedDelay(() -> ping(), 0, 5000, TimeUnit.MILLISECONDS);
    }

    /**
     * @Author maoxiaobing
     * @Description ping
     * @Date 2020/4/28
     * @Param []
     * @Return void
     */
    public void ping() {
        log.info("{}", "ping");
    }
}

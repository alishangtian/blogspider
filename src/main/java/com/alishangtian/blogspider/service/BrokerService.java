package com.alishangtian.blogspider.service;

import com.alishangtian.blogspider.cluster.BrokerProperties;
import com.alishangtian.blogspider.cluster.Node;
import com.alishangtian.blogspider.remoting.Remoting;
import com.alishangtian.blogspider.util.JSONUtils;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Description TopicService
 * @Date 2020/4/28 下午2:25
 * @Author maoxiaobing
 **/
@Service
@Log4j2
public class BrokerService {
    @Autowired
    private BrokerProperties brokerProperties;

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        AtomicLong num = new AtomicLong();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "broker-schedule-thread-" + num.getAndIncrement());
        }
    });

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadFactory() {
        AtomicLong num = new AtomicLong();

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, "broker-ping-thread-" + num.getAndIncrement());
        }
    });

    private ConcurrentMap<String, Node> activeNodes;
    private ConcurrentMap<String, Node> susoutNodes = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        brokerProperties.getNodeList().forEach(s -> activeNodes.put(s, new Node(s)));
        /**
         * ping schedule
         */
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
        activeNodes.forEach((s, node) -> {
            if (!s.equals(brokerProperties.getSelfServer())) {
                log.info("ping {}", s);
                threadPoolExecutor.submit(() -> {
                    if (!Remoting.ping(s, activeNodes.values())) {
                        activeNodes.remove(s);
                    }
                });
            }
        });
    }

    /**
     * @Author maoxiaobing
     * @Description pong
     * @Date 2020/4/30
     * @Param [server, port, body]
     * @Return java.lang.String
     */
    public String pong(String server, int port, String body) {
        List<Node> nodes = JSONUtils.parseObject(body, new TypeToken<List<Node>>() {
        }.getType());
        nodes.add(new Node(server, port));
        nodes.forEach(node -> activeNodes.putIfAbsent(node.getServer(), node));
        return "pong";
    }
}

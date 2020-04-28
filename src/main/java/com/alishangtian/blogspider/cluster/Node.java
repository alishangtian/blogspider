package com.alishangtian.blogspider.cluster;

import lombok.Data;

/**
 * @Description TODO
 * @ClassName Node
 * @Author alishangtian
 * @Date 2020/4/28 21:42
 * @Version 0.0.1
 */
@Data
public class Node {
    String host;
    int port;

    public Node(String server) {
        String[] ss = server.split(":");
        this.host = ss[0];
        this.port = Integer.valueOf(ss[1]);
    }
}

package com.alishangtian.blogspider.remoting;

import com.alishangtian.blogspider.cluster.Node;
import com.alishangtian.blogspider.util.JSONUtils;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;

import java.io.IOException;
import java.util.Collection;

/**
 * 发送心跳
 *
 * @Description TODO
 * @ClassName Remoting
 * @Author alishangtian
 * @Date 2020/4/28 21:58
 * @Version 0.0.1
 */
@Log4j2
public class Remoting {
    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static boolean ping(String server, Collection<Node> knownNodes) {
        RequestBody body = RequestBody.create(JSONUtils.toJSONString(knownNodes), JSON);
        Request request = new Request.Builder()
                .url("http://" + server + "/ping")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string().equals("pong");
        } catch (IOException e) {
            log.error("{}", e);
            return false;
        }
    }
}
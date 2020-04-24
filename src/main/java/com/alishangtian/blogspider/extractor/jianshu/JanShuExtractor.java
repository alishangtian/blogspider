package com.alishangtian.blogspider.extractor.jianshu;

import com.alishangtian.blogspider.extractor.AbstractExtractor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author maoxiaobing
 * @Description
 * @Date 2020/4/24
 */
@Service
@Log4j2
public class JanShuExtractor extends AbstractExtractor {
    static Map<String, String> hTagMap = new HashMap<>();

    static {
        hTagMap.put("H1", "# ");
        hTagMap.put("H2", "## ");
        hTagMap.put("H3", "### ");
        hTagMap.put("H4", "#### ");
        hTagMap.put("H5", "##### ");
        hTagMap.put("H6", "###### ");
        hTagMap.put("h1", "# ");
        hTagMap.put("h2", "## ");
        hTagMap.put("h3", "### ");
        hTagMap.put("h4", "#### ");
        hTagMap.put("h5", "##### ");
        hTagMap.put("h6", "###### ");
    }

    private static final int DEFAULT_TIME_OUT = 5 * 1000;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), new ThreadFactory() {
        AtomicLong num = new AtomicLong();

        @Override
        public Thread newThread(Runnable r) {
            Thread a = new Thread(r);
            a.setName("spider-thread-pool-" + num.getAndIncrement());
            return a;
        }
    });

    /**
     * @Author maoxiaobing
     * @Description crawling
     * @Date 2020/4/24
     * @Param [url]
     * @Return java.lang.String
     */
    @Override
    public String crawling(String url) throws Exception {
        StringBuilder builder = new StringBuilder();
        Future<String> future = executor.submit(() -> {
            try {
                Document doc = Jsoup.parse(new URL(url), DEFAULT_TIME_OUT);
                Elements eles = doc.select("article");
                printEle(eles.first().children(), builder);
                return builder.toString();
            } catch (Exception e) {
                log.error("{}", e);
            }
            return "null";
        });
        return future.get();
    }

    public void printEle(Elements eles, StringBuilder builder) {
        for (Element ele : eles) {
            String nodeName = ele.nodeName().toLowerCase();
            String mdTag;
            //h tag
            if (StringUtils.isNotEmpty(mdTag = hTagMap.get(ele.nodeName()))) {
                builder.append(mdTag).append(ele.text()).append("\n\n");
                continue;
            }
            // img
            if (nodeName.equals("div") && ele.hasClass("image-package")) {
                Element eleImg = ele.selectFirst("img");
                builder.append(String.format("![img](%s)", eleImg.attr("data-original-src"))).append("\n\n");
                continue;
            }
            // code
            if (nodeName.equals("pre")) {
                builder.append("```java").append("\n").append(ele.text()).append("\n\n").append("```").append("\n\n");
                continue;
            }
            // blockquote
            if (nodeName.equals("blockquote")) {
                builder.append(ele.outerHtml()).append("\n\n");
                continue;
            }
            // a
            if (nodeName.equals("a")) {
                builder.append(ele.outerHtml()).append("\n\n");
                continue;
            }
            // strong
            if (nodeName.equals("strong")) {
                builder.append(String.format("***%s***", ele.ownText())).append("\n\n");
                continue;
            }
            // own text is not empty
            if (StringUtils.isNotEmpty(ele.ownText())) {
                builder.append(ele.text()).append("\n\n");
            }
            printEle(ele.children(), builder);
        }
    }
}

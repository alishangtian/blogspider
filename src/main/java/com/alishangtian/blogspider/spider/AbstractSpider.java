package com.alishangtian.blogspider.spider;

import java.io.IOException;

/**
 * @Description AbstractSpider
 * @Date 2020/4/24 下午2:22
 * @Author maoxiaobing
 **/
public abstract class AbstractSpider implements Spider {
    @Override
    public abstract String crawling(String url) throws IOException, Exception;
}

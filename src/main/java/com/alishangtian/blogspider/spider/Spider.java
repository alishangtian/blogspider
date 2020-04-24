package com.alishangtian.blogspider.spider;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @Author maoxiaobing
 * @Description
 * @Date 2020/4/24
 */
public interface Spider {
    /**
     * @Author maoxiaobing
     * @Description crawling
     * @Date 2020/4/24
     * @Param [url]
     * @Return java.lang.String
     */
    public String crawling(String url) throws IOException, Exception;
    
}

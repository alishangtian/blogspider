package com.alishangtian.blogspider.extractor;

import java.io.IOException;

/**
 * @Author maoxiaobing
 * @Description
 * @Date 2020/4/24
 */
public interface Extractor {

    /**
     * @Author maoxiaobing
     * @Description crawling
     * @Date 2020/4/24
     * @Param [url]
     * @Return java.lang.String
     */
    public String crawling(String url) throws IOException, Exception;

}

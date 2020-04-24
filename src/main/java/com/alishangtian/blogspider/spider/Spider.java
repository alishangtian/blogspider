package com.alishangtian.blogspider.spider;

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
    public String crawling(String url);
}

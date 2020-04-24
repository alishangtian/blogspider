package com.alishangtian.blogspider.extractor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description AbstractSpider
 * @Date 2020/4/24 下午2:22
 * @Author maoxiaobing
 **/
public abstract class AbstractExtractor implements Extractor {

    @Override
    public abstract String extract(String url, String articleSelector) throws IOException, Exception;
}

package com.alishangtian.blogspider.extractor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName ExtractorrManager
 * @Author alishangtian
 * @Date 2020/4/25 09:50
 * @Version 0.0.1
 */
@Service
@Log4j2
public class ExtractorManager {
    @Autowired
    List<Extractor> extractorList;
    Map<String, Extractor> extractorMap = new HashMap<>();

    @PostConstruct
    public void init() {
        extractorList.forEach(extractor -> {
            extractorMap.put(extractor.getServiceCode(), extractor);
        });
    }

    /**
     * 根据服务code获取服务
     *
     * @param serviceCode
     * @return
     */
    public Extractor getExtractor(String serviceCode) {
        return extractorMap.get(serviceCode);
    }

    /**
     * @Author maoxiaobing
     * @Description extractLink
     * @Date 2020/5/7
     * @Param [html]
     * @Return java.lang.String
     */
    public String linkedExtractor(String html) {
        String rowData = html;
        for (Extractor extractor : extractorList) {
            if (extractor.match(rowData)) {
                rowData = extractor.extractFromHtml(rowData, "");
            }
        }
        return rowData;
    }
}

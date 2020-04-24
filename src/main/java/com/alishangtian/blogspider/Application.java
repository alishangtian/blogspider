package com.alishangtian.blogspider;

import com.alishangtian.blogspider.extractor.jianshu.JanShuExtractor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoxiaobing
 */
@SpringBootApplication
@Log4j2
@RestController
public class Application {
    @Autowired
    JanShuExtractor janShuExtractor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/janshu/spider")
    public String parseJanShu(@RequestParam String url, @RequestParam String articleSelector) throws Exception {
        return janShuExtractor.extract(url,articleSelector);
    }

}

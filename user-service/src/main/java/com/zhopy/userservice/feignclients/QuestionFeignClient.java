package com.zhopy.userservice.feignclients;

import com.zhopy.userservice.model.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "question-service")
public interface QuestionFeignClient {

    @GetMapping(value = "/api/question/detail/{questionCode}")
    Question findByQuestionCode(@PathVariable("questionCode") Long questionCode);

    @GetMapping("/api/question/validate/{questionCode}")
    boolean existsByQuestionCode(@PathVariable("questionCode") Long questionCode);
}

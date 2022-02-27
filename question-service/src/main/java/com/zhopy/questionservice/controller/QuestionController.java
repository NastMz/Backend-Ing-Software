package com.zhopy.questionservice.controller;

import com.zhopy.questionservice.dto.QuestionRequest;
import com.zhopy.questionservice.service.interfaces.IQuestionService;
import com.zhopy.questionservice.utils.exeptions.ApiNotFound;
import com.zhopy.questionservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.questionservice.validator.IQuestionValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "question-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    @Qualifier("QuestionService")
    private IQuestionService questionService;

    @Autowired
    @Qualifier("QuestionValidator")
    private IQuestionValidator questionValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.questionService.findAll());
    }

    @GetMapping(value = "/detail/{questionCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByQuestionCode(@PathVariable("questionCode") Long questionCode) throws ApiNotFound {
        this.questionValidator.validatorById(questionCode);
        return ResponseEntity.ok(this.questionService.findByQuestionCode(questionCode));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody QuestionRequest questionRequest) throws ApiUnprocessableEntity {
        this.questionValidator.validator(questionRequest);
        this.questionService.save(questionRequest);
        return ResponseEntity.ok("La pregunta se guardo correctamente");
    }

    @PutMapping("/update/{questionCode}")
    public ResponseEntity<Object> update(@PathVariable("questionCode") Long questionCode, @RequestBody QuestionRequest questionRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.questionValidator.validatorByIdRequest(questionCode, questionRequest.getQuestionCode());
        this.questionValidator.validatorUpdate(questionRequest);
        this.questionService.update(questionRequest, questionCode);
        return ResponseEntity.ok("La pregunta se actualizo correctamente");
    }

    @DeleteMapping("/delete/{questionCode}")
    public ResponseEntity<Object> delete(@PathVariable("questionCode") Long questionCode) throws ApiNotFound {
        this.questionValidator.validatorById(questionCode);
        this.questionService.delete(questionCode);
        return ResponseEntity.ok("La pregunta se elimino correctamente");
    }

    @GetMapping("/validate/{questionCode}")
    public ResponseEntity<Object> existsByQuestionCode(@PathVariable("questionCode") Long questionCode) throws ApiNotFound {
        this.questionValidator.validatorById(questionCode);
        return ResponseEntity.ok(questionService.existsByQuestionCode(questionCode));
    }

}

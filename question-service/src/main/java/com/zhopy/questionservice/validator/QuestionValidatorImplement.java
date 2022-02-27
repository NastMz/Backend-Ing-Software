package com.zhopy.questionservice.validator;

import com.zhopy.questionservice.dto.QuestionDTO;
import com.zhopy.questionservice.dto.QuestionRequest;
import com.zhopy.questionservice.service.interfaces.IQuestionService;
import com.zhopy.questionservice.utils.exeptions.ApiNotFound;
import com.zhopy.questionservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("QuestionValidator")
public class QuestionValidatorImplement implements IQuestionValidator {

    @Autowired
    @Qualifier("QuestionService")
    private IQuestionService questionService;

    @Override
    public void validator(QuestionRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        if (questionService.existsByQuestion(request.getQuestion())) {
            this.message422("La pregunta ya existe");
        }
    }

    @Override
    public void validatorUpdate(QuestionRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateName(request);
    }

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!questionService.existsByQuestionCode(id)) {
            this.message404("El codigo no existe");
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long questionCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!questionService.existsByQuestionCode(urlCode)) {
            this.message404("El codigo no existe");
        }
        if (!urlCode.equals(questionCode)) {
            this.message422("El codigo del cuerpo de la peticion no coincide con el de la uri");
        }
    }

    private void validateData(QuestionRequest request) throws ApiUnprocessableEntity {
        if (request.getQuestion() == null || request.getQuestion().isBlank()) {
            this.message422("La pregunta no puede estar vacia");
        }
    }

    private void validateName(QuestionRequest questionRequest) throws ApiUnprocessableEntity {
        QuestionDTO questionSearch = this.questionService.findByQuestionCode(questionRequest.getQuestionCode());
        if (!questionRequest.getQuestion().equals(questionSearch.getQuestion()) && questionService.existsByQuestion(questionRequest.getQuestion())) {
            this.message422("La pregunta ya existe");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}

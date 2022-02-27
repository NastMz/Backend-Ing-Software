package com.zhopy.questionservice.validator;

import com.zhopy.questionservice.dto.QuestionRequest;
import com.zhopy.questionservice.utils.exeptions.ApiNotFound;
import com.zhopy.questionservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface IQuestionValidator {
    void validator(QuestionRequest questionRequest) throws ApiUnprocessableEntity;

    void validatorById(Long questionCode) throws ApiNotFound;

    void validatorUpdate(QuestionRequest request) throws ApiUnprocessableEntity;

    void validatorByIdRequest(Long urlCode, Long questionCode) throws ApiNotFound, ApiUnprocessableEntity;
}

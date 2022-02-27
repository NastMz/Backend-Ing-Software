package com.zhopy.questionservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionRequest implements Serializable {
    private Long questionCode;
    private String question;
}

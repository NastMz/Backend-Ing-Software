package com.zhopy.questionservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionDTO implements Serializable {
    private Long questionCode;
    private String question;
}
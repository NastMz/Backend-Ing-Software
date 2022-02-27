package com.zhopy.questionservice.service.interfaces;

import com.zhopy.questionservice.dto.QuestionDTO;
import com.zhopy.questionservice.dto.QuestionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IQuestionService {
    List<QuestionDTO> findAll();

    QuestionDTO findByQuestionCode(Long questionCode);

    QuestionDTO findByQuestion(String question);

    void save(QuestionRequest questionRequest);

    void update(QuestionRequest questionRequest, Long questionCode);

    void delete(Long questionCode);

    boolean existsByQuestionCode(Long questionCode);

    boolean existsByQuestion(String question);
}

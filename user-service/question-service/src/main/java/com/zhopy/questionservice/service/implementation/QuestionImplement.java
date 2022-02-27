package com.zhopy.questionservice.service.implementation;

import com.zhopy.questionservice.dto.QuestionDTO;
import com.zhopy.questionservice.dto.QuestionRequest;
import com.zhopy.questionservice.entity.Question;
import com.zhopy.questionservice.repository.QuestionRepository;
import com.zhopy.questionservice.service.interfaces.IQuestionService;
import com.zhopy.questionservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("QuestionService")
public class QuestionImplement implements IQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<QuestionDTO> findAll() {
        List<QuestionDTO> dto = new ArrayList<>();
        Iterable<Question> questions = this.questionRepository.findAll();

        for (Question question : questions) {
            QuestionDTO questionDTO = MapperHelper.modelMapper().map(question, QuestionDTO.class);
            dto.add(questionDTO);
        }

        return dto;
    }

    @Override
    public QuestionDTO findByQuestionCode(Long questionCode) {
        Optional<Question> question = this.questionRepository.findById(questionCode);
        if (!question.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(question.get(), QuestionDTO.class);
    }

    @Override
    public QuestionDTO findByQuestion(String question) {
        Optional<Question> questionSearch = this.questionRepository.findByQuestion(question);
        if (questionSearch.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(questionSearch, QuestionDTO.class);
    }

    @Override
    public void save(QuestionRequest questionRequest) {
        Question question = MapperHelper.modelMapper().map(questionRequest, Question.class);
        this.questionRepository.save(question);
    }

    @Override
    public void update(QuestionRequest questionRequest, Long questionCode) {
        Question question = MapperHelper.modelMapper().map(questionRequest, Question.class);
        this.questionRepository.save(question);

    }

    @Override
    public void delete(Long questionCode) {
        questionRepository.deleteById(questionCode);
    }

    @Override
    public boolean existsByQuestionCode(Long questionCode) {
        return questionRepository.existsByQuestionCode(questionCode);
    }

    @Override
    public boolean existsByQuestion(String question) {
        return questionRepository.existsByQuestion(question);
    }

}

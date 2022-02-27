package com.zhopy.questionservice.repository;

import com.zhopy.questionservice.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    @Transactional(readOnly = true)
    Optional<Question> findByQuestion(String question);

    boolean existsByQuestion(String question);

    boolean existsByQuestionCode(Long questionCode);
}

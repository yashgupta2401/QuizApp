package com.micro.quiz_service.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.micro.quiz_service.model.Response;
import com.micro.quiz_service.model.QuestionWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInteface {
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,
                                                             @RequestParam Integer numQuestions)
                                                             throws JsonProcessingException;
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsId)
                                                                    throws JsonProcessingException;

    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
                                            throws JsonProcessingException;
}

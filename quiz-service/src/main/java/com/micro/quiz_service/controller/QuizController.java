package com.micro.quiz_service.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.micro.quiz_service.model.QuizDTO;
import com.micro.quiz_service.model.Response;
import com.micro.quiz_service.model.QuestionWrapper;
import com.micro.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO) throws JsonProcessingException {
        return quizService.createQuiz(quizDTO.getCategoryName(), quizDTO.getNumQuestions(), quizDTO.getTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer id) throws JsonProcessingException {
        return quizService.getQuizQuestion(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) throws JsonProcessingException {
        return quizService.calculateResult(id,responses);
    }

}
















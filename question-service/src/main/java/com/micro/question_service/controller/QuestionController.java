package com.micro.question_service.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.micro.question_service.model.QuestionWrapper;
import com.micro.question_service.model.Response;
import com.micro.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /*@GetMapping("/allQuestion")
    public ResponseEntity<List<Question>> getAllQuestion(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
       return questionService.addQuestion(question);
    }

    @PutMapping("/updateQuestion")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/deleteQuestion")
    public String deleteQuestion(@RequestBody Question question){
        return questionService.deleteQuestion(question);
    }

    @DeleteMapping("/deleteQuestionById")
    public String deleteQuestionById(@RequestBody Question question){
        Integer id = question.getId();
        return questionService.deleteQuestionById(id);
    }*/


    //find a way to generate quiz - generate
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,
                                                             @RequestParam Integer numQuestions) throws JsonProcessingException {
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }

    //find a way to get questions based on question id- getQuestion(questionid)
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsId) throws JsonProcessingException {
        return questionService.getQuestionsFromId(questionsId);

    }


    //find a way to getScore because now questions have everything to check score- getScore()
    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) throws JsonProcessingException {
        return questionService.getScore(responses);
    }

}










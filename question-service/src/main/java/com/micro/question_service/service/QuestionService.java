package com.micro.question_service.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.question_service.model.Question;
import com.micro.question_service.model.QuestionWrapper;
import com.micro.question_service.model.Response;
import com.micro.question_service.dao.QuestionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    ObjectMapper objectMapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(QuestionService.class);

    /*public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.getStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
        }catch(Exception e){
            e.getStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDAO.save(question);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }catch(Exception e){
            e.getStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public String deleteQuestion(Question question) {
        questionDAO.delete(question);
        return "deleted";
    }

    public String deleteQuestionById(Integer id) {
        questionDAO.deleteById(id);
        return "deleted by Id";
    }*/

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) throws JsonProcessingException {

        List<Integer> questions = questionDAO.findRandomQuestionsByCategory(categoryName , numQuestions);
        logger.info("Question after getting random quiz id from Query:{}",objectMapper.writeValueAsString(questions));
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsId) throws JsonProcessingException {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();


        for (Integer question : questionsId){
            questions.add(questionDAO.findById(question).get());
        }

        logger.info("Question after getting id from question table:{}",objectMapper.writeValueAsString(questions));

        for(Question question: questions){

            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) throws JsonProcessingException {

        int right=0;

        for (Response response : responses){
            Question question = questionDAO.findById(response.getId()).get();
            logger.info("Question generated by id {}: {}",response.getId(),objectMapper.writeValueAsString(question));
            if(response.getResponse().equals(question.getRightAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
















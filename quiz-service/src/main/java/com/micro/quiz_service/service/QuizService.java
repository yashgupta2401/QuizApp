package com.micro.quiz_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.quiz_service.dao.QuizDao;
import com.micro.quiz_service.feign.QuizInteface;
import com.micro.quiz_service.model.QuestionWrapper;
import com.micro.quiz_service.model.Quiz;
import com.micro.quiz_service.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInteface quizInteface;

    Logger logger = LoggerFactory.getLogger(QuizService.class);

    ObjectMapper objectMapper = new ObjectMapper();


    //Here we are calling question service via eureka server and creating a quiz with que id's
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) throws JsonProcessingException {

        List<Integer> questions = quizInteface.getQuestionsForQuiz(category,numQ).getBody();
        logger.info("questions returned from question service:{}",objectMapper.writeValueAsString(questions));

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        logger.info("Quiz after getting questions from Question service:{}",objectMapper.writeValueAsString(quiz));


        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


    //Here we are giving an Id of quiz as a request to question service which inturn is providing us Questions
    //from that quiz
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) throws JsonProcessingException {
        Quiz quiz=  quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();

        ResponseEntity<List<QuestionWrapper>> questions = quizInteface.getQuestionsFromId(questionIds);
        logger.info("Questions returned in QuestionWrapper form:{}",objectMapper.writeValueAsString(questions));

        return questions;

    }


    //Here we will calculate test score obtained by passing test responses to question service
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) throws JsonProcessingException {
        ResponseEntity<Integer> score =  quizInteface.getScore(responses);
        return score;
    }
}

















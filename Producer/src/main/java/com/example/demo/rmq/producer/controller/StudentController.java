package com.example.demo.rmq.producer.controller;

import com.example.demo.rmq.producer.model.Student;
import com.example.demo.rmq.producer.service.StudentPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentPublisher studentPublisher;

    @PostMapping("/publish/student")
    public Student publishStudent(@RequestBody Student student) {
        studentPublisher.publishStudentDetails(student);
        return student;
    }
}

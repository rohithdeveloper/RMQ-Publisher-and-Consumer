package com.example.demo.rmq.consumer.service;

import com.example.demo.rmq.consumer.model.Student;
import com.example.demo.rmq.consumer.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Async("rabbit-consumer")
    public void saveStudent(Student student) {
        log.info("💾 Student details received -> {}", student);
        student.setId(null);

        Student savedStudent = studentRepository.save(student);
        log.info("Student saved -> {}", savedStudent);
    }
}

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

    public void saveStudent(Student student) {
        String threadName = Thread.currentThread().getName();
        log.info("💾 [{}] Processing student: {}", threadName, student.getName());
        student.setId(null);

        Student savedStudent = studentRepository.save(student);
        log.info("✅ [{}] Student saved: {}", threadName, savedStudent);
    }
}

package com.example.demo.rmq.consumer.service;

import com.example.demo.rmq.consumer.model.Student;
import com.example.demo.rmq.consumer.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentConsumer {

    @Autowired
    private StudentRepository studentRepository;


    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeStudent(Student student) {
        log.info("Student details received -> {}", student);
        
        // Clear the ID so Consumer creates a new record with its own auto-generated ID
        student.setId(null);
        
        Student savedStudent = studentRepository.save(student);
        log.info("Student saved -> {}", savedStudent);
    }
}

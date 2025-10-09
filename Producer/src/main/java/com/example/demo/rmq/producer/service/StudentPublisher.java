package com.example.demo.rmq.producer.service;


import com.example.demo.rmq.producer.model.Student;
import com.example.demo.rmq.producer.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Value("${rabbitmq.direct.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String directRoutingKey;

    public void publishStudentDetails(Student student) {
        // Save student to the database
        studentRepository.save(student);
        log.info("Student details saved to DB -> {}", student);

        // Publish student details to RabbitMQ
        rabbitTemplate.convertAndSend(exchange, directRoutingKey, student);
        log.info("Student details sent to RabbitMQ -> {}", student);
    }
}

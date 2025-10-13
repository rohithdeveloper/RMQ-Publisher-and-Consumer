package com.example.demo.rmq.consumer.service;

import com.example.demo.rmq.consumer.model.Student;
import com.example.demo.rmq.consumer.repository.StudentRepository;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StudentConsumer {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

// This is a simple listener without manual acknowledgment

//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void consumeStudent(Student student) {
//        log.info("Student details received -> {}", student);
//
//        // Clear the ID so Consumer creates a new record with its own auto-generated ID
//        student.setId(null);
//
//        Student savedStudent = studentRepository.save(student);
//        log.info("Student saved -> {}", savedStudent);
//    }

    // This listener uses MANUAL ACKNOWLEDGMENT
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeStudent(Student student, Channel channel, Message message) {
        String threadName = Thread.currentThread().getName();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        
        log.info("🔥 [{}] Received - DeliveryTag: {}, Student: {}", 
                 threadName, deliveryTag, student.getName());

        try {
            student.setId(null);
            studentService.saveStudent(student);

            // MANUAL ACK
            channel.basicAck(deliveryTag, false);
            log.info("✅ [{}] ACK sent for deliveryTag: {}", threadName, deliveryTag);

        } catch (Exception e) {
            log.error("❌ [{}] Error: {}", threadName, e.getMessage(), e);
            try {
                channel.basicNack(deliveryTag, false, true);
                log.warn("🔄 [{}] NACK sent and requeued", threadName);
            } catch (Exception ex) {
                log.error("❌ [{}] Failed to NACK: {}", threadName, ex.getMessage());
            }
        }
    }

}

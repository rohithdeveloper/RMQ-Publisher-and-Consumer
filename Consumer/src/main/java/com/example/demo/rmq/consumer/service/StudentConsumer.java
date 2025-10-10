package com.example.demo.rmq.consumer.service;

import com.example.demo.rmq.consumer.model.Student;
import com.example.demo.rmq.consumer.repository.StudentRepository;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentConsumer {

    @Autowired
    private StudentRepository studentRepository;

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
        log.info("🔥 METHOD INVOKED - Received student: {}", student);
        log.info("📝 Message properties: {}", message.getMessageProperties());

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("📬 Delivery Tag: {}", deliveryTag);

        try {
            log.info("💾 Student details received -> {}", student);
            log.info("🔧 Setting student ID to null for new insertion");
            student.setId(null);

            log.info("💿 Attempting to save student to database...");
            Student savedStudent = studentRepository.save(student);
            log.info("✅ Student saved successfully -> {}", savedStudent);

            // MANUAL ACK
            log.info("⏳ Before ACK: deliveryTag = {}", deliveryTag);
            channel.basicAck(deliveryTag, false);
            log.info("✅ ACK SUCCESSFULLY SENT for deliveryTag = {}", deliveryTag);

        } catch (Exception e) {
            log.error("❌ Error processing student: {}", e.getMessage(), e);
            try {
                log.warn("🔄 Sending NACK and requeuing message with deliveryTag = {}", deliveryTag);
                channel.basicNack(deliveryTag, false, true); // Requeue
                log.info("✅ NACK sent successfully");
            } catch (Exception ex) {
                log.error("❌ Failed to send NACK: {}", ex.getMessage(), ex);
            }
        }
    }

}

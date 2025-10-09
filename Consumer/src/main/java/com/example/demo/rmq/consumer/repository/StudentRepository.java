package com.example.demo.rmq.consumer.repository;

import com.example.demo.rmq.consumer.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

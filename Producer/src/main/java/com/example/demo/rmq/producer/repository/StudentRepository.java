package com.example.demo.rmq.producer.repository;

import com.example.demo.rmq.producer.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
}

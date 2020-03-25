package com.ip2.myapp.backend.repository;

import com.ip2.myapp.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q " +
            "where lower(q.difficulty) like lower(concat('%', :searchTerm, '%'))" ) //
    List<Question> search(@Param("searchTerm") String searchTerm); //
}



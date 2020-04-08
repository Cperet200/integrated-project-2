package com.ip2.myapp.backend.repository;

import com.ip2.myapp.backend.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query("select r from Result r " +
            "where lower(r.score) like lower(concat('%', :searchTerm, '%'))" ) //
    List<Result> search(@Param("searchTerm") String searchTerm); //

}

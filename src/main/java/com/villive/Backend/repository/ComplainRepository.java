package com.villive.Backend.repository;

import com.villive.Backend.domain.Complain;
import com.villive.Backend.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long> {

    List<Complain> findAllByOrderByCreatedDateDesc();
}

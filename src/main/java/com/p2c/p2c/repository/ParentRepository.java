package com.p2c.p2c.repository;

import com.p2c.p2c.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long>
{
    @Query("SELECT p from Parent p where p.username = :username and p.isActive = 1")
    Optional<Parent> findByUsername(String username);

}

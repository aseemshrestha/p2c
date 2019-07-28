package com.p2c.p2c.repository;

import com.p2c.p2c.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>
{
}

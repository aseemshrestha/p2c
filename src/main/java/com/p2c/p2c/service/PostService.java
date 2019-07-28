package com.p2c.p2c.service;

import com.p2c.p2c.model.Post;
import com.p2c.p2c.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService
{
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository)
    {
        this.postRepository = postRepository;
    }

    public void savePost(Post post)
    {
        postRepository.save(post);
    }
}

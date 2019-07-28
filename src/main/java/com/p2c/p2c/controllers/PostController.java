package com.p2c.p2c.controllers;

import com.p2c.p2c.model.Album;
import com.p2c.p2c.model.Post;
import com.p2c.p2c.service.PostService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping( "/api" )
public class PostController
{
    private final PostService postService;

    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @PostMapping( "post/addNew" )
    public ResponseEntity<Post> postNew(@RequestBody @Valid Post post, HttpServletRequest request)
    {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String ipAdd = request.getHeader("X-FORWARDED-FOR");
        post.setCreated(new Date());
        post.setIp(ipAdd);
        post.setLastUpdated(new Date());
        post.setLastUpdatedBy(request.getUserPrincipal().getName());
        post.setBrowser(userAgent.getBrowser() + "-" + userAgent.getOperatingSystem());
        List<Album> albums = post.getAlbums();
        albums.forEach(album -> {
            album.setCreated(new Date());
            album.setLastUpdated(new Date());
            album.setPost(post);
            if (album.getMedia() != null)
                album.getMedia().forEach(media -> media.setAlbum(album));
        });

        post.setAlbums(post.getAlbums());
        this.postService.savePost(post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);

    }
}

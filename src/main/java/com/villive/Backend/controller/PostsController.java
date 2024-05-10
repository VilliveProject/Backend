package com.villive.Backend.controller;

import com.villive.Backend.domain.Posts;
import com.villive.Backend.dto.PostsRequestDto;
import com.villive.Backend.dto.PostsResponseDto;
import com.villive.Backend.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;


    /*
    @PostMapping("/add")
    public String create(@RequestBody PostsRequestDto requestDto){

        Long posts = postsService.save(requestDto);

        return "success";
    }

     */

    /*
    @PostMapping("/add")
    public Long save(@RequestBody PostsRequestDto requestdto) {
        return postsService.save(requestdto);
    }
    */




    @PostMapping("/add")
    public ResponseEntity<PostsResponseDto> createPosts(@RequestBody PostsRequestDto postsRequestDto, HttpServletRequest request){
        PostsResponseDto responseDto = postsService.createPosts(postsRequestDto, request);
        return ResponseEntity.ok(responseDto);
    }
}

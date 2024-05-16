package com.villive.Backend.service;

import com.villive.Backend.domain.Comment;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.Posts;
import com.villive.Backend.dto.CommentRequestDto;
import com.villive.Backend.dto.CommentResponseDto;
import com.villive.Backend.dto.MsgResponseDto;
import com.villive.Backend.repository.CommentRepository;
import com.villive.Backend.repository.MemberRepository;
import com.villive.Backend.repository.PostsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostsRepository postsRepository;
    private final CommentRepository commentRepository;
    private final MemberService memberService;

    // 댓글 등록
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long postsId, Member member){

        Posts posts = postsRepository.findById(postsId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Comment comment = new Comment(commentRequestDto, posts, member);
        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(saveComment);
    }
    
    // 댓글 삭제
    public MsgResponseDto deleteComment(Long postsId, Long commentId, Member member){


        Posts posts = postsRepository.findById(postsId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Comment comment = memberService.findByCommentAndMember(commentId, member);

        commentRepository.delete(comment);

        return new MsgResponseDto("댓글을 삭제했습니다.", HttpStatus.OK.value());
        
    }


}

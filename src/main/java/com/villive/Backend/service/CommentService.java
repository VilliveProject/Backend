package com.villive.Backend.service;

import com.villive.Backend.domain.Comment;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
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
    public MsgResponseDto deleteComment(Long postsId, Long commentId, Member member) {
        // 게시글 존재 여부 확인
        Posts posts = postsRepository.findById(postsId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        // 댓글 존재 여부 및 권한 확인
        Comment comment = findCommentByIdAndCheckPermission(commentId, member);

        // 댓글 삭제
        commentRepository.delete(comment);

        return new MsgResponseDto("댓글을 삭제했습니다.", HttpStatus.OK.value());
    }

    private Comment findCommentByIdAndCheckPermission(Long commentId, Member member) {
        // 관리자인 경우 모든 댓글을 삭제할 수 있음
        if (member.getRole().equals(MemberRole.ADMIN)) {
            return commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
            );
        }
        // 사용자인 경우 자신이 작성한 댓글만 삭제할 수 있음
        else {
            return commentRepository.findByIdAndMemberId(commentId, member.getId()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 작성한 댓글이 아닙니다.")
            );
        }
    }




}

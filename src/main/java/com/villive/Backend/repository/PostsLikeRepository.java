package com.villive.Backend.repository;

import com.villive.Backend.domain.PostsLike;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostsLikeRepository extends JpaRepository<PostsLike, Long> {

    boolean existsByPostsIdAndMemberId(Long postsId, Long memberId);
    void deleteByPostsIdAndMemberId(Long postsId, Long memberID);
}

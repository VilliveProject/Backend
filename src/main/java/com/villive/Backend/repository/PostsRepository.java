package com.villive.Backend.repository;

import com.villive.Backend.domain.PostCategory;
import com.villive.Backend.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByOrderByCreatedDateDesc();

    // 게시글 id와 사용자 id 일치 여부를 비교하기 위함
    Optional<Posts> findByIdAndMemberId(Long id, Long memberId);

    List<Posts> findByCategoryOrderByCreatedDateDesc(PostCategory category);

}

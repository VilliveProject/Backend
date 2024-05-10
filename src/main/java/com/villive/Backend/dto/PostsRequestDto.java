package com.villive.Backend.dto;

import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.PostCategory;
import com.villive.Backend.domain.Posts;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@ToString
public class PostsRequestDto {

    private String title;
    private String contents;
    private Boolean isAnonymous;
    private PostCategory category;

    @Builder
    public PostsRequestDto(String title, String contents, Boolean isAnonymous, PostCategory category){
        this.title = title;
        this.contents = contents;
        this.isAnonymous = isAnonymous;
        this.category = category;

    }

    /* DTO -> Entity */
    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .contents(contents)
                .isAnonymous(isAnonymous)
                .category(category)
                .build();

    }

}

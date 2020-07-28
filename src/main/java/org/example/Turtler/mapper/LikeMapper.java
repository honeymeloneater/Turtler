package org.example.Turtler.mapper;

import org.example.Turtler.dto.LikeResponse;
import org.example.Turtler.dto.PostResponse;
import org.example.Turtler.model.Likes;
import org.example.Turtler.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class LikeMapper {

    @Mapping(target = "likeId", source = "like.likeId")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "postId", source = "post.postId")
    public abstract LikeResponse mapToDto(Likes like);
}

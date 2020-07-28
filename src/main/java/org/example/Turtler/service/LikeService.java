package org.example.Turtler.service;


import lombok.AllArgsConstructor;
import org.example.Turtler.dto.LikeResponse;
import org.example.Turtler.exception.TurtlerException;
import org.example.Turtler.mapper.LikeMapper;
import org.example.Turtler.model.Likes;
import org.example.Turtler.model.Post;
import org.example.Turtler.repository.LikesRepository;
import org.example.Turtler.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class LikeService {
    private LikesRepository likesRepository;
    private PostRepository postRepository;
    private AuthService authService;
    private LikeMapper likeMapper;
    public void like(long id){
        Optional<Post> postOptional = postRepository.findById(id);
        postOptional.orElseThrow(() -> new TurtlerException("Post not found")) ;
        Optional<Likes> like = likesRepository.findByPostAndUser(postOptional.get(),authService.getCurrentUser());
        if(like.isPresent())
        {

            postOptional.get().setVoteCount(postOptional.get().getVoteCount()-1);
            postRepository.save(postOptional.get());
            likesRepository.delete(like.get());
        }
        else
        {
            Likes toLikes = new Likes();
            toLikes.setPost(postOptional.get());
            toLikes.setUser(authService.getCurrentUser());
            postOptional.get().setVoteCount(postOptional.get().getVoteCount()+1);
            postRepository.save(postOptional.get());
            likesRepository.save(toLikes);
        }
    }
    public List<LikeResponse> getLikes(long id){
        Optional<Post> postOptional = postRepository.findById(id);
        postOptional.orElseThrow(() -> new TurtlerException("Post not found")) ;
        return likesRepository.findByPost(postOptional.get()).stream().map(e-> likeMapper.mapToDto(e)).collect(toList());
    }
}

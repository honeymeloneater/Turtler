package org.example.Turtler.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Turtler.dto.PostRequest;
import org.example.Turtler.dto.PostResponse;
import org.example.Turtler.exception.TurtlerException;
import org.example.Turtler.mapper.PostMapper;
import org.example.Turtler.model.Post;
import org.example.Turtler.model.Reply;
import org.example.Turtler.model.User;
import org.example.Turtler.repository.PostRepository;
import org.example.Turtler.repository.ReplyRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final AuthService authService;
    private final PostMapper postMapper;
    public void save(Long id,PostRequest postRequest) {
        Reply reply = new Reply();
        Optional<Post> postOptional = postRepository.findById(id);
        Post post =  postOptional
                .orElseThrow(() -> new TurtlerException("No post " +
                        "Found"));
        reply.setPost(post);
        post = postRepository.findByUser(authService.getCurrentUser()).get(postRepository.findByUser(authService.getCurrentUser()).size()-1);
        reply.setReplyPost(post);
        replyRepository.save(reply);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllReplyes() {
        return replyRepository.findAll()
                .stream()
                .map(e-> postMapper.mapToDto(e.getReplyPost()))
                .collect(toList());
    }

    @Transactional
    public List<PostResponse> getReplyByPost(Long id){
        Optional<Post> postOptional = postRepository.findById(id);
        Post post =  postOptional
                .orElseThrow(() -> new TurtlerException("No post " +
                        "Found"));
        return replyRepository.findByPost(post)
                .stream()
                .map(e -> postMapper.mapToDto(e.getReplyPost()))
                .collect(toList());
    }


}

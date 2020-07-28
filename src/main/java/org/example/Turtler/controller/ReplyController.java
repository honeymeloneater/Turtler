package org.example.Turtler.controller;


import lombok.AllArgsConstructor;
import org.example.Turtler.dto.PostRequest;
import org.example.Turtler.dto.PostResponse;
import org.example.Turtler.service.PostService;
import org.example.Turtler.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/reply")
@AllArgsConstructor
public class ReplyController {
    private final PostService postService;
    private final ReplyService replyService;

    @PostMapping("{id}")
    public ResponseEntity<Void> createReply(@PathVariable Long id, @RequestBody PostRequest postRequest){
        postService.save(postRequest);
        replyService.save(id,postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllReplyes(){
        return status(HttpStatus.OK).body(replyService.getAllReplyes());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<PostResponse>> getReplyByPost(@PathVariable Long id){
        return status(HttpStatus.OK).body(replyService.getReplyByPost(id));
    }
}

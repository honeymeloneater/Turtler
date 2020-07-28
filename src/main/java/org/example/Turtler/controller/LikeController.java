package org.example.Turtler.controller;


import lombok.AllArgsConstructor;
import org.example.Turtler.dto.LikeResponse;
import org.example.Turtler.model.Likes;
import org.example.Turtler.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/like")
@AllArgsConstructor

public class LikeController {

    LikeService likeService;

    @PostMapping("{id}")
    public ResponseEntity<Void> postLike(@PathVariable long id){
        likeService.like(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<LikeResponse>> getLikes(@PathVariable long id){
        return status(HttpStatus.OK).body(likeService.getLikes(id));
    }
}

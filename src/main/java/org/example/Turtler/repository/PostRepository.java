package org.example.Turtler.repository;


import org.example.Turtler.model.Post;
import org.example.Turtler.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
}
package org.example.Turtler.repository;

import org.example.Turtler.model.Post;
import org.example.Turtler.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByPost(Post post);
}

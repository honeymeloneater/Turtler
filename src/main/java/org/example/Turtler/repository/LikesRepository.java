package org.example.Turtler.repository;

import org.example.Turtler.model.Likes;
import org.example.Turtler.model.Post;
import org.example.Turtler.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {

    Optional<Likes> findByPostAndUser(Post post, User user);
    List<Likes> findByPost(Post post);

}

package com.postgres.demopg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.postgres.demopg.models.TweetComment;

@Repository
public interface TweetCommentRepository extends JpaRepository<TweetComment, Long> {

    List<TweetComment> findByTweet_IdOrderByCreatedAtAsc(Long tweetId);

    void deleteByTweet_Id(Long tweetId);
}
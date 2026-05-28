package com.postgres.demopg.mapper;

import com.postgres.demopg.models.Tweet;
import com.postgres.demopg.payload.response.TweetResponseDTO;

/**
 * Strategy for mapping Tweet entities into API response DTOs.
 */
public interface TweetMapperStrategy {
    TweetResponseDTO toResponse(Tweet tweet);
}

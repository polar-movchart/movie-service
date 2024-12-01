package com.polar_moviechart.movieservice.domain.service;

import com.polar_moviechart.movieservice.domain.MovieRatingTestConfig;
import com.polar_moviechart.movieservice.controller.secureapi.UpdateRatingRequest;
import com.polar_moviechart.movieservice.domain.entity.MovieRating;
import com.polar_moviechart.movieservice.domain.service.movie.MovieCommandService;
import com.polar_moviechart.movieservice.domain.service.movie.MovieRatingQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieCommandServiceTest extends MovieRatingTestConfig {

    @Autowired
    private MovieRatingQueryService movieRatingQueryService;
    @Autowired
    private MovieCommandService movieCommandService;

    private final List<Integer> movieCodes = List.of(1);
    private final LocalDate releaseDate = LocalDate.of(2004, 1, 1);
    private final List<Double> ratingValues = List.of(1.0);
    private final List<Long> userIds = List.of(1L);

    @BeforeEach
    void setUp() {
        initMovies(movieCodes, releaseDate);
        initRating(ratingValues, userIds);
    }

    @DisplayName("기존 평점이 없을 때 영화 평점을 매길 수 있다.")
    @Test
    void updateRatingTest_whenRatingDoesNotExists() {
        // given
        Long userId = 2L;
        int movieCode = movieCodes.get(0);
        double ratingValue = 5.5;
        UpdateRatingRequest updateRatingRequest = new UpdateRatingRequest(ratingValue);

        // when
        movieCommandService.updateRating(movieCode, userId, updateRatingRequest);
        // then
        Optional<MovieRating> savedRating = movieRatingRepository.findByCodeAndUserId(movieCode, userId);
        assertTrue(savedRating.isPresent());
        assertEquals(ratingValue, savedRating.get().getRating());
    }

    @DisplayName("기존 평점이 있을 때 새로운 평점을 매기면 기존의 평점이 업데이트 된다.")
    @Test
    void updateRatingTest_whenRatingExists() {
        // given
        Long userId = userIds.get(0);

        MovieRating existingMovieRating = movieRatingRepository
                .findByCodeAndUserId(movieCodes.get(0), userId).get();
        Integer existingMovieCode = existingMovieRating.getCode();

        double newRatingValue = 2.0;
        UpdateRatingRequest updateRatingRequest = new UpdateRatingRequest(newRatingValue);
        // when
        movieCommandService.updateRating(existingMovieCode, userId, updateRatingRequest);
        MovieRating updatedMovieRating = movieRatingRepository
                .findByCodeAndUserId(existingMovieCode, userId).get();
        // then
        assertEquals(existingMovieRating.getId(), updatedMovieRating.getId());
        assertEquals(newRatingValue, updatedMovieRating.getRating());
    }
}

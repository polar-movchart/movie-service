package com.polar_moviechart.movieservice.domain;

import com.polar_moviechart.movieservice.domain.entity.*;
import com.polar_moviechart.movieservice.domain.repository.LeadActorRepository;
import com.polar_moviechart.movieservice.domain.repository.MovieLeadActorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;

public class LeadactorTestConfig extends MovieTestConfig {

    @Autowired private LeadActorRepository leadActorRepository;
    @Autowired private MovieLeadActorRepository movieLeadActorRepository;

    private static Integer leadActorCnt;

    protected static void initLeadactorCnt(Integer leadactorCnt) {
        leadActorCnt = leadactorCnt;
    }

    @BeforeAll
    void setUpLeadactors() {
        for (Movie movie : getMovies()) {
            int movieCode = movie.getCode();

            for (int leadactorNumber = 1; leadactorNumber <= leadActorCnt; leadactorNumber++) {
                Leadactor leadactor = leadActorRepository.save(Leadactor.builder()
                        .name("주연" + leadactorNumber)
                        .code(movieCode)
                        .build());

                movieLeadActorRepository.save(
                        MovieLeadactor.builder()
                                .movie(movie)
                                .leadactor(leadactor)
                                .build());
            }
        }
    }
}

package com.dailybuffer.springai.model;

import java.util.List;

public class ActorFilms {
    String actor;
    List<String> movies;

    public ActorFilms(String actor, List<String> movies) {
        this.actor = actor;
        this.movies = movies;
    }
    public String getActor() {
        return actor;
    }
    public List<String> getMovies() {
        return movies;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    public void setMovies(List<String> movies) {
        this.movies = movies;
    }
}


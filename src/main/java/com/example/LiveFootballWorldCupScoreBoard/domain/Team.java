package com.example.LiveFootballWorldCupScoreBoard.domain;

import javax.management.StandardEmitterMBean;

public record Team(String name) {
    public Team(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name.length() < 2 || name.length() > 50) {
            throw new IllegalArgumentException("Team name cannot be null, and must be between 2 and 50 signs");
        }
    }

}

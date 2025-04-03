package com.example.LiveFootballWorldCupScoreBoard.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Game {
    private UUID gameId;
    private Team homeTeam;
    private Team awayTeam;
    private int homeScore;
    private int awayScore;
    //Maybe use instant
    private LocalDateTime startTime;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameId = UUID.randomUUID();
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = LocalDateTime.now();
    }

    public void updateScore(int updatedHomeScore, int updatedAwayScore) {
        if (updatedHomeScore < homeScore || updatedAwayScore < awayScore) {
            throw new IllegalArgumentException("Scores cannot decrease");
        }
        this.homeScore = updatedHomeScore;
        this.awayScore = updatedAwayScore;
    }
}

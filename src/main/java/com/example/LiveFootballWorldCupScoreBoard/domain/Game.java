package com.example.LiveFootballWorldCupScoreBoard.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

public class Game {
    private final UUID gameId;
    private final Team homeTeam;
    private final Team awayTeam;
    private int homeScore;
    private int awayScore;
    //Maybe use instant
    private final LocalDateTime startTime;

  public   UUID getGameId() {
        return gameId;
    }

   public Team getAwayTeam() {
        return awayTeam;
    }

  public   int getHomeScore() {
        return homeScore;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", startTime=" + startTime +
                '}';
    }

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

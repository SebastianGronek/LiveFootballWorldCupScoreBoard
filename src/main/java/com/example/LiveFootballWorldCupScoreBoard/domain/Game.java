package com.example.LiveFootballWorldCupScoreBoard.domain;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public record Game(
        UUID gameId,
        Team homeTeam,
        Team awayTeam,
        AtomicInteger homeScore,
        AtomicInteger awayScore,
        AtomicInteger totalScore,
        Instant startTime) {

    public Game(Team homeTeam, Team awayTeam) {
        this(UUID.randomUUID(), homeTeam, awayTeam, new AtomicInteger(0), new AtomicInteger(0), new AtomicInteger(0), Instant.now());
    }

    public void updateScore(int updatedHomeScore, int updatedAwayScore) {
        if (updatedHomeScore < 0 || updatedAwayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        int updatedTotalScore = updatedAwayScore + updatedHomeScore;
        this.homeScore.set(updatedHomeScore);
        this.awayScore.set(updatedAwayScore);
        this.totalScore.set(updatedTotalScore);
    }
}

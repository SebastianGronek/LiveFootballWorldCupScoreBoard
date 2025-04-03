package com.example.LiveFootballWorldCupScoreBoard.application;

import com.example.LiveFootballWorldCupScoreBoard.domain.Team;

import java.util.UUID;

public interface GameService {
    UUID startGame(Team homeTeam, Team awayTeam);

    void updateScore(UUID gameId, int homeScore, int awayScore);

    void endGame(UUID gameId);
}

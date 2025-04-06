package com.example.LiveFootballWorldCupScoreBoard.application;

import com.example.LiveFootballWorldCupScoreBoard.domain.Game;
import com.example.LiveFootballWorldCupScoreBoard.domain.Team;

import java.util.List;
import java.util.UUID;

public interface GameService {
    UUID startGame(Team homeTeam, Team awayTeam);

    void updateScore(UUID gameId, int homeScore, int awayScore);

    void endGame(UUID gameId);

    List<Game> getScoreBoard();

    Game findGameById(UUID gameId);

    void clearBoard();
}

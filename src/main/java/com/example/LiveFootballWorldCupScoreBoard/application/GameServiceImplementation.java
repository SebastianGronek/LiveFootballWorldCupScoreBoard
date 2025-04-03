package com.example.LiveFootballWorldCupScoreBoard.application;

import com.example.LiveFootballWorldCupScoreBoard.domain.Game;
import com.example.LiveFootballWorldCupScoreBoard.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class GameServiceImplementation implements GameService {
    private List<Game> scoreBoard = new ArrayList<>();

    public UUID startGame(Team homeTeam, Team awayTeam) {
        Game game = new Game(homeTeam, awayTeam);
        scoreBoard.add(game);
        return game.getGameId();
    }

    public void updateScore(UUID gameId, int homeScore, int awayScore) {
        findGameById(gameId).updateScore(homeScore, awayScore);
    }

    public void endGame(UUID gameId) {
        Game game = findGameById(gameId);
        scoreBoard.remove(game);
    }

    public List<Game> getScoreBoard() {
        return scoreBoard.stream().sorted((game1, game2)->game1.getStartTime().compareTo(game2.getStartTime())).toList();
    }

    private Game findGameById(UUID gameId) {
        return scoreBoard.stream()
                .filter(game -> game.getGameId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }
}

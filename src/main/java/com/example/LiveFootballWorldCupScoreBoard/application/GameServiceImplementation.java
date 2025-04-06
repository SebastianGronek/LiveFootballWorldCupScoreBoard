package com.example.LiveFootballWorldCupScoreBoard.application;

import com.example.LiveFootballWorldCupScoreBoard.domain.Game;
import com.example.LiveFootballWorldCupScoreBoard.domain.Team;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Getter
class GameServiceImplementation implements GameService {
    private List<Game> scoreBoard = new ArrayList<>();
    private final Comparator<Game> gameComparatorByScore = Comparator.comparing(g -> g.totalScore().get(), Integer::compare);
    private final Comparator<Game> gameComparatorByTimeDesc = Comparator.comparing(Game::startTime).reversed();
    private final Comparator<Game> gameComparatorByScoreAndTime = gameComparatorByScore.reversed()
            .thenComparing(gameComparatorByTimeDesc);

    public UUID startGame(Team homeTeam, Team awayTeam) {
        Game game = new Game(homeTeam, awayTeam);
        scoreBoard.add(game);
        return game.gameId();
    }

    public void updateScore(UUID gameId, int homeScore, int awayScore) {
        findGameById(gameId).updateScore(homeScore, awayScore);
    }

    public void endGame(UUID gameId) {
        Game game = findGameById(gameId);
        scoreBoard.remove(game);
    }

    public List<Game> getScoreBoard() {
        return scoreBoard.stream().sorted(gameComparatorByScoreAndTime)
                .toList();
    }

    public Game findGameById(UUID gameId) {
        return scoreBoard.stream()
                .filter(game -> game.gameId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }

    public void clearBoard() {
        scoreBoard.clear();
    }
}

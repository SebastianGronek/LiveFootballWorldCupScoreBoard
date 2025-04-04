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
        return scoreBoard.stream().sorted(Comparator.comparing((game1, game2)-> {
                            if (game1.totalScore().get() == game2.totalScore().get()) {
                                return 0;
                            }
                            return game1.totalScore().get() > game2.totalScore().get() ? -1 : 1;
                        })
                        .reversed()
                        .thenComparing(Game::startTime)
                        .reversed())
                .toList();
    }

    public Game findGameById(UUID gameId) {
        return scoreBoard.stream()
                .filter(game -> game.gameId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }
}

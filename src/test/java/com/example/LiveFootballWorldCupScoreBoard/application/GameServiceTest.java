package com.example.LiveFootballWorldCupScoreBoard.application;

import com.example.LiveFootballWorldCupScoreBoard.domain.Game;
import com.example.LiveFootballWorldCupScoreBoard.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest {
    private GameService gameService = new GameServiceImplementation();

    @BeforeEach
    public void cleanBoard() {
//        gameService.getScoreBoard().clear();
    }

    @BeforeEach
    public void prepareInitialScoreBoard() {
        gameService.startGame(new Team("Team A"), new Team("Team B"));
        gameService.startGame(new Team("Team C"), new Team("Team D"));
        gameService.startGame(new Team("Team E"), new Team("Team F"));
        gameService.startGame(new Team("Team G"), new Team("Team H"));
        gameService.startGame(new Team("Team I"), new Team("Team J"));
    }

    @Test
    void shouldGetScoreBoard() {
        //given
        List<Game> currentScoreBoard = gameService.getScoreBoard();
        UUID game1Id = currentScoreBoard.get(0).getGameId();
        UUID game2Id = currentScoreBoard.get(1).getGameId();
        UUID game3Id = currentScoreBoard.get(2).getGameId();
        UUID game4Id = currentScoreBoard.get(3).getGameId();
        UUID game5Id = currentScoreBoard.get(4).getGameId();
        //when
        gameService.updateScore(game3Id, 1, 2);
        gameService.updateScore(game2Id, 3, 0);
        List<Game> scoreBoardResult = gameService.getScoreBoard();
        //then
        assertThat(scoreBoardResult).hasSize(5);
        assertThat(scoreBoardResult).containsExactly(
                gameService.findGameById(game2Id),
                gameService.findGameById(game3Id),
                gameService.findGameById(game4Id),
                gameService.findGameById(game5Id),
                gameService.findGameById(game1Id)
        );
    }

    @Test
    void shouldUpdateScoreBoard() {
        //given
        Game game = gameService.getScoreBoard().get(3);
        UUID gameId = game.getGameId();
        //when
        gameService.updateScore(gameId, 2, 3);
        //then
        assertThat(gameService.findGameById(gameId).getHomeScore()).isEqualTo(2);
    }
}
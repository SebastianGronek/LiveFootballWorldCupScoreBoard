package com.example.LiveFootballWorldCupScoreBoard.application;

import com.example.LiveFootballWorldCupScoreBoard.domain.Game;
import com.example.LiveFootballWorldCupScoreBoard.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class GameServiceTest {
    private GameService gameService = new GameServiceImplementation();

    @BeforeEach
    public void cleanBoard() {
        gameService.getScoreBoard().clear();
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

        //when
        List<Game> scoreBoardResult = gameService.getScoreBoard();
        //then
        assertThat(scoreBoardResult).hasSize(5);
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
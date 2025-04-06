package com.example.LiveFootballWorldCupScoreBoard.application;

import com.example.LiveFootballWorldCupScoreBoard.domain.Game;
import com.example.LiveFootballWorldCupScoreBoard.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameServiceTest {
    private GameService gameService = new GameServiceImplementation();
    List<Game> currentScoreBoard = gameService.getScoreBoard();

    @BeforeEach
    public void cleanBoard() {
        gameService.clearBoard();
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
    void shouldGetScoreBoardSorted() {
        //given
        List<Game> currentScoreBoard = gameService.getScoreBoard();
        UUID game1Id = currentScoreBoard.get(4).gameId();
        UUID game2Id = currentScoreBoard.get(3).gameId();
        UUID game3Id = currentScoreBoard.get(2).gameId();
        UUID game4Id = currentScoreBoard.get(1).gameId();
        UUID game5Id = currentScoreBoard.get(0).gameId();
        //when
        gameService.updateScore(game2Id, 3, 0);
        gameService.updateScore(game3Id, 1, 2);
        List<Game> scoreBoardResult = gameService.getScoreBoard();
        //then
        assertThat(scoreBoardResult).hasSize(5);
        assertThat(scoreBoardResult).containsExactly(
                gameService.findGameById(game3Id),
                gameService.findGameById(game2Id),
                gameService.findGameById(game5Id),
                gameService.findGameById(game4Id),
                gameService.findGameById(game1Id)
        );
        //and then
        gameService.updateScore(game3Id, 1, 1);
        assertThat(scoreBoardResult).containsExactly(
                gameService.findGameById(game2Id),
                gameService.findGameById(game3Id),
                gameService.findGameById(game5Id),
                gameService.findGameById(game4Id),
                gameService.findGameById(game1Id)
        );
    }

    @Test
    void shouldUpdateScoreBoard() {
        //given
        Game game = gameService.getScoreBoard().get(3);
        UUID gameId = game.gameId();
        //when
        gameService.updateScore(gameId, 2, 3);
        //then
        assertThat(gameService.findGameById(gameId).homeScore().getPlain()).isEqualTo(2);
    }

    @Test
    void shouldRemoveMatchFromScoreBoard() {
        //given
        List<Game> currentScoreBoard = gameService.getScoreBoard();
        UUID game1Id = currentScoreBoard.get(4).gameId();
        //when
        gameService.endGame(game1Id);
        List<Game> resultScoreBoard = gameService.getScoreBoard();
        //then
        assertThat(resultScoreBoard).hasSize(4);
    }

    @Test
    void shouldThrowExceptionWhenGameNotFound() {
        //given
        UUID gameId = UUID.randomUUID();
        //when and then
        assertThatThrownBy(() -> gameService.findGameById(gameId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Game not found");
    }

    @Test
    void shouldThrowExceptionWhenScoreUpdatedToValueBelowZero() {
        //given
        List<Game> currentScoreBoard = gameService.getScoreBoard();
        UUID gameId = currentScoreBoard.get(3).gameId();
        gameService.updateScore(gameId, 0, -1);
        // then
        assertThatThrownBy(() -> gameService.findGameById(gameId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Score cannot be negative");
    }
}
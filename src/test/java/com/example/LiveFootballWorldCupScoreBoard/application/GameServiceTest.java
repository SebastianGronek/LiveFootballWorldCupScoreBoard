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

    private UUID firstGameID;
    private UUID secondGameID;
    private UUID thirdGameID;
    private UUID forthGameID;
    private UUID fifthGameID;

    @BeforeEach
    public void cleanBoard() {
        gameService.clearBoard();
    }

    @BeforeEach
    public void prepareInitialScoreBoard() {
        firstGameID = gameService.startGame(new Team("Team A"), new Team("Team B"));
        secondGameID = gameService.startGame(new Team("Team C"), new Team("Team D"));
        thirdGameID = gameService.startGame(new Team("Team E"), new Team("Team F"));
        forthGameID = gameService.startGame(new Team("Team G"), new Team("Team H"));
        fifthGameID = gameService.startGame(new Team("Team I"), new Team("Team J"));
    }

    @Test
    void shouldGetScoreBoardSorted() {
        //given
        List<Game> currentScoreBoard = gameService.getScoreBoard();

        //when
        gameService.updateScore(secondGameID, 3, 0);
        gameService.updateScore(thirdGameID, 1, 2);
        List<Game> scoreBoardResult = gameService.getScoreBoard();
        //then
        assertThat(scoreBoardResult).hasSize(5);
        assertThat(scoreBoardResult).containsExactly(
                gameService.findGameById(thirdGameID),
                gameService.findGameById(secondGameID),
                gameService.findGameById(fifthGameID),
                gameService.findGameById(forthGameID),
                gameService.findGameById(firstGameID)
        );
        //and then
        gameService.updateScore(thirdGameID, 1, 1);
        scoreBoardResult = gameService.getScoreBoard();
        assertThat(scoreBoardResult).containsExactly(
                gameService.findGameById(secondGameID),
                gameService.findGameById(thirdGameID),
                gameService.findGameById(fifthGameID),
                gameService.findGameById(forthGameID),
                gameService.findGameById(firstGameID)
        );
    }

    @Test
    void shouldUpdateScoreBoard() {
        //given
        //when
        gameService.updateScore(forthGameID, 2, 3);
        //then
        assertThat(gameService.findGameById(forthGameID).homeScore().getPlain()).isEqualTo(2);
    }

    @Test
    void shouldRemoveMatchFromScoreBoard() {
        //given
        //when
        gameService.endGame(firstGameID);
        List<Game> resultScoreBoard = gameService.getScoreBoard();
        //then
        assertThat(resultScoreBoard).hasSize(4);
    }

    @Test
    void shouldThrowExceptionWhenGameNotFound() {
        //given
        UUID randomId = UUID.randomUUID();
        //when and then
        assertThatThrownBy(() -> gameService.findGameById(randomId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Game not found");
    }

    @Test
    void shouldThrowExceptionWhenScoreUpdatedToValueBelowZero() {
        //given
        //when and then
        assertThatThrownBy(() -> gameService.updateScore(fifthGameID, 0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Scores cannot be negative");
    }
}
package com.example.LiveFootballWorldCupScoreBoard.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TeamTest {
    @ParameterizedTest()
    @ValueSource(strings = {"", " ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt"})
    void shouldThrowExceptionWhenTeamNameIsInvalid(String teamName) {
        //given
        //when and then
        assertThatThrownBy(() -> new Team(teamName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Team name cannot be null, and must be between 2 and 50 signs");
    }
}
package com.github.notjamesm.DotaReplayBackup;

import com.github.notjamesm.DotaReplayBackup.clients.DotaApiClient;
import com.github.notjamesm.DotaReplayBackup.domain.Match;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import com.github.notjamesm.DotaReplayBackup.domain.PlayerId;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReplayServiceTest implements WithAssertions {

    private final DotaApiClient dotaApiClient = mock(DotaApiClient.class);
    private final List<PlayerId> playerIds = List.of(PlayerId.of(123L));
    private final Logger logger = mock(Logger.class);

    ReplayService replayService = new ReplayService(dotaApiClient, logger);

    @Test
    void shouldGetAllMatchIds() {
        List<Match> matches = List.of(aMatchWithMatchId(MatchId.of(987L)), aMatchWithMatchId(MatchId.of(951L)));

        when(dotaApiClient.getMatchesForPlayer(PlayerId.of(123L))).thenReturn(matches);
        List<MatchId> matchIds = replayService.getMatchIds(playerIds);

        assertThat(matchIds).containsExactly(MatchId.of(987L), MatchId.of(951L));
    }

    @Test
    void shouldGetAllMatchIdsWithNoDuplicates() {
        replayService = new ReplayService(dotaApiClient, logger);
        List<Match> player1Matches = List.of(aMatchWithMatchId(MatchId.of(987L)), aMatchWithMatchId(MatchId.of(951L)));
        List<Match> player2Matches = List.of(aMatchWithMatchId(MatchId.of(987L)), aMatchWithMatchId(MatchId.of(147L)));

        when(dotaApiClient.getMatchesForPlayer(PlayerId.of(123L))).thenReturn(player1Matches);
        when(dotaApiClient.getMatchesForPlayer(PlayerId.of(159L))).thenReturn(player2Matches);
        List<MatchId> matchIds = replayService.getMatchIds(List.of(PlayerId.of(123L), PlayerId.of(159L)));

        assertThat(matchIds).containsExactly(MatchId.of(987L), MatchId.of(951L), MatchId.of(147L));
    }

    private Match aMatchWithMatchId(MatchId matchId) {
        return new Match(matchId,
                null,
                true,
                null,
                0,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                null,
                null,
                0);
    }
}
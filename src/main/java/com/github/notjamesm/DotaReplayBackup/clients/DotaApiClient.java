package com.github.notjamesm.DotaReplayBackup.clients;

import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import com.github.notjamesm.DotaReplayBackup.domain.Match;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import com.github.notjamesm.DotaReplayBackup.domain.PlayerId;

import java.util.List;

public interface DotaApiClient {
    List<DotaReplay> getReplayDetails(List<MatchId> aMatchId);

    List<Match> getMatchesForPlayer(PlayerId playerId);
}

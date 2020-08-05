package com.github.notjamesm.DotaReplayBackup;

import com.github.notjamesm.DotaReplayBackup.clients.DotaApiClient;
import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import com.github.notjamesm.DotaReplayBackup.domain.Match;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import com.github.notjamesm.DotaReplayBackup.domain.PlayerId;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class ReplayService {

    private final DotaApiClient dotaApiClient;

    private static final List<PlayerId> PLAYER_IDS = List.of(PlayerId.of(32697432L), PlayerId.of(78764445L));


    public ReplayService(DotaApiClient dotaApiClient) {
        this.dotaApiClient = dotaApiClient;
    }

    public List<MatchId> getMatchIds() {
        return getMatchesFor(PLAYER_IDS);
    }

    public List<DotaReplay> getReplayDetails(List<MatchId> matchIds) {
        return dotaApiClient.getReplayDetails(matchIds);
    }

    private List<MatchId> getMatchesFor(List<PlayerId> playerIds) {
        return playerIds.stream().map(dotaApiClient::getMatchesForPlayer)
                .flatMap(matches -> matches.stream().map(Match::getMatch_id))
                .distinct()
                .collect(toList());
    }

}

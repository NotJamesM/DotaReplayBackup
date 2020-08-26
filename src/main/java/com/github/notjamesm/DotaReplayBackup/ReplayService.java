package com.github.notjamesm.DotaReplayBackup;

import com.github.notjamesm.DotaReplayBackup.clients.DotaApiClient;
import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import com.github.notjamesm.DotaReplayBackup.domain.Match;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import com.github.notjamesm.DotaReplayBackup.domain.PlayerId;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class ReplayService {

    private static final String LATEST_MATCH_ID_FILE_NAME = "data/latestMatchId.txt";
    private static final int MATCH_ID_PARTITION_SIZE = 5;
    private final DotaApiClient dotaApiClient;
    private final Logger logger;


    public ReplayService(DotaApiClient dotaApiClient, Logger logger) {
        this.dotaApiClient = dotaApiClient;
        this.logger = logger;
    }

    public List<MatchId> getMatchIds(List<PlayerId> playerIds) {
        return getMatchesFor(playerIds);
    }

    public List<DotaReplay> getReplayDetails(List<MatchId> matchIdsToFind) {
        List<MatchId> sortedMatchIds = matchIdsToFind.stream()
                .map(MatchId::getMatchId)
                .sorted()
                .map(MatchId::of)
                .collect(toUnmodifiableList());

        List<List<MatchId>> partitionedMatchIds = Lists.partition(sortedMatchIds, MATCH_ID_PARTITION_SIZE);

        List<DotaReplay> replays = partitionedMatchIds.stream()
                .map(dotaApiClient::getReplayDetails)
                .flatMap(Collection::stream)
                .collect(toList());

        audit(sortedMatchIds, replays);

        return replays;
    }

    private List<MatchId> getMatchesFor(List<PlayerId> playerIds) {
        long lastMatchId = getLastMatchId();

        return playerIds.stream().map(dotaApiClient::getMatchesForPlayer)
                .flatMap(matches -> matches.stream().map(Match::getMatch_id))
                .distinct()
                .filter(id -> id.getMatchId() > lastMatchId)
                .collect(toList());
    }

    private void audit(List<MatchId> requestedMatchIds, List<DotaReplay> replaysFound) {
        List<MatchId> matchIdsOfReplays = replaysFound.stream()
                .map(DotaReplay::getMatchId)
                .collect(toList());

        logMissingReplays(requestedMatchIds, matchIdsOfReplays);
    }

    private long getLastMatchId() {
        try {
            String fileContent = Files.readString(Paths.get(LATEST_MATCH_ID_FILE_NAME));
            return Long.parseLong(fileContent);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    private void logMissingReplays(List<MatchId> matchIds, List<MatchId> matchIdsOfReplays) {
        List<MatchId> copyOfMatchIds = new ArrayList<>(matchIds);
        copyOfMatchIds.removeAll(matchIdsOfReplays);

        if (!copyOfMatchIds.isEmpty())
            logger.warn(format("Missing replays for these match ids = { %s }", copyOfMatchIds.toString()));
    }

}

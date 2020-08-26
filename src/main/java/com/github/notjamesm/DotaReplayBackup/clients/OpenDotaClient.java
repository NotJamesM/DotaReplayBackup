package com.github.notjamesm.DotaReplayBackup.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.notjamesm.DotaReplayBackup.config.HttpClientConfig;
import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import com.github.notjamesm.DotaReplayBackup.domain.Match;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import com.github.notjamesm.DotaReplayBackup.domain.PlayerId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class OpenDotaClient implements DotaApiClient {

    private final HttpClientConfig config;
    private final HttpClient httpClient;
    private final Logger logger;

    private static final String RECENT_MATCHES = "players/%d/matches?date=7";

    public OpenDotaClient(HttpClientConfig config, HttpClient httpClient, Logger logger) {
        this.config = config;
        this.httpClient = httpClient;
        this.logger = logger;
    }

    @Override
    public List<Match> getMatchesForPlayer(PlayerId playerId) {
        logger.info("Getting matches for Player ID:");
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(constructRecentMatchesUri(playerId)).build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Match[] arraysOfMatch = new ObjectMapper().readValue(response.body(), Match[].class);
            return Arrays.asList(arraysOfMatch);
        } catch (IOException | InterruptedException e) {
            logger.error(format("Error getting matches Player ID: %s", playerId), e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<DotaReplay> getReplayDetails(List<MatchId> matchIds) {
        logger.info(format("Finding replay details for Match IDs: %s", matchIds));
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(constructReplayUri(matchIds)).build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return Arrays.asList(new ObjectMapper().readValue(response.body(), DotaReplay[].class));
        } catch (IOException | InterruptedException e) {
            logger.error(format("Error getting replay details for Match IDs { %s }\n", matchIds), e);
            throw new IllegalStateException(e);
        }
    }

    private URI constructReplayUri(List<MatchId> matchIds) {
        String matchIdQueryParams = matchIds.stream().map(MatchId::asQueryParam).collect(Collectors.joining("&"));
        return URI.create(format("https://%s/replays?%s", config.getDotaApiHost(), matchIdQueryParams));
    }

    private URI constructRecentMatchesUri(PlayerId playerId) {
        return URI.create(format("https://%s/%s", config.getDotaApiHost(), format(RECENT_MATCHES, playerId.getId())));
    }
}
package com.github.notjamesm.DotaReplayBackup.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.notjamesm.DotaReplayBackup.config.HttpClientConfig;
import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import com.github.notjamesm.DotaReplayBackup.domain.Match;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import com.github.notjamesm.DotaReplayBackup.domain.PlayerId;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OpenDotaClient implements DotaApiClient {

    private final HttpClientConfig config;
    private final HttpClient httpClient;

    private static final String RECENT_MATCHES = "players/%d/matches?date=7";

    @Override
    public List<Match> getMatchesForPlayer(PlayerId playerId) {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(constructRecentMatchesUri(playerId)).build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Match[] matchIds = new ObjectMapper().readValue(response.body(), Match[].class);
            return Arrays.asList(matchIds);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<DotaReplay> getReplayDetails(List<MatchId> matchIds) {
        System.out.println("matchIds = " + matchIds);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(constructReplayUri(matchIds)).build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("response = " + response);
            return Arrays.asList(new ObjectMapper().readValue(response.body(), DotaReplay[].class));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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
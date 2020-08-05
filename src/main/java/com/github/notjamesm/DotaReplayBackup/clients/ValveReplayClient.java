package com.github.notjamesm.DotaReplayBackup.clients;

import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ValveReplayClient {

    private final HttpClient httpClient;
    private static int counter = 1;

    public void downlodReplay(DotaReplay dotaReplay) {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(dotaReplay.replayUrl())).build();
        try {
            Files.createDirectories(Paths.get("replays"));
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofFile(Paths.get(constructReplayFilename(dotaReplay))));
            System.out.println("downloaded " + counter + " | Match ID: " + dotaReplay.getMatchId().getMatchId() );
            counter++;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String constructReplayFilename(DotaReplay dotaReplay) {
        return format("replays/%d.dem.bz2", dotaReplay.getMatchId().getMatchId());
    }
}

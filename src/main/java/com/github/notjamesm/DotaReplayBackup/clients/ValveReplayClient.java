package com.github.notjamesm.DotaReplayBackup.clients;

import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ValveReplayClient {

    private static final String LATEST_MATCH_ID_FILE_NAME = "data/latestMatchId.txt";
    private final Logger logger;
    private final HttpClient httpClient;

    private long counter = 1;
    private long total = 0;

    public void downloadReplays(List<DotaReplay> replayDetails) {
        total = replayDetails.size();
        replayDetails.forEach(this::downloadReplay);
    }

    private void downloadReplay(DotaReplay dotaReplay) {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(dotaReplay.replayUrl())).build();
        try {
            logger.info(format("Downloading replay %d of %d", counter, total));
            Files.createDirectories(Paths.get("replays"));
            final HttpResponse<Path> httpResponse = httpClient.send(httpRequest, getFileHandler(dotaReplay));

            if (httpResponse == null)
                throw new IllegalStateException("Http Response was null.");

            updateLatestMatchId(dotaReplay);
            counter++;
        } catch (Exception e) {
            logError(dotaReplay, e);
        }
    }

    private void logError(DotaReplay dotaReplay, Exception e) {
        logger.error("Error downloading: " + dotaReplay, e);
    }

    private HttpResponse.BodyHandler<Path> getFileHandler(DotaReplay dotaReplay) {
        return HttpResponse.BodyHandlers.ofFile(Paths.get(constructReplayFilename(dotaReplay)));
    }

    private void updateLatestMatchId(DotaReplay dotaReplay) {
        try {
            Files.writeString(Path.of(LATEST_MATCH_ID_FILE_NAME), Long.toString(dotaReplay.getMatchId().getMatchId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String constructReplayFilename(DotaReplay dotaReplay) {
        return format("data/replays/%d.dem.bz2", dotaReplay.getMatchId().getMatchId());
    }
}

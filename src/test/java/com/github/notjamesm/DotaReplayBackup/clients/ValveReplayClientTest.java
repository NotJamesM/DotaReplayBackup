package com.github.notjamesm.DotaReplayBackup.clients;

import com.github.notjamesm.DotaReplayBackup.TestLogger;
import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValveReplayClientTest implements WithAssertions {

    @Test
    void shouldWriteLatestDownloadedMatchIdToFile() throws IOException, InterruptedException {
        final List<DotaReplay> replayDetails = List.of(
                new DotaReplay(MatchId.of(123L), 321, 147),
                new DotaReplay(MatchId.of(124L), 321, 147)
        );

        valveReplayClient.downloadReplays(replayDetails);

        when(httpClient.send(any(), ArgumentMatchers.<HttpResponse.BodyHandler<Path>>any()))
                .thenReturn(new MockHttpResponse(Path.of("someBody.html")));

        assertThat(Paths.get("data/latestMatchId.txt")).hasContent("124");
    }

    @Test
    void shouldLogMissingReplayWithMatchId() throws IOException, InterruptedException {
        final List<DotaReplay> replayDetails = List.of(
                new DotaReplay(MatchId.of(123L), 321, 147),
                new DotaReplay(MatchId.of(124L), 321, 147)
        );

        when(httpClient.send(any(), ArgumentMatchers.<HttpResponse.BodyHandler<Path>>any()))
                .thenReturn(new MockHttpResponse(Path.of("someBody")))
                .thenThrow(new RuntimeException());

        valveReplayClient.downloadReplays(replayDetails);

        assertThat(testLogger.getErrorEvents())
                .hasSize(1)
                .containsExactly("Error downloading: DotaReplay(matchId=MatchId(matchId=124), cluster=321, replaySalt=147)");
    }

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(Paths.get("data/latestMatchId.txt"), "0");
    }

    private final TestLogger testLogger = new TestLogger();
    private final HttpClient httpClient = mock(HttpClient.class);

    ValveReplayClient valveReplayClient = new ValveReplayClient(testLogger, httpClient);

    static class MockHttpResponse implements HttpResponse<Path> {

        private final Path body;

        public MockHttpResponse(Path body) {
            this.body = body;
        }

        @Override
        public int statusCode() {
            return 200;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<Path>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public Path body() {
            return body;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return null;
        }

        @Override
        public HttpClient.Version version() {
            return null;
        }
    }

    ;
}
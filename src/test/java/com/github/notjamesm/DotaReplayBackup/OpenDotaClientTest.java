package com.github.notjamesm.DotaReplayBackup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.notjamesm.DotaReplayBackup.clients.OpenDotaClient;
import com.github.notjamesm.DotaReplayBackup.config.HttpClientConfig;
import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.slf4j.Logger;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OpenDotaClientTest implements WithAssertions {

    private final HttpClient httpClient = mock(HttpClient.class);
    private final HttpClientConfig config = mock(HttpClientConfig.class);
    private final Logger logger = mock(Logger.class);

    private final OpenDotaClient underTest = new OpenDotaClient(config, httpClient, logger);

    @Test
    void clientGetReplayDetailsFromMatchId() throws IOException, InterruptedException {

        ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);

        when(httpClient.send(requestCaptor.capture(), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(new MockHttpResponse(REPLAY_RESPONSE_JSON));
        List<DotaReplay> replayDetails = underTest.getReplayDetails(List.of(MatchId.of(123L)));

        assertThat(requestCaptor.getValue().uri().toASCIIString()).isEqualTo("https://something/replays");

        DotaReplay dotaReplay = replayDetails.get(0);
        assertThat(dotaReplay.getCluster()).isEqualTo(321);
        assertThat(dotaReplay.getReplaySalt()).isEqualTo(159);
        assertThat(dotaReplay.getMatchId()).isEqualTo(MatchId.of(12346));
    }

    @Test
    void clientThrowsExceptionIfHttpCallFails() throws IOException, InterruptedException {
        when(httpClient.send(any(), any())).thenThrow(IOException.class);

        assertThatThrownBy(() -> underTest.getReplayDetails(List.of(MatchId.of(123L))))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasCauseExactlyInstanceOf(IOException.class);
    }

    @Test
    void clientThrowsExceptionIfObjectMappingFails() throws IOException, InterruptedException {
        when(httpClient.send(any(), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(new MockHttpResponse(BAD_REPLAY_RESPONSE_JSON));

        assertThatThrownBy(() -> underTest.getReplayDetails(List.of(MatchId.of(123L))))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasCauseInstanceOf(JsonProcessingException.class);
    }

    @BeforeEach
    void setUp() {
        when(config.getDotaApiHost()).thenReturn("something");
    }

    private static final String REPLAY_RESPONSE_JSON = "[{\"match_id\": 12346,\"cluster\": 321,\"replay_salt\": 159}]";
    private static final String BAD_REPLAY_RESPONSE_JSON = "[{\"match_id\": dasa12346,\"cluster\": 321,\"replay_salt\": 159}]";

    static class MockHttpResponse implements HttpResponse<String> {

        private final String body;

        public MockHttpResponse(String body) {
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
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public String body() {
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
}
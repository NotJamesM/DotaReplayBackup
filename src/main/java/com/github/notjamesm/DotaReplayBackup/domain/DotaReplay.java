package com.github.notjamesm.DotaReplayBackup.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static java.lang.String.format;

@Value
public class DotaReplay {
    public static final String REPLAY_URL_FORMAT = "http://replay%d.valve.net/570/%s_%s.dem.bz2";

    @JsonProperty("match_id")
    MatchId matchId;

    @JsonProperty("cluster")
    int cluster;

    @JsonProperty("replay_salt")
    int replaySalt;

    public String replayUrl() {
        return format(REPLAY_URL_FORMAT, cluster, matchId.getMatchId(), replaySalt);
    }
}

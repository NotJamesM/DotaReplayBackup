package com.github.notjamesm.DotaReplayBackup.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;

import static java.lang.String.format;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchId implements Serializable {

    @JsonProperty("match_id")
    long matchId;

    //for jackson?
    public MatchId(long matchId){
        this.matchId = matchId;
    }

    public static MatchId of(long matchId) {
        return new MatchId(matchId);
    }

    public String asQueryParam() {
        return format("match_id=%d", matchId);
    }

}

package com.github.notjamesm.DotaReplayBackup.domain;

import lombok.Value;

@Value
public class PlayerId {

    long id;

    public static PlayerId of(long id) {
        return new PlayerId(id);
    }
}

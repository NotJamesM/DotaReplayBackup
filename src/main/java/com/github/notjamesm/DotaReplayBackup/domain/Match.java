package com.github.notjamesm.DotaReplayBackup.domain;

import lombok.Value;

@Value
public class Match {
      MatchId match_id;
      String player_slot;
      boolean radiant_win;
      String start_time;
      long duration;
      String game_mode;
      String lobby_type;
      String hero_id;
      String version;
      long kills;
      long deaths;
      long assists;
      String skill;
      String leaver_status;
      long party_size;
}

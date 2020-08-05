package com.github.notjamesm.DotaReplayBackup.domain;

import lombok.Value;

@Value
public class Match {
      MatchId match_id;
      String player_slot;
      String radiant_win;
      String start_time;
      String duration;
      String game_mode;
      String lobby_type;
      String hero_id;
      String version;
      String kills;
      String deaths;
      String assists;
      String skill;
      String leaver_status;
      String party_size;
}

package com.github.notjamesm.DotaReplayBackup;

import com.github.notjamesm.DotaReplayBackup.clients.ValveReplayClient;
import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import com.github.notjamesm.DotaReplayBackup.domain.MatchId;
import com.github.notjamesm.DotaReplayBackup.domain.PlayerId;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReplaySchedule {

    private final Logger logger;
    private final ReplayService replayService;
    private final ValveReplayClient valveReplayClient;


    private static final List<PlayerId> PLAYER_IDS = List.of(
            PlayerId.of(32697432L), // James
            PlayerId.of(85743446L), // Angus
            PlayerId.of(141572670L), // Arjun
            PlayerId.of(70587231L), // Zack
            PlayerId.of(90260986L), // Henry
            PlayerId.of(144765668L), // Rob
            PlayerId.of(241887018L), // Jools
            PlayerId.of(428953965L), // DaffyDuck
            PlayerId.of(78764445L) // Jon
    );


    public ReplaySchedule(Logger logger, ReplayService replayService, ValveReplayClient valveReplayClient) {
        this.logger = logger;
        this.replayService = replayService;
        this.valveReplayClient = valveReplayClient;
    }

    //    @Scheduled(cron = "${replay.cron.string}")
    @Scheduled(fixedDelay = 60000, initialDelay = 1000)
    public void job() {
        List<MatchId> matchIds = replayService.getMatchIds(PLAYER_IDS);
        List<DotaReplay> replayDetails = replayService.getReplayDetails(matchIds);

        valveReplayClient.downloadReplays(replayDetails);
        logger.info("!!! DOWNLOAD COMPLETED !!!");
    }
}
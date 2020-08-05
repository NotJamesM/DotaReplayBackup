package com.github.notjamesm.DotaReplayBackup;

import com.github.notjamesm.DotaReplayBackup.clients.DotaApiClient;
import com.github.notjamesm.DotaReplayBackup.clients.ValveReplayClient;
import com.github.notjamesm.DotaReplayBackup.domain.DotaReplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Component
public class ReplaySchedule {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReplayService replayService;
    private final ValveReplayClient valveReplayClient;

    public ReplaySchedule(DotaApiClient dotaApiClient, ReplayService replayService, ValveReplayClient valveReplayClient) {
        this.replayService = replayService;
        this.valveReplayClient = valveReplayClient;
    }

    //    @Scheduled(cron = "${replay.cron.string}")
    @Scheduled(fixedDelay = 60000, initialDelay = 1000)
    public void job() {
        List<DotaReplay> replayDetails = replayService.getReplayDetails(replayService.getMatchIds());
        replayDetails.forEach(valveReplayClient::downlodReplay);
        System.out.println("!DOWNLOAD COMEPLETED!");
    }
}
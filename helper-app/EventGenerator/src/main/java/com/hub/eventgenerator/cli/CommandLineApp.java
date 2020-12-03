package com.hub.eventgenerator.cli;

import com.hub.eventgenerator.service.ApiService;
import com.hub.eventgenerator.service.EventGenerator;
import com.hub.eventgenerator.utility.AudioEventSerializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Class that represent CLI-command tool for interaction.
 */
@CommandLine.Command(
        name = "eventgen",
        mixinStandardHelpOptions = true,
        version = "EventGenerator 1.0",
        description = "Generate audio events for SensitiveField API.")
public class CommandLineApp implements Callable<Integer> {
    public static final Logger logger = LogManager.getLogger(CommandLineApp.class);

    @CommandLine.Option(
            names = {"-d", "--delay"},
            description = "Delay between event generation (in seconds).")
    private int delay;

    @CommandLine.Option(
            names = {"-r", "--range"},
            split = ",",
            arity = "1",
            description = "Sets random delay (in MIN..MAX range) for event generation.")
    private List<Integer> delayBounds;

    @CommandLine.Option(
            names = {"-v", "--verbose"},
            description = "Run in verbose mode.")
    private boolean isVerbose;

    @CommandLine.Option(
            names = {"-p", "--pretty"},
            description = "Prettify JSON-objects output.")
    private boolean isPrettyJson;

    @CommandLine.Option(
            names = {"-e", "--existing"},
            description = "Using only existing audio sensors for event generation.")
    private boolean isUseExistingSensors;

    @Override
    public Integer call() {
        this.clear();
        this.printBanner();
        this.initEventGenerator();
        return 0;
    }

    private void initEventGenerator() {
        if (isVerbose) {
            Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.DEBUG);
        }

        if (!new ApiService().isBackendRun()) {
            logger.error("Can`t connect to backend, connection refused.");
            return;
        }

        if (isPrettyJson) {
            AudioEventSerializer.enablePrettyJson();
        }

        logger.debug("Entering application..");
        logger.debug("Building audio event generator..");
        EventGenerator.Builder eventGenBuilder = EventGenerator.builder();

        if (isUseExistingSensors) {
            logger.debug("Using ONLY existing audio sensors for event generation.");
            eventGenBuilder.withExistingSensors();
        }else {
            logger.debug("Using audio sensors with random ID [0..100].");
        }

        if (delay == 0) {
            if (delayBounds == null) {
                logger.debug("Set RANDOM delay between events in default range.");
                eventGenBuilder.withRandomDelay();
            } else {
                logger.debug("Set RANDOM delay between events in range [" + delayBounds.get(0) + ".." + delayBounds.get(1) + "].");
                eventGenBuilder.withRandomDelay(delayBounds.get(0), delayBounds.get(1));
            }
        } else {
            logger.debug("Set " + delay + "-seconds delay between events.");
            eventGenBuilder.withDelay(delay);
        }
        eventGenBuilder.build().start();
    }

    private void printBanner() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/banner.txt");

        new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}


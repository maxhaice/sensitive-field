package com.hub.eventgenerator;

import com.hub.eventgenerator.cli.CommandLineApp;
import picocli.CommandLine;

/**
 * EventGenerator`s start point.
 */
public class Main {
    public static void main(String[] args) {
        new CommandLine(new CommandLineApp())
                .execute(args);
    }
}

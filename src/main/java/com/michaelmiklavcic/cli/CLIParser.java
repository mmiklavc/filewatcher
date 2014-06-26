package com.michaelmiklavcic.cli;

import org.apache.commons.cli.*;

public class CLIParser {

    private Options options;

    public CLIParser() {
        options = new Options();
    }

    public void addOption(String name, boolean hasArgs, String desc) {
        options.addOption(name, hasArgs, desc);
    }

    public CommandLine parse(String[] args) throws ParseException {
        CommandLineParser parser = new GnuParser();
        return parser.parse(options, args);
    }

}

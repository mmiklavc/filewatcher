package com.michaelmiklavcic.cli;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.apache.commons.cli.*;
import org.junit.Test;

public class CLIParserTest {

    @Test
    public void parses_multiple_options() throws Exception {
        CLIParser parser = new CLIParser();
        parser.addOption("option1", true, "some option with an arg");
        parser.addOption("option2", false, "some option without an arg");
        parser.addOption("option3", true, "another option with an arg");
        CommandLine line = parser.parse(new String[] {
                "-option1",
                "arg1",
                "-option2",
                "-option3",
                "arg3" });
        assertThat(line.getOptionValue("option1"), equalTo("arg1"));
        assertThat(line.hasOption("option2"), equalTo(true));
        assertThat(line.getOptionValue("option3"), equalTo("arg3"));
    }

    @Test(expected = ParseException.class)
    public void throws_parse_exception_if_option_not_recognized() throws Exception {
        new CLIParser().parse(new String[] { "-blah", });
    }

}

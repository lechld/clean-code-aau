package aau.edu.dolechl.cleancode.input;

import org.apache.commons.cli.*;

import java.net.MalformedURLException;
import java.net.URL;

public class CliCrawlParameterFactory implements CrawlParameterFactory {

    private final String[] args;

    public CliCrawlParameterFactory(String[] args) {
        this.args = args;
    }

    @Override
    public CrawlParameter create() {
        Options options = new Options();
        Option urlOption = Option.builder("u")
                .longOpt("url")
                .hasArg(true)
                .type(URL.class)
                .required()
                .build();
        options.addOption(urlOption);
        options.addOption("d", "depth", true, "Depth of crawling");
        options.addOption("l", "language", true, "Target language");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("crawler", options);
            return null;
        }

        URL url;
        try {
            url = new URL(cmd.getOptionValue("url"));
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        int depth;
        try {
            depth = Integer.parseInt(cmd.getOptionValue("depth", "0"));
        } catch (NumberFormatException e) {
            System.err.println("Invalid depth value.");
            return null;
        }
        String language = cmd.getOptionValue("language", "english");

        return new CrawlParameter(url, depth, language);
    }
}

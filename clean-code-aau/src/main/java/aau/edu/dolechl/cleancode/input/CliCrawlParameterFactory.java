package aau.edu.dolechl.cleancode.input;

import org.apache.commons.cli.*;

public class CliCrawlParameterFactory implements CrawlParameterFactory {

    private final String[] args;

    public CliCrawlParameterFactory(String[] args) {
        this.args = args;
    }

    @Override
    public CrawlParameter create() {
        Options options = new Options();
        options.addOption(Option.builder("u").longOpt("url").hasArg(true).required().build());
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

        String url = cmd.getOptionValue("url");
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

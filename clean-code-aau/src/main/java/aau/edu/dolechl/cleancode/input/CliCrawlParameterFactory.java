package aau.edu.dolechl.cleancode.input;

import org.apache.commons.cli.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CliCrawlParameterFactory implements CrawlParameterFactory {

    private final String[] args;

    public CliCrawlParameterFactory(String[] args) {
        this.args = args;
    }

    @Override
    public CrawlParameter create() {
        Options options = new Options();
        Option urlOption = Option.builder("u")
                .longOpt("urls")
                .hasArg(true)
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .required()
                .build();
        options.addOption(urlOption);

        options.addOption("d", "depth", true, "Depth of crawling");

        Option websitesOption = Option.builder("w")
                .longOpt("websites")
                .hasArg(true)
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .build();
        options.addOption(websitesOption);

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

        String[] urlsOptionValues = cmd.getOptionValues("u");
        if (urlsOptionValues == null) {
            System.err.println("Need to specify at least one url.");
            return null;
        }

        List<URL> urls = new ArrayList<>();
        for (String url : urlsOptionValues) {
            try {
                urls.add(new URL(url));
            } catch (MalformedURLException e) {
                System.err.println("Malformed url: " + url);
            }
        }
        if (urls.isEmpty()) {
            System.err.println("Non of the provided urls is valid.");
            return null;
        }

        int depth;
        try {
            depth = Integer.parseInt(cmd.getOptionValue("depth", "0"));
        } catch (NumberFormatException e) {
            System.err.println("Invalid depth value.");
            return null;
        }

        List<String> websites = new ArrayList<>();
        String[] websiteOptionValues = cmd.getOptionValues("w");
        if (websiteOptionValues != null) {
            websites = List.of(websiteOptionValues);
        }

        String language = cmd.getOptionValue("language", "english");

        return new CrawlParameter(urls, depth, websites, language);
    }
}

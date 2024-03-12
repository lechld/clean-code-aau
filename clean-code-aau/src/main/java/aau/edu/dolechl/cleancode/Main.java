package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.input.CliCrawlParameterFactory;
import aau.edu.dolechl.cleancode.input.CrawlParameter;
import aau.edu.dolechl.cleancode.input.CrawlParameterFactory;

public class Main {

    public static void main(String[] args) {
        CrawlParameterFactory crawlParameterFactory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = crawlParameterFactory.create();

        if (crawlParameter == null) {
            return;
        }

        System.out.print("Hello and welcome!");
    }
}
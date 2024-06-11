package aau.edu.dolechl.cleancode;

public class Main {

    public static void main(String[] args) {
        Environment environment = new DefaultEnvironment(args);
        CrawlerApplication app = new CrawlerApplication();

        app.run(environment);
    }
}
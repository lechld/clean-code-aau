package aau.edu.dolechl.cleancode.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CliCrawlParameterFactoryTest {

    @Test
    public void testCreate_WithValidArgs() {
        String[] args = {"-u", "http://example.com", "-d", "5", "-l", "english"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNotNull(crawlParameter);
        assertEquals("http://example.com", crawlParameter.url());
        assertEquals(5, crawlParameter.depth());
        assertEquals("english", crawlParameter.targetLanguage());
    }

    @Test
    public void testCreate_MissingUrlArg() {
        String[] args = {"-d", "5", "-l", "english"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNull(crawlParameter);
    }

    @Test
    public void testCreate_InvalidDepthArg() {
        String[] args = {"-u", "http://example.com", "-d", "invalid", "-l", "english"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNull(crawlParameter);
    }
}

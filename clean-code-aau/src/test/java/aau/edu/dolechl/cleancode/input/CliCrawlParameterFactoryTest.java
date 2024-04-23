package aau.edu.dolechl.cleancode.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

public class CliCrawlParameterFactoryTest {

    @Test
    public void testCreate_WithValidArgs() {
        String[] args = {"-u", "http://example.com", "-d", "5", "-l", "en", "-w", "site1.com", "site2.com"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNotNull(crawlParameter);
        assertEquals("http://example.com", crawlParameter.url().toString());
        assertEquals(5, crawlParameter.depth());
        assertEquals("en", crawlParameter.targetLanguage());
        assertEquals(List.of("site1.com", "site2.com"), crawlParameter.websites());
    }

    @Test
    public void testCreate_MissingUrlArg() {
        String[] args = {"-d", "5", "-l", "en"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNull(crawlParameter);
    }

    @Test
    public void testCreate_InvalidDepthArg() {
        String[] args = {"-u", "http://example.com", "-d", "invalid", "-l", "en"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNull(crawlParameter);
    }
}

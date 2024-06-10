package aau.edu.dolechl.cleancode.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

public class CliCrawlParameterFactoryTest {

    @Test
    public void testCreate_WithValidArgs_SingleUrl() {
        String[] args = {"-u", "http://example.com", "-d", "5", "-l", "en", "-w", "site1.com", "site2.com"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNotNull(crawlParameter);
        assertEquals(List.of("http://example.com"), crawlParameter.urls().stream().map(URL::toString).toList());
        assertEquals(5, crawlParameter.depth());
        assertEquals("en", crawlParameter.targetLanguage());
        assertEquals(List.of("site1.com", "site2.com"), crawlParameter.websites());
    }

    @Test
    public void testCreate_WithValidArgs_MultipleUrls() {
        String[] args = {"-u", "http://example.com", "http://example.org", "-d", "5", "-l", "en", "-w", "site1.com", "site2.com"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNotNull(crawlParameter);
        assertEquals(List.of("http://example.com", "http://example.org"), crawlParameter.urls().stream().map(URL::toString).toList());
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

    @Test
    public void testCreate_WithInvalidUrl() {
        String[] args = {"-u", "invalid_url", "-d", "5", "-l", "en"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNull(crawlParameter);
    }

    @Test
    public void testCreate_WithMixedValidAndInvalidUrls() {
        String[] args = {"-u", "http://example.com", "invalid_url", "-d", "5", "-l", "en"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNotNull(crawlParameter);
        assertEquals(List.of("http://example.com"), crawlParameter.urls().stream().map(URL::toString).toList());
        assertEquals(5, crawlParameter.depth());
        assertEquals("en", crawlParameter.targetLanguage());
        assertTrue(crawlParameter.websites().isEmpty());
    }

    @Test
    public void testCreate_WithNoUrlsValid() {
        String[] args = {"-u", "invalid_url1", "invalid_url2", "-d", "5", "-l", "en"};
        CliCrawlParameterFactory factory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = factory.create();

        assertNull(crawlParameter);
    }
}
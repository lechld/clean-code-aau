package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.DocumentCrawler;
import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.domain.DocumentWriter;
import aau.edu.dolechl.cleancode.input.CrawlParameter;
import aau.edu.dolechl.cleancode.input.CrawlParameterFactory;
import aau.edu.dolechl.cleancode.translator.DocumentTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class CrawlerApplicationTest {

    @Mock
    private Environment environment;
    @Mock
    private CrawlParameterFactory crawlParameterFactory;
    @Mock
    private CrawlParameter crawlParameter;
    @Mock
    private DocumentCrawler documentCrawler;
    @Mock
    private DocumentTranslator documentTranslator;
    @Mock
    private DocumentWriter documentWriter;
    @Mock
    private Document document;

    @InjectMocks
    private CrawlerApplication crawlerApplication;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(crawlParameterFactory.create()).thenReturn(crawlParameter);
        when(environment.getCrawlParameterFactory()).thenReturn(crawlParameterFactory);
        when(crawlParameter.urls()).thenReturn(List.of(new URL("http://example.com")));
        when(environment.getDocumentCrawler()).thenReturn(documentCrawler);
        when(environment.getDocumentTranslator()).thenReturn(documentTranslator);
        when(environment.getDocumentWriter()).thenReturn(documentWriter);
    }

    @Test
    void testRunWithValidParameters() throws IOException {
        // Setup
        when(crawlParameter.depth()).thenReturn(1);
        when(crawlParameter.websites()).thenReturn(Arrays.asList("site1", "site2"));
        when(crawlParameter.targetLanguage()).thenReturn("en");
        when(documentCrawler.crawlDocument(any(), anyInt(), anyList())).thenReturn(document);
        when(documentTranslator.translate(any(Document.class), anyString())).thenReturn(document);

        // Run the method
        crawlerApplication.run(environment);

        // Verify that the methods were called with the correct parameters
        verify(documentCrawler).crawlDocument(new URL("http://example.com"), 1, Arrays.asList("site1", "site2"));
        verify(documentTranslator).translate(document, "en");
        verify(documentWriter).write(new URL("http://example.com"), 1, "en", document);
    }

    @Test
    void testRunWithNullCrawlParameter() throws IOException {
        when(environment.getCrawlParameterFactory().create()).thenReturn(null);

        crawlerApplication.run(environment);

        verify(documentCrawler, never()).crawlDocument(any(), anyInt(), anyList());
        verify(documentTranslator, never()).translate(any(Document.class), anyString());
        verify(documentWriter, never()).write(any(), anyInt(), anyString(), any(Document.class));
    }

    @Test
    void testRunWithIOException() throws IOException {
        when(crawlParameter.depth()).thenReturn(1);
        when(crawlParameter.websites()).thenReturn(Arrays.asList("site1", "site2"));
        when(crawlParameter.targetLanguage()).thenReturn("en");
        when(documentCrawler.crawlDocument(any(), anyInt(), anyList())).thenThrow(new IOException("Test IOException"));

        assertThrows(RuntimeException.class, () -> crawlerApplication.run(environment));

        verify(documentCrawler).crawlDocument(new URL("http://example.com"), 1, Arrays.asList("site1", "site2"));
        verify(documentTranslator, never()).translate(any(Document.class), anyString());
        verify(documentWriter, never()).write(any(), anyInt(), anyString(), any(Document.class));
    }
}
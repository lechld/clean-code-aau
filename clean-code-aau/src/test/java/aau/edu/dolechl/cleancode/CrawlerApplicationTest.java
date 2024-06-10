package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.DocumentCrawler;
import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.domain.DocumentWriter;
import aau.edu.dolechl.cleancode.input.CrawlParameter;
import aau.edu.dolechl.cleancode.input.CrawlParameterFactory;
import aau.edu.dolechl.cleancode.translator.DocumentTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CrawlerApplicationTest {

    @Mock
    private CrawlParameterFactory crawlParameterFactory;

    @Mock
    private DocumentCrawler crawler;

    @Mock
    private DocumentTranslator translator;

    @Mock
    private DocumentWriter writer;

    @Mock
    private CrawlParameter crawlParameter;

    @Mock
    private Document document;

    private CrawlerApplication crawlerApplication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        crawlerApplication = new CrawlerApplication();
    }

    @Test
    public void testRun_NormalExecution() throws IOException {
        when(crawlParameterFactory.create()).thenReturn(crawlParameter);
        when(crawler.crawlDocument(crawlParameter)).thenReturn(document);
        when(translator.translate(document, crawlParameter.targetLanguage())).thenReturn(document);

        crawlerApplication.run(crawlParameterFactory, crawler, translator, writer);

        verify(crawlParameterFactory).create();
        verify(crawler).crawlDocument(crawlParameter);
        verify(translator).translate(document, crawlParameter.targetLanguage());
        verify(writer).write(crawlParameter, document);
    }

    @Test
    public void testRun_NullCrawlParameter() {
        when(crawlParameterFactory.create()).thenReturn(null);

        crawlerApplication.run(crawlParameterFactory, crawler, translator, writer);

        verify(crawlParameterFactory).create();
        verifyNoMoreInteractions(crawler, translator, writer);
    }

    @Test
    public void testRun_CrawlerIOException() throws IOException {
        when(crawlParameterFactory.create()).thenReturn(crawlParameter);
        when(crawler.crawlDocument(crawlParameter)).thenThrow(new IOException("Internet interrupted."));

        crawlerApplication.run(crawlParameterFactory, crawler, translator, writer);

        verify(crawlParameterFactory).create();
        verify(crawler).crawlDocument(crawlParameter);
        verifyNoMoreInteractions(translator, writer);
    }
}
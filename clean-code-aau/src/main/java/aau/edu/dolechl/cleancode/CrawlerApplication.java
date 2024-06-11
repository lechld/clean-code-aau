package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.async.CrawlTaskResult;
import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.input.CrawlParameter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class CrawlerApplication {
    public void run(Environment environment) {
        CrawlParameter crawlParameter = environment.getCrawlParameterFactory().create();

        if (crawlParameter == null) {
            return;
        }

        List<CompletableFuture<CrawlTaskResult>> allCrawls = crawlParameter.urls().stream()
                .map(url -> CompletableFuture.supplyAsync(() -> {
                            try {
                                Document doc = environment.getDocumentCrawler().crawlDocument(url, crawlParameter.depth(), crawlParameter.websites());

                                return new CrawlTaskResult(doc, url);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                )).toList();

        List<CompletableFuture<CrawlTaskResult>> allTranslated = allCrawls.stream()
                .map(crawled -> crawled.thenApply(crawlTaskResult -> {
                    Document translatedDoc = environment.getDocumentTranslator().translate(crawlTaskResult.document(), crawlParameter.targetLanguage());

                    return new CrawlTaskResult(translatedDoc, crawlTaskResult.url());
                })).toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(allTranslated.toArray(new CompletableFuture[0]));

        CompletableFuture<List<CrawlTaskResult>> allTranslatedCrawls = allOf.thenApply(v ->
                allTranslated.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );

        allTranslatedCrawls.thenAccept(results -> {
            for (CrawlTaskResult result : results) {
                try {
                    environment.getDocumentWriter().write(result.url(), crawlParameter.depth(), crawlParameter.targetLanguage(), result.document());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).join();
    }
}

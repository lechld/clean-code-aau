package aau.edu.dolechl.cleancode.crawler;

import aau.edu.dolechl.cleancode.domain.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface DocumentCrawler {
    Document crawlDocument(URL url, int depth, List<String> websites) throws IOException;
}

package aau.edu.dolechl.cleancode.html.fetch;

import java.io.IOException;
import java.net.URL;

public interface HtmlFetcher {

    HtmlFetchResult fetch(URL url) throws IOException;
}

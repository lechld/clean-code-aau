package aau.edu.dolechl.cleancode.html.fetch;

import aau.edu.dolechl.cleancode.html.elements.Header;
import aau.edu.dolechl.cleancode.html.elements.Link;

import java.util.List;

public record HtmlFetchResult(
        List<Header> headers,
        List<Link> links
) {
}

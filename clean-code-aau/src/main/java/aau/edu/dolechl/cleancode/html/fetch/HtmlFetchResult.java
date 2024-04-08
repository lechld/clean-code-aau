package aau.edu.dolechl.cleancode.html.fetch;

import aau.edu.dolechl.cleancode.html.elements.HtmlHeader;
import aau.edu.dolechl.cleancode.html.elements.HtmlLink;

import java.util.List;

public record HtmlFetchResult(
        List<HtmlHeader> headers,
        List<HtmlLink> links
) {
}

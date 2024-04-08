package aau.edu.dolechl.cleancode.domain;

import aau.edu.dolechl.cleancode.input.CrawlParameter;

public interface DocumentWriter {

    void write(CrawlParameter parameter, Document document);
}

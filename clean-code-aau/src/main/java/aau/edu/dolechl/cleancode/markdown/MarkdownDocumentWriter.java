package aau.edu.dolechl.cleancode.markdown;

import aau.edu.dolechl.cleancode.domain.*;
import aau.edu.dolechl.cleancode.input.CrawlParameter;

import java.io.FileWriter;
import java.io.IOException;

public class MarkdownDocumentWriter implements DocumentWriter {

    private final FileWriter writer;

    public MarkdownDocumentWriter(FileWriter writer) {
        this.writer = writer;
    }

    @Override
    public void write(CrawlParameter parameter, Document document) {
        try {
            writer.write("input: <a>" + parameter.url() + "</a>\n");
            writer.write("<br>depth: " + parameter.depth() + "\n");
            writer.write("<br>target language: " + parameter.targetLanguage() + "\n");
            writer.write("<br>summary: \n");

            for (DocumentElement element : document.getElements()) {
                if (element instanceof DocumentHeader header) {

                    writer.write("#".repeat(header.level()) + getPrefix(header.depth(), parameter.depth()) + " " + header.value() + "\n");
                } else if (element instanceof DocumentLink link) {
                    String linkTo = link.isValidLink() ? "link to" : "broken link";

                    writer.write("\n<br>" + getPrefix(link.depth(), parameter.depth()) + linkTo + " <a>" + link.uri() + "</a>");
                }
            }
        } catch (IOException e) {
            System.out.println("Failure writing to the output.");
        }
    }

    public String getPrefix(int elementDepth, int maxDepth) {
        int dashes = Math.max(0, (maxDepth - elementDepth) * 2);
        if (dashes == 0) {
            return " ";
        } else {
            return "-".repeat(dashes) + "> ";
        }
    }
}

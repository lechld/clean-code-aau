package aau.edu.dolechl.cleancode.markdown;

import aau.edu.dolechl.cleancode.domain.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class MarkdownDocumentWriter implements DocumentWriter {

    private final FileWriter writer;

    public MarkdownDocumentWriter(FileWriter writer) {
        this.writer = writer;
    }

    @Override
    public void write(URL url, int depth, String targetLanguage, Document document) {
        try {
            writer.write("input: <a>" + url + "</a>\n");
            writer.write("<br>depth: " + depth + "\n");
            writer.write("<br>target language: " + targetLanguage + "\n");
            writer.write("<br>summary: \n");

            for (DocumentElement element : document.getElements()) {
                if (element instanceof DocumentHeader header) {

                    writer.write("#".repeat(header.level()) + getPrefix(header.depth(), depth) + " " + header.value() + "\n");
                } else if (element instanceof DocumentLink link) {
                    String linkTo = link.isValidLink() ? "link to" : "broken link";

                    writer.write("\n<br>" + getPrefix(link.depth(), depth) + linkTo + " <a>" + link.uri() + "</a>");
                }
            }

            writer.write("\n");
            writer.flush();

        } catch (IOException e) {
            System.err.println("Failure writing to the output.");
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

    @Override
    public void close() throws IOException {
        writer.close();
    }
}

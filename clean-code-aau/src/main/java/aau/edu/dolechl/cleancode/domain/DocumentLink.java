package aau.edu.dolechl.cleancode.domain;

public record DocumentLink(
        int depth,
        String uri,
        boolean isValidLink
) implements DocumentElement {
}

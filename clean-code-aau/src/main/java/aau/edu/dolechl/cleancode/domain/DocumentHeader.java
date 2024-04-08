package aau.edu.dolechl.cleancode.domain;

public record DocumentHeader(
        int depth,
        int level,
        String value
) implements DocumentElement {
}

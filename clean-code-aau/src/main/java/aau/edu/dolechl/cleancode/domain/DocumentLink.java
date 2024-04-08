package aau.edu.dolechl.cleancode.domain;

import java.net.URI;

public record DocumentLink(
        int depth,
        String uri
) implements DocumentElement {
}

package aau.edu.dolechl.cleancode.html;

public class Header implements HtmlElement {

    int level;
    String value;

    public Header(int level, String value) {
        this.level = level;
        this.value = value;
    }
}

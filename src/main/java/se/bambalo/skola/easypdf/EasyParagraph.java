package se.bambalo.skola.easypdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class EasyParagraph extends EasyObject<EasyParagraph> {
    private String text;

    public EasyParagraph() {
    }

    public EasyParagraph(String format, Object... args) {
        text = String.format(format, args);
    }

    @Override
    public void append(Document document) {
        Paragraph paragraph = new Paragraph();
        // TODO fix this
        paragraph.add(text != null ? text : "-");

        document.add(paragraph);
    }

    @Override
    public EasyParagraph self() {
        return this;
    }
}

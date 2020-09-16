package se.bambalo.skola.easypdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class EasyParagraph extends EasyObject<EasyParagraph> {
    private String text;

    public EasyParagraph(String format, Object... args) {
        text = String.format(format, args);
    }

    @Override
    public void append(Document document) throws Exception {
        Paragraph paragraph = new Paragraph();
        paragraph.add(text != null ? text : "");

        setup(paragraph);

        document.add(paragraph);
    }

    @Override
    public EasyParagraph self() {
        return this;
    }
}

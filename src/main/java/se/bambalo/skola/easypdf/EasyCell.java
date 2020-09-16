package se.bambalo.skola.easypdf;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

public class EasyCell extends EasyFormat<EasyCell> {

    public static final EasyCell EMTPY = new EasyCell();
    public static final EasyCell EMTPY_WITH_BORDER = new EasyCell().withBorder();

    private String text;
    private TextAlignment horizontal = TextAlignment.CENTER;
    private VerticalAlignment vertical = VerticalAlignment.MIDDLE;
    private float height;
    private boolean border;

    private EasyCell() {
    }

    public EasyCell(String format, Object... args) {
        text = String.format(format, args);
    }

    public EasyCell(int number) {
        text = Integer.toString(number);
    }

    public EasyCell withBorder() {
        this.border = true;
        return this;
    }

    public EasyCell withoutBorder() {
        this.border = false;
        return this;
    }

    public EasyCell height(float height) {
        this.height = height;
        return this;
    }

    public EasyCell left() {
        return horizontal(TextAlignment.LEFT);
    }

    public EasyCell right() {
        return horizontal(TextAlignment.RIGHT);
    }

    public EasyCell center() {
        return horizontal(TextAlignment.CENTER);
    }

    private EasyCell horizontal(TextAlignment horizontal) {
        this.horizontal = horizontal;
        return this;
    }

    public EasyCell top() {
        return vertical(VerticalAlignment.TOP);
    }

    public EasyCell bottom() {
        return vertical(VerticalAlignment.BOTTOM);
    }

    public EasyCell middle() {
        return vertical(VerticalAlignment.MIDDLE);
    }

    private EasyCell vertical(VerticalAlignment vertical) {
        this.vertical = vertical;
        return this;
    }

    Cell createCell() throws Exception {
        Paragraph paragraph = new Paragraph();

        if (text != null) {
            paragraph.add(text);
            setup(paragraph);
        }

        Cell result = new Cell().add(paragraph)
                                .setVerticalAlignment(vertical)
                                .setTextAlignment(horizontal);

        if (!border) {
            result.setBorder(Border.NO_BORDER);
        }

        if (height > 0) {
            result.setHeight(height);
        }

        return result;
    }

    @Override
    public EasyCell self() {
        return this;
    }

}

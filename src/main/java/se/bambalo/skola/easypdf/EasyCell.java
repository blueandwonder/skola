package se.bambalo.skola.easypdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

public class EasyCell extends EasyFormat<EasyCell> {
    private String text;
    private TextAlignment horizontal = TextAlignment.CENTER;
    private VerticalAlignment vertical = VerticalAlignment.MIDDLE;
    private Color color= ColorConstants.BLACK;
    private float height;
    private boolean border;
    private boolean bold;

    public EasyCell() {
    }

    public EasyCell(String format, Object... args) {
        text(format, args);
    }

    public EasyCell(int number) {
        text = Integer.toString(number);
    }

    public EasyCell text(String format, Object... args) {
        this.text = String.format(format, args);
        return this;
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

    // TODO add fontSize

    // TODO pass some of these to EasyFormat instead
    public EasyCell black() {
        return color(ColorConstants.BLACK);
    }

    public EasyCell grey() {
        return color(ColorConstants.LIGHT_GRAY);
    }

    public EasyCell bold() {
        bold = true;
        return this;
    }

    public EasyCell color(Color color) {
        this.color = color;
        return this;
    }

    public EasyCell withBorder(Border border) {
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

    Cell createCell() {
        Paragraph paragraph = new Paragraph();

        if (text != null) {
            paragraph.add(text);
            paragraph.setFontColor(color);
            if (bold) {
                paragraph.setBold();
            }
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

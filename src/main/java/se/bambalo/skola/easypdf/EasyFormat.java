package se.bambalo.skola.easypdf;

import com.itextpdf.kernel.colors.Color;

public abstract class EasyFormat<T extends EasyFormat<?>> {
    private String fontName;
    private float fontSize;
    private Color fontColor;

    public T fontName(String fontName) {
        this.fontName = fontName;
        return self();
    }

    public abstract T self();
}

package se.bambalo.skola.easypdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.ElementPropertyContainer;

public abstract class EasyFormat<T extends EasyFormat<?>> {

    private String fontName;
    private float fontSize;
    private Color fontColor;
    private boolean bold;

    public abstract T self();

    public final T fontName(String fontName) {
        this.fontName = fontName;
        return self();
    }

    public final T fontSize(float fontSize) {
        this.fontSize = fontSize;
        return self();
    }

    public final T fontColor(Color fontColor) {
        this.fontColor = fontColor;
        return self();
    }

    public final T black() {
        return fontColor(ColorConstants.BLACK);
    }

    public final T grey() {
        return fontColor(ColorConstants.LIGHT_GRAY);
    }

    public final T bold() {
        this.bold = true;
        return self();
    }

    void setup(ElementPropertyContainer<?> container) throws Exception {
        if (fontName != null) {
            container.setFont(PdfFontFactory.createFont(fontName));
        }
        if (fontSize > 0) {
            container.setFontSize(fontSize);
        }
        if (fontColor != null) {
            container.setFontColor(fontColor);
        }
        if (bold) {
            container.setBold();
        }
    }

}

package se.bambalo.skola.easypdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;

public class EasyLineSeparator extends EasyObject<EasyLineSeparator> {

    private ILineDrawer drawer = new SolidLine();
    private Color color;

    public EasyLineSeparator(ILineDrawer drawer) {
        this.drawer = drawer;
    }

    public EasyLineSeparator color(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public EasyLineSeparator self() {
        return this;
    }

    @Override
    void append(Document document) {
        if (color != null) {
            drawer.setColor(color);
        }

        document.add(new LineSeparator(drawer));
    }

}

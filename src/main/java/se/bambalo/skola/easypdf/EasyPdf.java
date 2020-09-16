package se.bambalo.skola.easypdf;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;

public class EasyPdf extends EasyFormat<EasyPdf> {
    private PageSize pageSize;
    private String filename;

    private List<EasyObject> objects = new ArrayList<>();

    public static EasyPdf portrait(String format, Object... args) {
        return new EasyPdf(PageSize.A4, format, args);
    }

    public static EasyPdf landscape(String format, Object... args) {
        return new EasyPdf(PageSize.A4.rotate(), format, args);
    }

    private EasyPdf(PageSize pageSize, String format, Object... args) {
        this.pageSize = pageSize;
        this.filename = String.format(format, args);
    }

    public EasyPdf add(EasyObject object) {
        objects.add(object);
        return this;
    }

    public EasyPdf doubleSpace() {
        space();
        space();
        return this;
    }

    public EasyPdf space() {
        objects.add(new EasyParagraph(" "));
        return this;
    }

    public EasyPdf pagebreak() {
        objects.add(new PageBreak());
        return this;
    }

    public void createDocument() {
        try {
            // XXX not like this
            PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);
            int fontSize = 20;

            Document document = new Document(new PdfDocument(new PdfWriter(filename)), pageSize);
            document.setFont(plain);
            document.setFontSize(fontSize);

            objects.forEach(object -> object.append(document));

            document.close();

            System.out.println("Created " + filename);
        }
        catch (Exception e) {
            System.err.println("Failed to create " + filename);
            e.printStackTrace();
        }
    }

    @Override
    public EasyPdf self() {
        return this;
    }

    private static class PageBreak extends EasyObject<PageBreak> {
        @Override
        public PageBreak self() {
            return this;
        }

        @Override
        void append(Document document) {
            document.add(new AreaBreak());
        }
    }

}

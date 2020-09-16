package se.bambalo.skola.easypdf;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyPdf extends EasyFormat<EasyPdf> {
    private Logger logger = LoggerFactory.getLogger(getClass());

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

        fontName(StandardFonts.COURIER);
        fontSize(20);
        fontColor(ColorConstants.BLACK);
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
            Document document = new Document(new PdfDocument(new PdfWriter(filename)), pageSize);
            setup(document);

            for (EasyObject each : objects) {
                each.append(document);
            }

            document.close();
            logger.info("Created document {}", filename);
        }
        catch (Exception e) {
            logger.info("Failed to create {}", filename, e);
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

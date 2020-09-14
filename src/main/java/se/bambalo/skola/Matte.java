package se.bambalo.skola;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

public class Matte {

    private static final String DIRECTORY = "/tmp/ruta/";
    private static final boolean SHUFFLE = true;

    private static final float[] WIDTHS = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
    public static final String ANSWER_LINE = "___";

    void tabell() throws Exception {
        for (int series = 1; series < 11; series++) {
            String filename = String.format("%s/tabell-%s.pdf", DIRECTORY, series);

            System.out.println("Creating file " + filename);

            PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);

            int fontSize = 20;
            int startAt = 0;

            Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4.rotate());
            document.setFont(plain);

            {
                document.add(new Paragraph("Tabell: " + series).setFont(bold).setFontSize(fontSize));
                document.add(new Paragraph());
                document.add(new Paragraph());

                Table table = new Table(WIDTHS);
                table.setFixedLayout();
                table.setWidth(UnitValue.createPercentValue(100));

                for (int i = 0; i < 100; i++) {
                    int number = i + startAt;

                    Paragraph paragraph = new Paragraph(Integer.toString(number)).setFontSize(fontSize);

                    if (number % series == 0) {
                        paragraph.setFont(bold).setFontColor(ColorConstants.BLACK);
                    }
                    else {
                        paragraph.setFontColor(ColorConstants.LIGHT_GRAY);
                    }

                    table.addCell(new Cell().add(paragraph)
                                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                            .setTextAlignment(TextAlignment.CENTER)
                                            .setHeight(40));
                }

                document.add(table);
            }

            document.add(new AreaBreak());

            {
                float[] widths = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

                Table table = new Table(widths);
                table.setFixedLayout();
                table.setWidth(UnitValue.createPercentValue(100));


                for (int i = 1; i < 11; i++) {
                    Paragraph paragraph = new Paragraph(Integer.toString(i * series)).setFontSize(fontSize);

                    if (i > 2 && i < 10) {
                        paragraph = new Paragraph();
                    }

                    table.addCell(new Cell().add(paragraph)
                                            // .setBorder(Border.NO_BORDER)
                                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                            .setTextAlignment(TextAlignment.CENTER)
                                            .setHeight(40));
                }

                document.add(table);
            }

            {
                document.add(new Paragraph());
                document.add(new Paragraph());
                document.add(new Paragraph());

                float[] widths = { 10 };

                Table table = new Table(widths);
                table.setFixedLayout();
                table.setWidth(UnitValue.createPercentValue(50));

                List<Integer> numbers = IntStream.rangeClosed(1, 10)
                                                 .mapToObj(Integer::valueOf)
                                                 .collect(Collectors.toList());

                if (SHUFFLE) {
                    Collections.shuffle(numbers);
                }

                for (Integer each : numbers) {
                    String str = String.format("%d . %d = %s", each, series, ANSWER_LINE);

                    Paragraph paragraph = new Paragraph(str).setFontSize(fontSize);

                    table.addCell(new Cell().add(paragraph)
                                            .setBorder(Border.NO_BORDER)
                                            .setVerticalAlignment(VerticalAlignment.BOTTOM)
                                            .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                            .setTextAlignment(TextAlignment.LEFT)
                                            .setHeight(40));
                }

                document.add(table);
            }

            document.close();
        }
    }

    private void multiplikation() throws Exception {
        String filename = String.format("%s/multiplikation.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);

        int fontSize = 18;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4.rotate());
        document.setFont(plain);
        document.setFontSize(fontSize);

        {
            float[] widths = {10, 10, 10, 10};

            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(plain);
            table.setFontSize(fontSize);

            for (int g = 1; g <= 10; g++) {
                for (int t : Arrays.asList(2, 3, 4, 5)) {
                    table.addCell(new Cell().add(paragraph(String.format(" %d %c %d = ____", g, 183, t)))
                                            .setBorder(Border.NO_BORDER)
                                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                            .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                            .setTextAlignment(TextAlignment.LEFT));
                }
            }

            document.add(table);
        }

        document.close();
    }

    void tioKompisarPlus() throws Exception {
        String filename = String.format("%s/tiokompisar-plus.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);

        int fontSize = 20;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);

        document.add(new Paragraph("Tiokompisar").setFont(bold).setFontSize(fontSize));
        document.add(new Paragraph());
        document.add(new Paragraph());

        {
            float[] widths = { 10 };

            List<TioKompis> kompisar = new ArrayList<>();

            for (int i = 1; i < 10; i++) {
                kompisar.add(new TioKompis(i, true));
                kompisar.add(new TioKompis(i, false));
            }

            Collections.shuffle(kompisar);

            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(50));

            int height = 25;

            for (TioKompis each : kompisar) {
                table.addCell(new Cell().add(paragraph(String.format("%s + %s = 10", each.first, each.second)))
                                        .setBorder(Border.NO_BORDER)
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setHeight(height));
            }

            document.add(table);
        }

        document.close();
    }

    void tioKompisarMinus() throws Exception {
        String filename = String.format("%s/tiokompisar-minus.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);

        int fontSize = 20;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);

        document.add(new Paragraph("Tiokompisar").setFont(bold).setFontSize(fontSize));
        document.add(new Paragraph());
        document.add(new Paragraph());

        {
            float[] widths = { 10 };

            List<TioKompis> kompisar = new ArrayList<>();

            for (int i = 1; i < 10; i++) {
                kompisar.add(new TioKompis(i, true));
                kompisar.add(new TioKompis(i, false));
            }

            Collections.shuffle(kompisar);

            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(50));

            int height = 25;

            for (TioKompis each : kompisar) {
                table.addCell(new Cell().add(paragraph(String.format("10 - %s = %s", each.first, each.second)))
                                        .setBorder(Border.NO_BORDER)
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setHeight(height));
            }

            document.add(table);
        }

        document.close();
    }

    private void addition() throws Exception {
        String filename = String.format("%s/addition.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);

        int fontSize = 18;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);
        document.setFontSize(fontSize);

        {
            float[] widths = {10, 10, 10};

            List<Pair> pairs = new ArrayList<>();

            {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {
                        pairs.add(new Pair(i, j));
                    }
                }
            }

            {
                for (int i = 1; i < 10; i++) {
                    for (int j = 1; j < 10; j++) {
                        pairs.add(new Pair(i, j));
                    }
                }
            }

            if (SHUFFLE) {
                Collections.shuffle(pairs);
            }


            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(plain);
            table.setFontSize(fontSize);

            for (Pair each : pairs) {

                table.addCell(new Cell().add(paragraph(String.format(" %d + %d = ", each.first, each.second)))
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                        .setTextAlignment(TextAlignment.LEFT));
                                        // .setHeight(height));
            }

            document.add(table);
        }

        document.close();
    }

    private void subtraktion() throws Exception {
        String filename = String.format("%s/subtraktion.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);

        int fontSize = 18;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);
        document.setFontSize(fontSize);

        {
            float[] widths = {10, 10, 10};

            List<Pair> pairs = new ArrayList<>();

            {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j <= i; j++) {
                        pairs.add(new Pair(i, j));
                    }
                }
            }

            {
                for (int i = 1; i < 10; i++) {
                    for (int j = 1; j <= i; j++) {
                        pairs.add(new Pair(i, j));
                    }
                }
            }

            if (SHUFFLE) {
                Collections.shuffle(pairs);
            }


            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(plain);
            table.setFontSize(fontSize);

            for (Pair each : pairs) {

                table.addCell(new Cell().add(paragraph(String.format(" %d - %d = ", each.first, each.second)))
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                        .setTextAlignment(TextAlignment.LEFT));
            }

            document.add(table);
        }

        document.close();
    }

    private void additionTiotalsovergang() throws Exception {
        String filename = String.format("%s/addition-tiotalsövergång.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);

        int fontSize = 18;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);
        document.setFontSize(fontSize);

        {
            float[] widths = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));

            for (int i = 1; i <= 20; i++) {
                String str = Integer.toString(i);
                Paragraph paragraph = new Paragraph(str).setFontSize(fontSize);

                table.addCell(new Cell().add(paragraph)
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setHeight(40));
            }

            document.add(table);
        }

        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(new Paragraph());

        {
            float[] widths = {10, 10, 10};

            List<Pair> pairs = new ArrayList<>();

            {
                for (int i = 1; i < 10; i++) {
                    for (int j = 1; j < 10; j++) {
                        if (i + j > 10) {
                            pairs.add(new Pair(i, j));
                        }
                    }
                }
            }

            if (SHUFFLE) {
                Collections.shuffle(pairs);
            }


            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(plain);
            table.setFontSize(fontSize);

            for (Pair each : pairs) {

                table.addCell(new Cell().add(paragraph(String.format(" %d + %d = ", each.first, each.second)))
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                        .setTextAlignment(TextAlignment.LEFT));
                // .setHeight(height));
            }

            document.add(table);
        }

        document.close();
    }

    private void subtraktionHogaEntal() throws Exception {
        String filename = String.format("%s/subtraktion-höga-ental.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);

        int fontSize = 18;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);
        document.setFontSize(fontSize);

        {
            float[] widths = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));

            for (int i = 1; i <= 10; i++) {
                Paragraph paragraph = new Paragraph(Integer.toString(i)).setFontSize(fontSize);

                table.addCell(new Cell().add(paragraph)
                                        // .setBorder(Border.NO_BORDER)
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setHeight(40));
            }

            document.add(table);
        }

        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(new Paragraph());

        {
            float[] widths = {10, 10, 10};

            List<Pair> pairs = new ArrayList<>();

            {
                for (int i = 5; i < 10; i++) {
                    for (int j = 2; j < i; j++) {
                        pairs.add(new Pair(i, j));
                    }
                }
            }

            if (SHUFFLE) {
                Collections.shuffle(pairs);
            }


            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(plain);
            table.setFontSize(fontSize);

            for (Pair each : pairs) {

                table.addCell(new Cell().add(paragraph(String.format(" %d - %d = ", each.first, each.second)))
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                        .setTextAlignment(TextAlignment.LEFT));
            }

            document.add(table);
        }

        document.close();
    }

    private void subtraktionTiotalsovergang() throws Exception {
        String filename = String.format("%s/subtraktion-tiotalsövergång.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);

        int fontSize = 18;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);
        document.setFontSize(fontSize);

        {
            float[] widths = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));

            for (int i = 1; i <= 20; i++) {
                String str = Integer.toString(i);
                Paragraph paragraph = new Paragraph(str).setFontSize(fontSize);

                table.addCell(new Cell().add(paragraph)
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setHeight(40));
            }

            document.add(table);
        }

        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(new Paragraph());

        {
            float[] widths = {10, 10, 10};

            List<Pair> pairs = new ArrayList<>();

            {
                for (int i = 11; i < 20; i++) {
                    for (int j = 2; j < 10; j++) {
                        if (i - j < 10) {
                            pairs.add(new Pair(i, j));
                        }
                    }
                }
            }

            if (SHUFFLE) {
                Collections.shuffle(pairs);
            }


            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(plain);
            table.setFontSize(fontSize);

            for (Pair each : pairs) {
                table.addCell(new Cell().add(paragraph(String.format(" %d - %d = ", each.first, each.second)))
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                        .setTextAlignment(TextAlignment.LEFT));
            }

            document.add(table);
        }

        document.close();
    }

    private void subtraktionMix() throws Exception {
        String filename = String.format("%s/subtraktion-mix.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);

        int fontSize = 18;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);
        document.setFontSize(fontSize);

        {
            float[] widths = {10, 10, 10};

            List<Pair> pairs = new ArrayList<>();

            {
                for (int i = 4; i < 10; i++) {
                    for (int j = 2; j < i; j++) {
                        pairs.add(new Pair(i, j));
                    }
                }
            }

            {
                for (int i = 11; i < 20; i++) {
                    for (int j = 2; j < 10; j++) {
                        if (i - j < 10) {
                            pairs.add(new Pair(i, j));
                        }
                    }
                }
            }

            if (SHUFFLE) {
                Collections.shuffle(pairs);
            }

            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(plain);
            table.setFontSize(fontSize);

            for (Pair each : pairs) {
                table.addCell(new Cell().add(paragraph(String.format(" %d - %d = ", each.first, each.second)))
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                        .setTextAlignment(TextAlignment.LEFT));
            }

            document.add(table);
        }

        document.close();
    }

    private void hundraKompisar() throws Exception {
        String filename = String.format("%s/hundrakompisar.pdf", DIRECTORY);

        System.out.println("Creating file " + filename);

        PdfFont plain = PdfFontFactory.createFont(StandardFonts.COURIER);

        int fontSize = 18;

        Document document = new Document(new PdfDocument(new PdfWriter(filename)), PageSize.A4);
        document.setFont(plain);
        document.setFontSize(fontSize);

        document.add(new Paragraph("Hundrakompisar").setFontSize(fontSize));
        document.add(new Paragraph());
        document.add(new Paragraph());

        {
            float[] widths = {10, 10, 3, 10, 10, 3, 10, 10, 3};

            Table table = new Table(widths);
            table.setFixedLayout();
            table.setWidth(UnitValue.createPercentValue(100));

            List<Integer> numbers = IntStream.rangeClosed(0, 100)
                                             .mapToObj(Integer::valueOf)
                                             .collect(Collectors.toList());

            if (SHUFFLE) {
                Collections.shuffle(numbers);
            }

            numbers.stream().limit(45).forEach(each -> {
                        // String str = String.format("%d . %d = %s", each, series, ANSWER_LINE);

                        // Paragraph paragraph = new Paragraph(str).setFontSize(fontSize);

                        table.addCell(new Cell().add(paragraph(each.toString()))
                                                // .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                                                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setHeight(40));

                        table.addCell(new Cell().add(paragraph(" "))
                                                // .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                                                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setHeight(40));

                        table.addCell(new Cell().add(paragraph(" "))
                                        .setBorder(Border.NO_BORDER)
                                        .setVerticalAlignment(VerticalAlignment.BOTTOM)
                                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                                        .setTextAlignment(TextAlignment.LEFT)
                                        .setHeight(40));


            });


            document.add(table);
        }

        document.close();
    }

    private Paragraph paragraph(String text) {
        int fontSize = 20;
        return new Paragraph(text).setFontSize(fontSize);
    }

    private class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    private class TioKompis {
        String first;
        String second;

        TioKompis(int number, boolean isFirst) {
            String str = Integer.toString(number);

            this.first = isFirst ? str : ANSWER_LINE;
            this.second = isFirst ? ANSWER_LINE : str;
        }
    }

    public static void main(String[] args) {
        try {
            new Matte().tabell();
            new Matte().multiplikation();
            new Matte().tioKompisarPlus();
            new Matte().tioKompisarMinus();
            new Matte().addition();
            new Matte().additionTiotalsovergang();
            new Matte().subtraktion();
            new Matte().subtraktionHogaEntal();
            new Matte().subtraktionTiotalsovergang();
            new Matte().subtraktionMix();
            new Matte().hundraKompisar();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

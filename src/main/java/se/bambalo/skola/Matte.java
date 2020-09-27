package se.bambalo.skola;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.bambalo.skola.easypdf.EasyCell;
import se.bambalo.skola.easypdf.EasyLineSeparator;
import se.bambalo.skola.easypdf.EasyList;
import se.bambalo.skola.easypdf.EasyListItem;
import se.bambalo.skola.easypdf.EasyParagraph;
import se.bambalo.skola.easypdf.EasyPdf;
import se.bambalo.skola.easypdf.EasyTable;

public class Matte {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DIRECTORY = "ruta";

    private static final char MULTIPLY = 183;
    private static final char PLUS = '+';
    private static final char MINUS = '-';

    private List<Integer> range(int from, int to) {
        return IntStream.rangeClosed(from, to)
                        .mapToObj(Integer::valueOf)
                        .collect(Collectors.toList());
    }

    private List<Integer> shuffled(int from, int to) {
        List<Integer> result = range(from, to);
        Collections.shuffle(result);
        return result;
    }

    void multiplikation() {
        EasyTable table = new EasyTable().rows(10)
                                         .columns(4)
                                         .fillOrder(EasyTable.FillOrder.BY_COLUMN);

        for (int col : range(2, 5)) {
            for (int row : range(1, 10)) {
                table.add(new EasyCell(" %d %c %d = ____", row, MULTIPLY, col).left().middle());
            }
        }

        EasyPdf.landscape("%s/multiplikation.pdf", DIRECTORY)
               .add(table)
               .createDocument();
    }

    void tiokompisar_addition() {
        EasyTable table = new EasyTable().rows(20)
                                         .columns(1)
                                         .height(30);

        getTiokompisar().forEach(each -> table.add(new EasyCell("%s + %s = 10", each.first, each.second).left()));

        EasyPdf.portrait("%s/tiokompisar-plus.pdf", DIRECTORY)
               .add(new EasyParagraph("Tiokompisar Plus"))
               .space()
               .add(table)
               .createDocument();
    }

    void tiokompisar_subtraktion() {
        EasyTable table = new EasyTable().rows(20)
                                         .columns(1)
                                         .height(30);

        getTiokompisar().forEach(each -> table.add(new EasyCell("10 - %s = %s", each.first, each.second).left()));

        EasyPdf.portrait("%s/tiokompisar-minus.pdf", DIRECTORY)
               .add(new EasyParagraph("Tiokompisar Minus"))
               .space()
               .add(table)
               .createDocument();
    }

    void addition() {
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

        withoutHelpTable("addition", pairs, PLUS);
    }

    private void subtraktion() {
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

        withoutHelpTable("subtraktion", pairs, MINUS);
    }

    private void addition_tiotalsovergang() {
        List<Pair> pairs = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (i + j > 10) {
                    pairs.add(new Pair(i, j));
                }
            }
        }

        withHelpTable("addition-tiotalsövergång", pairs, PLUS);
    }

    private void subtraktion_hoga_ental() {
        List<Pair> pairs = new ArrayList<>();

        for (int i = 5; i < 10; i++) {
            for (int j = 2; j < i; j++) {
                pairs.add(new Pair(i, j));
            }
        }

        withHelpTable("subtraktion-höga-ental", pairs, MINUS);
    }

    private void subtraktion_tiotalsovergang() {
        List<Pair> pairs = new ArrayList<>();

        for (int i = 11; i < 20; i++) {
            for (int j = 2; j < 10; j++) {
                if (i - j < 10) {
                    pairs.add(new Pair(i, j));
                }
            }
        }

        withHelpTable("subtraktion-tiotalsövergång", pairs, MINUS);
    }

    private void subtraktion_mix()  {
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

        withHelpTable("subtraktion-mix", pairs, MINUS);
    }

    private void withHelpTable(String filename, List<Pair> pairs, char operator) {
        EasyTable top = new EasyTable().rows(2)
                                       .columns(10)
                                       .height(40)
                                       .withBorder();

        range(1, 20).forEach(each -> top.add(new EasyCell(each)));

        EasyTable exercise = new EasyTable().rows(12)
                                            .columns(3)
                                            .withBorder();

        Collections.shuffle(pairs);

        pairs.forEach(each -> exercise.add(new EasyCell("%s %c %s =", each.first, operator, each.second).left()));

        EasyPdf.portrait("%s/%s.pdf", DIRECTORY, filename)
               .add(top)
               .doubleSpace()
               .doubleSpace()
               .add(exercise)
               .createDocument();
    }

    private void withoutHelpTable(String filename, List<Pair> pairs, char operator) {
        EasyTable exercise = new EasyTable().rows(12)
                                            .columns(3)
                                            .withBorder();

        Collections.shuffle(pairs);

        pairs.forEach(each -> exercise.add(new EasyCell("%s %c %s =", each.first, operator, each.second).left()));

        EasyPdf.portrait("%s/%s.pdf", DIRECTORY, filename)
               .add(exercise)
               .createDocument();
    }

    private void hundrakompisar() {
        EasyTable table = new EasyTable().rows(15)
                                         .columns(9)
                                         .widths(10, 10, 3, 10, 10, 3, 10, 10, 3)
                                         .height(40);

        shuffled(0, 100).stream()
                        .limit(45)
                        .forEach(number -> {
                            table.add(new EasyCell(number).left().withBorder());
                            table.add(EasyCell.EMTPY_WITH_BORDER);
                            table.add(EasyCell.EMTPY);
                        });

        EasyPdf.portrait("%s/hundrakompisar.pdf", DIRECTORY)
               .add(new EasyParagraph("Hundrakompisar"))
               .space()
               .add(table)
               .createDocument();
    }

    private void alla_tabeller() {
        range(1, 10).forEach(each -> tabell(each));
    }

    private void tabell(int tabell) {
        EasyTable square = new EasyTable().rows(10)
                                          .columns(10)
                                          .withBorder()
                                          .height(40);

        for (int number : range(1, 100)) {
            EasyCell cell = new EasyCell(number);

            if (number % tabell == 0) {
                cell.bold();
            }
            else {
                cell.grey();
            }

            square.add(cell);
        }

        EasyTable sequence = new EasyTable().rows(1)
                                            .columns(10)
                                            .withBorder()
                                            .height(40)
                                            .add(new EasyCell(tabell * 1))
                                            .add(new EasyCell(tabell * 2))
                                            .add(EasyCell.EMTPY)
                                            .add(EasyCell.EMTPY)
                                            .add(EasyCell.EMTPY)
                                            .add(EasyCell.EMTPY)
                                            .add(EasyCell.EMTPY)
                                            .add(EasyCell.EMTPY)
                                            .add(EasyCell.EMTPY)
                                            .add(new EasyCell(tabell * 10));

        EasyTable exercise = new EasyTable().rows(10)
                                            .columns(1)
                                            .height(40);

        shuffled(1, 10).forEach(each -> exercise.add(new EasyCell("%d %c %d = ____", each, MULTIPLY, tabell).left()));

        EasyPdf.landscape( "%s/tabell-%d.pdf", DIRECTORY, tabell)
               .add(square)
               .pageBreak()
               .add(sequence)
               .doubleSpace()
               .add(exercise)
               .createDocument();
    }

    private void test() {
        EasyTable first = new EasyTable().rows(2)
                                         .columns(2)
                                         .height(30)
                                         .withBorder()
                                         .add(new EasyCell("default look"))
                                         .add(new EasyCell("with fontName").fontName(StandardFonts.COURIER_BOLD))
                                         .add(new EasyCell("with fontsize").fontSize(20))
                                         .add(new EasyCell("with color").fontColor(ColorConstants.RED));

        EasyTable second = new EasyTable().rows(2)
                                          .columns(2)
                                          .height(30)
                                          .fontName(StandardFonts.TIMES_ITALIC)
                                          .fontSize(17)
                                          .fontColor(ColorConstants.MAGENTA)
                                          .withBorder()
                                          .add(new EasyCell("default look"))
                                          .add(new EasyCell("with fontName").fontName(StandardFonts.COURIER_BOLD))
                                          .add(new EasyCell("with fontsize").fontSize(20))
                                          .add(new EasyCell("with color").fontColor(ColorConstants.RED));

        EasyTable third = new EasyTable().rows(3)
                                         .columns(1)
                                         .height(30)
                                         .withBorder()
                                         .add(new EasyCell("top left").top().left())
                                         .add(new EasyCell("bottom right").bottom().right())
                                         .add(new EasyCell("middle center").middle().center());


        EasyList list = new EasyList().fontColor(ColorConstants.BLUE)
                                      .add(new EasyListItem("one"))
                                      .add(new EasyListItem("two").fontSize(22))
                                      .add(new EasyListItem("three"));

        EasyPdf.portrait("%s/test.pdf", DIRECTORY)
               .fontName(StandardFonts.TIMES_ITALIC)
               .fontSize(15)
               .fontColor(ColorConstants.DARK_GRAY)
               .add(new EasyParagraph("Default"))
               .add(new EasyParagraph("With fontName").fontName(StandardFonts.HELVETICA_BOLD))
               .add(new EasyParagraph("With fontSize").fontSize(36))
               .add(new EasyParagraph("With fontColor").fontColor(ColorConstants.GREEN))
               .space()
               .add(new EasyParagraph("Table without defaults"))
               .space()
               .add(first)
               .doubleSpace()
               .add(new EasyParagraph("Table with defaults"))
               .add(second)
               .space()
               .solidLineSeparator()
               .space()
               .dottedLineSeparator()
               .space()
               .add(new EasyLineSeparator(new SolidLine()).color(ColorConstants.ORANGE))
               .space()
               .dottedLineSeparator()
               .space()
               .add(new EasyParagraph("Table with positions").bold())
               .add(third)
               .doubleSpace()
               .add(list)
               .createDocument();
    }

    private List<Pair> getTiokompisar() {
        List<Pair> kompisar = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            kompisar.add(new Pair(Integer.toString(i), "___"));
            kompisar.add(new Pair("___", Integer.toString(i)));
        }

        Collections.shuffle(kompisar);

        return kompisar;
    }

    private class Pair {
        String first;
        String second;

        Pair(int first, int second) {
            this.first = Integer.toString(first);
            this.second = Integer.toString(second);
        }

        Pair(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        try {
        	Files.createDirectories(Paths.get(DIRECTORY));
        	
            Matte matte = new Matte();

            matte.multiplikation();

            matte.tiokompisar_addition();
            matte.tiokompisar_subtraktion();

            matte.addition();
            matte.addition_tiotalsovergang();

            matte.subtraktion();
            matte.subtraktion_hoga_ental();
            matte.subtraktion_tiotalsovergang();
            matte.subtraktion_mix();

            matte.hundrakompisar();
            matte.alla_tabeller();

            matte.test();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

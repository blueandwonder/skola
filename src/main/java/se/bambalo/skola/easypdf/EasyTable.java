package se.bambalo.skola.easypdf;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

public class EasyTable extends EasyObject<EasyTable> {

    public enum FillOrder {
        BY_ROW,
        BY_COLUMN
    }

    private ArrayList<EasyCell> cells = new ArrayList<>();
    private int rows;
    private int columns;
    private float[] widths;
    private boolean border;
    private float height;
    private FillOrder fillOrder = FillOrder.BY_ROW;

    public EasyTable rows(int rows) {
        this.rows = rows;
        return this;
    }

    public EasyTable columns(int columns) {
        this.columns = columns;
        return this;
    }

    public EasyTable widths(float... widths) {
        if (widths.length != columns) {
            throw new IllegalArgumentException("Widths must be equal to Columns");
        }
        this.widths = widths;
        return this;
    }

    public EasyTable height(float height) {
        this.height = height;
        return this;
    }

    public EasyTable withBorder() {
        border = true;
        return this;
    }

    public EasyTable withoutBorder() {
        border = false;
        return this;
    }

    public EasyTable fillOrder(FillOrder fillOrder) {
        this.fillOrder = fillOrder;
        return this;
    }

    public EasyTable add(EasyCell cell) {
        if (height > 0) {
            cell.height(height);
        }
        if (border) {
            cell.withBorder();
        }
        cells.add(cell);
        return this;
    }

    @Override
    public void append(Document document) throws Exception {
        Table table = new Table(getWidths());
        table.setFixedLayout();
        table.setWidth(UnitValue.createPercentValue(100));

        setup(table);
        fillLastRow();

        for (EasyCell each : getOrderedCells()) {
            table.addCell(each.createCell());
        }

        document.add(table);
    }

    private float[] getWidths() {
        if (widths != null) {
            return widths;
        }

        float[] result = new float[columns];
        for (int i = 0; i < columns; i++) {
            result[i] = 10;
        }

        return result;
    }

    private List<EasyCell> getOrderedCells() {
        if (fillOrder == FillOrder.BY_ROW) {
            return cells;
        }
        List<EasyCell> result = new ArrayList<>();

        int expectedSize = rows * columns;
        if (cells.size() != expectedSize) {
            throw new IllegalStateException("Expected size: " + expectedSize + " Actual size: " + cells.size());
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int index = j * rows + i;
                result.add(cells.get(index));
            }
        }

        return result;
    }

    private void fillLastRow() {
        int cellsOnLastRow = cells.size() % columns;
        if (cellsOnLastRow != 0) {
            for (int i = cellsOnLastRow; i < columns; i++) {
                add(EasyCell.EMTPY);
            }
        }
    }

    @Override
    public EasyTable self() {
        return this;
    }
}

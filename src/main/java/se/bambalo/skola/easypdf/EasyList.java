package se.bambalo.skola.easypdf;

import java.util.ArrayList;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;

public class EasyList extends EasyObject<EasyList> {

    private java.util.List<EasyListItem> items = new ArrayList<>();

    @Override
    public EasyList self() {
        return this;
    }

    public EasyList add(EasyListItem item) {
        items.add(item);
        return this;
    }

    @Override
    void append(Document document) throws Exception {
        List list = new List();

        // TODO add setter for ListSymbol. Also see ListNumberingType
        list.setListSymbol("* ");

        setup(list);

        for (EasyListItem each : items) {
            list.add(each.createListItem());
        }

        document.add(new Paragraph().add(list));
    }

}

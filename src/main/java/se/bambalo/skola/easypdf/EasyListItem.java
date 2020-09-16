package se.bambalo.skola.easypdf;

import com.itextpdf.layout.element.ListItem;

public class EasyListItem extends EasyFormat<EasyListItem> {

    private String text;

    public EasyListItem(String format, Object... args) {
        text = String.format(format, args);
    }

    @Override
    public EasyListItem self() {
        return this;
    }

    ListItem createListItem() throws Exception {
        ListItem item = new ListItem(text);
        setup(item);
        return item;
    }

}

package se.bambalo.skola.easypdf;

import com.itextpdf.layout.Document;

abstract class EasyObject<T extends EasyFormat<?>> extends EasyFormat<T> {

    abstract void append(Document document);

}

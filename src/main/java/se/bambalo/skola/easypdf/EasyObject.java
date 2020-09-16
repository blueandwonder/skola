package se.bambalo.skola.easypdf;

import com.itextpdf.layout.Document;

public abstract class EasyObject<T extends EasyFormat<?>> extends EasyFormat<T> {

    abstract void append(Document document) throws Exception;

}

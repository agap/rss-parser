package aga.rssparser.model;

/**
 * @author artem
 * Created on 17.08.14.
 */
public abstract class FieldTypeAware {
    private static final String ENCLOSURE = "enclosure";

    public boolean isTextTag(final String tagName) {
        return false;
    }

    public boolean isEnclosure(final String tagName) {
        return ENCLOSURE.equalsIgnoreCase(tagName);
    }
}

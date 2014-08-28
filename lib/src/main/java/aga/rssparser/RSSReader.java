package aga.rssparser;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import aga.rssparser.model.Enclosure;
import aga.rssparser.model.FieldTypeAware;
import aga.rssparser.model.RSSChannel;
import aga.rssparser.model.RSSItem;

/**
 * @author artem
 * Created on 16.08.14.
 */

public class RSSReader {
    public RSSChannel readFrom(final String url) throws MalformedURLException, RSSReadException {
        return readFrom(new URL(url));
    }

    public RSSChannel readFrom(final URL url) throws RSSReadException {
        InputStream is;
        BufferedInputStream bis = null;

        try {
            is = url.openStream();
            bis = new BufferedInputStream(is);

            final XmlPullParser parser = Xml.newPullParser();
            parser.setInput(bis, null);
            parser.nextTag();
            parser.nextTag();

            return parse(parser);
        } catch (IOException e) {
            throw new RSSReadException("Exception during the read from remote resource", e);
        } catch (XmlPullParserException e) {
            throw new RSSReadException("Issue during the rss parsing", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    Log.e(
                        RSSReader.class.getCanonicalName(),
                        String.format("can't close input stream of url %s : %s", url.toString(), e.toString())
                    );
                }
            }
        }
    }

    private RSSChannel parse(final XmlPullParser parser) throws RSSReadException {
        try {
            final RSSChannel.Builder channelBuilder = new RSSChannel.Builder();
            final List<RSSItem> items = new ArrayList<>();

            parser.require(XmlPullParser.START_TAG, null, RSSChannel.ROOT_TAG);

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                switch (parser.getName()) {
                    case RSSItem.ROOT_TAG:
                        items.add(new RSSItem(parseRSSItem(parser)));
                        break;
                    default:
                        setBuilderField(parser, channelBuilder);
                        break;
                }
            }

            final RSSChannel channel = new RSSChannel(channelBuilder);
            channel.getItems().addAll(items);

            return channel;
        } catch (XmlPullParserException | IOException e) {
            throw new RSSReadException("Issue during the rss parsing", e);
        }
    }

    private RSSItem.Builder parseRSSItem(final XmlPullParser parser) throws RSSReadException {
        try {
            final RSSItem.Builder builder = new RSSItem.Builder();
            parser.require(XmlPullParser.START_TAG, null, RSSItem.ROOT_TAG);

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                setBuilderField(parser, builder);
            }

            return builder;
        } catch (XmlPullParserException | IOException e) {
            throw new RSSReadException("Issue during the rss parsing", e);
        }
    }

    private void setBuilderField(final XmlPullParser parser, final FieldTypeAware builder) throws RSSReadException {
        final String tagName = parser.getName();

        try {
            if (builder.isTextTag(tagName)) {
                parser.require(XmlPullParser.START_TAG, null, tagName);
                final String value = readText(parser);
                parser.require(XmlPullParser.END_TAG, null, tagName);

                final Method m = builder.getClass().getMethod("set" + Utils.capitalize(tagName), String.class);

                if (m != null) {
                    m.invoke(builder, value);
                }
            } else if (builder.isEnclosure(tagName)) {
                final Enclosure.Builder enclosureBuilder = parseEnclosure(parser);

                final Method m = builder.getClass().getMethod("setEnclosure", Enclosure.class);

                if (m != null) {
                    m.invoke(builder, new Enclosure(enclosureBuilder));
                }
            } else {
                skip(parser);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.e(RSSReader.class.getName(), "no set method for tag named " + tagName);
        } catch (IOException | XmlPullParserException e) {
            throw new RSSReadException("Issue during the rss parsing", e);
        }
    }

    private Enclosure.Builder parseEnclosure(final XmlPullParser parser) throws IOException, XmlPullParserException {
        final Enclosure.Builder enclosureBuilder = new Enclosure.Builder();

        enclosureBuilder.setUrl(parser.getAttributeValue(null, "url"));
        enclosureBuilder.setType(parser.getAttributeValue(null, "type"));
        enclosureBuilder.setLength(Integer.parseInt(parser.getAttributeValue(null, "length")));
        parser.nextTag();

        return enclosureBuilder;
    }

    private void skip(final XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException("current event isn't a XmlPullParser.START_TAG");
        }

        int depth = 1;

        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";

        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }

        return result;
    }
}
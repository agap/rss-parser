package aga.rssparser.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import aga.rssparser.Utils;

/**
 * @author artem
 * Created on 16.08.14.
 */
@SuppressWarnings("UnusedDeclaration")
public class RSSItem implements Parcelable {
    public static final String ROOT_TAG = "item";

    private String mTitle;
    private String mLink;
    private String mDescription;
    private String mAuthor;
    private String mComments;

    private Date mPubDate;

    private Enclosure mEnclosure;

    public static final Creator<RSSItem> CREATOR = new Creator<RSSItem>() {
        @Override
        public RSSItem createFromParcel(Parcel source) {
            return new RSSItem(source);
        }

        @Override
        public RSSItem[] newArray(int size) {
            return new RSSItem[size];
        }
    };

    public RSSItem(final Builder builder) {
        mTitle = builder.getTitle();
        mLink = builder.getLink();
        mDescription = builder.getDescription();
        mAuthor = builder.getAuthor();
        mComments = builder.getComments();
        mPubDate = builder.getPubDate();
        mEnclosure = builder.getEnclosure();
    }

    private RSSItem(final Parcel source) {
        mTitle = source.readString();
        mLink = source.readString();
        mDescription = source.readString();
        mAuthor = source.readString();
        mComments = source.readString();
        mPubDate = new Date(source.readLong());
        mEnclosure = source.readParcelable(Enclosure.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mLink);
        dest.writeString(mDescription);
        dest.writeString(mAuthor);
        dest.writeString(mComments);
        dest.writeLong(mPubDate != null ? mPubDate.getTime() : 0L);
        dest.writeParcelable(mEnclosure, 0);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getComments() {
        return mComments;
    }

    public Date getPubDate() {
        return mPubDate;
    }

    public Enclosure getEnclosure() {
        return mEnclosure;
    }

    public static class Builder extends FieldTypeAware {
        private static final Set<String> TEXT_TAGS = new HashSet<>();

        static {
            TEXT_TAGS.add("title");
            TEXT_TAGS.add("link");
            TEXT_TAGS.add("description");
            TEXT_TAGS.add("author");
            TEXT_TAGS.add("comments");
            TEXT_TAGS.add("pubDate");
        }

        private String mTitle;
        private String mLink;
        private String mDescription;
        private String mAuthor;
        private String mComments;

        private Date mPubDate;

        private Enclosure mEnclosure;

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getLink() {
            return mLink;
        }

        public void setLink(String link) {
            mLink = link;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public void setAuthor(String author) {
            mAuthor = author;
        }

        public String getComments() {
            return mComments;
        }

        public void setComments(String comments) {
            mComments = comments;
        }

        public Date getPubDate() {
            return mPubDate;
        }

        public void setPubDate(String pubDate) {
            mPubDate = Utils.toDate(pubDate);
        }

        public Enclosure getEnclosure() {
            return mEnclosure;
        }

        public void setEnclosure(Enclosure enclosure) {
            mEnclosure = enclosure;
        }

        @Override
        public boolean isTextTag(final String tagName) {
            return TEXT_TAGS.contains(tagName);
        }
    }
}
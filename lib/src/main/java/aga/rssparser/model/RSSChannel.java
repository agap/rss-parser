package aga.rssparser.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aga.rssparser.Utils;

/**
 * @author artem
 * Created on 16.08.14.
 */
@SuppressWarnings("UnusedDeclaration")
public class RSSChannel implements Parcelable {
    public static final String ROOT_TAG = "channel";

    private String mTitle;
    private String mLink;
    private String mDescription;

    private Date mPubDate;
    private Date mLastBuildDate;

    private final List<RSSItem> mItems = new ArrayList<>();

    public static final Creator<RSSChannel> CREATOR = new Creator<RSSChannel>() {
        @Override
        public RSSChannel createFromParcel(Parcel source) {
            return new RSSChannel(source);
        }

        @Override
        public RSSChannel[] newArray(int size) {
            return new RSSChannel[size];
        }
    };

    public RSSChannel(final Builder builder) {
        mTitle = builder.getTitle();
        mLink = builder.getLink();
        mDescription = builder.getDescription();

        mPubDate = builder.getPubDate();
        mLastBuildDate = builder.getLastBuildDate();
    }

    private RSSChannel(final Parcel source) {
        mTitle = source.readString();
        mLink = source.readString();
        mDescription = source.readString();

        mPubDate = new Date(source.readLong());
        mLastBuildDate = new Date(source.readLong());

        source.readTypedList(mItems, RSSItem.CREATOR);
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

        dest.writeLong(mPubDate != null ? mPubDate.getTime() : 0L);
        dest.writeLong(mLastBuildDate != null ? mLastBuildDate.getTime() : 0L);

        dest.writeTypedList(mItems);
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

    public Date getPubDate() {
        return mPubDate;
    }

    public Date getLastBuildDate() {
        return mLastBuildDate;
    }

    public List<RSSItem> getItems() {
        return mItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ((Object) this).getClass() != o.getClass()) return false;

        RSSChannel channel = (RSSChannel) o;

        if (!mDescription.equals(channel.mDescription)) return false;
        if (!mItems.equals(channel.mItems)) return false;
        if (mLastBuildDate != null ? !mLastBuildDate.equals(channel.mLastBuildDate) : channel.mLastBuildDate != null)
            return false;
        if (!mLink.equals(channel.mLink)) return false;
        if (mPubDate != null ? !mPubDate.equals(channel.mPubDate) : channel.mPubDate != null)
            return false;
        //noinspection RedundantIfStatement
        if (!mTitle.equals(channel.mTitle)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mTitle.hashCode();
        result = 31 * result + mLink.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + (mPubDate != null ? mPubDate.hashCode() : 0);
        result = 31 * result + (mLastBuildDate != null ? mLastBuildDate.hashCode() : 0);
        result = 31 * result + (mItems.hashCode());
        return result;
    }

    public static class Builder extends FieldTypeAware {
        private static final Set<String> TEXT_TAGS = new HashSet<>();

        static {
            TEXT_TAGS.add("title");
            TEXT_TAGS.add("link");
            TEXT_TAGS.add("description");
            TEXT_TAGS.add("pubDate");
            TEXT_TAGS.add("lastBuildDate");
        }

        private String mTitle;
        private String mLink;
        private String mDescription;

        private Date mPubDate;
        private Date mLastBuildDate;

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

        public Date getPubDate() {
            return mPubDate;
        }

        public void setPubDate(String pubDate) {
            mPubDate = Utils.toDate(pubDate);
        }

        public Date getLastBuildDate() {
            return mLastBuildDate;
        }

        public void setLastBuildDate(String lastBuildDate) {
            mLastBuildDate = Utils.toDate(lastBuildDate);
        }

        @Override
        public boolean isTextTag(final String tagName) {
            return TEXT_TAGS.contains(tagName);
        }
    }
}
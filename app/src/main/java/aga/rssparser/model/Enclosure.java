package aga.rssparser.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author artem
 * Created on 27.08.14.
 */
@SuppressWarnings("UnusedDeclaration")
public class Enclosure implements Parcelable {
    public String mUrl;
    public String mType;
    public int mLength;

    public static final Creator<Enclosure> CREATOR = new Creator<Enclosure>() {
        @Override
        public Enclosure createFromParcel(Parcel source) {
            return new Enclosure(source);
        }

        @Override
        public Enclosure[] newArray(int size) {
            return new Enclosure[size];
        }
    };

    public Enclosure(final Parcel source) {
        mUrl = source.readString();
        mType = source.readString();
        mLength = source.readInt();
    }

    public Enclosure(final Builder builder) {
        mUrl = builder.getUrl();
        mType = builder.getType();
        mLength = builder.getLength();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeString(mType);
        dest.writeInt(mLength);
    }

    public String getUrl() {
        return mUrl;
    }

    public String getType() {
        return mType;
    }

    public int getLength() {
        return mLength;
    }

    public static class Builder extends FieldTypeAware {
        public String mUrl;
        public String mType;
        public int mLength;

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            mUrl = url;
        }

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }

        public int getLength() {
            return mLength;
        }

        public void setLength(int length) {
            mLength = length;
        }
   }
}
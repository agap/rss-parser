package aga.rssparser.sample;

import android.view.View;
import android.widget.TextView;

/**
 * @author artem
 * Created on 17.08.14.
 */
public class ViewHolder {
    private final TextView mTitle;
    private final TextView mLink;
    private final TextView mPubDate;

    public ViewHolder(final View rootView) {
        mTitle = (TextView) rootView.findViewById(R.id.title);
        mLink = (TextView) rootView.findViewById(R.id.link);
        mPubDate = (TextView) rootView.findViewById(R.id.pub_date);
    }

    public TextView getTitle() {
        return mTitle;
    }

    public TextView getLink() {
        return mLink;
    }

    public TextView getPubDate() {
        return mPubDate;
    }
}
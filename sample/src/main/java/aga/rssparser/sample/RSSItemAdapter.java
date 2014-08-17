package aga.rssparser.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;

import aga.rssparser.model.RSSItem;

/**
 * @author artem
 * Created on 17.08.14.
 */
public class RSSItemAdapter extends ArrayAdapter<RSSItem> {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final LayoutInflater mLayoutInflater;

    public RSSItemAdapter(Context context) {
        super(context, 0);

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RSSItem item = getItem(position);

        final View root = convertView == null
                ? mLayoutInflater.inflate(R.layout.rss_item, parent, false)
                : convertView;

        ViewHolder holder = (ViewHolder) root.getTag();

        if (holder == null) {
            holder = new ViewHolder(root);
            root.setTag(holder);
        }

        holder.getTitle().setText(item.getTitle());
        holder.getLink().setText(item.getLink());

        holder.getPubDate().setText("Published " + FORMATTER.format(item.getPubDate()));

        return root;
    }
}

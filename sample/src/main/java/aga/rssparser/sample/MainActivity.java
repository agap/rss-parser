package aga.rssparser.sample;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.net.MalformedURLException;

import aga.rssparser.RSSReadException;
import aga.rssparser.RSSReader;
import aga.rssparser.model.RSSChannel;
import aga.rssparser.model.RSSItem;

public class MainActivity extends ListActivity {
    private ArrayAdapter<RSSItem> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new RSSItemAdapter(this);

        setListAdapter(mAdapter);

        new AsyncTask<Void, Void, RSSChannel> () {
            @Override
            protected RSSChannel doInBackground(Void... params) {
                try {
                    final RSSReader reader = new RSSReader();

                    return reader.readFrom("http://feeds.feedburner.com/androidcentral?format=xml");
                } catch (MalformedURLException | RSSReadException ignored) {
                    Log.e(MainActivity.class.getName(), ignored.toString());
                }

                return null;
            }

            @Override
            protected void onPostExecute(final RSSChannel channel) {
                if (channel != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        mAdapter.addAll(channel.getItems());
                    } else {
                        for (RSSItem item : channel.getItems()) {
                            mAdapter.add(item);
                        }
                    }

                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "RSS loading error", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null);
    }
}

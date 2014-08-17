## rss-parser

**rss-parser** is an Open Source Android library which allows developers to easily parse RSS feeds which conform to the RSS 2.0.
Repository also contains sample application.

## Usage
The API is pretty straightforward:

* Create the instance of the `RSSReader`.
* Call one of its `readFrom` methods which accept either `String` or `URL` parameter.
* Get the `RSSChannel` which contains channel-related fields along with the list of `RSSItem`s. Both classes implement `Parcelable` interface, s you can pass them wherever you like.
* Step 4 is omitted.

Here goes the code:

    final RSSReader reader = new RSSReader();
    final RSSChannel channel = reader.readFrom("http://feeds.feedburner.com/androidcentral?format=xml");

## Authors

Artem Gapchenko (mailto:artemgapchenko@yandex.ru)
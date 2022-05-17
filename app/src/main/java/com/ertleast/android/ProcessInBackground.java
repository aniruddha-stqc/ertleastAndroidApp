package com.ertleast.android;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
{
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> links = new ArrayList<>();
    ArrayList<String> image_urls = new ArrayList<>();
    String rss_feed_url;

    Exception exception = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Exception doInBackground(Integer... params) {

        try
        {
            //URL url = new URL("http://feeds.news24.com/articles/fin24/tech/rss");
            URL url = new URL(rss_feed_url);
            int count = 0;
            //creates new instance of PullParserFactory that can be used to create XML pull parsers
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            //Specifies whether the parser produced by this factory will provide support
            //for XML namespaces
            factory.setNamespaceAware(false);

            //creates a new instance of a XML pull parser using the currently configured
            //factory features
            XmlPullParser xpp = factory.newPullParser();

            // We will get the XML from an input stream
            xpp.setInput(getInputStream(url), "UTF_8");

            /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
             * We should take into consideration that the rss feed name is also enclosed in a "<title>" tag.
             * Every feed begins with these lines: "<channel><title>Feed_Name</title> etc."
             * We should skip the "<title>" tag which is a child of "<channel>" tag,
             * and take into consideration only the "<title>" tag which is a child of the "<item>" tag
             *
             * In order to achieve this, we will make use of a boolean variable called "insideItem".
             */
            boolean insideItem = false;
            boolean insideItemImage = false;

            // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT, END_DOCUMENT etc..
            int eventType = xpp.getEventType(); //loop control variable

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                //if we are at a START_TAG (opening tag)
                if (eventType == XmlPullParser.START_TAG)
                {
                    //if the tag is called "item"
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = true;
                    }
                    //if the tag is called "title"
                    //else if (xpp.getName().equalsIgnoreCase("title"))
                    else if (xpp.getName().equalsIgnoreCase("image"))
                    {
                        if (insideItem)
                        {
                            insideItemImage = true;
                            // extract the text between <title> and </title>
                            //titles.add(xpp.nextText());
                        }
                    }
                    //if the tag is called "link"
                    else if (xpp.getName().equalsIgnoreCase("link"))
                    {
                        if (insideItemImage)
                        {
                            if(count == 10)
                                break;
                            // extract the text between <link> and </link>
                            links.add(xpp.nextText());
                            count++;
                        }
                    }
                    //if the tag is called "title"
                    else if (xpp.getName().equalsIgnoreCase("title"))
                    {
                        if (insideItemImage)
                        {
                            // extract the text between <link> and </link>
                            titles.add(xpp.nextText());
                        }
                    }
                    //if the tag is called "url"
                    else if (xpp.getName().equalsIgnoreCase("url"))
                    {
                        if (insideItemImage)
                        {
                            // extract the text between <link> and </link>
                            image_urls.add(xpp.nextText());
                        }
                    }
                }
                //if we are at an END_TAG and the END_TAG is called "item"
                else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                {
                    insideItem = false;
                    insideItemImage = false;
                }

                eventType = xpp.next(); //move to next element
            }


        } catch (IOException e)
        {
            exception = e;
        } catch (XmlPullParserException e)
        {
            exception = e;
        }

        return exception;
    }

    @Override
    protected void onPostExecute(Exception s) {
        super.onPostExecute(s);
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

}
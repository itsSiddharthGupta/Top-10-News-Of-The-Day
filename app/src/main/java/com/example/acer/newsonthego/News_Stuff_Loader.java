package com.example.acer.newsonthego;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;


public class News_Stuff_Loader extends AsyncTaskLoader<ArrayList<News_Stuff>> {
    String categoryCode;
    String countryCode;
    public News_Stuff_Loader(Context context,String countryCode,String categoryCode) {
        super(context);
        this.categoryCode = categoryCode;
        this.countryCode = countryCode;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<News_Stuff> loadInBackground() {
        ArrayList<News_Stuff> news = new ArrayList<News_Stuff>();
        String data = ((new HTTPNewsClient(countryCode,categoryCode)).getNewsStuff());

        //If the network is disconnected in between fetching then inputStream is null
        //                                                      =>data is null
        //and hence our app will crash
        //Therefore we need to hanc=dle this problem...
        if(data != null) {
            try {
                for (int count = 0; count < 10; count++) {
                    News_Stuff news_stuff = new News_Stuff();
                    JSON_newsParser json_newsParser = new JSON_newsParser(categoryCode);
                    news_stuff = json_newsParser.get_News_Stuff(data, count);
                    news.add(news_stuff);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return news;
        }
        return null;
    }
}

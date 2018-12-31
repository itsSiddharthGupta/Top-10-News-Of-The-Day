package com.example.acer.newsonthego;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON_newsParser{
    static String category;
    public JSON_newsParser(String category){
        this.category = category;
    }
    public static News_Stuff get_News_Stuff(String url,int index) throws JSONException{
        News_Stuff news_stuff = new News_Stuff();
        JSONObject rootObject = new JSONObject(url);
        JSONArray jsonArray = rootObject.getJSONArray("articles");
        JSONObject jsonObject = jsonArray.getJSONObject(index);
        news_stuff.setTitle(category);

        if(getString("author",jsonObject).equalsIgnoreCase("null"))
            news_stuff.setAuthor("");
        else
            news_stuff.setAuthor(getString("author",jsonObject));

        news_stuff.setHeadline(getString("title",jsonObject));
        news_stuff.setDate(getString("publishedAt",jsonObject).substring(0,10));
        news_stuff.setUrl(getString("url",jsonObject));
        return news_stuff;
    }
    public static JSONObject getJSONObject(String tagName,JSONObject jsonObject) throws JSONException{
        return jsonObject.getJSONObject(tagName);
    }

    public static JSONArray getJSONArray(String tagName,JSONObject jsonObject) throws JSONException{
        return jsonObject.getJSONArray(tagName);
    }

    public static String getString(String tagName,JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(tagName);
    }

    public static long getDate(String tagName,JSONObject jsonObject) throws JSONException{
        return jsonObject.getLong(tagName);
    }
}

package com.example.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper method related to requesting and receiving news data from Guardian
 */
public class QueryUtils {

    private static final String TAG = "QueryUtils";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /*Read Timeout*/
    private static final int READ_TIMEOUT = 10000;

    /*Connect Timeout*/
    private static final int CONNECT_TIMEOUT = 10000;

    /**
     * @param queryUrl is the url to query the Guardian server
     * @return the list of articles
     */
    public static List<News> getArticleData(String queryUrl) {
        //Create the url
        URL url = createUrl(queryUrl);

        //Perform a HTTP request to the url and receive a JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request", e);
        }

        //Get the list of articles
        return extractFeatureFromJson(jsonResponse);
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        //Initialize a string for the JSON response from the Guardian server
        String jsonResponseFromGuardian = "";

        //Make sure the url is not null.
        if (url == null) {
            return jsonResponseFromGuardian;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        //Try making a connection
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Check if the request was successful. If it is, read the input stream
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponseFromGuardian = readFromInputStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(TAG, "Error retrieving JSON data from the Guardian server: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponseFromGuardian;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        //Make sure the input stream is not null before proceeding
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from parsing a JSON response.
     */
    public static List<News> extractFeatureFromJson(String newsData) {
        //  If the JSON string is empty of null, then return early.
        if (TextUtils.isEmpty(newsData)) {
            return null;
        }

        //  Create an empty ArrayList that we can start adding earthquakes to
        List<News> news = new ArrayList<News>();

        //  Try to parse the JSON response string. If there's a problem with the way the JSON is formatted,
        //  a JSONException object will be thrown.
        //  Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //  Create a JSONObject from the JSON response string
            JSONObject initialObject = new JSONObject(newsData);

            //  Extract the JSONObject associated with the key called "response"
            JSONObject response = initialObject.getJSONObject("response");

            //  Extract the JSONArray associated with the key called "results"
            JSONArray resultArray = response.getJSONArray("results");

            //  For each news in the resultArray, create a news object
            for (int i = 0; i < 1; i = i + 1) {

                //  Get a single news at position i within the list of news
                JSONObject currentObject = resultArray.getJSONObject(i);

                //  Extract the value for the key called "webTitle"
                String webTitle = currentObject.getString("webTitle");

                //  Extract the value for the key called "sectionName"
                String sectionName = currentObject.getString("sectionName");

                //  Extract the value for the key called "webPublicationDate"
                String time = currentObject.getString("webPublicationDate");

                //  Extract the value for the key called "webUrl"
                String url = currentObject.getString("webUrl");

                //  For a given earthquake, extract the JSONObject associated with the key called "fields", which represents the thumbnail
                JSONObject fields = currentObject.getJSONObject("fields");

                //  Extract the value for the key called "thumbnail"
                String thumbnail = fields.getString("thumbnail");

                //  For a given earthquake, extract the JSONArray associated with the key called "tags"
                JSONArray tagsArray = currentObject.getJSONArray("tags");

                //  Get the first tag (position 0) within the list of tags
                JSONObject currentTag = tagsArray.getJSONObject(0);

                //  Extract the value for the key called "webTitle"
                String author = currentTag.getString("webTitle");

                //  Create a new NEws object with thumbnail, webTitle, author, sectionName, time and url from the JSON response
                News newsObject = new News(thumbnail, webTitle, author, sectionName, time, url);

                //  Add the new News object to the list of news.
                news.add(newsObject);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        //  Return the list of news
        return news;
    }
}
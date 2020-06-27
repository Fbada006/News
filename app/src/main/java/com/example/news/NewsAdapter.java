package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * NewsAdapter is an ArrayAdapter that can provide the layout for each list item based on a data source, which is a list of news objects.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Create a new News object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param news    is the list of news to be displayed
     */
    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //  Check if an existing view is being reused, otherwise inflate the view.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.card_view_items, parent,false);
        }

        //  Get the news object located at this position in the list.
        News currentNews = getItem(position);

        //  Find the TextView in the card_view_items.xml layout with the ID thumbnail
        TextView thumbnail = listItemView.findViewById(R.id.thumbnail);
        //  Get the thumbnail from the object and set this text on thumbnail
        thumbnail.setText(currentNews.getThumbnail());

        //  Find the title in the card_view_items.xml layout with the ID title
        TextView title = listItemView.findViewById(R.id.title);
        //  Get the title from the object and set this text on title
        title.setText(currentNews.getTitle());

        //  Find the author in the card_view_items.xml layout with the ID content
        TextView author = listItemView.findViewById(R.id.author);
        //  Get the author from the object and set this text on author
        author.setText(currentNews.getAuthor());

        //  Find the section in the card_view_items.xml layout with the ID section
        TextView section = listItemView.findViewById(R.id.section);
        section.setText(currentNews.getSection());

        //  Find the time in the card_view_items.xml layout with the ID time
        TextView time = listItemView.findViewById(R.id.time);
        time.setText(currentNews.getTime());

        return listItemView;
    }
}
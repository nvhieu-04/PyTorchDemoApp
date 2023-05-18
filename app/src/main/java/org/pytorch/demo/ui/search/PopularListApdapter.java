package org.pytorch.demo.ui.search;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.pytorch.demo.R;

public class PopularListApdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemNames;
    private final Integer[] itemImages;

    public PopularListApdapter(Activity context, String[] itemNames, Integer[] itemImages) {
        super(context, R.layout.item_plant_popular, itemNames);
        this.context = context;
        this.itemNames = itemNames;
        this.itemImages = itemImages;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_plant_popular, null, true);

        TextView textView = rowView.findViewById(R.id.textView);
        ImageView imageView = rowView.findViewById(R.id.imageView);

        textView.setText(itemNames[position]);
        imageView.setImageResource(itemImages[position]);

        return rowView;
    }
}

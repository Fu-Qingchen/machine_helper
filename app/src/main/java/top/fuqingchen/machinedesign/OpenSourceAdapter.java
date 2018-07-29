package top.fuqingchen.machinedesign;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OpenSourceAdapter extends ArrayAdapter<OpenSource> {

    OpenSourceAdapter(Activity activity, ArrayList<OpenSource> list) {
        super(activity, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        OpenSource openSource = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.openSource_name);
        nameTextView.setText(openSource.getMname());
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "CaviarDreams.ttf");
        nameTextView.setTypeface(typeface);

        TextView numberTextView = listItemView.findViewById(R.id.openSource_detal);
        numberTextView.setText(openSource.getDetal());

        return listItemView;
    }
}

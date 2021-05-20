package chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.mds.R;
import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter {

    public static final int TYPE_LEFT = -1;
    public static final int TYPE_RIGHT = 1;
    public static final int TYPE_CENTER = 0;

    private ArrayList<ListTypeChat> objects;

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return objects.get(position).getType();
    }

    public ChatAdapter(Context context, int resource, ArrayList<ListTypeChat> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatViewHolder viewHolder;
        ListTypeChat listViewItem = objects.get(position);
        int listViewItemType = getItemViewType(position);


        if (convertView == null) {

            if (listViewItemType == TYPE_LEFT) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_right, null);
            } else if (listViewItemType == TYPE_RIGHT) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_left, null);
            }
            else{
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_center, null);
            }

            assert convertView != null;
            TextView textView = (TextView) convertView.findViewById(R.id.textMessage);
            viewHolder = new ChatViewHolder(textView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ChatViewHolder) convertView.getTag();
        }

        viewHolder.getText().setText(listViewItem.getText());

        return convertView;
    }

}

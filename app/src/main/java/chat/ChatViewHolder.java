package chat;

import android.widget.TextView;

public class ChatViewHolder {
    TextView text;

    public ChatViewHolder(TextView text) {
        this.text = text;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }
}

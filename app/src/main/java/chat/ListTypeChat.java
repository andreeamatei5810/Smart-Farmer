package chat;

public class ListTypeChat {

    private String text;
    private int type;

    public ListTypeChat(String text, int type) {
            this.text = text;
            this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}


package me.majiajie.pagerbottomtabstrip.item;


import android.graphics.drawable.Drawable;

public class ItemData
{
    private Drawable icon;

    private Drawable checkedIcon;

    private int color;

    private int checkedColor;

    private String title;

    private boolean checked;

    private int messageNumber;

    private boolean hasMessage;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getCheckedIcon() {
        return checkedIcon;
    }

    public void setCheckedIcon(Drawable checkedIcon) {
        this.checkedIcon = checkedIcon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCheckedColor() {
        return checkedColor;
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public boolean hasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }
}

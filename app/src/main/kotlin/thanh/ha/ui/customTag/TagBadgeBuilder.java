package thanh.ha.ui.customTag;


import androidx.annotation.Nullable;

public interface TagBadgeBuilder {

    void appendTag(String tagName, String badgeColor);

    void appendTag(String tagName, String badgeColor, @Nullable SpannableClickAction clickAction);

    CharSequence getTags();

    void clear();
}

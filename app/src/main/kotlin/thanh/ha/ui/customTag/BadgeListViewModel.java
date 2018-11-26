package thanh.ha.ui.customTag;


import java.util.ArrayList;
import java.util.List;

import thanh.ha.domain.DefinitionInfo;

public class BadgeListViewModel {
    private final TagBadgeBuilder badgeBuilder;
    private List<TagDto> tags;
    private SpannableClickAction clickAction;

    public BadgeListViewModel(TagBadgeBuilder badgeBuilder, SpannableClickAction clickAction) {
        this.badgeBuilder = badgeBuilder;
        this.tags = new ArrayList<>();
        this.clickAction = clickAction;
    }

    public CharSequence getRenderedTagBadges() {
        return createTagBadges();
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    public void clearTags() {
        badgeBuilder.clear();
    }

    private CharSequence createTagBadges() {
        // make every other badge clickable
        for (int i = 0; i < tags.size(); i++) {
            final TagDto tag = tags.get(i);
            if ((i + 1) % 2 == 0) {
                badgeBuilder.appendTag(tag.getName(), tag.getColorHex(), clickAction);
            } else {
                badgeBuilder.appendTag(tag.getName(), tag.getColorHex());
            }
        }
        return badgeBuilder.getTags();
    }

}

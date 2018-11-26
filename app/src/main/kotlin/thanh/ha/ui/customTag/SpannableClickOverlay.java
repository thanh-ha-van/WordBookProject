package thanh.ha.ui.customTag;

import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;


public class SpannableClickOverlay extends ClickableSpan {

    private final SpannableClickAction clickAction;

    public SpannableClickOverlay(@NonNull SpannableClickAction clickAction) {
        this.clickAction = clickAction;
    }

    @Override
    public void onClick(View view) {
        clickAction.onClick();
    }
}


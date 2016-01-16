package chickennugget.spaceengineersdata.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

public interface CardViewHelper {

    void setBackground(View view, Drawable drawable);

    void setButtonBackground(ImageButton imageButton, int buttonDrawableResource);

    void setCardSelector(View viewClickable, Drawable defaultDrawable);

    void setElevation(View view, float elevation);

    Drawable getResourceFromAttrs(Context themedContext, int attr);
}

package chickennugget.spaceengineersdata.unused.cards.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class CardViewHelperImplJB extends CardViewHelperImplBase {

    public CardViewHelperImplJB(Context context) {
        super(context);
    }

    @Override
    public void setBackground(View view, Drawable drawable) {
        if (view != null)
            view.setBackground(drawable);
    }

    @Override
    public void setButtonBackground(ImageButton imageButton, int buttonDrawableResource) {
        setBackground(imageButton, mContext.getResources().getDrawable(buttonDrawableResource));
    }
}

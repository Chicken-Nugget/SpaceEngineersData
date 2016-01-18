package chickennugget.spaceengineersdata.cards.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageButton;

import chickennugget.spaceengineersdata.cards.interfaces.CardViewHelper;

public class CardViewHelperImplBase implements CardViewHelper {

    protected Context mContext;

    public CardViewHelperImplBase(Context context) {
        mContext = context;
    }

    @Override
    public void setBackground(View view, Drawable drawable) {
        if (view != null)
            view.setBackgroundDrawable(drawable);
    }

    @Override
    public void setButtonBackground(ImageButton imageButton, int buttonDrawableResource) {
        setBackground(imageButton, mContext.getResources().getDrawable(buttonDrawableResource));
    }

    @Override
    public void setCardSelector(View viewClickable, Drawable defaultDrawable) {
        setBackground(viewClickable, defaultDrawable);
    }

    @Override
    public void setElevation(View view, float elevation) {
        ViewCompat.setElevation(view, elevation);
    }

    @Override
    public Drawable getResourceFromAttrs(Context themedContext, int attr) {
        int[] attrs = new int[]{attr /* index 0 */};
        TypedArray ta = themedContext.obtainStyledAttributes(attrs);
        Drawable drawableFromTheme = ta.getDrawable(0 /* index */);
        ta.recycle();
        return drawableFromTheme;
    }
}

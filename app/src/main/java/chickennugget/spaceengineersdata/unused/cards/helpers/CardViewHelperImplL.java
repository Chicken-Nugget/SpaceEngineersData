package chickennugget.spaceengineersdata.unused.cards.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CardViewHelperImplL extends CardViewHelperImplKK {

    public CardViewHelperImplL(Context context) {
        super(context);
    }

    @Override
    public void setButtonBackground(ImageButton imageButton, int buttonDrawableResource) {
        imageButton.setImageResource(buttonDrawableResource);
    }

    @Override
    public void setElevation(View view, float elevation) {
        if (view != null) {
            view.setElevation(elevation);
        }
    }
}

package chickennugget.spaceengineersdata.unused.cards.helpers;

import android.content.Context;
import android.os.Build;

import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewHelper;

public class CardViewHelperUtil {

    public static CardViewHelper getInstance(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            return new CardViewHelperImplL(context);
        else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
            return new CardViewHelperImplKK(context);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            return new CardViewHelperImplJB(context);
        else return new CardViewHelperImplBase(context);
    }
}

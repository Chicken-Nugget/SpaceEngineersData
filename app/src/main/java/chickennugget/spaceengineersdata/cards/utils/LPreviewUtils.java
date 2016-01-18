package chickennugget.spaceengineersdata.cards.utils;

import chickennugget.spaceengineersdata.cards.activity.BaseActivity;

public class LPreviewUtils {

    private LPreviewUtils() {
    }

    public static LPreviewUtilsBase getInstance(BaseActivity activity) {
        return new LPreviewUtilsBase(activity);
    }
}

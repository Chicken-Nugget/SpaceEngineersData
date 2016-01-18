package chickennugget.spaceengineersdata.unused.cards.utils;

import chickennugget.spaceengineersdata.unused.cards.activity.BaseActivity;

public class LPreviewUtils {

    private LPreviewUtils() {
    }

    public static LPreviewUtilsBase getInstance(BaseActivity activity) {
        return new LPreviewUtilsBase(activity);
    }
}

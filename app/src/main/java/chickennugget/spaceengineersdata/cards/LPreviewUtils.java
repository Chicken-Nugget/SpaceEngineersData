package chickennugget.spaceengineersdata.cards;

public class LPreviewUtils {

    private LPreviewUtils() {
    }

    public static LPreviewUtilsBase getInstance(BaseActivity activity) {
        return new LPreviewUtilsBase(activity);
    }
}

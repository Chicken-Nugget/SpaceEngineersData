package chickennugget.spaceengineersdata.unused.cards.others;

import android.os.Build;

import chickennugget.spaceengineersdata.BuildConfig;

public class Constants {

    public static int API_L = Build.VERSION_CODES.LOLLIPOP;

    public static class IntentManager {

        public static final String INTENT_ACTION_IMAGE_DOWNLOADED = "chickennugget.spaceengineersdata.unused.cards.intent.action.IMAGE_DOWNLOADED";
        public static final String INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_RESULT = "ExtraResult";
        public static final String INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_ERROR_LOADING = "ExtraErrorLoading";
        public static final String INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_CARD_ID = "ExtraCardId";
        public static final String AUTHORITY_CARDDEMO = buildAuthority();

        private static String buildAuthority() {
            String authority = "chickennugget.spaceengineersdata.unused.cards.provider";
            if (BuildConfig.DEBUG) authority += ".debug";
            return authority;
        }
    }
}

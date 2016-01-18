package chickennugget.spaceengineersdata.unused.cards.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.utils.LPreviewUtilsBase;

public abstract class BaseActivity extends AppCompatActivity {

    protected static final int NAVDRAWER_ITEM_NATIVE_CARDSLIB = 0;
    protected static final int NAVDRAWER_ITEM_CARDSLIB_V1 = 1;
    protected static final int NAVDRAWER_ITEM_GUIDELINES = 2;
    protected static final int NAVDRAWER_ITEM_GITHUB = 3;
    protected static final int NAVDRAWER_ITEM_DONATE = 4;
    protected static final int NAVDRAWER_ITEM_INFO = 5;
    protected static final int NAVDRAWER_ITEM_INVALID = -1;
    protected static final int NAVDRAWER_ITEM_SEPARATOR = -2;
    protected static final int NAVDRAWER_ITEM_SEPARATOR_SPECIAL = -3;
    private static final String TAG = null;
    private static final TypeEvaluator ARGB_EVALUATOR = new ArgbEvaluator();
    private static final int HEADER_HIDE_ANIM_DURATION = 300;
    // Titles for navdrawer items (indices must correspond to the above)
    private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.navdrawer_item_native_cardslib,
            R.string.navdrawer_item_cardslib_v1,
            R.string.navdrawer_item_guidelines,
            R.string.navdrawer_item_github,
            R.string.navdrawer_item_donate,
            R.string.navdrawer_item_info
    };
    // Icons for navdrawer items (indices must correspond to above array)
    private static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_warning_black_36dp,
            R.drawable.ic_github,
            R.drawable.ic_money,
            R.drawable.ic_l_info
    };
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
    private static final int MAIN_CONTENT_FADEIN_DURATION = 250;
    private LPreviewUtilsBase mLPreviewUtils;
    private DrawerLayout mDrawerLayout;
    private ObjectAnimator mStatusBarColorAnimator;
    private int mThemedStatusBarColor;
    private int mNormalStatusBarColor;
    private ArrayList<View> mHideableHeaderViews = new ArrayList<View>();
    private Toolbar mActionBarToolbar;
    private ArrayList<Integer> mNavDrawerItems = new ArrayList<Integer>();
    private ViewGroup mDrawerItemsListContainer;
    private View[] mNavDrawerItemViews = null;
    private boolean mActionBarAutoHideEnabled = false;
    private boolean mActionBarShown = true;

    private Runnable mDeferredOnDrawerClosedRunnable;

    private Handler mHandler;

    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }
        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("_uri", data);
        }
        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }
        return arguments;
    }

    public static Intent fragmentArgumentsToIntent(Bundle arguments) {
        Intent intent = new Intent();
        if (arguments == null) {
            return intent;
        }
        final Uri data = arguments.getParcelable("_uri");
        if (data != null) {
            intent.setData(data);
        }
        intent.putExtras(arguments);
        intent.removeExtra("_uri");
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLPreviewUtils = LPreviewUtilsBase.getInstance(this);
        mThemedStatusBarColor = getResources().getColor(R.color.demo_colorPrimaryDark);
        mNormalStatusBarColor = mThemedStatusBarColor;
        mHandler = new Handler();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
    }

    private void setupNavDrawer() {
        int selfItem = getSelfNavDrawerItem();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.demo_colorPrimaryDark));
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        populateNavDrawer();
    }

    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_INVALID;
    }

    public LPreviewUtilsBase getLPreviewUtils() {
        return mLPreviewUtils;
    }

    // Subclasses can override this for custom behavior
    protected void onNavDrawerStateChanged(boolean isOpen, boolean isAnimating) {
        if (mActionBarAutoHideEnabled && isOpen) {
            autoShowOrHideActionBar(true);
        }
    }

    protected void autoShowOrHideActionBar(boolean show) {
        if (show == mActionBarShown) {
            return;
        }
        mActionBarShown = show;
        onActionBarAutoShowOrHide(show);
    }

    private void updateStatusBarForNavDrawerSlide(float slideOffset) {
    }

    protected void onNavDrawerSlide(float offset) {
    }

    // Populates the navigation drawer with the appropriate items.
    private void populateNavDrawer() {
        mNavDrawerItems.clear();
        // Explore is always shown
        mNavDrawerItems.add(NAVDRAWER_ITEM_NATIVE_CARDSLIB);
        mNavDrawerItems.add(NAVDRAWER_ITEM_SEPARATOR);
        mNavDrawerItems.add(NAVDRAWER_ITEM_CARDSLIB_V1);
        mNavDrawerItems.add(NAVDRAWER_ITEM_SEPARATOR_SPECIAL);
        mNavDrawerItems.add(NAVDRAWER_ITEM_GUIDELINES);
        mNavDrawerItems.add(NAVDRAWER_ITEM_GITHUB);
        mNavDrawerItems.add(NAVDRAWER_ITEM_DONATE);
        mNavDrawerItems.add(NAVDRAWER_ITEM_INFO);
    }

    private View makeNavDrawerItem(final int itemId, ViewGroup container) {
        boolean selected = getSelfNavDrawerItem() == itemId;
        int layoutToInflate = 0;
        View view = getLayoutInflater().inflate(layoutToInflate, container, false);
        if (isSeparator(itemId)) return view;
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        int iconId = itemId >= 0 && itemId < NAVDRAWER_ICON_RES_ID.length ? NAVDRAWER_ICON_RES_ID[itemId] : 0;
        int titleId = itemId >= 0 && itemId < NAVDRAWER_TITLE_RES_ID.length ? NAVDRAWER_TITLE_RES_ID[itemId] : 0;
        // Set icon and text
        iconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
        if (iconId > 0) iconView.setImageResource(iconId);
        titleView.setText(getString(titleId));
        return view;
    }

    private boolean isSpecialItem(int itemId) {
        return itemId == NAVDRAWER_ITEM_DONATE || itemId == NAVDRAWER_ITEM_INFO || itemId == NAVDRAWER_ITEM_GITHUB;
    }

    private boolean isSeparator(int itemId) {
        return itemId == NAVDRAWER_ITEM_SEPARATOR || itemId == NAVDRAWER_ITEM_SEPARATOR_SPECIAL;
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    protected void registerHideableHeaderView(View hideableHeaderView) {
        if (!mHideableHeaderViews.contains(hideableHeaderView)) {
            mHideableHeaderViews.add(hideableHeaderView);
        }
    }

    protected void deregisterHideableHeaderView(View hideableHeaderView) {
        if (mHideableHeaderViews.contains(hideableHeaderView)) {
            mHideableHeaderViews.remove(hideableHeaderView);
        }
    }

    public int getThemedStatusBarColor() {
        return mThemedStatusBarColor;
    }

    public void setNormalStatusBarColor(int color) {
        mNormalStatusBarColor = color;
        if (mDrawerLayout != null) {
            mDrawerLayout.setStatusBarBackgroundColor(mNormalStatusBarColor);
        }
    }

    protected void onActionBarAutoShowOrHide(boolean shown) {
        if (mStatusBarColorAnimator != null) {
            mStatusBarColorAnimator.cancel();
        }
        mStatusBarColorAnimator = ObjectAnimator.ofInt(
                (mDrawerLayout != null) ? mDrawerLayout : mLPreviewUtils,
                (mDrawerLayout != null) ? "statusBarBackgroundColor" : "statusBarColor",
                shown ? Color.BLACK : mNormalStatusBarColor,
                shown ? mNormalStatusBarColor : Color.BLACK)
                .setDuration(250);
        if (mDrawerLayout != null) {
            mStatusBarColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ViewCompat.postInvalidateOnAnimation(mDrawerLayout);
                }
            });
        }
        mStatusBarColorAnimator.setEvaluator(ARGB_EVALUATOR);
        mStatusBarColorAnimator.start();
        for (View view : mHideableHeaderViews) {
            if (shown) {
                view.animate()
                        .translationY(0)
                        .alpha(1)
                        .setDuration(HEADER_HIDE_ANIM_DURATION)
                        .setInterpolator(new DecelerateInterpolator());
            } else {
                view.animate()
                        .translationY(-view.getBottom())
                        .alpha(0)
                        .setDuration(HEADER_HIDE_ANIM_DURATION)
                        .setInterpolator(new DecelerateInterpolator());
            }
        }
    }
}

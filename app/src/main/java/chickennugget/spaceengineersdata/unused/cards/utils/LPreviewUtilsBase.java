package chickennugget.spaceengineersdata.unused.cards.utils;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.activity.BaseActivity;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LPreviewUtilsBase {

    protected BaseActivity mActivity;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mActionBarToolbar;

    LPreviewUtilsBase(BaseActivity activity) {
        mActivity = activity;
    }

    public static LPreviewUtilsBase getInstance(BaseActivity activity) {
        return new LPreviewUtilsBase(activity);
    }

    public boolean hasL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public boolean shouldChangeActionBarForDrawer() {
        return false;
    }

    public void setupCircleButton(ImageButton sourceButton) {
        if (hasL()) if (sourceButton != null) {
            final int size = mActivity.getResources().getDimensionPixelSize(R.dimen.hd_fab_size);
            sourceButton.setOutlineProvider(
                    new ViewOutlineProvider() {
                        @Override
                        public void getOutline(View view, Outline outline) {
                            outline.setOval(0, 0, size, size);
                        }
                    });
            sourceButton.setClipToOutline(true);
        }
    }

    public ActionBarDrawerToggle setupDrawerToggle(DrawerLayout drawerLayout, final DrawerLayout.DrawerListener drawerListener) {
        mDrawerToggle = new ActionBarDrawerToggle(
                mActivity,
                drawerLayout,
                mActionBarToolbar,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.setDrawerListener(mDrawerToggle);
        return mDrawerToggle;
    }

    public void startActivityWithTransition(Intent intent, final View clickedView, final String transitionName) {
        ActivityOptions options = null;
/*        if (hasL() && clickedView != null && !TextUtils.isEmpty(transitionName)) {
              options = ActivityOptions.makeSceneTransitionAnimation(
                      mActivity, clickedView, transitionName);
          }
*/
        mActivity.startActivity(intent, (options != null) ? options.toBundle() : null);
    }

    public int getStatusBarColor() {
        if (!hasL()) return Color.BLACK;
        return mActivity.getWindow().getStatusBarColor();
    }

    public void setStatusBarColor(int color) {
        if (!hasL()) return;
        mActivity.getWindow().setStatusBarColor(color);
    }

    public void setViewElevation(View v, float elevation) {
        if (hasL()) {
            v.setElevation(elevation);
        }
    }
}

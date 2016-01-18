package chickennugget.spaceengineersdata.cards.card_main;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import chickennugget.spaceengineersdata.R;

public class CardHeader extends BaseCard {

    public static int NO_POPUP_MENU = -1;
    protected boolean mIsButtonExpandVisible = false;
    protected boolean mIsButtonOverflowVisible = false;
    protected boolean mIsOtherButtonVisible = false;
    protected int mPopupMenu = NO_POPUP_MENU;
    protected OnClickCardHeaderPopupMenuListener mPopupMenuListener;
    protected OnPrepareCardHeaderPopupMenuListener mPopupMenuPrepareListener;
    protected OnClickCardHeaderOtherButtonListener mOtherButtonClickListener;
    protected int mOtherButtonDrawable = 0;
    protected CustomOverflowAnimation mCustomOverflowAnimation = null;
    protected boolean mIsOverflowSelected = false;
    private boolean couldUseNativeInnerLayout = false;

    public CardHeader(Context context) {
        this(context, R.layout.inner_base_header);
    }

    public CardHeader(Context context, int innerLayout) {
        super(context);
        mInnerLayout = innerLayout;
        if (innerLayout == R.layout.inner_base_header)
            couldUseNativeInnerLayout = true;
    }

    public void setPopupMenu(int menuRes, OnClickCardHeaderPopupMenuListener listener, OnPrepareCardHeaderPopupMenuListener prepareListener) {
        mPopupMenu = menuRes;
        mPopupMenuListener = listener;
        mPopupMenuPrepareListener = prepareListener;
        if (menuRes == NO_POPUP_MENU) {
            mIsButtonOverflowVisible = false;
            listener = null;
        } else {
            mIsButtonOverflowVisible = true;
        }
    }

    public void setPopupMenu(int menuRes, OnClickCardHeaderPopupMenuListener listener) {
        setPopupMenu(menuRes, listener, null);
    }

    public CustomOverflowAnimation getCustomOverflowAnimation() {
        return mCustomOverflowAnimation;
    }

    public void setCustomOverflowAnimation(CustomOverflowAnimation customAnimation) {
        this.mCustomOverflowAnimation = customAnimation;
        mIsButtonOverflowVisible = mCustomOverflowAnimation != null;
    }

    public boolean isOverflowSelected() {
        return mIsOverflowSelected;
    }

    public void setOverflowSelected(boolean isOverflowSelected) {
        mIsOverflowSelected = isOverflowSelected;
    }

    @Override
    public View getInnerView(Context context, ViewGroup parent) {
        if (couldUseNativeInnerLayout && isNative())
            mInnerLayout = R.layout.native_inner_base_header;
        View view = super.getInnerView(context, parent);
        if (view != null) {
            parent.addView(view);

            if (mInnerLayout > -1) {
                setupInnerViewElements(parent, view);
            }
        }
        return view;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view != null) {
            TextView mTitleView = (TextView) view.findViewById(R.id.card_header_inner_simple_title);
            if (mTitleView != null)
                mTitleView.setText(mTitle);
        }
    }

    public OnClickCardHeaderPopupMenuListener getPopupMenuListener() {
        return mPopupMenuListener;
    }

    public void setPopupMenuListener(OnClickCardHeaderPopupMenuListener popupMenuListener) {
        mPopupMenuListener = popupMenuListener;
    }

    public OnPrepareCardHeaderPopupMenuListener getPopupMenuPrepareListener() {
        return mPopupMenuPrepareListener;
    }

    public void setPopupMenuPrepareListener(OnPrepareCardHeaderPopupMenuListener popupMenuListener) {
        mPopupMenuPrepareListener = popupMenuListener;
    }

    public boolean isButtonExpandVisible() {
        return mIsButtonExpandVisible;
    }

    public void setButtonExpandVisible(boolean buttonExpandVisible) {
        mIsButtonExpandVisible = buttonExpandVisible;
    }

    public boolean isButtonOverflowVisible() {
        return mIsButtonOverflowVisible;
    }

    public void setButtonOverflowVisible(boolean buttonOverflowVisible) {
        mIsButtonOverflowVisible = buttonOverflowVisible;
    }

    public int getPopupMenu() {
        return mPopupMenu;
    }

    public boolean isOtherButtonVisible() {
        if (mOtherButtonClickListener == null) {
            if (mIsOtherButtonVisible)
                Log.w("CardHeader", "You set visible=true to other button menu, but you don't add any listener");
            return false;
        }
        return mIsOtherButtonVisible;
    }

    public void setOtherButtonVisible(boolean isOtherButtonVisible) {
        mIsOtherButtonVisible = isOtherButtonVisible;
    }

    public OnClickCardHeaderOtherButtonListener getOtherButtonClickListener() {
        return mOtherButtonClickListener;
    }

    public void setOtherButtonClickListener(OnClickCardHeaderOtherButtonListener otherButtonClickListener) {
        mOtherButtonClickListener = otherButtonClickListener;
    }

    public int getOtherButtonDrawable() {
        return mOtherButtonDrawable;
    }

    public void setOtherButtonDrawable(int otherButtonDrawable) {
        mOtherButtonDrawable = otherButtonDrawable;
    }

    protected boolean isNative() {
        if (getParentCard() != null)
            return getParentCard().isNative();
        return false;
    }

    public interface CustomOverflowAnimation {
        void doAnimation(Card card, View imageOverflow);
    }

    public interface OnClickCardHeaderPopupMenuListener {
        void onMenuItemClick(BaseCard card, MenuItem item);
    }

    public interface OnPrepareCardHeaderPopupMenuListener {
        boolean onPreparePopupMenu(BaseCard card, PopupMenu popupMenu);
    }

    public interface OnClickCardHeaderOtherButtonListener {
        void onButtonItemClick(Card card, View view);
    }
}

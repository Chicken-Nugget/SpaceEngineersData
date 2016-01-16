package chickennugget.spaceengineersdata.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import chickennugget.spaceengineersdata.R;

public class Card extends BaseCard {

    public static final int CLICK_LISTENER_ALL_VIEW = 0;
    public static final int CLICK_LISTENER_THUMBNAIL_VIEW = 1;
    public static final int CLICK_LISTENER_HEADER_VIEW = 2;
    public static final int CLICK_LISTENER_CONTENT_VIEW = 10;
    public static final int CLICK_LISTENER_ACTIONAREA1_VIEW = 9;
    public static int DEFAULT_COLOR = 0;
    protected static String TAG = "Card";
    protected boolean mIsClickable = false;
    protected boolean mIsLongClickable = false;
    protected boolean mIsSwipeable = false;
    protected boolean mShadow = true;
    protected CardHeader mCardHeader;
    protected CardThumbnail mCardThumbnail;
    protected CardExpand mCardExpand;
    protected boolean mIsExpanded = false;
    protected OnSwipeListener mOnSwipeListener;
    protected OnCardClickListener mOnClickListener;
    protected OnExpandAnimatorEndListener mOnExpandAnimatorEndListener;
    protected OnCollapseAnimatorEndListener mOnCollapseAnimatorEndListener;
    protected OnExpandAnimatorStartListener mOnExpandAnimatorStartListener;
    protected OnCollapseAnimatorStartListener mOnCollapseAnimatorStartListener;
    protected HashMap<Integer, OnCardClickListener> mMultipleOnClickListener;
    protected OnUndoSwipeListListener mOnUndoSwipeListListener;
    protected OnUndoHideSwipeListListener mOnUndoHideSwipeListListener;
    protected boolean mMultiChoiceEnabled = false;
    protected ViewToClickToExpand viewToClickToExpand = null;
    protected Float mCardElevation;
    protected OnLongCardClickListener mOnLongClickListener;
    private int mBackgroundResourceId = 0;
    private Drawable mBackgroundResource = null;
    private int mBackgroundColorResourceId = 0;
    private boolean mCheckable = true;
    private boolean couldUseNativeInnerLayout = false;

    public Card(Context context) {
        this(context, R.layout.inner_base_main);
    }

    public Card(Context context, int innerLayout) {
        super(context);
        mParentCard = null;
        mInnerLayout = innerLayout;
        if (innerLayout == R.layout.inner_base_main)
            couldUseNativeInnerLayout = true;
    }

    public static boolean equalsInnerLayout(Card oldCard, Card newCard) {
        if (oldCard == null || newCard == null) return false;
        if (oldCard.getInnerLayout() != newCard.getInnerLayout()) return true;
        if (oldCard.getCardHeader() != null) {
            if (newCard.getCardHeader() == null)
                return true;
            else if (oldCard.getCardHeader().getInnerLayout() != newCard.getCardHeader().getInnerLayout())
                return true;
        } else {
            if (newCard.getCardHeader() != null)
                return true;
        }
        if (oldCard.getCardThumbnail() != null) {
            if (newCard.getCardThumbnail() == null)
                return true;
            else if (oldCard.getCardThumbnail().getInnerLayout() != newCard.getCardThumbnail().getInnerLayout())
                return true;
        } else {
            if (newCard.getCardThumbnail() != null)
                return true;
        }
        if (oldCard.getCardExpand() != null) {
            if (newCard.getCardExpand() == null)
                return true;
            else if (oldCard.getCardExpand().getInnerLayout() != newCard.getCardExpand().getInnerLayout())
                return true;
        } else {
            if (newCard.getCardExpand() != null)
                return true;
        }
        return false;
    }

    @Override
    public View getInnerView(Context context, ViewGroup parent) {
        setupInnerLayout();
        View view = super.getInnerView(context, parent);
        if (view != null) {
            if (parent != null)
                parent.addView(view);
            if (mInnerLayout > -1) {
                setupInnerViewElements(parent, view);
            }
        }
        return view;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        // Add simple title to header
        if (view != null) {
            TextView mTitleView = (TextView) view.findViewById(R.id.card_main_inner_simple_title);
            if (mTitleView != null)
                mTitleView.setText(mTitle);
        }
    }

    protected void setupInnerLayout() {
        if (couldUseNativeInnerLayout && isNative())
            mInnerLayout = R.layout.native_inner_base_main;
    }

    public void addCardThumbnail(CardThumbnail cardThumbnail) {
        mCardThumbnail = cardThumbnail;
        if (mCardThumbnail != null)
            mCardThumbnail.setParentCard(this);
    }

    public CardThumbnail getCardThumbnail() {
        return mCardThumbnail;
    }

    public void addCardHeader(CardHeader cardHeader) {
        mCardHeader = cardHeader;
        if (mCardHeader != null)
            mCardHeader.setParentCard(this);
    }

    public CardHeader getCardHeader() {
        return mCardHeader;
    }

    public void addCardExpand(CardExpand cardExpand) {
        mCardExpand = cardExpand;
        if (mCardExpand != null)
            mCardExpand.setParentCard(this);
    }

    public CardExpand getCardExpand() {
        return mCardExpand;
    }

    public void setupSupplementalActions() {
    }

    public void onSwipeCard() {
        if (isSwipeable() && mOnSwipeListener != null) {
            //mOnSwipeListener.onSwipe(this, mCardView);
            mOnSwipeListener.onSwipe(this);
        }
    }

    public OnSwipeListener getOnSwipeListener() {
        return mOnSwipeListener;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        mIsSwipeable = onSwipeListener != null;
        this.mOnSwipeListener = onSwipeListener;
    }

    public void onUndoSwipeListCard() {
        if (isSwipeable() && mOnUndoSwipeListListener != null) {
            mOnUndoSwipeListListener.onUndoSwipe(this);
        }
    }

    public OnUndoSwipeListListener getOnUndoSwipeListListener() {
        return mOnUndoSwipeListListener;
    }

    public void setOnUndoSwipeListListener(OnUndoSwipeListListener onUndoSwipeListListener) {
        this.mOnUndoSwipeListListener = onUndoSwipeListListener;
    }

    public OnUndoHideSwipeListListener getOnUndoHideSwipeListListener() {
        return mOnUndoHideSwipeListListener;
    }

    public void setOnUndoHideSwipeListListener(OnUndoHideSwipeListListener onUndoHideSwipeListListener) {
        mOnUndoHideSwipeListListener = onUndoHideSwipeListListener;
    }

    public OnCardClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnCardClickListener onClickListener) {
        mIsClickable = onClickListener != null;
        mOnClickListener = onClickListener;
    }

    public OnLongCardClickListener getOnLongClickListener() {
        return mOnLongClickListener;
    }

    public void setOnLongClickListener(OnLongCardClickListener onLongClickListener) {
        mIsLongClickable = onLongClickListener != null;
        mOnLongClickListener = onLongClickListener;
    }

    public void onExpandEnd() {
        if (mOnExpandAnimatorEndListener != null) {
            mOnExpandAnimatorEndListener.onExpandEnd(this);
        }
    }

    public OnExpandAnimatorEndListener getOnExpandAnimatorEndListener() {
        return mOnExpandAnimatorEndListener;
    }

    public void setOnExpandAnimatorEndListener(OnExpandAnimatorEndListener onExpandAnimatorEndListener) {
        this.mOnExpandAnimatorEndListener = onExpandAnimatorEndListener;
    }

    public void onExpandStart() {
        if (mOnExpandAnimatorStartListener != null) {
            mOnExpandAnimatorStartListener.onExpandStart(this);
        }
    }

    public OnExpandAnimatorStartListener getOnExpandAnimatorStartListener() {
        return mOnExpandAnimatorStartListener;
    }

    public void setOnExpandAnimatorStartListener(OnExpandAnimatorStartListener onExpandAnimatorStartListener) {
        this.mOnExpandAnimatorStartListener = onExpandAnimatorStartListener;
    }

    public void onCollapseEnd() {
        if (mOnCollapseAnimatorEndListener != null) {
            mOnCollapseAnimatorEndListener.onCollapseEnd(this);
        }
    }

    public OnCollapseAnimatorEndListener getOnCollapseAnimatorEndListener() {
        return mOnCollapseAnimatorEndListener;
    }

    public void setOnCollapseAnimatorEndListener(OnCollapseAnimatorEndListener onCollapseAnimatorEndListener) {
        this.mOnCollapseAnimatorEndListener = onCollapseAnimatorEndListener;
    }

    public void onCollapseStart() {
        if (mOnCollapseAnimatorStartListener != null) {
            mOnCollapseAnimatorStartListener.onCollapseStart(this);
        }
    }

    public OnCollapseAnimatorStartListener getOnCollapseAnimatorStartListener() {
        return mOnCollapseAnimatorStartListener;
    }

    public void setOnCollapseAnimatorStartListener(OnCollapseAnimatorStartListener onCollapseAnimatorStartListener) {
        this.mOnCollapseAnimatorStartListener = onCollapseAnimatorStartListener;
    }

    public void doExpand() {
        getCardView().doExpand();
    }

    public void doCollapse() {
        getCardView().doCollapse();
    }

    public void doToogleExpand() {
        getCardView().doToggleExpand();
    }

    public boolean hasHeader() {
        return getCardHeader() != null;
    }

    public Float getCardElevation() {
        return mCardElevation;
    }

    public void setCardElevation(float elevation) {
        this.mCardElevation = elevation;
    }

    public boolean isShadow() {
        return mShadow;
    }

    public void setShadow(boolean shadow) {
        mShadow = shadow;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public boolean isClickable() {
        if (mIsClickable) {
            if (mOnClickListener == null && (mMultipleOnClickListener == null || mMultipleOnClickListener.isEmpty())) {
                Log.w(TAG, "Clickable set to true without onClickListener");
                return false;
            }
        }
        return mIsClickable;
    }

    public void setClickable(boolean isClickable) {
        mIsClickable = isClickable;
    }

    public boolean isSwipeable() {
        return mIsSwipeable;
    }

    public void setSwipeable(boolean isSwipeable) {
        mIsSwipeable = isSwipeable;
    }

    public boolean isLongClickable() {
        if (mOnLongClickListener == null) {
            if (mIsLongClickable)
                Log.w(TAG, "LongClickable set to true without onLongClickListener");
            return false;
        }
        return mIsLongClickable;
    }

    public void setLongClickable(boolean isLongClickable) {
        mIsLongClickable = isLongClickable;
    }

    public void addPartialOnClickListener(int area, OnCardClickListener onClickListener) {
        if (area < CLICK_LISTENER_ALL_VIEW && area > CLICK_LISTENER_CONTENT_VIEW)
            Log.w(TAG, "area value not valid in addPartialOnClickListner");
        HashMap multipleOnClickListener = getMultipleOnClickListener();
        if (onClickListener != null) {
            multipleOnClickListener.put(area, onClickListener);
            mIsClickable = true;
        } else {
            removePartialOnClickListener(area);
        }
    }

    public void removePartialOnClickListener(int area) {
        HashMap multipleOnClickListener = getMultipleOnClickListener();
        multipleOnClickListener.remove(area);
        if (mOnClickListener == null && multipleOnClickListener.isEmpty())
            mIsClickable = false;
    }

    public HashMap<Integer, OnCardClickListener> getMultipleOnClickListener() {
        if (mMultipleOnClickListener != null)
            return mMultipleOnClickListener;
        return mMultipleOnClickListener = new HashMap<Integer, OnCardClickListener>();
    }

    public boolean isExpanded() {
        return mIsExpanded;
    }

    public void setExpanded(boolean expanded) {
        mIsExpanded = expanded;
    }

    public void notifyDataSetChanged() {
        getCardView().refreshCard(this);
    }

    public int getBackgroundResourceId() {
        return mBackgroundResourceId;
    }

    public void setBackgroundResourceId(int drawableResourceId) {
        this.mBackgroundResourceId = drawableResourceId;
    }

    public Drawable getBackgroundResource() {
        return mBackgroundResource;
    }

    public void setBackgroundResource(Drawable drawableResource) {
        this.mBackgroundResource = drawableResource;
    }

    public void changeBackgroundResourceId(int drawableResourceId) {
        if (mCardView != null) {
            mCardView.changeBackgroundResourceId(drawableResourceId);
        }
    }

    public void changeBackgroundResource(Drawable drawableResource) {
        if (mCardView != null) {
            mCardView.changeBackgroundResource(drawableResource);
        }
    }

    public boolean isCheckable() {
        return mCheckable;
    }

    public void setCheckable(boolean checkable) {
        mCheckable = checkable;
    }

    public boolean isMultiChoiceEnabled() {
        return mMultiChoiceEnabled;
    }

    public ViewToClickToExpand getViewToClickToExpand() {
        return viewToClickToExpand;
    }

    public void setViewToClickToExpand(ViewToClickToExpand viewToClickToExpand) {
        this.viewToClickToExpand = viewToClickToExpand;
    }

    protected boolean isNative() {
        if (mCardView != null)
            return mCardView.isNative();
        return false;
    }

    public int getBackgroundColorResourceId() {
        return mBackgroundColorResourceId;
    }

    public void setBackgroundColorResourceId(int backgroundColorResourceId) {
        mBackgroundColorResourceId = backgroundColorResourceId;
    }

    public interface OnSwipeListener {
        void onSwipe(Card card);
    }

    public interface OnUndoSwipeListListener {
        void onUndoSwipe(Card card);
    }

    public interface OnUndoHideSwipeListListener {
        void onUndoHideSwipe(Card card);
    }

    public interface OnCardClickListener {
        void onClick(Card card, View view);
    }

    public interface OnLongCardClickListener {
        boolean onLongClick(Card card, View view);
    }

    public interface OnExpandAnimatorEndListener {
        void onExpandEnd(Card card);
    }

    public interface OnExpandAnimatorStartListener {
        void onExpandStart(Card card);
    }

    public interface OnCollapseAnimatorEndListener {
        void onCollapseEnd(Card card);
    }

    public interface OnCollapseAnimatorStartListener {
        void onCollapseStart(Card card);
    }
}

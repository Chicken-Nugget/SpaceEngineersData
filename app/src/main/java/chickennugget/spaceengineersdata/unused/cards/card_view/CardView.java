package chickennugget.spaceengineersdata.unused.cards.card_view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.internal.ForegroundLinearLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.HashMap;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.card_main.Card;
import chickennugget.spaceengineersdata.unused.cards.card_main.CardExpand;
import chickennugget.spaceengineersdata.unused.cards.card_main.CardHeader;
import chickennugget.spaceengineersdata.unused.cards.card_main.CardThumbnail;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewWrapper;
import chickennugget.spaceengineersdata.unused.cards.listeners.SwipeDismissViewTouchListener;
import chickennugget.spaceengineersdata.unused.cards.others.ViewToClickToExpand;

public class CardView extends BaseCardView implements CardViewWrapper {

    protected CardHeader mCardHeader;
    protected CardThumbnail mCardThumbnail;
    protected CardExpand mCardExpand;
    protected View mInternalMainCardLayout;
    protected View mInternalContentLayout;
    protected View mInternalInnerView;
    protected View mInternalExpandLayout;
    protected View mInternalExpandInnerView;
    protected Animator mExpandAnimator;
    protected OnExpandListAnimatorListener mOnExpandListAnimatorListener;

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void initAttrs(AttributeSet attrs, int defStyle) {

        card_layout_resourceID = R.layout.card_layout;

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            card_layout_resourceID = a.getResourceId(R.styleable.card_options_card_layout_resourceID, this.card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (mInternalMainCardLayout != null && mInternalMainCardLayout instanceof ForegroundLinearLayout) {
            mInternalMainCardLayout.drawableHotspotChanged(x, y);
        }
    }

    @Override
    public void setCard(Card card) {
        super.setCard(card);
        if (card != null) {
            mCardHeader = card.getCardHeader();
            mCardThumbnail = card.getCardThumbnail();
            mCardExpand = card.getCardExpand();
        }
        if (!isRecycle()) retrieveLayoutIDs();
        buildUI();
    }

    public void refreshCard(Card card) {
        mIsRecycle = true;
        setCard(card);
        mIsRecycle = false;
    }

    public void replaceCard(Card card) {
        mForceReplaceInnerLayout = true;
        refreshCard(card);
        mForceReplaceInnerLayout = false;
    }

    @Override
    protected void buildUI() {
        super.buildUI();
        mCard.setCardView(this);
        setupHeaderView();
        setupMainView();
        setupThumbnailView();
        setupExpandView();
        setupSupplementalActions();
        setupListeners();
        setupExpandAction();
        setupDrawableResources();
    }

    @Override
    protected void retrieveLayoutIDs() {
        super.retrieveLayoutIDs();
        mInternalMainCardLayout = findViewById(R.id.card_main_layout);
        mInternalHeaderLayout = (CardHeaderView) findViewById(R.id.card_header_layout);
        mInternalExpandLayout = findViewById(R.id.card_content_expand_layout);
        mInternalContentLayout = findViewById(R.id.card_main_content_layout);
        mInternalThumbnailLayout = (CardThumbnailView) findViewById(R.id.card_thumbnail_layout);
    }

    protected void setupHeaderView() {
        if (mCardHeader != null) {
            if (mInternalHeaderLayout != null) {
                mInternalHeaderLayout.setVisibility(VISIBLE);
                mInternalHeaderLayout.setRecycle(isRecycle());
                mInternalHeaderLayout.setForceReplaceInnerLayout(isForceReplaceInnerLayout());
                mInternalHeaderLayout.addCardHeader(mCardHeader);
            }
        } else {
            if (mInternalHeaderLayout != null) {
                mInternalHeaderLayout.setVisibility(GONE);
                if (isForceReplaceInnerLayout()) mInternalHeaderLayout.addCardHeader(null);
            }
        }
    }

    protected void setupMainView() {
        if (mInternalContentLayout != null) {
            ViewGroup mParentGroup = null;
            try {
                mParentGroup = (ViewGroup) mInternalContentLayout;
            } catch (Exception e) {
                setRecycle(false);
            }
            if (!isRecycle() || isForceReplaceInnerLayout()) {
                if (isForceReplaceInnerLayout() && mInternalContentLayout != null && mInternalInnerView != null)
                    ((ViewGroup) mInternalContentLayout).removeView(mInternalInnerView);
                mInternalInnerView = mCard.getInnerView(getContext(), (ViewGroup) mInternalContentLayout);
            } else if (mCard.getInnerLayout() > -1)
                mCard.setupInnerViewElements(mParentGroup, mInternalInnerView);
        }
    }

    protected void setupThumbnailView() {
        if (mInternalThumbnailLayout != null) {
            if (mCardThumbnail != null) {
                mInternalThumbnailLayout.setVisibility(VISIBLE);
                mInternalThumbnailLayout.setRecycle(isRecycle());
                mInternalThumbnailLayout.setForceReplaceInnerLayout(isForceReplaceInnerLayout());
                mInternalThumbnailLayout.addCardThumbnail(mCardThumbnail);
            } else mInternalThumbnailLayout.setVisibility(GONE);
        }
    }

    protected void setupDrawableResources() {
        if (mCard != null) {
            if (mCard.getBackgroundResourceId() != 0)
                changeBackgroundResourceId(mCard.getBackgroundResourceId());
            else if (mCard.getBackgroundResource() != null)
                changeBackgroundResource(mCard.getBackgroundResource());
        }
    }

    protected void setupSupplementalActions() {
        if (mCard != null)
            mCard.setupSupplementalActions();
    }

    protected void setupExpandAction() {
        if (mInternalExpandLayout != null && ((mCardHeader != null && mCardHeader.isButtonExpandVisible()) ||
                mCard.getViewToClickToExpand() != null)) {
            mInternalExpandLayout.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            mInternalExpandLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                            View parent = (View) mInternalExpandLayout.getParent();
                            final int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
                            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            mInternalExpandLayout.measure(widthSpec, heightSpec);
                            mExpandAnimator = ExpandCollapseHelper.createSlideAnimator((CardView) mCard.getCardView(), 0, mInternalExpandLayout.getMeasuredHeight());
                            return true;
                        }
                    });
        }
        setupExpandCollapseActionListener();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    protected void setupListeners() {
        if (mCard.isSwipeable()) {
            this.setOnTouchListener(new SwipeDismissViewTouchListener(this, mCard, new SwipeDismissViewTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(Card card) {
                    return card.isSwipeable();
                }

                @Override
                public void onDismiss(CardViewWrapper cardView, Card card) {
                    final ViewGroup vg = (ViewGroup) (((View) cardView).getParent());
                    if (vg != null) {
                        vg.removeView((View) cardView);
                        card.onSwipeCard();
                    }
                }
            }));
        } else this.setOnTouchListener(null);
        resetPartialListeners();
        if (mCard.isClickable()) if (!mCard.isMultiChoiceEnabled())
            if (mCard.getOnClickListener() != null) {
                this.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCard.getOnClickListener() != null)
                            mCard.getOnClickListener().onClick(mCard, v);
                    }
                });

            } else {
                HashMap<Integer, Card.OnCardClickListener> mMultipleOnClickListner = mCard.getMultipleOnClickListener();
                if (mMultipleOnClickListner != null && !mMultipleOnClickListner.isEmpty())
                    for (int key : mMultipleOnClickListner.keySet()) {
                        View viewClickable = decodeAreaOnClickListener(key);
                        final Card.OnCardClickListener mListener = mMultipleOnClickListner.get(key);
                        if (viewClickable != null) {
                            viewClickable.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mListener != null)
                                        mListener.onClick(mCard, v);
                                }
                            });
                            if (key > Card.CLICK_LISTENER_ALL_VIEW) {
                                mHelperImpl.setCardSelector(viewClickable, getResources().getDrawable(R.drawable.card_selector));
                            }
                        }
                    }
                else this.setClickable(false);
            }
        else this.setClickable(false);
        if (mCard.isLongClickable()) this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mCard.getOnLongClickListener() != null)
                    return mCard.getOnLongClickListener().onLongClick(mCard, v);
                return false;
            }
        });
        else this.setLongClickable(false);
    }

    protected void resetPartialListeners() {
        View viewClickable = decodeAreaOnClickListener(Card.CLICK_LISTENER_HEADER_VIEW);
        if (viewClickable != null)
            viewClickable.setClickable(false);
        viewClickable = decodeAreaOnClickListener(Card.CLICK_LISTENER_THUMBNAIL_VIEW);
        if (viewClickable != null)
            viewClickable.setClickable(false);
        viewClickable = decodeAreaOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW);
        if (viewClickable != null)
            viewClickable.setClickable(false);
    }

    protected View decodeAreaOnClickListener(int area) {
        if (area < Card.CLICK_LISTENER_ALL_VIEW && area > Card.CLICK_LISTENER_CONTENT_VIEW)
            return null;
        View view = null;
        switch (area) {
            case Card.CLICK_LISTENER_ALL_VIEW:
                view = this;
                break;
            case Card.CLICK_LISTENER_HEADER_VIEW:
                view = mInternalHeaderLayout;
                break;
            case Card.CLICK_LISTENER_THUMBNAIL_VIEW:
                view = mInternalThumbnailLayout;
                break;
            case Card.CLICK_LISTENER_CONTENT_VIEW:
                view = mInternalContentLayout;
                break;
            default:
                break;
        }
        return view;
    }

    protected void setupExpandCollapseActionListener() {
        if (mInternalExpandLayout != null) {
            mInternalExpandLayout.setVisibility(View.GONE);
            boolean internal_blockForLongClickOnImageButtonExpand = false;
            ViewToClickToExpand viewToClickToExpand = null;
            if (mCardHeader != null && mCardHeader.isButtonExpandVisible()) {
                viewToClickToExpand = ViewToClickToExpand.builder()
                        .setupView(mInternalHeaderLayout.getImageButtonExpand())
                        .highlightView(true);
                internal_blockForLongClickOnImageButtonExpand = true;
            } else if (mCard.getViewToClickToExpand() != null)
                viewToClickToExpand = mCard.getViewToClickToExpand();

            if (viewToClickToExpand != null) {
                TitleViewOnClickListener titleViewOnClickListener = new TitleViewOnClickListener(mInternalExpandLayout, mCard, viewToClickToExpand.isViewToSelect());
                View viewToClick = viewToClickToExpand.getViewToClick();
                if (viewToClick != null)
                    if (internal_blockForLongClickOnImageButtonExpand)
                        viewToClick.setOnClickListener(titleViewOnClickListener);
                    else if (viewToClickToExpand.isUseLongClick())
                        viewToClick.setOnLongClickListener(new TitleViewOnLongClickListener(titleViewOnClickListener));
                    else viewToClick.setOnClickListener(titleViewOnClickListener);
                else {
                    ViewToClickToExpand.CardElementUI cardElementUI = viewToClickToExpand.getCardElementUIToClick();
                    if (cardElementUI != null) {
                        switch (cardElementUI) {
                            case CARD:
                                viewToClick = this;
                                break;
                            case HEADER:
                                viewToClick = getInternalHeaderLayout();
                                break;
                            case THUMBNAIL:
                                viewToClick = getInternalThumbnailLayout();
                                break;
                            case MAIN_CONTENT:
                                viewToClick = getInternalContentLayout();
                                break;
                        }
                        if (viewToClick != null) if (viewToClickToExpand.isUseLongClick())
                            viewToClick.setOnLongClickListener(new TitleViewOnLongClickListener(titleViewOnClickListener));
                        else {
                            viewToClick.setOnClickListener(titleViewOnClickListener);
                        }
                    }
                }

                if (isExpanded()) {
                    mInternalExpandLayout.setVisibility(View.VISIBLE);
                    if (viewToClick != null) if (viewToClickToExpand.isViewToSelect())
                        viewToClick.setSelected(true);
                } else {
                    mInternalExpandLayout.setVisibility(View.GONE);
                    if (viewToClick != null) if (viewToClickToExpand.isViewToSelect())
                        viewToClick.setSelected(false);
                }
            }
        }
    }

    protected void setupExpandView() {
        if (mInternalExpandLayout != null && mCardExpand != null) {
            if (!isRecycle() || isForceReplaceInnerLayout()) {
                if (isForceReplaceInnerLayout() && mInternalExpandLayout != null && mInternalExpandInnerView != null)
                    ((ViewGroup) mInternalExpandLayout).removeView(mInternalExpandInnerView);
                mInternalExpandInnerView = mCardExpand.getInnerView(getContext(), (ViewGroup) mInternalExpandLayout);
            } else if (mCardExpand.getInnerLayout() > -1)
                mCardExpand.setupInnerViewElements((ViewGroup) mInternalExpandLayout, mInternalExpandInnerView);
            ViewGroup.LayoutParams layoutParams = mInternalExpandLayout.getLayoutParams();
            layoutParams.height = LayoutParams.WRAP_CONTENT;
            mInternalExpandLayout.setLayoutParams(layoutParams);
        }
    }

    public void doToggleExpand() {
        if (mInternalExpandLayout != null) {
            ExpandContainerHelper helper = new ExpandContainerHelper(mInternalExpandLayout, mCard, false);
            boolean isVisible = mInternalExpandLayout.getVisibility() == View.VISIBLE;
            if (isVisible) ExpandCollapseHelper.animateCollapsing(helper);
            else ExpandCollapseHelper.animateExpanding(helper);
        }
    }

    public void doExpand() {
        if (mInternalExpandLayout != null) {
            ExpandContainerHelper helper = new ExpandContainerHelper(mInternalExpandLayout, mCard, false);
            boolean isVisible = mInternalExpandLayout.getVisibility() == View.VISIBLE;
            if (!isVisible) ExpandCollapseHelper.animateExpanding(helper);
        }
    }

    public void doCollapse() {
        if (mInternalExpandLayout != null) {
            ExpandContainerHelper helper = new ExpandContainerHelper(mInternalExpandLayout, mCard, false);
            boolean isVisible = mInternalExpandLayout.getVisibility() == View.VISIBLE;
            if (isVisible) ExpandCollapseHelper.animateCollapsing(helper);
        }
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
    }

    public OnExpandListAnimatorListener getOnExpandListAnimatorListener() {
        return mOnExpandListAnimatorListener;
    }

    public void setOnExpandListAnimatorListener(OnExpandListAnimatorListener onExpandListAnimatorListener) {
        this.mOnExpandListAnimatorListener = onExpandListAnimatorListener;
    }

    public Bitmap createBitmap() {
        if (getWidth() <= 0 && getHeight() <= 0) {
            int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            measure(spec, spec);
            layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        draw(c);
        return b;
    }

    public View getInternalExpandLayout() {
        return mInternalExpandLayout;
    }

    public View getInternalContentLayout() {
        return mInternalContentLayout;
    }

    public View getInternalInnerView() {
        return mInternalInnerView;
    }

    public boolean isExpanded() {
        return mCard != null && mCard.isExpanded();
    }

    public void setExpanded(boolean expanded) {
        if (mCard != null) mCard.setExpanded(expanded);
    }

    @Override
    public boolean isNative() {
        return false;
    }

    public View getInternalMainCardLayout() {
        return mInternalMainCardLayout;
    }

    @Override
    public void changeBackgroundResourceId(int drawableResourceId) {
        if (drawableResourceId != 0) if (mInternalMainCardLayout != null)
            mInternalMainCardLayout.setBackgroundResource(drawableResourceId);
    }

    @Override
    public void changeBackgroundResource(Drawable drawableResource) {
        if (drawableResource != null) if (mInternalMainCardLayout != null)
            mHelperImpl.setBackground(mInternalMainCardLayout, drawableResource);
    }

    @Override
    public void changeBackgroundColorResourceId(int colorResourceId) {
    }

    private static class ExpandCollapseHelper {
        private static void animateExpanding(final ExpandContainerHelper helper) {
            if (helper.card.getOnExpandAnimatorStartListener() != null)
                helper.card.getOnExpandAnimatorStartListener().onExpandStart(helper.card);
            if (helper.getCardView().getOnExpandListAnimatorListener() != null)
                helper.getCardView().getOnExpandListAnimatorListener().onExpandStart(helper.getCardView(), helper.contentParent);
            else {
                helper.contentParent.setVisibility(View.VISIBLE);
                if (helper.getCardView().mExpandAnimator != null) {
                    helper.getCardView().mExpandAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            helper.card.setExpanded(true);
                            if (helper.card.getOnExpandAnimatorEndListener() != null)
                                helper.card.getOnExpandAnimatorEndListener().onExpandEnd(helper.card);
                        }
                    });
                    helper.getCardView().mExpandAnimator.start();
                } else {
                    if (helper.card.getOnExpandAnimatorEndListener() != null)
                        helper.card.getOnExpandAnimatorEndListener().onExpandEnd(helper.card);
                    Log.w(TAG, "Does the card have the ViewToClickToExpand?");
                }
            }
        }

        private static void animateCollapsing(final ExpandContainerHelper helper) {
            if (helper.card.getOnCollapseAnimatorStartListener() != null)
                helper.card.getOnCollapseAnimatorStartListener().onCollapseStart(helper.card);
            if (helper.getCardView().getOnExpandListAnimatorListener() != null)
                helper.getCardView().getOnExpandListAnimatorListener().onCollapseStart(helper.getCardView(), helper.contentParent);
            else {
                int origHeight = helper.contentParent.getHeight();
                ValueAnimator animator = createSlideAnimator(helper.getCardView(), origHeight, 0);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        helper.contentParent.setVisibility(View.GONE);
                        helper.card.setExpanded(false);
                        if (helper.card.getOnCollapseAnimatorEndListener() != null)
                            helper.card.getOnCollapseAnimatorEndListener().onCollapseEnd(helper.card);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
                animator.start();
            }
        }

        protected static ValueAnimator createSlideAnimator(final CardView cardView, int start, int end) {
            ValueAnimator animator = ValueAnimator.ofInt(start, end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = cardView.mInternalExpandLayout.getLayoutParams();
                    layoutParams.height = value;
                    cardView.mInternalExpandLayout.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }
    }

    protected class TitleViewOnClickListener implements View.OnClickListener {

        ExpandContainerHelper mExpandContainerHelper;

        private TitleViewOnClickListener(View contentParent, Card card) {
            this(contentParent, card, true);
        }

        private TitleViewOnClickListener(View contentParent, Card card, boolean viewToSelect) {
            mExpandContainerHelper = new ExpandContainerHelper(contentParent, card, viewToSelect);
        }

        @Override
        public void onClick(View view) {
            boolean isVisible = mExpandContainerHelper.contentParent.getVisibility() == View.VISIBLE;
            if (isVisible) {
                ExpandCollapseHelper.animateCollapsing(mExpandContainerHelper);
                if (mExpandContainerHelper.viewToSelect)
                    view.setSelected(false);
            } else {
                ExpandCollapseHelper.animateExpanding(mExpandContainerHelper);
                if (mExpandContainerHelper.viewToSelect)
                    view.setSelected(true);
            }
        }
    }

    protected class TitleViewOnLongClickListener implements OnLongClickListener {

        TitleViewOnClickListener mOnClickListener;

        private TitleViewOnLongClickListener(TitleViewOnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        @Override
        public boolean onLongClick(View view) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(view);
                return true;
            }
            return false;
        }
    }

    private class ExpandContainerHelper {

        private View contentParent;
        private Card card;
        private boolean viewToSelect = true;

        private ExpandContainerHelper(View contentParent, Card card, boolean viewToSelect) {
            this.contentParent = contentParent;
            this.card = card;
            this.viewToSelect = viewToSelect;
        }

        public CardView getCardView() {
            return (CardView) card.getCardView();
        }
    }
}

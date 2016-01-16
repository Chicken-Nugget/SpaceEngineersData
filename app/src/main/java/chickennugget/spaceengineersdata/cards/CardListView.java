package chickennugget.spaceengineersdata.cards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import chickennugget.spaceengineersdata.R;

public class CardListView extends ListView implements CardViewWrapper.OnExpandListAnimatorListener {

    protected static String TAG = "CardListView";
    protected CardArrayAdapter mAdapter;
    protected CardCursorAdapter mCursorAdapter;
    protected SwipeOnScrollListener mOnScrollListener;
    protected int list_card_layout_resourceID = R.layout.list_card_layout;

    public CardListView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        initAttrs(attrs, defStyle);
        setDividerHeight(0);
    }

    protected void initAttrs(AttributeSet attrs, int defStyle) {
        list_card_layout_resourceID = R.layout.list_card_layout;
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);
        try {
            list_card_layout_resourceID = a.getResourceId(R.styleable.card_options_list_card_layout_resourceID, this.list_card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof CardArrayAdapter) {
            setAdapter((CardArrayAdapter) adapter);
        } else if (adapter instanceof CardCursorAdapter) {
            setAdapter((CardCursorAdapter) adapter);
        } else {
            Log.w(TAG, "You are using a generic adapter. Pay attention: your adapter has to call cardArrayAdapter#getView method");
            super.setAdapter(adapter);
        }
    }

    public void setAdapter(CardArrayAdapter adapter) {
        super.setAdapter(adapter);
        adapter.setRowLayoutId(list_card_layout_resourceID);
        adapter.setCardListView(this);
        mAdapter = adapter;
    }

    public void setAdapter(CardCursorAdapter adapter) {
        super.setAdapter(adapter);
        adapter.setRowLayoutId(list_card_layout_resourceID);
        adapter.setCardListView(this);
        mCursorAdapter = adapter;
    }

    public void setExternalAdapter(ListAdapter adapter, CardArrayAdapter cardArrayAdapter) {
        setAdapter(adapter);
        mAdapter = cardArrayAdapter;
        mAdapter.setCardListView(this);
        mAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    public void setExternalAdapter(ListAdapter adapter, CardCursorAdapter cardCursorAdapter) {
        setAdapter(adapter);
        mCursorAdapter = cardCursorAdapter;
        mCursorAdapter.setCardListView(this);
        mCursorAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    public OnScrollListener getOnScrollListener() {
        return this.mOnScrollListener;
    }

    @Override
    public void setOnScrollListener(OnScrollListener mOnScrollListener) {
        super.setOnScrollListener(mOnScrollListener);
        if (mOnScrollListener instanceof SwipeOnScrollListener)
            this.mOnScrollListener = (SwipeOnScrollListener) mOnScrollListener;
    }

    @Override
    public void onExpandStart(CardViewWrapper viewCard, View expandingLayout) {
        boolean expandable = true;
        if (mCursorAdapter != null) expandable = mCursorAdapter.onExpandStart(viewCard);
        if (expandable)
            ExpandCollapseHelper.animateExpanding(expandingLayout, viewCard, this);
        if (mCursorAdapter != null) mCursorAdapter.onExpandEnd(viewCard);
    }

    @Override
    public void onCollapseStart(CardViewWrapper viewCard, View expandingLayout) {
        boolean collapsible = true;
        if (mCursorAdapter != null)
            collapsible = mCursorAdapter.onCollapseStart(viewCard);
        if (collapsible)
            ExpandCollapseHelper.animateCollapsing(expandingLayout, viewCard, this);
        if (mCursorAdapter != null)
            mCursorAdapter.onCollapseEnd(viewCard);
    }

    private static class ExpandCollapseHelper {

        public static void animateCollapsing(final View expandingLayout, final CardViewWrapper cardView, final AbsListView listView) {
            int origHeight = expandingLayout.getHeight();
            ValueAnimator animator = createHeightAnimator(expandingLayout, origHeight, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(final Animator animator) {
                    expandingLayout.setVisibility(View.GONE);
                    cardView.setExpanded(false);
                    notifyAdapter(listView);
                    Card card = cardView.getCard();
                    if (card.getOnCollapseAnimatorEndListener() != null)
                        card.getOnCollapseAnimatorEndListener().onCollapseEnd(card);
                }
            });
            animator.start();
        }

        public static void animateExpanding(final View expandingLayout, final CardViewWrapper cardView, final AbsListView listView) {
            expandingLayout.setVisibility(View.VISIBLE);
            View parent = (View) expandingLayout.getParent();
            final int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            expandingLayout.measure(widthSpec, heightSpec);
            ValueAnimator animator = createHeightAnimator(expandingLayout, 0, expandingLayout.getMeasuredHeight());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                final int listViewHeight = listView.getHeight();
                final int listViewBottomPadding = listView.getPaddingBottom();
                final View v = findDirectChild(expandingLayout, listView);

                @Override
                public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                    final int bottom = v.getBottom();
                    if (bottom > listViewHeight) {
                        final int top = v.getTop();
                        if (top > 0) {
                            listView.smoothScrollBy(Math.min(bottom - listViewHeight + listViewBottomPadding, top), 0);
                        }
                    }
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    cardView.setExpanded(true);
                    notifyAdapter(listView);
                    Card card = cardView.getCard();
                    if (card.getOnExpandAnimatorEndListener() != null)
                        card.getOnExpandAnimatorEndListener().onExpandEnd(card);
                }
            });
            animator.start();
        }

        private static View findDirectChild(final View view, final AbsListView listView) {
            View result = view;
            View parent = (View) result.getParent();
            while (parent != listView) {
                result = parent;
                parent = (View) result.getParent();
            }
            return result;
        }

        public static ValueAnimator createHeightAnimator(final View view, final int start, final int end) {
            ValueAnimator animator = ValueAnimator.ofInt(start, end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                    int value = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = value;
                    view.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }

        public static void notifyAdapter(AbsListView listView) {
            if (listView instanceof CardListView) {
                CardListView cardListView = (CardListView) listView;
                if (cardListView.mAdapter != null) {
                    cardListView.mAdapter.notifyDataSetChanged();
                } else if (cardListView.mCursorAdapter != null) {
                }
            }
        }
    }
}

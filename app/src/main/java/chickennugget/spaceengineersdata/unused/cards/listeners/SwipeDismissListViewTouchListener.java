package chickennugget.spaceengineersdata.unused.cards.listeners;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.card_main.Card;
import chickennugget.spaceengineersdata.unused.cards.interfaces.Dismissable;

public class SwipeDismissListViewTouchListener implements View.OnTouchListener {

    protected Dismissable mDismissable;
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private long mAnimationTime;
    private ListView mListView;
    private DismissCallbacks mCallbacks;
    private int mViewWidth = 1;
    private List<PendingDismissData> mPendingDismisses = new ArrayList<PendingDismissData>();
    private int mDismissAnimationRefCount = 0;
    private float mDownX;
    private float mDownY;
    private boolean mSwiping;
    private int mSwipingSlop;
    private VelocityTracker mVelocityTracker;
    private int mDownPosition;
    private View mDownView;
    private boolean mPaused;
    private int swipeDistanceDivisor = 2;

    public SwipeDismissListViewTouchListener(ListView listView, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = listView.getContext().getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        mListView = listView;
        mCallbacks = callbacks;
        swipeDistanceDivisor = listView.getContext().getResources().getInteger(R.integer.list_card_swipe_distance_divisor);
    }

    public void setEnabled(boolean enabled) {
        mPaused = !enabled;
    }

    /*  public AbsListView.OnScrollListener makeScrollListener() {
            return new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                    setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                }

                @Override
                public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                }
            };
        }
    */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mViewWidth < 2) mViewWidth = mListView.getWidth();

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (mPaused) return false;
                if (mSwiping) return true;
                Rect rect = new Rect();
                int childCount = mListView.getChildCount();
                int headerCount = mListView.getHeaderViewsCount();
                int footerCount = mListView.getFooterViewsCount();
                int[] listViewCoords = new int[2];
                mListView.getLocationOnScreen(listViewCoords);
                int x = (int) motionEvent.getRawX() - listViewCoords[0];
                int y = (int) motionEvent.getRawY() - listViewCoords[1];
                View child = null;
                for (int i = headerCount; i < (childCount - footerCount); i++) {
                    child = mListView.getChildAt(i);
                    child.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        mDownView = child;
                        break;
                    }
                }
                if (mDownView != null) {
                    mDownX = motionEvent.getRawX();
                    mDownY = motionEvent.getRawY();
                    mDownPosition = mListView.getPositionForView(mDownView);
                    if (mDownPosition != ListView.INVALID_POSITION && mDownPosition < mListView.getAdapter().getCount())
                        if (mListView.getAdapter().getItem(mDownPosition) instanceof Card)
                            if (mCallbacks.canDismiss(mDownPosition, (Card) mListView.getAdapter().getItem(mDownPosition))) {
                                mVelocityTracker = VelocityTracker.obtain();
                                mVelocityTracker.addMovement(motionEvent);
                            } else mDownView = null;
                        else mDownView = null;
                    else mDownView = null;
                }
                view.onTouchEvent(motionEvent);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                if (mVelocityTracker == null) break;
                float deltaX = motionEvent.getRawX() - mDownX;
                mVelocityTracker.addMovement(motionEvent);
                mVelocityTracker.computeCurrentVelocity(1000);
                float velocityX = mVelocityTracker.getXVelocity();
                float absVelocityX = Math.abs(velocityX);
                float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
                boolean dismiss = false;
                boolean dismissRight = false;
                if (Math.abs(deltaX) > mViewWidth / swipeDistanceDivisor && mSwiping) {
                    dismiss = true;
                    dismissRight = deltaX > 0;
                } else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity && absVelocityY < absVelocityX && mSwiping) {
                    dismiss = (velocityX < 0) == (deltaX < 0);
                    dismissRight = mVelocityTracker.getXVelocity() > 0;
                }
                if (dismiss && mDownPosition != ListView.INVALID_POSITION)
                    dismiss(mDownView, mDownPosition - mListView.getHeaderViewsCount(), dismissRight);
                else mDownView.animate().translationX(0).alpha(1).setDuration(mAnimationTime).setListener(null);
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mDownX = 0;
                mDownY = 0;
                mDownView = null;
                mDownPosition = ListView.INVALID_POSITION;
                if (mSwiping) {
                    mSwiping = false;
                    return true;
                }
                mSwiping = false;
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                if (mVelocityTracker == null) break;
                if (mDownView != null) mDownView.animate().translationX(0).alpha(1).setDuration(mAnimationTime).setListener(null);
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mDownX = 0;
                mDownY = 0;
                mDownView = null;
                mDownPosition = ListView.INVALID_POSITION;
                mSwiping = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mVelocityTracker == null || mPaused) break;
                mVelocityTracker.addMovement(motionEvent);
                float deltaX = motionEvent.getRawX() - mDownX;
                float deltaY = motionEvent.getRawY() - mDownY;
                boolean movementAllowed = isSwipeMovementAllowed(deltaX);
                if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2 && movementAllowed) {
                    mSwiping = true;
                    mSwipingSlop = (deltaX > 0 ? mSlop : -mSlop);
                    mListView.requestDisallowInterceptTouchEvent(true);
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL | motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT);
                    mListView.onTouchEvent(cancelEvent);
                    view.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                }

                if (mSwiping) {
                    mDownView.setTranslationX(deltaX - mSwipingSlop);
                    mDownView.setAlpha(Math.max(0f, Math.min(1f, 1f - 2f * Math.abs(deltaX) / mViewWidth)));
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private void dismiss(final View view, final int position, boolean dismissRight) {
        ++mDismissAnimationRefCount;
        if (view == null) {
            mCallbacks.onDismiss(mListView, new int[]{position});
            return;
        }
        view.animate().translationX(dismissRight ? mViewWidth : -mViewWidth).alpha(0).setDuration(mAnimationTime).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                performDismiss(view, position);
            }
        });
    }

    private void performDismiss(final View dismissView, final int dismissPosition) {
        final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
        final int originalHeight = dismissView.getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                --mDismissAnimationRefCount;
                if (mDismissAnimationRefCount == 0) {
                    Collections.sort(mPendingDismisses);
                    int[] dismissPositions = new int[mPendingDismisses.size()];
                    for (int i = mPendingDismisses.size() - 1; i >= 0; i--)
                        dismissPositions[i] = mPendingDismisses.get(i).position;
                    mCallbacks.onDismiss(mListView, dismissPositions);
                    mDownPosition = ListView.INVALID_POSITION;
                    ViewGroup.LayoutParams lp;
                    for (PendingDismissData pendingDismiss : mPendingDismisses) {
                        pendingDismiss.view.setAlpha(1f);
                        pendingDismiss.view.setTranslationX(0);
                        lp = pendingDismiss.view.getLayoutParams();
                        lp.height = 0;
                        pendingDismiss.view.setLayoutParams(lp);
                    }
                    long time = SystemClock.uptimeMillis();
                    MotionEvent cancelEvent = MotionEvent.obtain(time, time, MotionEvent.ACTION_CANCEL, 0, 0, 0);
                    mListView.dispatchTouchEvent(cancelEvent);
                    mPendingDismisses.clear();
                }
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                dismissView.setLayoutParams(lp);
            }
        });
        mPendingDismisses.add(new PendingDismissData(dismissPosition, dismissView));
        animator.start();
    }

    private boolean isSwipeMovementAllowed(float deltaX) {
        switch (mDismissable.getSwipeDirectionAllowed()) {
            case BOTH:
                return Math.abs(deltaX) > 0;
            case RIGHT:
                return deltaX > 0;
            case LEFT:
                return deltaX < 0;
            default:
                return false;
        }
    }

    public void setDismissable(Dismissable dismissable) {
        mDismissable = dismissable;
    }

    public interface DismissCallbacks {
        boolean canDismiss(int position, Card card);

        void onDismiss(ListView listView, int[] reverseSortedPositions);
    }

    class PendingDismissData implements Comparable<PendingDismissData> {

        public int position;
        public View view;

        public PendingDismissData(int position, View view) {
            this.position = position;
            this.view = view;
        }

        @Override
        public int compareTo(PendingDismissData other) {
            return other.position - position;
        }
    }
}
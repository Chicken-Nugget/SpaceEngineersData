package chickennugget.spaceengineersdata.cards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import chickennugget.spaceengineersdata.R;

public class SwipeDismissViewTouchListener implements View.OnTouchListener {

    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private long mAnimationTime;
    private CardViewWrapper mCardView;
    private DismissCallbacks mCallbacks;
    private int mViewWidth = 1;
    private float mDownX;
    private float mDownY;
    private Card mToken;
    private boolean mSwiping;
    private int mSwipingSlop;
    private VelocityTracker mVelocityTracker;
    private boolean mPaused;
    private float mTranslationX;
    private int swipeDistanceDivisor = 2;

    public SwipeDismissViewTouchListener(CardViewWrapper cardView, Card card, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(cardView.getContext());
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = cardView.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
        mCardView = cardView;
        mToken = card;
        mCallbacks = callbacks;
        swipeDistanceDivisor = cardView.getContext().getResources().getInteger(R.integer.list_card_swipe_distance_divisor);
    }

    public void setEnabled(boolean enabled) {
        mPaused = !enabled;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        motionEvent.offsetLocation(mTranslationX, 0);
        if (mViewWidth < 2) mViewWidth = ((View) mCardView).getWidth();
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (mPaused) return false;
                mDownX = motionEvent.getRawX();
                mDownY = motionEvent.getRawY();
                if (mCallbacks.canDismiss(mToken)) {
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(motionEvent);
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
                if (dismiss) {
                    ((View) mCardView).animate().translationX(dismissRight ? mViewWidth : -mViewWidth).alpha(0).setDuration(mAnimationTime).setListener(
                            new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    performDismiss();
                                }
                            });
                } else if (mSwiping) ((View) mCardView).animate().translationX(0).alpha(1).setDuration(mAnimationTime).setListener(null);
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mTranslationX = 0;
                mDownX = 0;
                mDownY = 0;
                mSwiping = false;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                if (mVelocityTracker == null) break;
                ((View) mCardView).animate().translationX(0).alpha(1).setDuration(mAnimationTime).setListener(null);
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mTranslationX = 0;
                mDownX = 0;
                mDownY = 0;
                mSwiping = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mVelocityTracker == null || mPaused) break;
                mVelocityTracker.addMovement(motionEvent);
                float deltaX = motionEvent.getRawX() - mDownX;
                float deltaY = motionEvent.getRawY() - mDownY;
                if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2) {
                    mSwiping = true;
                    ((View) mCardView).getParent().requestDisallowInterceptTouchEvent(true);
                    mSwipingSlop = (deltaX > 0 ? mSlop : -mSlop);
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL | (motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    ((View) mCardView).onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                }
                if (mSwiping) {
                    mTranslationX = deltaX;
                    ((View) mCardView).setTranslationX(deltaX - mSwipingSlop);
                    ((View) mCardView).setAlpha(Math.max(0f, Math.min(1f, 1f - 2f * Math.abs(deltaX) / mViewWidth)));
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private void performDismiss() {
        final ViewGroup.LayoutParams lp = ((View) mCardView).getLayoutParams();
        final int originalHeight = ((View) mCardView).getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCallbacks.onDismiss(mCardView, mToken);
                ((View) mCardView).setAlpha(1f);
                ((View) mCardView).setTranslationX(0);
                lp.height = originalHeight;
                ((View) mCardView).setLayoutParams(lp);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                ((View) mCardView).setLayoutParams(lp);
            }
        });
        animator.start();
    }

    public interface DismissCallbacks {
        boolean canDismiss(Card card);

        void onDismiss(CardViewWrapper cardView, Card card);
    }
}
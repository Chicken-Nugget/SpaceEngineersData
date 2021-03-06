package chickennugget.spaceengineersdata.material.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.material.drawables.BlankDrawable;

public class BottomSheetDialog extends android.app.Dialog {

    private final Handler mHandler = new Handler();
    private final Runnable mDismissAction = new Runnable() {
        public void run() {
            try {
                BottomSheetDialog.super.dismiss();
            } catch (IllegalArgumentException ignored) {
            }
        }
    };
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private boolean mRunShowAnimation = false;
    private Interpolator mInInterpolator;
    private Interpolator mOutInterpolator;
    private ContainerFrameLayout mContainer;
    private View mContentView;
    private GestureDetector mGestureDetector;
    private Animation mAnimation;
    private int mLayoutHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mInDuration;
    private int mOutDuration;
    private int mMinFlingVelocity;
    private int mOffset;

    public BottomSheetDialog(Context context) {
        this(context, R.style.Material_App_BottomSheetDialog);
    }

    public BottomSheetDialog(Context context, int style) {
        super(context, style);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(BlankDrawable.getInstance());
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layout.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layout.windowAnimations = R.style.DialogNoAnimation;
        getWindow().setAttributes(layout);
        init(context, style);
    }

    private void init(Context context, int style) {
        mContainer = new ContainerFrameLayout(context);
        cancelable(true);
        canceledOnTouchOutside(true);
        onCreate();
        applyStyle(style);
        mMinFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity() * 2;
        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityY > mMinFlingVelocity) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        super.setContentView(mContainer);
    }

    protected void onCreate() {
    }

    public BottomSheetDialog applyStyle(int styleId) {
        Context context = getContext();
        TypedArray a = context.obtainStyledAttributes(styleId, R.styleable.BottomSheetDialog);
        for (int i = 0, count = a.getIndexCount(); i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.BottomSheetDialog_android_layout_height)
                heightParam(a.getLayoutDimension(attr, ViewGroup.LayoutParams.WRAP_CONTENT));
            else if (attr == R.styleable.BottomSheetDialog_bsd_cancelable)
                cancelable(a.getBoolean(attr, true));
            else if (attr == R.styleable.BottomSheetDialog_bsd_canceledOnTouchOutside)
                canceledOnTouchOutside(a.getBoolean(attr, true));
            else if (attr == R.styleable.BottomSheetDialog_bsd_dimAmount)
                dimAmount(a.getFloat(attr, 0f));
            else if (attr == R.styleable.BottomSheetDialog_bsd_inDuration)
                inDuration(a.getInteger(attr, 0));
            else if (attr == R.styleable.BottomSheetDialog_bsd_inInterpolator) {
                int resId = a.getResourceId(attr, 0);
                if (resId != 0)
                    inInterpolator(AnimationUtils.loadInterpolator(context, resId));
            } else if (attr == R.styleable.BottomSheetDialog_bsd_outDuration)
                outDuration(a.getInteger(attr, 0));
            else if (attr == R.styleable.BottomSheetDialog_bsd_outInterpolator) {
                int resId = a.getResourceId(attr, 0);
                if (resId != 0)
                    outInterpolator(AnimationUtils.loadInterpolator(context, resId));
            }
        }
        a.recycle();
        if (mInInterpolator == null)
            mInInterpolator = new DecelerateInterpolator();
        if (mOutInterpolator == null)
            mOutInterpolator = new AccelerateInterpolator();
        return this;
    }

    public BottomSheetDialog cancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        mCancelable = cancelable;
        return this;
    }

    public BottomSheetDialog canceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        mCanceledOnTouchOutside = cancel;
        return this;
    }

    public BottomSheetDialog dimAmount(float amount) {
        Window window = getWindow();
        if (amount > 0f) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount = amount;
            window.setAttributes(lp);
        } else
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return this;
    }

    public BottomSheetDialog contentView(View v) {
        mContentView = v;
        mContainer.removeAllViews();
        mContainer.addView(v);
        return this;
    }

    public BottomSheetDialog contentView(int layoutId) {
        if (layoutId == 0)
            return this;
        View v = LayoutInflater.from(getContext()).inflate(layoutId, null);
        return contentView(v);
    }

    public BottomSheetDialog heightParam(int height) {
        if (mLayoutHeight != height) {
            mLayoutHeight = height;
            if (isShowing() && mContentView != null) {
                mRunShowAnimation = true;
                mContainer.forceLayout();
                mContainer.requestLayout();
            }
        }
        return this;
    }

    public BottomSheetDialog inDuration(int duration) {
        mInDuration = duration;
        return this;
    }

    public BottomSheetDialog inInterpolator(Interpolator interpolator) {
        mInInterpolator = interpolator;
        return this;
    }

    public BottomSheetDialog outDuration(int duration) {
        mOutDuration = duration;
        return this;
    }

    public BottomSheetDialog outInterpolator(Interpolator interpolator) {
        mOutInterpolator = interpolator;
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mContentView != null) {
            mRunShowAnimation = true;
            mContainer.forceLayout();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mContainer = null;
        mContentView = null;
        mGestureDetector = null;
    }

    @Override
    public void setCancelable(boolean flag) {
        cancelable(flag);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        canceledOnTouchOutside(cancel);
    }

    @Override
    public void setContentView(View v) {
        contentView(v);
    }

    @Override
    public void setContentView(int layoutId) {
        contentView(layoutId);
    }

    @Override
    public void setContentView(View v, ViewGroup.LayoutParams params) {
        contentView(v);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        contentView(view);
    }

    public void dismissImmediately() {
        super.dismiss();
        if (mAnimation != null)
            mAnimation.cancel();
        if (mHandler != null)
            mHandler.removeCallbacks(mDismissAction);
    }

    @Override
    public void dismiss() {
        if (!isShowing())
            return;
        if (mContentView != null) {
            mAnimation = new SlideAnimation(mContentView.getTop(), mContainer.getMeasuredHeight());
            mAnimation.setDuration(mOutDuration);
            mAnimation.setInterpolator(mOutInterpolator);
            mAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mAnimation = null;
                    mHandler.post(mDismissAction);
                }
            });
            mContentView.startAnimation(mAnimation);
        } else
            mHandler.post(mDismissAction);
    }

    protected int getContainerHeight() {
        return mContainer == null ? 0 : mContainer.getHeight();
    }

    private class ContainerFrameLayout extends FrameLayout {

        private boolean mClickOutside = false;
        private int mChildTop = -1;

        public ContainerFrameLayout(Context context) {
            super(context);
        }

        public void setChildTop(int top) {
            mChildTop = top;
            View child = getChildAt(0);
            if (child != null)
                child.offsetTopAndBottom(mChildTop - child.getTop());
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            View child = getChildAt(0);
            if (child != null) {
                switch (mLayoutHeight) {
                    case ViewGroup.LayoutParams.WRAP_CONTENT:
                        child.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
                        break;
                    case ViewGroup.LayoutParams.MATCH_PARENT:
                        child.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
                        break;
                    default:
                        child.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(Math.min(mLayoutHeight, heightSize), MeasureSpec.EXACTLY));
                        break;
                }
            }
            setMeasuredDimension(widthSize, heightSize);
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            View child = getChildAt(0);
            if (child != null) {
                if (mChildTop < 0)
                    mChildTop = bottom - top;
                child.layout(0, mChildTop, child.getMeasuredWidth(), Math.max(bottom - top, mChildTop + child.getMeasuredHeight()));
                if (mRunShowAnimation) {
                    mRunShowAnimation = false;
                    if (mAnimation != null) {
                        mAnimation.cancel();
                        mAnimation = null;
                    }
                    if (mContentView != null) {
                        int start = mChildTop < 0 ? getHeight() : child.getTop();
                        int end = getMeasuredHeight() - mContentView.getMeasuredHeight();
                        mOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 52, getResources().getDisplayMetrics());
                        if (start != end) {
                            mAnimation = new SlideAnimation(start, end);
                            mAnimation.setDuration(mInDuration);
                            mAnimation.setInterpolator(mInInterpolator);
                            mAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    mAnimation = null;
                                }
                            });
                            mContentView.startAnimation(mAnimation);
                        }
                    }
                }
            }
        }

        private boolean isOutsideDialog(float x, float y) {
            if (y < mChildTop)
                return true;
            View child = getChildAt(0);
            return child != null && y > mChildTop + child.getMeasuredHeight();
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (!super.dispatchTouchEvent(ev) && mGestureDetector != null)
                mGestureDetector.onTouchEvent(ev);
            return true;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            boolean handled = super.onTouchEvent(event);
            if (handled)
                return true;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isOutsideDialog(event.getX(), event.getY())) {
                        mClickOutside = true;
                        return true;
                    }
                    return false;
                case MotionEvent.ACTION_MOVE:
                    return mClickOutside;
                case MotionEvent.ACTION_CANCEL:
                    mClickOutside = false;
                    return false;
                case MotionEvent.ACTION_UP:
                    if (mClickOutside && isOutsideDialog(event.getX(), event.getY())) {
                        mClickOutside = false;
                        if (mCancelable && mCanceledOnTouchOutside)
                            dismiss();
                        return true;
                    }
                    return false;
            }
            return false;
        }

    }

    private class SlideAnimation extends Animation {

        int mStart;
        int mEnd;

        public SlideAnimation(int start, int end) {
            mStart = start;
            mEnd = end + mOffset;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int top = Math.round((mEnd - mStart) * interpolatedTime + mStart);
            if (mContainer != null)
                mContainer.setChildTop(top);
            else
                cancel();
        }
    }
}

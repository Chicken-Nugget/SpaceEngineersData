package chickennugget.spaceengineersdata.cards.listeners;

import android.widget.AbsListView;

public class SwipeOnScrollListener implements AbsListView.OnScrollListener {

    private SwipeDismissListViewTouchListener mTouchListener;

    public void setTouchListener(final SwipeDismissListViewTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        if (mTouchListener != null) mTouchListener.setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
    }
}
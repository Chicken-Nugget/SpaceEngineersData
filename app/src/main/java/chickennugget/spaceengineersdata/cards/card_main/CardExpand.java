package chickennugget.spaceengineersdata.cards.card_main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chickennugget.spaceengineersdata.R;

public class CardExpand extends BaseCard {

    private boolean couldUseNativeInnerLayout = false;

    public CardExpand(Context context) {
        this(context, R.layout.inner_base_expand);
    }

    public CardExpand(Context context, int innerLayout) {
        super(context);
        mInnerLayout = innerLayout;
        if (innerLayout == R.layout.inner_base_expand)
            couldUseNativeInnerLayout = true;
    }

    @Override
    public View getInnerView(Context context, ViewGroup parent) {
        if (couldUseNativeInnerLayout && isNative())
            mInnerLayout = R.layout.native_inner_base_expand;
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
        // Add simple title to expand area
        if (view != null) {
            TextView mTitleView = (TextView) view.findViewById(R.id.card_expand_inner_simple_title);
            if (mTitleView != null)
                mTitleView.setText(mTitle);
        }
    }

    protected boolean isNative() {
        if (getParentCard() != null)
            return getParentCard().isNative();
        return false;
    }
}

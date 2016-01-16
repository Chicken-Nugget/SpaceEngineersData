package chickennugget.spaceengineersdata.cards;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import chickennugget.spaceengineersdata.R;

public class CardShadowView extends FrameLayout implements CardViewInterface {

    protected int card_shadow_layout_resourceID = R.layout.base_shadow_layout;
    protected View mInternalOuterView;

    public CardShadowView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardShadowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        initAttrs(attrs, defStyle);
        if (!isInEditMode())
            initView();
    }

    protected void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);
        try {
            card_shadow_layout_resourceID = a.getResourceId(R.styleable.card_options_card_shadow_layout_resourceID, card_shadow_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInternalOuterView = inflater.inflate(card_shadow_layout_resourceID, this, true);
    }

    @Override
    public View getInternalOuterView() {
        return mInternalOuterView;
    }
}

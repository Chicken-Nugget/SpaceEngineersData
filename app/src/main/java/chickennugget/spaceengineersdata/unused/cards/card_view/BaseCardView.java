package chickennugget.spaceengineersdata.unused.cards.card_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.card_main.Card;
import chickennugget.spaceengineersdata.unused.cards.helpers.CardViewHelperUtil;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewHelper;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewInterface;

public class BaseCardView extends LinearLayout implements CardViewInterface {

    protected static String TAG = "BaseCardView";
    protected Card mCard;
    protected int card_layout_resourceID = R.layout.card_base_layout;
    protected View mInternalOuterView;
    protected CardShadowView mInternalShadowLayout;
    protected CardHeaderView mInternalHeaderLayout;
    protected CardThumbnailView mInternalThumbnailLayout;
    protected boolean mIsRecycle = false;
    protected boolean mForceReplaceInnerLayout = false;
    protected CardViewHelper mHelperImpl;

    public BaseCardView(Context context) {
        this(context, null, 0);
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
        mHelperImpl = CardViewHelperUtil.getInstance(context);
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
            card_layout_resourceID = a.getResourceId(R.styleable.card_options_card_layout_resourceID, card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInternalOuterView = inflater.inflate(card_layout_resourceID, this, true);
    }

    protected void buildUI() {
        if (mCard == null) {
            Log.e(TAG, "No card model found. Please use setCard(card) to set all values.");
            return;
        }
        setupShadowView();
    }

    protected void setupShadowView() {
        if (mInternalShadowLayout != null && mCard != null) {
            if (!mCard.isShadow()) {
                mInternalShadowLayout.setVisibility(GONE);
            } else {
                mInternalShadowLayout.setVisibility(VISIBLE);
            }
        }
    }

    protected void retrieveLayoutIDs() {
        mInternalShadowLayout = (CardShadowView) findViewById(R.id.card_shadow_layout);
    }

    @Override
    public View getInternalOuterView() {
        return mInternalOuterView;
    }

    public Card getCard() {
        return mCard;
    }

    public void setCard(Card card) {
        mCard = card;
    }

    public CardShadowView getInternalShadowLayout() {
        return mInternalShadowLayout;
    }

    public CardHeaderView getInternalHeaderLayout() {
        return mInternalHeaderLayout;
    }

    public CardThumbnailView getInternalThumbnailLayout() {
        return mInternalThumbnailLayout;
    }

    public boolean isRecycle() {
        return mIsRecycle;
    }

    public void setRecycle(boolean isRecycle) {
        this.mIsRecycle = isRecycle;
    }

    public boolean isForceReplaceInnerLayout() {
        return mForceReplaceInnerLayout;
    }

    public void setForceReplaceInnerLayout(boolean forceReplaceInnerLayout) {
        this.mForceReplaceInnerLayout = forceReplaceInnerLayout;
    }
}

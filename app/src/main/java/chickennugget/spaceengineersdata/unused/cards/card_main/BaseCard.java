package chickennugget.spaceengineersdata.unused.cards.card_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chickennugget.spaceengineersdata.unused.cards.interfaces.CardUIInferface;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewWrapper;

public abstract class BaseCard implements CardUIInferface {

    protected Context mContext;
    protected int mInnerLayout = -1;
    protected CardViewWrapper mCardView;
    protected View mInnerView;
    protected Card mParentCard;
    protected String mTitle;
    protected String mId = null;
    protected int type = 0;

    public BaseCard(Context context) {
        mContext = context;
    }

    public CardViewWrapper getCardView() {
        return mCardView;
    }

    public void setCardView(CardViewWrapper cardView) {
        mCardView = cardView;
    }

    @Override
    public View getInnerView(Context context, ViewGroup parent) {
        if (mInnerLayout > -1) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mInnerView = inflater.inflate(mInnerLayout, parent, false);
            return mInnerView;
        }
        return null;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public int getInnerLayout() {
        return mInnerLayout;
    }

    public void setInnerLayout(int innerLayout) {
        mInnerLayout = innerLayout;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Card getParentCard() {
        return mParentCard;
    }

    public void setParentCard(Card parentCard) {
        mParentCard = parentCard;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

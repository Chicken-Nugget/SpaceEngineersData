package chickennugget.spaceengineersdata.cards.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.cards.card_main.Card;
import chickennugget.spaceengineersdata.cards.card_view.CardGridView;
import chickennugget.spaceengineersdata.cards.interfaces.CardViewWrapper;
import chickennugget.spaceengineersdata.cards.listeners.SwipeDismissListViewTouchListener;

public class CardGridArrayAdapter extends BaseCardArrayAdapter {

    protected static String TAG = "CardGridArrayAdapter";
    protected CardGridView mCardGridView;
    protected SwipeDismissListViewTouchListener mOnTouchListener;

    public CardGridArrayAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CardViewWrapper mCardView;
        Card mCard;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCard = getItem(position);
        if (mCard != null) {
            int layout = mRowLayoutId;
            boolean recycle = false;
            if (view == null) {
                recycle = false;
                view = mInflater.inflate(layout, parent, false);
            } else {
                recycle = true;
            }
            // Setup card
            mCardView = (CardViewWrapper) view.findViewById(R.id.list_cardId);
            if (mCardView != null) {
                mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(), mCard));
                mCardView.setRecycle(recycle);
                boolean origianlSwipeable = mCard.isSwipeable();
                mCard.setSwipeable(false);
                mCardView.setCard(mCard);
                if (origianlSwipeable)
                    Log.d(TAG, "Swipe action not enabled in this type of view");
                if (mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible())
                    Log.d(TAG, "Expand action not enabled in this type of view");
                setupSwipeableAnimation(mCard, mCardView);
                setupMultichoice(view, mCard, mCardView, position);
            }
        }
        return view;
    }

    protected void setupSwipeableAnimation(final Card card, CardViewWrapper cardView) {
        cardView.setOnTouchListener(null);
    }

    protected void setupExpandCollapseListAnimation(CardViewWrapper cardView) {
        if (cardView == null) return;
        cardView.setOnExpandListAnimatorListener(mCardGridView);
    }

    public CardGridView getCardGridView() {
        return mCardGridView;
    }

    public void setCardGridView(CardGridView cardGridView) {
        this.mCardGridView = cardGridView;
    }
}

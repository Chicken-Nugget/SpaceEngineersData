package chickennugget.spaceengineersdata.unused.cards.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.card_main.Card;
import chickennugget.spaceengineersdata.unused.cards.card_main.CardListView;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewWrapper;

public abstract class CardCursorAdapter extends BaseCardCursorAdapter {

    protected static String TAG = "CardCursorAdapter";
    protected final List<String> mExpandedIds;
    protected CardListView mCardListView;
    protected HashMap<String /* id */, Card> mInternalObjects;
    protected boolean recycle = false;

    public CardCursorAdapter(Context context) {
        super(context, null, 0);
        mExpandedIds = new ArrayList<String>();
    }

    protected CardCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mExpandedIds = new ArrayList<String>();
    }

    protected CardCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mExpandedIds = new ArrayList<String>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        recycle = convertView != null;
        return super.getView(position, convertView, parent);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layout = mRowLayoutId;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return mInflater.inflate(layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CardViewWrapper mCardView;
        Card mCard;
        mCard = getCardFromCursor(cursor);
        if (mCard != null) {
            mCardView = (CardViewWrapper) view.findViewById(R.id.list_cardId);
            if (mCardView != null) {
                mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(), mCard));
                mCardView.setRecycle(recycle);
                boolean origianlSwipeable = mCard.isSwipeable();
                mCard.setSwipeable(false);
                mCard.setExpanded(isExpanded(mCard));
                mCardView.setCard(mCard);
                if (origianlSwipeable)
                    Log.d(TAG, "Swipe action not enabled in this type of view");
                if ((mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) || mCard.getViewToClickToExpand() != null) {
                    setupExpandCollapseListAnimation(mCardView);
                }
                setupSwipeableAnimation(mCard, mCardView);
                setupMultichoice(view, mCard, mCardView, cursor.getPosition());
            }
        }
    }

    protected void setupSwipeableAnimation(final Card card, CardViewWrapper cardView) {
        cardView.setOnTouchListener(null);
    }

    protected void setupExpandCollapseListAnimation(CardViewWrapper cardView) {
        if (cardView == null) return;
        cardView.setOnExpandListAnimatorListener(mCardListView);
    }

    public void setExpanded(Card card) {
        if (card != null)
            setExpanded(card.getId());
    }

    public void setExpanded(final String id) {
        if (mExpandedIds != null) {
            if (mExpandedIds.contains(id)) {
                return;
            }
            mExpandedIds.add(id);
        }
    }

    public void setCollapsed(Card card) {
        if (card != null)
            setCollapsed(card.getId());
    }

    public void setCollapsed(final String id) {
        if (mExpandedIds != null) {
            if (!mExpandedIds.contains(id)) {
                return;
            }
            mExpandedIds.remove(id);
        }
    }

    public boolean isExpanded(Card card) {
        String itemId = card.getId();
        return mExpandedIds.contains(itemId);
    }

    public boolean onExpandStart(CardViewWrapper viewCard) {
        Card card = viewCard.getCard();
        if (card != null) {
            String itemId = card.getId();
            if (!mExpandedIds.contains(itemId)) {
                return true;
            }
        }
        return false;
    }

    public boolean onCollapseStart(CardViewWrapper viewCard) {
        Card card = viewCard.getCard();
        if (card != null) {
            String itemId = card.getId();
            if (mExpandedIds.contains(itemId)) {
                return true;
            }
        }
        return false;
    }

    public void onExpandEnd(CardViewWrapper viewCard) {
        Card card = viewCard.getCard();
        if (card != null) {
            setExpanded(card);
        }
    }

    public void onCollapseEnd(CardViewWrapper viewCard) {
        Card card = viewCard.getCard();
        if (card != null) {
            setCollapsed(card);
        }
    }

    public CardListView getCardListView() {
        return mCardListView;
    }

    public void setCardListView(CardListView cardListView) {
        this.mCardListView = cardListView;
    }

    public List<String> getExpandedIds() {
        return mExpandedIds;
    }
}

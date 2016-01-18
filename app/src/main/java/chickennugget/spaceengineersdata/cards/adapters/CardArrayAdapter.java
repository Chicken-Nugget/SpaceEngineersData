package chickennugget.spaceengineersdata.cards.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.cards.card_main.Card;
import chickennugget.spaceengineersdata.cards.card_main.CardListView;
import chickennugget.spaceengineersdata.cards.interfaces.CardViewWrapper;
import chickennugget.spaceengineersdata.cards.interfaces.Dismissable;
import chickennugget.spaceengineersdata.cards.listeners.SwipeDismissListViewTouchListener;

public class CardArrayAdapter extends BaseCardArrayAdapter {

    protected static String TAG = "CardArrayAdapter";
    protected CardListView mCardListView;
    protected SwipeDismissListViewTouchListener mOnTouchListener;
    protected boolean mEnableUndo = false;
    protected HashMap<String /* id */, Card> mInternalObjects;
    protected Dismissable mDismissable;

    SwipeDismissListViewTouchListener.DismissCallbacks mCallback = new SwipeDismissListViewTouchListener.DismissCallbacks() {

        @Override
        public boolean canDismiss(int position, Card card) {
            return mDismissable.isDismissable(position, card);
        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            int[] itemPositions = new int[reverseSortedPositions.length];
            String[] itemIds = new String[reverseSortedPositions.length];
            int i = 0;
            final ArrayList<Card> removedCards = new ArrayList<Card>();
            for (int position : reverseSortedPositions) {
                Card card = null;
                if (listView.getAdapter() != null && listView.getAdapter().getItem(position) instanceof Card)
                    card = (Card) listView.getAdapter().getItem(position);
                //Card card = getItem(position);

                if (card != null) {
                    itemPositions[i] = position;
                    itemIds[i] = card.getId();
                    i++;
                    /*if (card.isExpanded()){
                        if (card.getCardView()!=null && card.getCardView().getOnExpandListAnimatorListener()!=null){
                            //There is a List Animator.
                            card.getCardView().getOnExpandListAnimatorListener().onCollapseStart(card.getCardView(), card.getCardView().getInternalExpandLayout());
                        }
                    }*/
                    removedCards.add(card);
                    remove(card);
                    if (card.getOnSwipeListener() != null) {
                        card.getOnSwipeListener().onSwipe(card);
                    }
                } else {
                    Log.e(TAG, "Error on swipe action. Impossible to retrieve the card from position");
                }
            }
            notifyDataSetChanged();
        }
    };

    public CardArrayAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CardViewWrapper mCardView;
        Card mCard;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Retrieve card from items
        mCard = getItem(position);
        if (mCard != null) {
            int layout = mRowLayoutId;
            boolean recycle = false;
            // Inflate layout
            if (view == null) {
                recycle = false;
                view = mInflater.inflate(layout, parent, false);
            } else recycle = true;
            // Setup card
            mCardView = (CardViewWrapper) view.findViewById(R.id.list_cardId);
            if (mCardView != null) {
                mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(), mCard));
                mCardView.setRecycle(recycle);
                boolean origianlSwipeable = mCard.isSwipeable();
                mCard.setSwipeable(false);
                mCardView.setCard(mCard);
                mCard.setSwipeable(origianlSwipeable);
                if ((mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) || mCard.getViewToClickToExpand() != null)
                    setupExpandCollapseListAnimation(mCardView);
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
        cardView.setOnExpandListAnimatorListener(mCardListView);
    }

    public boolean isEnableUndo() {
        return mEnableUndo;
    }

    @Override
    public void add(Card card) {
        super.add(card);
        if (mEnableUndo) {
            mInternalObjects.put(card.getId(), card);
        }
    }

    @Override
    public void addAll(Collection<? extends Card> cardCollection) {
        super.addAll(cardCollection);
        if (mEnableUndo) {
            for (Card card : cardCollection) {
                mInternalObjects.put(card.getId(), card);
            }
        }
    }

    @Override
    public void addAll(Card... cards) {
        super.addAll(cards);
        if (mEnableUndo) {
            for (Card card : cards) {
                mInternalObjects.put(card.getId(), card);
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        if (mEnableUndo) {
            mInternalObjects.clear();
        }
    }

    @Override
    public void insert(Card card, int index) {
        super.insert(card, index);
        if (mEnableUndo) {
            mInternalObjects.put(card.getId(), card);
        }
    }

    public CardListView getCardListView() {
        return mCardListView;
    }

    public void setCardListView(CardListView cardListView) {
        this.mCardListView = cardListView;
    }
}

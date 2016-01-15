/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package chickennugget.spaceengineersdata.cards;

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

/**
 * Array Adapter for {@link Card} model
 * <p/>
 * Usage:
 * <pre><code>
 * ArrayList<Card> cards = new ArrayList<Card>();
 * for (int i=0;i<1000;i++){
 *     CardExample card = new CardExample(getActivity(),"My title "+i,"Inner text "+i);
 *     cards.add(card);
 * }
 * <p>
 * CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);
 * <p>
 * CardListView listView = (CardListView) getActivity().findViewById(R.id.listId);
 * listView.setAdapter(mCardArrayAdapter); *
 * </code></pre>
 * It provides a default layout id for each row @layout/list_card_layout
 * Use can easily customize it using card:list_card_layout_resourceID attr in your xml layout:
 * <pre><code>
 *    <chickennugget.spaceengineersdata.cards.CardListView
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"
 *      android:id="@+id/carddemo_list_gplaycard"
 *      card:list_card_layout_resourceID="@layout/list_card_thumbnail_layout" />
 * </code></pre>
 * or:
 * <pre><code>
 * adapter.setRowLayoutId(list_card_layout_resourceID);
 * </code></pre>
 * </p>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardArrayAdapter extends BaseCardArrayAdapter {

    protected static String TAG = "CardArrayAdapter";

    /**
     * {@link CardListView}
     */
    protected CardListView mCardListView;

    /**
     * Listener invoked when a card is swiped
     */
    protected SwipeDismissListViewTouchListener mOnTouchListener;

    /**
     * Used to enable an undo message after a swipe action
     */
    protected boolean mEnableUndo = false;

    /**
     * Internal Map with all Cards.
     * It uses the card id value as key.
     */
    protected HashMap<String /* id */, Card> mInternalObjects;

    /**
     * Dismissable Manager
     */
    protected Dismissable mDismissable;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------
    /**
     * Listener invoked when a card is swiped
     */
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

            // Keep track of the cards that will be removed
            final ArrayList<Card> removedCards = new ArrayList<Card>();

            //Remove cards and notifyDataSetChanged
            for (int position : reverseSortedPositions) {

                Card card = null;
                if (listView.getAdapter() != null && listView.getAdapter().getItem(position) instanceof Card)
                    card = (Card) listView.getAdapter().getItem(position);
                //Card card = getItem(position);

                if (card != null) {
                    itemPositions[i] = position;
                    itemIds[i] = card.getId();
                    i++;

                    /*
                    if (card.isExpanded()){
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

    // -------------------------------------------------------------
    // Views
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardArrayAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        CardViewWrapper mCardView;
        Card mCard;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Retrieve card from items
        mCard = getItem(position);
        if (mCard != null) {

            int layout = mRowLayoutId;
            boolean recycle = false;

            //Inflate layout
            if (view == null) {
                recycle = false;
                view = mInflater.inflate(layout, parent, false);
            } else {
                recycle = true;
            }

            //Setup card
            mCardView = (CardViewWrapper) view.findViewById(R.id.list_cardId);
            if (mCardView != null) {
                //It is important to set recycle value for inner layout elements
                mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(mCardView.getCard(), mCard));

                //It is important to set recycle value for performance issue
                mCardView.setRecycle(recycle);

                //Save original swipeable to prevent cardSwipeListener (listView requires another cardSwipeListener)
                boolean origianlSwipeable = mCard.isSwipeable();
                mCard.setSwipeable(false);

                mCardView.setCard(mCard);

                //Set originalValue
                mCard.setSwipeable(origianlSwipeable);

                //If card has an expandable button override animation
                if ((mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) || mCard.getViewToClickToExpand() != null) {
                    setupExpandCollapseListAnimation(mCardView);
                }

                //Setup swipeable animation
                setupSwipeableAnimation(mCard, mCardView);

                //setupMultiChoice
                setupMultichoice(view, mCard, mCardView, position);
            }
        }

        return view;
    }

    /**
     * Sets SwipeAnimation on List
     *
     * @param card     {@link Card}
     * @param cardView {@link chickennugget.spaceengineersdata.cards.CardViewWrapper}
     */
    protected void setupSwipeableAnimation(final Card card, CardViewWrapper cardView) {
        cardView.setOnTouchListener(null);
    }

    // -------------------------------------------------------------
    //  SwipeListener and undo action
    // -------------------------------------------------------------

    /**
     * Overrides the default collapse/expand animation in a List
     *
     * @param cardView {@link chickennugget.spaceengineersdata.cards.CardViewWrapper}
     */
    protected void setupExpandCollapseListAnimation(CardViewWrapper cardView) {

        if (cardView == null) return;
        cardView.setOnExpandListAnimatorListener(mCardListView);
    }

    /**
     * Indicates if the undo message is enabled after a swipe action
     *
     * @return <code>true</code> if the undo message is enabled
     */
    public boolean isEnableUndo() {
        return mEnableUndo;
    }


    // ---------------------------------------------------------------------
    //  Override Array Manipulation Methods To Keep mInternalObjects in Sync
    // ---------------------------------------------------------------------

    // public void remove() intentionally omitted, since mInternalObjects needs
    // to keep a reference so the remove can be undone, if necessary.

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

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * @return {@link CardListView}
     */
    public CardListView getCardListView() {
        return mCardListView;
    }

    /**
     * Sets the {@link CardListView}
     *
     * @param cardListView cardListView
     */
    public void setCardListView(CardListView cardListView) {
        this.mCardListView = cardListView;
    }

}

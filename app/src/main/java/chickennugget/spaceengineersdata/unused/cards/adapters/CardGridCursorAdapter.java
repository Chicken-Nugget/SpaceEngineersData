package chickennugget.spaceengineersdata.unused.cards.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.card_main.Card;
import chickennugget.spaceengineersdata.unused.cards.card_view.CardGridView;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewWrapper;

public abstract class CardGridCursorAdapter extends BaseCardCursorAdapter {

    protected static String TAG = "CardGridCursorAdapter";
    protected CardGridView mCardGridView;
    protected HashMap<String /* id */, Card> mInternalObjects;
    private boolean recycle = false;

    public CardGridCursorAdapter(Context context) {
        super(context, null, 0);
    }

    protected CardGridCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    protected CardGridCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
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
                mCardView.setCard(mCard);

                if (origianlSwipeable)
                    Log.d(TAG, "Swipe action not enabled in this type of view");

                if (mCard.getCardHeader() != null && mCard.getCardHeader().isButtonExpandVisible()) {
                    //setupExpandCollapseListAnimation(mCardView);
                    Log.d(TAG, "Expand action not enabled in this type of view");
                }
                setupSwipeableAnimation(mCard, mCardView);
            }
        }
    }

    protected void setupSwipeableAnimation(final Card card, CardViewWrapper cardView) {
        cardView.setOnTouchListener(null);
    }


    public CardGridView getCardGridView() {
        return mCardGridView;
    }

    public void setCardGridView(CardGridView cardGridView) {
        this.mCardGridView = cardGridView;
    }

/*  public boolean isEnableUndo() {
        return mEnableUndo;
    }

    public void setEnableUndo(boolean enableUndo) {
        mEnableUndo = enableUndo;
        if (enableUndo) {
            mInternalObjects = new HashMap<String, Card>();
            for (int i=0;i<getCount();i++) {
                Card card = getItem(i);
                mInternalObjects.put(card.getId(), card);
            }

            //Create a UndoController
            if (mUndoBarController==null){
                View undobar = ((Activity)mContext).findViewById(R.id.list_card_undobar);
                if (undobar != null) {
                    mUndoBarController = new UndoBarController(undobar, this);
                }
            }
        }else{
            mUndoBarController=null;
        }
    }

    public UndoBarController getUndoBarController() {
        return mUndoBarController;
    }
*/
}

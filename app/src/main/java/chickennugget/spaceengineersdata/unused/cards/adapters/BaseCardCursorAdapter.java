package chickennugget.spaceengineersdata.unused.cards.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CursorAdapter;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.card_main.Card;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewWrapper;

public abstract class BaseCardCursorAdapter extends CursorAdapter {

    protected Context mContext;
    protected int mRowLayoutId = R.layout.list_card_layout;
    protected int innerviewTypeCount = 1;

    protected BaseCardCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
    }

    protected BaseCardCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
    }

    @Override
    public int getViewTypeCount() {
        return innerviewTypeCount;
    }

    @Override
    public int getItemViewType(int position) {
        Card card = getItem(position);
        return card.getType();
    }

    @Override
    public boolean isEnabled(int position) {
        Card card = getItem(position);
        return card.isClickable() || card.isLongClickable();
    }

    protected void setupMultichoice(View view, Card mCard, CardViewWrapper mCardView, long position) {
    }

    @Override
    public Card getItem(int position) {
        Object obj = super.getItem(position);
        if (obj instanceof Cursor)
            return getCardFromCursor((Cursor) obj);
        else
            return null;
    }

    protected abstract Card getCardFromCursor(Cursor cursor);

    public Context getContext() {
        return mContext;
    }

    public void setRowLayoutId(int rowLayoutId) {
        this.mRowLayoutId = rowLayoutId;
    }

    public void setInnerViewTypeCount(int viewTypeCount) {
        this.innerviewTypeCount = viewTypeCount;
    }
}

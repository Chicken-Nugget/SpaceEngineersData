package chickennugget.spaceengineersdata.unused.cards.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.card_main.Card;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewWrapper;

public abstract class BaseCardArrayAdapter extends ArrayAdapter<Card> {

    protected Context mContext;
    protected int mRowLayoutId = R.layout.list_card_layout;
    protected int innerviewTypeCount = 1;

    public BaseCardArrayAdapter(Context context, List<Card> cards) {
        super(context, 0, cards);
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

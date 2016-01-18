package chickennugget.spaceengineersdata.cards.card_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.cards.adapters.CardGridArrayAdapter;
import chickennugget.spaceengineersdata.cards.adapters.CardGridCursorAdapter;
import chickennugget.spaceengineersdata.cards.interfaces.CardViewWrapper;

public class CardGridView extends GridView implements CardViewWrapper.OnExpandListAnimatorListener {

    protected static String TAG = "CardGridView";
    protected CardGridArrayAdapter mAdapter;
    protected CardGridCursorAdapter mCursorAdapter;
    protected int list_card_layout_resourceID = R.layout.list_card_layout;

    public CardGridView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        initAttrs(attrs, defStyle);
    }

    protected void initAttrs(AttributeSet attrs, int defStyle) {
        list_card_layout_resourceID = R.layout.list_card_layout;
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.card_options, defStyle, defStyle);
        try {
            list_card_layout_resourceID = a.getResourceId(R.styleable.card_options_list_card_layout_resourceID, this.list_card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof CardGridArrayAdapter) {
            setAdapter((CardGridArrayAdapter) adapter);
        } else if (adapter instanceof CardGridCursorAdapter) {
            setAdapter((CardGridCursorAdapter) adapter);
        } else {
            Log.w(TAG, "You are using a generic adapter. Pay attention: your adapter has to call cardGridArrayAdapter#getView method.");
            super.setAdapter(adapter);
        }
    }

    public void setAdapter(CardGridArrayAdapter adapter) {
        super.setAdapter(adapter);
        adapter.setRowLayoutId(list_card_layout_resourceID);
        adapter.setCardGridView(this);
        mAdapter = adapter;
    }

    public void setAdapter(CardGridCursorAdapter adapter) {
        super.setAdapter(adapter);
        adapter.setRowLayoutId(list_card_layout_resourceID);
        adapter.setCardGridView(this);
        mCursorAdapter = adapter;
    }

    public void setExternalAdapter(ListAdapter adapter, CardGridArrayAdapter cardGridArrayAdapter) {
        setAdapter(adapter);
        mAdapter = cardGridArrayAdapter;
        mAdapter.setCardGridView(this);
        mAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    public void setExternalAdapter(ListAdapter adapter, CardGridCursorAdapter cardCursorAdapter) {
        setAdapter(adapter);
        mCursorAdapter = cardCursorAdapter;
        mCursorAdapter.setCardGridView(this);
        mCursorAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    @Override
    public void onExpandStart(CardViewWrapper viewCard, View expandingLayout) {
        Log.w(TAG, "Don't use this kind of animation in a grid");
    }

    @Override
    public void onCollapseStart(CardViewWrapper viewCard, View expandingLayout) {
        Log.w(TAG, "Don't use this kind of animation in a grid");
    }
}

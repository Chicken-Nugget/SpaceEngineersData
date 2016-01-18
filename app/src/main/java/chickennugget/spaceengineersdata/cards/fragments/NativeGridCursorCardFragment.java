package chickennugget.spaceengineersdata.cards.fragments;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.cards.adapters.CardGridCursorAdapter;
import chickennugget.spaceengineersdata.cards.card_main.BaseCard;
import chickennugget.spaceengineersdata.cards.card_main.Card;
import chickennugget.spaceengineersdata.cards.card_main.CardHeader;
import chickennugget.spaceengineersdata.cards.card_main.CardThumbnail;
import chickennugget.spaceengineersdata.cards.card_view.CardGridView;
import chickennugget.spaceengineersdata.cards.others.CardCursorContract;

public class NativeGridCursorCardFragment extends BaseNativeListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    MyCursorCardAdapter mAdapter;
    CardGridView mGridView;

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_subtitle_gcursor;
    }

    @Override
    protected int getTitleHeaderResourceId() {
        return R.string.header_title_group3;
    }

    @Override
    protected String getDocUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/doc/CARDGRID.md";
    }

    @Override
    protected String getSourceUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/nativeview/NativeGridCursorCardFragment.java";
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_grid_cursor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.demo_fragment_native_grid_cursor, container, false);
        setupListFragment(root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideList(false);
        init();
    }

    private void init() {
        mAdapter = new MyCursorCardAdapter(getActivity());
        mGridView = (CardGridView) getActivity().findViewById(R.id.carddemo_grid_cursor);
        if (mGridView != null) mGridView.setAdapter(mAdapter);
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        loader = new CursorLoader(getActivity(), null, CardCursorContract.CardCursor.ALL_PROJECTION, null, null, CardCursorContract.CardCursor.DEFAULT_SORT);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (getActivity() == null) return;
        mAdapter.swapCursor(data);
        displayList();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private void removeCard(Card card) {
        ContentResolver resolver = getActivity().getContentResolver();
        //mAdapter.notifyDataSetChanged();
    }

    public class MyCursorCardAdapter extends CardGridCursorAdapter {

        public MyCursorCardAdapter(Context context) {
            super(context);
        }

        @Override
        protected Card getCardFromCursor(Cursor cursor) {
            MyCursorCard card = new MyCursorCard(super.getContext());
            setCardFromCursor(card, cursor);
            CardHeader header = new CardHeader(getActivity(), R.layout.native_inner_gplay2_header);
            header.setTitle(card.mainHeader);
            header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    Toast.makeText(getContext(), "Click on card=" + card.getId() + " item=" + item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            card.addCardHeader(header);
            CardThumbnail thumb = new CardThumbnail(getActivity());
            thumb.setDrawableResource(card.resourceIdThumb);
            card.addCardThumbnail(thumb);
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Card id=" + card.getId() + " Title=" + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            return card;
        }

        private void setCardFromCursor(MyCursorCard card, Cursor cursor) {
            card.mainTitle = cursor.getString(CardCursorContract.CardCursor.IndexColumns.TITLE_COLUMN);
            card.secondaryTitle = cursor.getString(CardCursorContract.CardCursor.IndexColumns.SUBTITLE_COLUMN);
            card.mainHeader = cursor.getString(CardCursorContract.CardCursor.IndexColumns.HEADER_COLUMN);
            card.setId("" + cursor.getInt(CardCursorContract.CardCursor.IndexColumns.ID_COLUMN));
            int thumb = cursor.getInt(CardCursorContract.CardCursor.IndexColumns.THUMBNAIL_COLUMN);
            switch (thumb) {
                case 0:
                    card.resourceIdThumb = R.drawable.ic_ic_launcher_web;
                    break;
                case 1:
                    card.resourceIdThumb = R.drawable.ic_ic_dh_net;
                    break;
                case 2:
                    card.resourceIdThumb = R.drawable.ic_tris;
                    break;
                case 3:
                    card.resourceIdThumb = R.drawable.ic_info;
                    break;
                case 4:
                    card.resourceIdThumb = R.drawable.ic_smile;
                    break;
            }
        }
    }

    public class MyCursorCard extends Card {

        String mainTitle;
        String secondaryTitle;
        String mainHeader;
        int resourceIdThumb;

        public MyCursorCard(Context context) {
            super(context, R.layout.carddemo_cursor_native_inner_content);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            TextView mTitleTextView = (TextView) parent.findViewById(R.id.carddemo_cursor_main_inner_title);
            TextView mSecondaryTitleTextView = (TextView) parent.findViewById(R.id.carddemo_cursor_main_inner_subtitle);
            if (mTitleTextView != null)
                mTitleTextView.setText(mainTitle);
            if (mSecondaryTitleTextView != null)
                mSecondaryTitleTextView.setText(secondaryTitle);
        }
    }
}

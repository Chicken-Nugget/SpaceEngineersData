package chickennugget.spaceengineersdata.unused.cards.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.adapters.CardGridArrayAdapter;
import chickennugget.spaceengineersdata.unused.cards.card_main.Card;
import chickennugget.spaceengineersdata.unused.cards.card_main.CardHeader;
import chickennugget.spaceengineersdata.unused.cards.card_main.CardThumbnail;
import chickennugget.spaceengineersdata.unused.cards.card_view.CardGridView;

public class GridBaseFragment extends MaterialV1Fragment {

    protected ScrollView mScrollView;

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_subtitle_gbase;
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
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/v1/GridBaseFragment.java";
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_grid_base;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_grid_base, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCards();
    }


    private void initCards() {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {
            GdriveGridCard card = new GdriveGridCard(getActivity());
            card.headerTitle = "Folder " + i;
            card.init();
            cards.add(card);
        }
        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getActivity(), cards);
        CardGridView gridView = (CardGridView) getActivity().findViewById(R.id.carddemo_grid_base);
        if (gridView != null) gridView.setAdapter(mCardArrayAdapter);
    }


    public class GdriveGridCard extends Card {

        protected String headerTitle;

        public GdriveGridCard(Context context) {
            super(context);
        }

        private void init() {
            CardHeader header = new CardHeader(getContext(), R.layout.carddemo_gdrive_header_inner);
            header.setTitle(headerTitle);
            header.setOtherButtonVisible(true);
            header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                @Override
                public void onButtonItemClick(Card card, View view) {
                    Toast.makeText(getActivity(), "Click on Other Button", Toast.LENGTH_SHORT).show();
                }
            });
            header.setOtherButtonDrawable(R.drawable.card_menu_button_expand);
            addCardHeader(header);
            GdriveGridThumb thumbnail = new GdriveGridThumb(getContext());
            thumbnail.setDrawableResource(R.drawable.ic_action_folder_closed);
            addCardThumbnail(thumbnail);
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Click Listener card=" + headerTitle, Toast.LENGTH_SHORT).show();
                }
            });
            setSwipeable(true);
        }

        class GdriveGridThumb extends CardThumbnail {

            public GdriveGridThumb(Context context) {
                super(context);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {
                viewImage.getLayoutParams().width = 32;
                viewImage.getLayoutParams().height = 32;
            }
        }
    }
}

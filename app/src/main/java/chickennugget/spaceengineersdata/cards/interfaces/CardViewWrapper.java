package chickennugget.spaceengineersdata.cards.interfaces;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import chickennugget.spaceengineersdata.cards.card_main.Card;
import chickennugget.spaceengineersdata.cards.card_view.CardThumbnailView;

public interface CardViewWrapper {

    Context getContext();

    void doExpand();

    void doCollapse();

    void doToggleExpand();

    void setLongClickable(boolean b);

    void changeBackgroundResourceId(int drawableResourceId);

    void changeBackgroundResource(Drawable drawableResource);

    void changeBackgroundColorResourceId(int colorResourceId);

    Card getCard();

    void setCard(Card card);

    void setForceReplaceInnerLayout(boolean forceReplaceInnerLayout);

    void setRecycle(boolean recycle);

    void refreshCard(Card card);

    CardThumbnailView getInternalThumbnailLayout();

    void setOnTouchListener(View.OnTouchListener onTouchListener);

    void setOnExpandListAnimatorListener(OnExpandListAnimatorListener onExpandListAnimatorListener);

    void setOnClickListener(View.OnClickListener advanceClickListener);

    void setExpanded(boolean expanded);

    boolean isNative();

    View getInternalMainCardLayout();

    interface OnExpandListAnimatorListener {
        void onExpandStart(CardViewWrapper viewCard, View expandingLayout);

        void onCollapseStart(CardViewWrapper viewCard, View expandingLayout);
    }
}

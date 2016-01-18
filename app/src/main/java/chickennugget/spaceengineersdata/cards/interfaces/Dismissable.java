package chickennugget.spaceengineersdata.cards.interfaces;

import android.widget.Adapter;

import chickennugget.spaceengineersdata.cards.card_main.Card;

public interface Dismissable {

    boolean isDismissable(int position, Card card);

    SwipeDirection getSwipeDirectionAllowed();

    void setAdapter(Adapter adapter);

    enum SwipeDirection {
        BOTH(0),
        LEFT(1),
        RIGHT(2);

        private final int mValue;

        SwipeDirection(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }
}

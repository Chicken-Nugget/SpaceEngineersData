package chickennugget.spaceengineersdata.cards.others;

import android.view.View;

public class ViewToClickToExpand {

    protected View viewToClick;
    protected boolean viewToSelect = false;
    protected CardElementUI cardElementUIToClick;
    protected boolean enableForCode = false;
    protected boolean useLongClick = false;

    protected ViewToClickToExpand() {
    }

    public static ViewToClickToExpand builder() {
        return new ViewToClickToExpand();
    }

    public ViewToClickToExpand setupView(View viewToClick) {
        this.viewToClick = viewToClick;
        return this;
    }

    public ViewToClickToExpand highlightView(boolean highlight) {
        this.viewToSelect = highlight;
        return this;
    }

    public ViewToClickToExpand setupCardElement(CardElementUI cardElementUIToClick) {
        this.cardElementUIToClick = cardElementUIToClick;
        return this;
    }

    public ViewToClickToExpand enableForExpandAction() {
        this.enableForCode = true;
        return this;
    }

    public ViewToClickToExpand useLongClick(boolean useLongClick) {
        this.useLongClick = useLongClick;
        return this;
    }

    public View getViewToClick() {
        return viewToClick;
    }

    public boolean isViewToSelect() {
        return viewToSelect;
    }

    public CardElementUI getCardElementUIToClick() {
        return cardElementUIToClick;
    }

    public boolean isEnableForCode() {
        return enableForCode;
    }

    public boolean isUseLongClick() {
        return useLongClick;
    }

    public enum CardElementUI {
        CARD(0),
        HEADER(1),
        THUMBNAIL(2),
        MAIN_CONTENT(3);
        int mElement;

        CardElementUI(int element) {
            mElement = element;
        }
    }
}

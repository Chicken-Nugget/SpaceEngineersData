package chickennugget.spaceengineersdata.cards.card_main;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import chickennugget.spaceengineersdata.cards.card_view.CardThumbnailView;

public class CardThumbnail extends BaseCard {

    protected int drawableResource;
    protected String urlResource;
    protected boolean mExternalUsage = false;
    protected int errorResourceId = 0;
    protected CustomSource customSource = null;
    protected boolean sendBroadcastAfterAttach = true;

    public CardThumbnail(Context context) {
        super(context);
    }

    public CardThumbnail(Context context, int innerLayout) {
        super(context);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View imageView) {
        return;
    }

    public boolean applyBitmap(View imageView, Bitmap bitmap) {
        return false;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }

    public String getUrlResource() {
        return urlResource;
    }

    public void setUrlResource(String urlResource) {
        this.urlResource = urlResource;
    }

    public CustomSource getCustomSource() {
        return customSource;
    }

    public void setCustomSource(CustomSource customSource) {
        this.customSource = customSource;
    }

    public boolean isExternalUsage() {
        return mExternalUsage;
    }

    public void setExternalUsage(boolean externalUsage) {
        this.mExternalUsage = externalUsage;
    }

    public int getErrorResourceId() {
        return errorResourceId;
    }

    public void setErrorResource(int errorResourceId) {
        this.errorResourceId = errorResourceId;
    }

    public boolean isSendBroadcastAfterAttach() {
        return sendBroadcastAfterAttach;
    }

    public void setSendBroadcastAfterAttach(boolean sendBroadcastAfterAttach) {
        this.sendBroadcastAfterAttach = sendBroadcastAfterAttach;
    }

    public CardThumbnailView getCardThumbnailView() {
        if (getParentCard() != null)
            return getParentCard().getCardView().getInternalThumbnailLayout();
        return null;
    }

    public interface CustomSource {
        String getTag();

        Bitmap getBitmap();
    }
}

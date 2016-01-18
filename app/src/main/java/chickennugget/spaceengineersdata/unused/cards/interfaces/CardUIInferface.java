package chickennugget.spaceengineersdata.unused.cards.interfaces;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface CardUIInferface {

    View getInnerView(Context context, ViewGroup parent);

    void setupInnerViewElements(ViewGroup parent, View view);
}

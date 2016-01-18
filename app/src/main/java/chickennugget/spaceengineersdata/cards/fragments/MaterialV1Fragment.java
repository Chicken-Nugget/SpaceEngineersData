package chickennugget.spaceengineersdata.cards.fragments;

import android.os.Bundle;

import chickennugget.spaceengineersdata.R;

public abstract class MaterialV1Fragment extends BaseMaterialFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.gray_background));
    }
}
package chickennugget.spaceengineersdata.cards;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public abstract class BaseFragment extends Fragment {

    public static final int TITLE_NONE = -1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setTitle();
    }

    protected void setTitle() {
        int titleResId = getTitleResourceId();
        if (titleResId != TITLE_NONE)
            getActivity().setTitle(getTitleResourceId());
    }

    public abstract int getTitleResourceId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this activity, the Up button is shown.
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

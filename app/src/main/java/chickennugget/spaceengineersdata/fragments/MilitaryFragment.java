package chickennugget.spaceengineersdata.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.activity.MainActivity;
import chickennugget.spaceengineersdata.material.app.BottomSheetDialog;
import chickennugget.spaceengineersdata.material.app.Dialog;
import chickennugget.spaceengineersdata.material.app.DialogFragment;
import chickennugget.spaceengineersdata.material.drawables.ThemeDrawable;
import chickennugget.spaceengineersdata.material.utils.ViewUtil;
import chickennugget.spaceengineersdata.material.widgets.Button;

public class MilitaryFragment extends Fragment implements View.OnClickListener {
    // Military (turrets, guns, launchers, warhead, decoy)
    private static final int[] button_id = {
            R.id.b_gatling_turret_l,
            R.id.b_gatling_turret_c,
            R.id.b_gatling_turret_s,
            R.id.b_missile_turret_l,
            R.id.b_missile_turret_c,
            R.id.b_missile_turret_s,
            R.id.b_interior_turret_l,
            R.id.b_interior_turret_c,
            R.id.b_interior_turret_s,
            R.id.b_gatling_gun_l,
            R.id.b_gatling_gun_c,
            R.id.b_gatling_gun_s,
            R.id.b_rocket_launcher_l,
            R.id.b_rocket_launcher_c,
            R.id.b_rocket_launcher_s,
            R.id.b_reloadable_rocket_launcher_l,
            R.id.b_reloadable_rocket_launcher_c,
            R.id.b_reloadable_rocket_launcher_s,
            R.id.b_warhead_l,
            R.id.b_warhead_c,
            R.id.b_warhead_s,
            R.id.b_decoy_l,
            R.id.b_decoy_c,
            R.id.b_decoy_s,
    };
    private static final int[] sheet_id = {
            R.layout.f_gatling_turret_l,
            R.layout.f_gatling_turret_c,
            R.layout.f_gatling_turret_s,
            R.layout.f_missile_turret_l,
            R.layout.f_missile_turret_c,
            R.layout.f_missile_turret_s,
            R.layout.f_interior_turret_l,
            R.layout.f_interior_turret_l,
            R.layout.f_interior_turret_l,
            R.layout.f_gatling_gun_s,
            R.layout.f_gatling_gun_s,
            R.layout.f_gatling_gun_s,
            R.layout.f_rocket_launcher_l,
            R.layout.f_rocket_launcher_c,
            R.layout.f_rocket_launcher_s,
            R.layout.f_reloadable_rocket_launcher_s,
            R.layout.f_reloadable_rocket_launcher_s,
            R.layout.f_reloadable_rocket_launcher_s,
            R.layout.f_warhead_l,
            R.layout.f_warhead_c,
            R.layout.f_warhead_s,
            R.layout.f_decoy_l,
            R.layout.f_decoy_c,
            R.layout.f_decoy_s,
    };
    private static final Button[] button = new Button[button_id.length];
    private MainActivity mActivity;
    private BottomSheetDialog mBottomSheetDialog;

    public static MilitaryFragment newInstance() {
        return new MilitaryFragment();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_military, container, false);

        for (int i = 0; i < button_id.length; i++) {
            button[i] = (Button) v.findViewById(button_id[i]);
            final int finalI = i;
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomSheet(sheet_id[finalI]);
                }
            });
        }

        mActivity = (MainActivity) getActivity();
        return v;
    }

    private void showBottomSheet(int resID) {
        mBottomSheetDialog = new BottomSheetDialog(mActivity, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(mActivity).inflate(resID, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window_bottom));
        mBottomSheetDialog.contentView(v).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismissImmediately();
            mBottomSheetDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        Dialog.Builder builder = null;
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }
}

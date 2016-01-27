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

public class ArmorFragment extends Fragment implements View.OnClickListener {
    // Light Armor, Heavy Armor
    private static final int[] button_id = {
            R.id.b_light_armor_corner_tip_smooth_l,
            R.id.b_light_armor_corner_tip_smooth_c,
            R.id.b_light_armor_corner_tip_smooth_s,
            R.id.b_heavy_armor_inv_corner_tip_l,
            R.id.b_heavy_armor_inv_corner_tip_c,
            R.id.b_heavy_armor_inv_corner_tip_s,
            R.id.b_heavy_armor_corner_round_l,
            R.id.b_heavy_armor_corner_round_c,
            R.id.b_heavy_armor_corner_round_s,
            R.id.b_light_armor_corner_round_l,
            R.id.b_light_armor_corner_round_c,
            R.id.b_light_armor_corner_round_s,
            R.id.b_heavy_armor_corner_angled_l,
            R.id.b_heavy_armor_corner_angled_c,
            R.id.b_heavy_armor_corner_angled_s,
            R.id.b_light_armor_slope_base_l,
            R.id.b_light_armor_slope_base_c,
            R.id.b_light_armor_slope_base_s,
            R.id.b_heavy_armor_slope_l,
            R.id.b_heavy_armor_slope_c,
            R.id.b_heavy_armor_slope_s,
            R.id.b_heavy_armor_slope_round_l,
            R.id.b_heavy_armor_slope_round_c,
            R.id.b_heavy_armor_slope_round_s,
            R.id.b_heavy_armor_slope_tip_smooth_l,
            R.id.b_heavy_armor_slope_tip_smooth_c,
            R.id.b_heavy_armor_slope_tip_smooth_s,
            R.id.b_light_armor_corner_angled_l,
            R.id.b_light_armor_corner_angled_c,
            R.id.b_light_armor_corner_angled_s,
            R.id.b_heavy_armor_inv_corner_round_l,
            R.id.b_heavy_armor_inv_corner_round_c,
            R.id.b_heavy_armor_inv_corner_round_s,
            R.id.b_heavy_armor_block_l,
            R.id.b_heavy_armor_block_c,
            R.id.b_heavy_armor_block_s,
            R.id.b_heavy_armor_inv_corner_tip_smooth_l,
            R.id.b_heavy_armor_inv_corner_tip_smooth_c,
            R.id.b_heavy_armor_inv_corner_tip_smooth_s,
            R.id.b_light_armor_corner_base_l,
            R.id.b_light_armor_corner_base_c,
            R.id.b_light_armor_corner_base_s,
            R.id.b_light_armor_inv_corner_tip_l,
            R.id.b_light_armor_inv_corner_tip_c,
            R.id.b_light_armor_inv_corner_tip_s,
            R.id.b_light_armor_slope_tip_smooth_l,
            R.id.b_light_armor_slope_tip_smooth_c,
            R.id.b_light_armor_slope_tip_smooth_s,
            R.id.b_light_armor_corner_tip_l,
            R.id.b_light_armor_corner_tip_c,
            R.id.b_light_armor_corner_tip_s,
            R.id.b_heavy_armor_inv_corner_l,
            R.id.b_heavy_armor_inv_corner_c,
            R.id.b_heavy_armor_inv_corner_s,
            R.id.b_light_armor_block_l,
            R.id.b_light_armor_block_c,
            R.id.b_light_armor_block_s,
            R.id.b_heavy_armor_corner_base_smooth_l,
            R.id.b_heavy_armor_corner_base_smooth_c,
            R.id.b_heavy_armor_corner_base_smooth_s,
            R.id.b_light_armor_slope_rounded_l,
            R.id.b_light_armor_slope_rounded_c,
            R.id.b_light_armor_slope_rounded_s,
            R.id.b_light_armor_slope_l,
            R.id.b_light_armor_slope_c,
            R.id.b_light_armor_slope_s,
            R.id.b_heavy_armor_inv_corner_base_smooth_l,
            R.id.b_heavy_armor_inv_corner_base_smooth_c,
            R.id.b_heavy_armor_inv_corner_base_smooth_s,
            R.id.b_light_armor_inv_corner_base_smooth_l,
            R.id.b_light_armor_inv_corner_base_smooth_c,
            R.id.b_light_armor_inv_corner_base_smooth_s,
            R.id.b_heavy_armor_slope_base_smooth_l,
            R.id.b_heavy_armor_slope_base_smooth_c,
            R.id.b_heavy_armor_slope_base_smooth_s,
            R.id.b_heavy_armor_corner_rounded_l,
            R.id.b_heavy_armor_corner_rounded_c,
            R.id.b_heavy_armor_corner_rounded_s,
            R.id.b_heavy_armor_corner_base_l,
            R.id.b_heavy_armor_corner_base_c,
            R.id.b_heavy_armor_corner_base_s,
            R.id.b_light_armor_inv_corner_tip_smooth_l,
            R.id.b_light_armor_inv_corner_tip_smooth_c,
            R.id.b_light_armor_inv_corner_tip_smooth_s,
            R.id.b_light_armor_inv_corner_l,
            R.id.b_light_armor_inv_corner_c,
            R.id.b_light_armor_inv_corner_s,
            R.id.b_heavy_armor_slope_angled_l,
            R.id.b_heavy_armor_slope_angled_c,
            R.id.b_heavy_armor_slope_angled_s,
            R.id.b_heavy_armor_corner_tip_smooth_l,
            R.id.b_heavy_armor_corner_tip_smooth_c,
            R.id.b_heavy_armor_corner_tip_smooth_s,
            R.id.b_light_armor_inv_corner_base_l,
            R.id.b_light_armor_inv_corner_base_c,
            R.id.b_light_armor_inv_corner_base_s,
            R.id.b_light_armor_corner_rounded_l,
            R.id.b_light_armor_corner_rounded_c,
            R.id.b_light_armor_corner_rounded_s,
            R.id.b_light_armor_corner_base_smooth_l,
            R.id.b_light_armor_corner_base_smooth_c,
            R.id.b_light_armor_corner_base_smooth_s,
            R.id.b_light_armor_slope_tip_l,
            R.id.b_light_armor_slope_tip_c,
            R.id.b_light_armor_slope_tip_s,
            R.id.b_heavy_armor_corner_tip_l,
            R.id.b_heavy_armor_corner_tip_c,
            R.id.b_heavy_armor_corner_tip_s,
            R.id.b_light_armor_slope_angled_l,
            R.id.b_light_armor_slope_angled_c,
            R.id.b_light_armor_slope_angled_s,
            R.id.b_light_armor_corner_l,
            R.id.b_light_armor_corner_c,
            R.id.b_light_armor_corner_s,
            R.id.b_heavy_armor_slope_base_l,
            R.id.b_heavy_armor_slope_base_c,
            R.id.b_heavy_armor_slope_base_s,
            R.id.b_light_armor_slope_base_smooth_l,
            R.id.b_light_armor_slope_base_smooth_c,
            R.id.b_light_armor_slope_base_smooth_s,
            R.id.b_light_armor_inv_corner_round_l,
            R.id.b_light_armor_inv_corner_round_c,
            R.id.b_light_armor_inv_corner_round_s,
            R.id.b_heavy_armor_inv_corner_base_l,
            R.id.b_heavy_armor_inv_corner_base_c,
            R.id.b_heavy_armor_inv_corner_base_s,
            R.id.b_light_armor_slope_round_l,
            R.id.b_light_armor_slope_round_c,
            R.id.b_light_armor_slope_round_s,
            R.id.b_heavy_armor_corner_l,
            R.id.b_heavy_armor_corner_c,
            R.id.b_heavy_armor_corner_s,
            R.id.b_heavy_armor_slope_tip_l,
            R.id.b_heavy_armor_slope_tip_c,
            R.id.b_heavy_armor_slope_tip_s,
            R.id.b_heavy_armor_slope_rounded_l,
            R.id.b_heavy_armor_slope_rounded_c,
            R.id.b_heavy_armor_slope_rounded_s,
    };
    private static final int[] sheet_id = {
            R.layout.f_light_armor_corner_tip_smooth_l,
            R.layout.f_light_armor_corner_tip_smooth_c,
            R.layout.f_light_armor_corner_tip_smooth_s,
            R.layout.f_heavy_armor_inv_corner_tip_l,
            R.layout.f_heavy_armor_inv_corner_tip_c,
            R.layout.f_heavy_armor_inv_corner_tip_s,
            R.layout.f_heavy_armor_corner_round_l,
            R.layout.f_heavy_armor_corner_round_c,
            R.layout.f_heavy_armor_corner_round_s,
            R.layout.f_light_armor_corner_round_s,
            R.layout.f_light_armor_corner_round_s,
            R.layout.f_light_armor_corner_round_s,
            R.layout.f_heavy_armor_corner_angled_l,
            R.layout.f_heavy_armor_corner_angled_c,
            R.layout.f_heavy_armor_corner_angled_s,
            R.layout.f_light_armor_slope_base_l,
            R.layout.f_light_armor_slope_base_c,
            R.layout.f_light_armor_slope_base_s,
            R.layout.f_heavy_armor_slope_l,
            R.layout.f_heavy_armor_slope_c,
            R.layout.f_heavy_armor_slope_s,
            R.layout.f_heavy_armor_slope_round_l,
            R.layout.f_heavy_armor_slope_round_c,
            R.layout.f_heavy_armor_slope_round_s,
            R.layout.f_heavy_armor_slope_tip_smooth_l,
            R.layout.f_heavy_armor_slope_tip_smooth_c,
            R.layout.f_heavy_armor_slope_tip_smooth_s,
            R.layout.f_light_armor_corner_angled_l,
            R.layout.f_light_armor_corner_angled_c,
            R.layout.f_light_armor_corner_angled_s,
            R.layout.f_heavy_armor_inv_corner_round_l,
            R.layout.f_heavy_armor_inv_corner_round_c,
            R.layout.f_heavy_armor_inv_corner_round_s,
            R.layout.f_heavy_armor_block_l,
            R.layout.f_heavy_armor_block_c,
            R.layout.f_heavy_armor_block_s,
            R.layout.f_heavy_armor_inv_corner_tip_smooth_l,
            R.layout.f_heavy_armor_inv_corner_tip_smooth_c,
            R.layout.f_heavy_armor_inv_corner_tip_smooth_s,
            R.layout.f_light_armor_corner_base_l,
            R.layout.f_light_armor_corner_base_c,
            R.layout.f_light_armor_corner_base_s,
            R.layout.f_light_armor_inv_corner_tip_l,
            R.layout.f_light_armor_inv_corner_tip_c,
            R.layout.f_light_armor_inv_corner_tip_s,
            R.layout.f_light_armor_slope_tip_smooth_l,
            R.layout.f_light_armor_slope_tip_smooth_c,
            R.layout.f_light_armor_slope_tip_smooth_s,
            R.layout.f_light_armor_corner_tip_l,
            R.layout.f_light_armor_corner_tip_c,
            R.layout.f_light_armor_corner_tip_s,
            R.layout.f_heavy_armor_inv_corner_l,
            R.layout.f_heavy_armor_inv_corner_c,
            R.layout.f_heavy_armor_inv_corner_s,
            R.layout.f_light_armor_block_l,
            R.layout.f_light_armor_block_c,
            R.layout.f_light_armor_block_s,
            R.layout.f_heavy_armor_corner_base_smooth_l,
            R.layout.f_heavy_armor_corner_base_smooth_c,
            R.layout.f_heavy_armor_corner_base_smooth_s,
            R.layout.f_light_armor_slope_rounded_l,
            R.layout.f_light_armor_slope_rounded_c,
            R.layout.f_light_armor_slope_rounded_s,
            R.layout.f_light_armor_slope_l,
            R.layout.f_light_armor_slope_c,
            R.layout.f_light_armor_slope_s,
            R.layout.f_heavy_armor_inv_corner_base_smooth_l,
            R.layout.f_heavy_armor_inv_corner_base_smooth_c,
            R.layout.f_heavy_armor_inv_corner_base_smooth_s,
            R.layout.f_light_armor_inv_corner_base_smooth_l,
            R.layout.f_light_armor_inv_corner_base_smooth_c,
            R.layout.f_light_armor_inv_corner_base_smooth_s,
            R.layout.f_heavy_armor_slope_base_smooth_l,
            R.layout.f_heavy_armor_slope_base_smooth_c,
            R.layout.f_heavy_armor_slope_base_smooth_s,
            R.layout.f_heavy_armor_corner_rounded_l,
            R.layout.f_heavy_armor_corner_rounded_c,
            R.layout.f_heavy_armor_corner_rounded_s,
            R.layout.f_heavy_armor_corner_base_l,
            R.layout.f_heavy_armor_corner_base_c,
            R.layout.f_heavy_armor_corner_base_s,
            R.layout.f_light_armor_inv_corner_tip_smooth_l,
            R.layout.f_light_armor_inv_corner_tip_smooth_c,
            R.layout.f_light_armor_inv_corner_tip_smooth_s,
            R.layout.f_light_armor_inv_corner_l,
            R.layout.f_light_armor_inv_corner_c,
            R.layout.f_light_armor_inv_corner_s,
            R.layout.f_heavy_armor_slope_angled_l,
            R.layout.f_heavy_armor_slope_angled_c,
            R.layout.f_heavy_armor_slope_angled_s,
            R.layout.f_heavy_armor_corner_tip_smooth_l,
            R.layout.f_heavy_armor_corner_tip_smooth_c,
            R.layout.f_heavy_armor_corner_tip_smooth_s,
            R.layout.f_light_armor_inv_corner_base_l,
            R.layout.f_light_armor_inv_corner_base_c,
            R.layout.f_light_armor_inv_corner_base_s,
            R.layout.f_light_armor_corner_rounded_l,
            R.layout.f_light_armor_corner_rounded_c,
            R.layout.f_light_armor_corner_rounded_s,
            R.layout.f_light_armor_corner_base_smooth_l,
            R.layout.f_light_armor_corner_base_smooth_c,
            R.layout.f_light_armor_corner_base_smooth_s,
            R.layout.f_light_armor_slope_tip_l,
            R.layout.f_light_armor_slope_tip_c,
            R.layout.f_light_armor_slope_tip_s,
            R.layout.f_heavy_armor_corner_tip_l,
            R.layout.f_heavy_armor_corner_tip_c,
            R.layout.f_heavy_armor_corner_tip_s,
            R.layout.f_light_armor_slope_angled_l,
            R.layout.f_light_armor_slope_angled_c,
            R.layout.f_light_armor_slope_angled_s,
            R.layout.f_light_armor_corner_l,
            R.layout.f_light_armor_corner_c,
            R.layout.f_light_armor_corner_s,
            R.layout.f_heavy_armor_slope_base_l,
            R.layout.f_heavy_armor_slope_base_c,
            R.layout.f_heavy_armor_slope_base_s,
            R.layout.f_light_armor_slope_base_smooth_l,
            R.layout.f_light_armor_slope_base_smooth_c,
            R.layout.f_light_armor_slope_base_smooth_s,
            R.layout.f_light_armor_inv_corner_round_s,
            R.layout.f_light_armor_inv_corner_round_s,
            R.layout.f_light_armor_inv_corner_round_s,
            R.layout.f_heavy_armor_inv_corner_base_l,
            R.layout.f_heavy_armor_inv_corner_base_c,
            R.layout.f_heavy_armor_inv_corner_base_s,
            R.layout.f_light_armor_slope_round_s,
            R.layout.f_light_armor_slope_round_s,
            R.layout.f_light_armor_slope_round_s,
            R.layout.f_heavy_armor_corner_l,
            R.layout.f_heavy_armor_corner_c,
            R.layout.f_heavy_armor_corner_s,
            R.layout.f_heavy_armor_slope_tip_l,
            R.layout.f_heavy_armor_slope_tip_c,
            R.layout.f_heavy_armor_slope_tip_s,
            R.layout.f_heavy_armor_slope_rounded_l,
            R.layout.f_heavy_armor_slope_rounded_c,
            R.layout.f_heavy_armor_slope_rounded_s,
    };
    private static final Button[] button = new Button[button_id.length];
    private MainActivity mActivity;
    private BottomSheetDialog mBottomSheetDialog;

    public static ArmorFragment newInstance() {
        return new ArmorFragment();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_armor, container, false);

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
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));
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

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

public class StructureFragment extends Fragment implements View.OnClickListener {
    // Windows, Blast Doors, Wheels
    // [Hangar Door], [Merge Block]
    private static final int[] button_id = {
            R.id.b_merge_block_l,
            R.id.b_merge_block_c,
            R.id.b_merge_block_s,
            R.id.b_hangar_door_l,
            R.id.b_hangar_door_c,
            R.id.b_hangar_door_s,
            R.id.b_blast_door_l,
            R.id.b_blast_door_c,
            R.id.b_blast_door_s,
            R.id.b_blast_door_edge_l,
            R.id.b_blast_door_edge_c,
            R.id.b_blast_door_edge_s,
            R.id.b_blast_door_corner_l,
            R.id.b_blast_door_corner_c,
            R.id.b_blast_door_corner_s,
            R.id.b_blast_door_inv_corner_l,
            R.id.b_blast_door_inv_corner_c,
            R.id.b_blast_door_inv_corner_s,
            R.id.b_window_flat_1x1_l,
            R.id.b_window_flat_1x1_c,
            R.id.b_window_flat_1x1_s,
            R.id.b_window_flat_inv_1x1_l,
            R.id.b_window_flat_inv_1x1_c,
            R.id.b_window_flat_inv_1x1_s,
            R.id.b_window_flat_1x2_l,
            R.id.b_window_flat_1x2_c,
            R.id.b_window_flat_1x2_s,
            R.id.b_window_flat_inv_1x2_l,
            R.id.b_window_flat_inv_1x2_c,
            R.id.b_window_flat_inv_1x2_s,
            R.id.b_window_flat_2x3_l,
            R.id.b_window_flat_2x3_c,
            R.id.b_window_flat_2x3_s,
            R.id.b_window_flat_inv_2x3_l,
            R.id.b_window_flat_inv_2x3_c,
            R.id.b_window_flat_inv_2x3_s,
            R.id.b_window_flat_3x3_l,
            R.id.b_window_flat_3x3_c,
            R.id.b_window_flat_3x3_s,
            R.id.b_window_flat_inv_3x3_l,
            R.id.b_window_flat_inv_3x3_c,
            R.id.b_window_flat_inv_3x3_s,
            R.id.b_window_face_1x1_l,
            R.id.b_window_face_1x1_c,
            R.id.b_window_face_1x1_s,
            R.id.b_window_inv_1x1_l,
            R.id.b_window_inv_1x1_c,
            R.id.b_window_inv_1x1_s,
            R.id.b_window_face_1x2_l,
            R.id.b_window_face_1x2_c,
            R.id.b_window_face_1x2_s,
            R.id.b_window_inv_1x2_l,
            R.id.b_window_inv_1x2_c,
            R.id.b_window_inv_1x2_s,
            R.id.b_window_slope_1x1_l,
            R.id.b_window_slope_1x1_c,
            R.id.b_window_slope_1x1_s,
            R.id.b_window_slope_1x2_l,
            R.id.b_window_slope_1x2_c,
            R.id.b_window_slope_1x2_s,
            R.id.b_window_side_1x1_l,
            R.id.b_window_side_1x1_c,
            R.id.b_window_side_1x1_s,
            R.id.b_window_side_left_1x2_l,
            R.id.b_window_side_left_1x2_c,
            R.id.b_window_side_left_1x2_s,
            R.id.b_window_side_right_1x2_l,
            R.id.b_window_side_right_1x2_c,
            R.id.b_window_side_right_1x2_s,
            R.id.b_wheel_1x1_l,
            R.id.b_wheel_1x1_c,
            R.id.b_wheel_1x1_s,
            R.id.b_wheel_3x3_l,
            R.id.b_wheel_3x3_c,
            R.id.b_wheel_3x3_s,
            R.id.b_wheel_5x5_l,
            R.id.b_wheel_5x5_c,
            R.id.b_wheel_5x5_s,
            R.id.b_suspension_1x1_l,
            R.id.b_suspension_1x1_c,
            R.id.b_suspension_1x1_s,
            R.id.b_suspension_3x3_l,
            R.id.b_suspension_3x3_c,
            R.id.b_suspension_3x3_s,
            R.id.b_suspension_5x5_l,
            R.id.b_suspension_5x5_c,
            R.id.b_suspension_5x5_s,
            R.id.b_suspension_wheel_1x1_l,
            R.id.b_suspension_wheel_1x1_c,
            R.id.b_suspension_wheel_1x1_s,
            R.id.b_suspension_wheel_3x3_l,
            R.id.b_suspension_wheel_3x3_c,
            R.id.b_suspension_wheel_3x3_s,
            R.id.b_suspension_wheel_5x5_l,
            R.id.b_suspension_wheel_5x5_c,
            R.id.b_suspension_wheel_5x5_s,
    };
    private static final int[] sheet_id = {
            R.layout.f_merge_block_l,
            R.layout.f_merge_block_c,
            R.layout.f_merge_block_s,
            R.layout.f_hangar_door_l,
            R.layout.f_hangar_door_l,
            R.layout.f_hangar_door_l,
            R.layout.f_blast_door_l,
            R.layout.f_blast_door_l,
            R.layout.f_blast_door_l,
            R.layout.f_blast_door_edge_l,
            R.layout.f_blast_door_edge_l,
            R.layout.f_blast_door_edge_l,
            R.layout.f_blast_door_corner_l,
            R.layout.f_blast_door_corner_l,
            R.layout.f_blast_door_corner_l,
            R.layout.f_blast_door_inv_corner_l,
            R.layout.f_blast_door_inv_corner_l,
            R.layout.f_blast_door_inv_corner_l,
            R.layout.f_window_flat_1x1_l,
            R.layout.f_window_flat_1x1_l,
            R.layout.f_window_flat_1x1_l,
            R.layout.f_window_flat_inv_1x1_l,
            R.layout.f_window_flat_inv_1x1_l,
            R.layout.f_window_flat_inv_1x1_l,
            R.layout.f_window_flat_1x2_l,
            R.layout.f_window_flat_1x2_l,
            R.layout.f_window_flat_1x2_l,
            R.layout.f_window_flat_inv_1x2_l,
            R.layout.f_window_flat_inv_1x2_l,
            R.layout.f_window_flat_inv_1x2_l,
            R.layout.f_window_flat_2x3_l,
            R.layout.f_window_flat_2x3_l,
            R.layout.f_window_flat_2x3_l,
            R.layout.f_window_flat_inv_2x3_l,
            R.layout.f_window_flat_inv_2x3_l,
            R.layout.f_window_flat_inv_2x3_l,
            R.layout.f_window_flat_3x3_l,
            R.layout.f_window_flat_3x3_l,
            R.layout.f_window_flat_3x3_l,
            R.layout.f_window_flat_inv_3x3_l,
            R.layout.f_window_flat_inv_3x3_l,
            R.layout.f_window_flat_inv_3x3_l,
            R.layout.f_window_face_1x1_l,
            R.layout.f_window_face_1x1_l,
            R.layout.f_window_face_1x1_l,
            R.layout.f_window_inv_1x1_l,
            R.layout.f_window_inv_1x1_l,
            R.layout.f_window_inv_1x1_l,
            R.layout.f_window_face_1x2_l,
            R.layout.f_window_face_1x2_l,
            R.layout.f_window_face_1x2_l,
            R.layout.f_window_inv_1x2_l,
            R.layout.f_window_inv_1x2_l,
            R.layout.f_window_inv_1x2_l,
            R.layout.f_window_slope_1x1_l,
            R.layout.f_window_slope_1x1_l,
            R.layout.f_window_slope_1x1_l,
            R.layout.f_window_slope_1x2_l,
            R.layout.f_window_slope_1x2_l,
            R.layout.f_window_slope_1x2_l,
            R.layout.f_window_side_1x1_l,
            R.layout.f_window_side_1x1_l,
            R.layout.f_window_side_1x1_l,
            R.layout.f_window_side_left_1x2_l,
            R.layout.f_window_side_left_1x2_l,
            R.layout.f_window_side_left_1x2_l,
            R.layout.f_window_side_right_1x2_l,
            R.layout.f_window_side_right_1x2_l,
            R.layout.f_window_side_right_1x2_l,
            R.layout.f_wheel_1x1_l,
            R.layout.f_wheel_1x1_c,
            R.layout.f_wheel_1x1_s,
            R.layout.f_wheel_3x3_l,
            R.layout.f_wheel_3x3_c,
            R.layout.f_wheel_3x3_s,
            R.layout.f_wheel_5x5_l,
            R.layout.f_wheel_5x5_c,
            R.layout.f_wheel_5x5_s,
            R.layout.f_suspension_1x1_l,
            R.layout.f_suspension_1x1_c,
            R.layout.f_suspension_1x1_s,
            R.layout.f_suspension_3x3_l,
            R.layout.f_suspension_3x3_c,
            R.layout.f_suspension_3x3_s,
            R.layout.f_suspension_5x5_l,
            R.layout.f_suspension_5x5_c,
            R.layout.f_suspension_5x5_s,
            R.layout.f_suspension_wheel_1x1_l,
            R.layout.f_suspension_wheel_1x1_c,
            R.layout.f_suspension_wheel_1x1_s,
            R.layout.f_suspension_wheel_3x3_l,
            R.layout.f_suspension_wheel_3x3_c,
            R.layout.f_suspension_wheel_3x3_s,
            R.layout.f_suspension_wheel_5x5_l,
            R.layout.f_suspension_wheel_5x5_c,
            R.layout.f_suspension_wheel_5x5_s,
    };
    private static final Button[] button = new Button[button_id.length];
    private MainActivity mActivity;
    private BottomSheetDialog mBottomSheetDialog;

    public static StructureFragment newInstance() {
        return new StructureFragment();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_structure, container, false);

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

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

public class InteriorFragment extends Fragment implements View.OnClickListener {
    // Interior (Wall, Light, Pillar, Doors, Passage, Stairs, Ramp)
    // Exterior (-Spotlight, Catwalks, Cover Walls, Vert/Diag windows)
    private static final int[] button_id = {
            R.id.b_interior_wall_l,
            R.id.b_interior_wall_c,
            R.id.b_interior_wall_s,
            R.id.b_interior_pillar_l,
            R.id.b_interior_pillar_c,
            R.id.b_interior_pillar_s,
            R.id.b_interior_light_l,
            R.id.b_interior_light_c,
            R.id.b_interior_light_s,
            R.id.b_door_l,
            R.id.b_door_c,
            R.id.b_door_s,
            R.id.b_sliding_door_l,
            R.id.b_sliding_door_c,
            R.id.b_sliding_door_s,
            R.id.b_passage_l,
            R.id.b_passage_c,
            R.id.b_passage_s,
            R.id.b_stairs_l,
            R.id.b_stairs_c,
            R.id.b_stairs_s,
            R.id.b_ramp_l,
            R.id.b_ramp_c,
            R.id.b_ramp_s,
            R.id.b_catwalk_l,
            R.id.b_catwalk_c,
            R.id.b_catwalk_s,
            R.id.b_catwalk_plate_l,
            R.id.b_catwalk_plate_c,
            R.id.b_catwalk_plate_s,
            R.id.b_catwalk_corner_l,
            R.id.b_catwalk_corner_c,
            R.id.b_catwalk_corner_s,
            R.id.b_catwalk_2_sides_l,
            R.id.b_catwalk_2_sides_c,
            R.id.b_catwalk_2_sides_s,
            R.id.b_cover_wall_full_l,
            R.id.b_cover_wall_full_c,
            R.id.b_cover_wall_full_s,
            R.id.b_cover_wall_half_l,
            R.id.b_cover_wall_half_c,
            R.id.b_cover_wall_half_s,
            R.id.b_vertical_window_l,
            R.id.b_vertical_window_c,
            R.id.b_vertical_window_s,
            R.id.b_diagonal_window_l,
            R.id.b_diagonal_window_c,
            R.id.b_diagonal_window_s,
    };
    private static final int[] sheet_id = {
            R.layout.f_interior_wall_l,
            R.layout.f_interior_wall_l,
            R.layout.f_interior_wall_l,
            R.layout.f_interior_pillar_l,
            R.layout.f_interior_pillar_l,
            R.layout.f_interior_pillar_l,
            R.layout.f_interior_light_l,
            R.layout.f_interior_light_l,
            R.layout.f_interior_light_l,
            R.layout.f_door_l,
            R.layout.f_door_l,
            R.layout.f_door_l,
            R.layout.f_sliding_door_l,
            R.layout.f_sliding_door_l,
            R.layout.f_sliding_door_l,
            R.layout.f_passage_l,
            R.layout.f_passage_l,
            R.layout.f_passage_l,
            R.layout.f_stairs_l,
            R.layout.f_stairs_l,
            R.layout.f_stairs_l,
            R.layout.f_ramp_l,
            R.layout.f_ramp_l,
            R.layout.f_ramp_l,
            R.layout.f_catwalk_l,
            R.layout.f_catwalk_l,
            R.layout.f_catwalk_l,
            R.layout.f_catwalk_plate_l,
            R.layout.f_catwalk_plate_l,
            R.layout.f_catwalk_plate_l,
            R.layout.f_catwalk_corner_l,
            R.layout.f_catwalk_corner_l,
            R.layout.f_catwalk_corner_l,
            R.layout.f_catwalk_2_sides_l,
            R.layout.f_catwalk_2_sides_l,
            R.layout.f_catwalk_2_sides_l,
            R.layout.f_cover_wall_full_l,
            R.layout.f_cover_wall_full_l,
            R.layout.f_cover_wall_full_l,
            R.layout.f_cover_wall_half_l,
            R.layout.f_cover_wall_half_l,
            R.layout.f_cover_wall_half_l,
            R.layout.f_vertical_window_l,
            R.layout.f_vertical_window_l,
            R.layout.f_vertical_window_l,
            R.layout.f_diagonal_window_l,
            R.layout.f_diagonal_window_l,
            R.layout.f_diagonal_window_l,
    };
    private static final Button[] button = new Button[button_id.length];
    private MainActivity mActivity;
    private BottomSheetDialog mBottomSheetDialog;

    public static InteriorFragment newInstance() {
        return new InteriorFragment();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_interior, container, false);

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

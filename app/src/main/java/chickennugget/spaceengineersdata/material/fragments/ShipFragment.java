package chickennugget.spaceengineersdata.material.fragments;

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

public class ShipFragment extends Fragment implements View.OnClickListener {
    //Ship (Ship, Control, Thrust, [Passenger Seat])
    private static final int[] button_id = {
            R.id.b_gyroscope,
            R.id.b_landing_gear,
            R.id.b_artificial_mass,
            R.id.b_jump_drive,
            R.id.b_cockpit_screen,
            R.id.b_cockpit_enclosed,
            R.id.b_cockpit_small_screen,
            R.id.b_cockpit_fighter,
            R.id.b_passenger_seat,
            R.id.b_control_panel,
            R.id.b_small_thruster,
            R.id.b_large_thruster,
            R.id.b_small_hydrogen_thruster,
            R.id.b_large_hydrogen_thruster,
            R.id.b_small_atmospheric_thruster,
            R.id.b_large_atmospheric_thruster,
    };
    private static final int[] sheet_id = {
            R.layout.f_gyroscope,
            R.layout.f_landing_gear,
            R.layout.f_artificial_mass,
            R.layout.f_jump_drive,
            R.layout.f_cockpit_screen,
            R.layout.f_cockpit_enclosed,
            R.layout.f_cockpit_small_screen,
            R.layout.f_cockpit_fighter,
            R.layout.f_passenger_seat,
            R.layout.f_control_panel,
            R.layout.f_small_thruster,
            R.layout.f_large_thruster,
            R.layout.f_small_hydrogen_thruster,
            R.layout.f_large_hydrogen_thruster,
            R.layout.f_small_atmospheric_thruster,
            R.layout.f_large_atmospheric_thruster,
    };
    private static final Button[] button = new Button[button_id.length];
    private MainActivity mActivity;
    private BottomSheetDialog mBottomSheetDialog;

    public static ShipFragment newInstance() {
        return new ShipFragment();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship, container, false);

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

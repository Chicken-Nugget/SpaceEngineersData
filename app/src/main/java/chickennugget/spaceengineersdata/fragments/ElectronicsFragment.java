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

public class ElectronicsFragment extends Fragment implements View.OnClickListener {
    // Power (reactors, batteries, solar)
    // Comms (antenna, beacon, laser antenna)
    // Displays (txt panel, lcd panel,
    // Gadgets (sensor, sound, camera, button panel, timer, remote control, programming),
    // [Medical Room], [Gravity Gen], [Space Ball], [Cryo Chamber], [Control Panel], [Spotlight]
    private static final int[] button_id = {
            R.id.b_small_reactor_l,
            R.id.b_small_reactor_c,
            R.id.b_small_reactor_s,
            R.id.b_large_reactor_l,
            R.id.b_large_reactor_c,
            R.id.b_large_reactor_s,
            R.id.b_battery_l,
            R.id.b_battery_c,
            R.id.b_battery_s,
            R.id.b_solar_panel_l,
            R.id.b_solar_panel_c,
            R.id.b_solar_panel_s,
            R.id.b_antenna_l,
            R.id.b_antenna_c,
            R.id.b_antenna_s,
            R.id.b_laser_antenna_l,
            R.id.b_laser_antenna_c,
            R.id.b_laser_antenna_s,
            R.id.b_beacon_l,
            R.id.b_beacon_c,
            R.id.b_beacon_s,
            R.id.b_lcd_panel_l,
            R.id.b_lcd_panel_c,
            R.id.b_lcd_panel_s,
            R.id.b_lcd_panel_wide_l,
            R.id.b_lcd_panel_wide_c,
            R.id.b_lcd_panel_wide_s,
            R.id.b_text_panel_l,
            R.id.b_text_panel_c,
            R.id.b_text_panel_s,
            R.id.b_sensor_l,
            R.id.b_sensor_c,
            R.id.b_sensor_s,
            R.id.b_sound_block_l,
            R.id.b_sound_block_c,
            R.id.b_sound_block_s,
            R.id.b_camera_l,
            R.id.b_camera_c,
            R.id.b_camera_s,
            R.id.b_spotlight_l,
            R.id.b_spotlight_c,
            R.id.b_spotlight_s,
            R.id.b_button_panel_l,
            R.id.b_button_panel_c,
            R.id.b_button_panel_s,
            R.id.b_timer_block_l,
            R.id.b_timer_block_c,
            R.id.b_timer_block_s,
            R.id.b_remote_control_l,
            R.id.b_remote_control_c,
            R.id.b_remote_control_s,
            R.id.b_programmable_block_l,
            R.id.b_programmable_block_c,
            R.id.b_programmable_block_s,
            R.id.b_control_panel_l,
            R.id.b_control_panel_c,
            R.id.b_control_panel_s,
            R.id.b_gravity_generator_l,
            R.id.b_gravity_generator_c,
            R.id.b_gravity_generator_s,
            R.id.b_gravity_generator_sphere_l,
            R.id.b_gravity_generator_sphere_c,
            R.id.b_gravity_generator_sphere_s,
            R.id.b_space_ball_l,
            R.id.b_space_ball_c,
            R.id.b_space_ball_s,
            R.id.b_medical_room_l,
            R.id.b_medical_room_c,
            R.id.b_medical_room_s,
            R.id.b_cryo_chamber_l,
            R.id.b_cryo_chamber_c,
            R.id.b_cryo_chamber_s,
    };
    private static final int[] sheet_id = {
            R.layout.f_small_reactor_l,
            R.layout.f_small_reactor_c,
            R.layout.f_small_reactor_s,
            R.layout.f_large_reactor_l,
            R.layout.f_large_reactor_c,
            R.layout.f_large_reactor_s,
            R.layout.f_battery_l,
            R.layout.f_battery_c,
            R.layout.f_battery_s,
            R.layout.f_solar_panel_l,
            R.layout.f_solar_panel_c,
            R.layout.f_solar_panel_s,
            R.layout.f_antenna_l,
            R.layout.f_antenna_c,
            R.layout.f_antenna_s,
            R.layout.f_laser_antenna_l,
            R.layout.f_laser_antenna_c,
            R.layout.f_laser_antenna_s,
            R.layout.f_beacon_l,
            R.layout.f_beacon_c,
            R.layout.f_beacon_s,
            R.layout.f_lcd_panel_l,
            R.layout.f_lcd_panel_c,
            R.layout.f_lcd_panel_s,
            R.layout.f_lcd_panel_wide_l,
            R.layout.f_lcd_panel_wide_c,
            R.layout.f_lcd_panel_wide_s,
            R.layout.f_text_panel_l,
            R.layout.f_text_panel_c,
            R.layout.f_text_panel_s,
            R.layout.f_sensor_l,
            R.layout.f_sensor_c,
            R.layout.f_sensor_s,
            R.layout.f_sound_block_l,
            R.layout.f_sound_block_c,
            R.layout.f_sound_block_s,
            R.layout.f_camera_l,
            R.layout.f_camera_c,
            R.layout.f_camera_s,
            R.layout.f_spotlight_l,
            R.layout.f_spotlight_c,
            R.layout.f_spotlight_s,
            R.layout.f_button_panel_l,
            R.layout.f_button_panel_c,
            R.layout.f_button_panel_s,
            R.layout.f_timer_block_l,
            R.layout.f_timer_block_c,
            R.layout.f_timer_block_s,
            R.layout.f_remote_control_l,
            R.layout.f_remote_control_c,
            R.layout.f_remote_control_s,
            R.layout.f_programmable_block_l,
            R.layout.f_programmable_block_c,
            R.layout.f_programmable_block_s,
            R.layout.f_control_panel_l,
            R.layout.f_control_panel_c,
            R.layout.f_control_panel_s,
            R.layout.f_gravity_generator_l,
            R.layout.f_gravity_generator_l,
            R.layout.f_gravity_generator_l,
            R.layout.f_gravity_generator_sphere_l,
            R.layout.f_gravity_generator_sphere_l,
            R.layout.f_gravity_generator_sphere_l,
            R.layout.f_space_ball_l,
            R.layout.f_space_ball_c,
            R.layout.f_space_ball_s,
            R.layout.f_medical_room_l,
            R.layout.f_medical_room_l,
            R.layout.f_medical_room_l,
            R.layout.f_cryo_chamber_l,
            R.layout.f_cryo_chamber_l,
            R.layout.f_cryo_chamber_l,
    };
    private static final Button[] button = new Button[button_id.length];
    private MainActivity mActivity;
    private BottomSheetDialog mBottomSheetDialog;

    public static ElectronicsFragment newInstance() {
        return new ElectronicsFragment();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_electronics, container, false);

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

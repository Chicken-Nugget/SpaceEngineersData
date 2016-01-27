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

public class IndustryFragment extends Fragment implements View.OnClickListener {
    // Industry (refinery, arc furnace, assembler, drill, grinder, welder, ...
    // ... modules, ore detector, projector),
    // Storage (cargo containers)
    // O2/H2 (vent, tanks, farm, generator),
    // Conveyor (block, tubes, collector, connector, ejector, frame, sorter)
    private static final int[] button_id = {
            R.id.b_refinery_l,
            R.id.b_refinery_c,
            R.id.b_refinery_s,
            R.id.b_arc_furnace_l,
            R.id.b_arc_furnace_c,
            R.id.b_arc_furnace_s,
            R.id.b_assembler_l,
            R.id.b_assembler_c,
            R.id.b_assembler_s,
            R.id.b_energy_module_l,
            R.id.b_energy_module_c,
            R.id.b_energy_module_s,
            R.id.b_productivity_module_l,
            R.id.b_productivity_module_c,
            R.id.b_productivity_module_s,
            R.id.b_effectiveness_module_l,
            R.id.b_effectiveness_module_c,
            R.id.b_effectiveness_module_s,
            R.id.b_drill_l,
            R.id.b_drill_c,
            R.id.b_drill_s,
            R.id.b_welder_l,
            R.id.b_welder_c,
            R.id.b_welder_s,
            R.id.b_grinder_l,
            R.id.b_grinder_c,
            R.id.b_grinder_s,
            R.id.b_ore_detector_l,
            R.id.b_ore_detector_c,
            R.id.b_ore_detector_s,
            R.id.b_projector_l,
            R.id.b_projector_c,
            R.id.b_projector_s,
            R.id.b_small_cargo_container_l,
            R.id.b_small_cargo_container_c,
            R.id.b_small_cargo_container_s,
            R.id.b_medium_cargo_container_l,
            R.id.b_medium_cargo_container_c,
            R.id.b_medium_cargo_container_s,
            R.id.b_large_cargo_container_l,
            R.id.b_large_cargo_container_c,
            R.id.b_large_cargo_container_s,
            R.id.b_air_vent_l,
            R.id.b_air_vent_c,
            R.id.b_air_vent_s,
            R.id.b_oxygen_generator_l,
            R.id.b_oxygen_generator_c,
            R.id.b_oxygen_generator_s,
            R.id.b_oxygen_tank_l,
            R.id.b_oxygen_tank_c,
            R.id.b_oxygen_tank_s,
            R.id.b_hydrogen_tank_l,
            R.id.b_hydrogen_tank_c,
            R.id.b_hydrogen_tank_s,
            R.id.b_oxygen_farm_l,
            R.id.b_oxygen_farm_c,
            R.id.b_oxygen_farm_s,
            R.id.b_conveyor_block_l,
            R.id.b_conveyor_block_c,
            R.id.b_conveyor_block_s,
            R.id.b_small_conveyor_block_l,
            R.id.b_small_conveyor_block_c,
            R.id.b_small_conveyor_block_s,
            R.id.b_connector_l,
            R.id.b_connector_c,
            R.id.b_connector_s,
            R.id.b_collector_l,
            R.id.b_collector_c,
            R.id.b_collector_s,
            R.id.b_ejector_l,
            R.id.b_ejector_c,
            R.id.b_ejector_s,
            R.id.b_conveyor_sorter_l,
            R.id.b_conveyor_sorter_c,
            R.id.b_conveyor_sorter_s,
            R.id.b_small_conveyor_sorter_l,
            R.id.b_small_conveyor_sorter_c,
            R.id.b_small_conveyor_sorter_s,
            R.id.b_conveyor_tube_straight_and_curved_l,
            R.id.b_conveyor_tube_straight_and_curved_c,
            R.id.b_conveyor_tube_straight_and_curved_s,
            R.id.b_conveyor_tube_straight_l,
            R.id.b_conveyor_tube_straight_c,
            R.id.b_conveyor_tube_straight_s,
            R.id.b_conveyor_tube_curved_l,
            R.id.b_conveyor_tube_curved_c,
            R.id.b_conveyor_tube_curved_s,
            R.id.b_conveyor_frame_l,
            R.id.b_conveyor_frame_c,
            R.id.b_conveyor_frame_s,
    };
    private static final int[] sheet_id = {
            R.layout.f_refinery_l,
            R.layout.f_refinery_l,
            R.layout.f_refinery_l,
            R.layout.f_arc_furnace_l,
            R.layout.f_arc_furnace_l,
            R.layout.f_arc_furnace_l,
            R.layout.f_assembler_l,
            R.layout.f_assembler_l,
            R.layout.f_assembler_l,
            R.layout.f_energy_module_l,
            R.layout.f_energy_module_l,
            R.layout.f_energy_module_l,
            R.layout.f_productivity_module_l,
            R.layout.f_productivity_module_l,
            R.layout.f_productivity_module_l,
            R.layout.f_effectiveness_module_l,
            R.layout.f_effectiveness_module_l,
            R.layout.f_effectiveness_module_l,
            R.layout.f_drill_l,
            R.layout.f_drill_c,
            R.layout.f_drill_s,
            R.layout.f_welder_l,
            R.layout.f_welder_c,
            R.layout.f_welder_s,
            R.layout.f_grinder_l,
            R.layout.f_grinder_c,
            R.layout.f_grinder_s,
            R.layout.f_ore_detector_l,
            R.layout.f_ore_detector_c,
            R.layout.f_ore_detector_s,
            R.layout.f_projector_l,
            R.layout.f_projector_c,
            R.layout.f_projector_s,
            R.layout.f_small_cargo_container_l,
            R.layout.f_small_cargo_container_c,
            R.layout.f_small_cargo_container_s,
            R.layout.f_medium_cargo_container_s,
            R.layout.f_medium_cargo_container_s,
            R.layout.f_medium_cargo_container_s,
            R.layout.f_large_cargo_container_l,
            R.layout.f_large_cargo_container_c,
            R.layout.f_large_cargo_container_s,
            R.layout.f_air_vent_l,
            R.layout.f_air_vent_c,
            R.layout.f_air_vent_s,
            R.layout.f_oxygen_generator_l,
            R.layout.f_oxygen_generator_c,
            R.layout.f_oxygen_generator_s,
            R.layout.f_oxygen_tank_l,
            R.layout.f_oxygen_tank_c,
            R.layout.f_oxygen_tank_s,
            R.layout.f_hydrogen_tank_l,
            R.layout.f_hydrogen_tank_c,
            R.layout.f_hydrogen_tank_s,
            R.layout.f_oxygen_farm_l,
            R.layout.f_oxygen_farm_l,
            R.layout.f_oxygen_farm_l,
            R.layout.f_conveyor_block_l,
            R.layout.f_conveyor_block_c,
            R.layout.f_conveyor_block_s,
            R.layout.f_small_conveyor_block_s,
            R.layout.f_small_conveyor_block_s,
            R.layout.f_small_conveyor_block_s,
            R.layout.f_connector_l,
            R.layout.f_connector_c,
            R.layout.f_connector_s,
            R.layout.f_collector_l,
            R.layout.f_collector_c,
            R.layout.f_collector_s,
            R.layout.f_ejector_s,
            R.layout.f_ejector_s,
            R.layout.f_ejector_s,
            R.layout.f_conveyor_sorter_l,
            R.layout.f_conveyor_sorter_c,
            R.layout.f_conveyor_sorter_s,
            R.layout.f_small_conveyor_sorter_s,
            R.layout.f_small_conveyor_sorter_s,
            R.layout.f_small_conveyor_sorter_s,
            R.layout.f_conveyor_tube_straight_and_curved_l,
            R.layout.f_conveyor_tube_straight_and_curved_c,
            R.layout.f_conveyor_tube_straight_and_curved_s,
            R.layout.f_conveyor_tube_straight_s,
            R.layout.f_conveyor_tube_straight_s,
            R.layout.f_conveyor_tube_straight_s,
            R.layout.f_conveyor_tube_curved_s,
            R.layout.f_conveyor_tube_curved_s,
            R.layout.f_conveyor_tube_curved_s,
            R.layout.f_conveyor_frame_s,
            R.layout.f_conveyor_frame_s,
            R.layout.f_conveyor_frame_s,
    };
    private static final Button[] button = new Button[button_id.length];
    private MainActivity mActivity;
    private BottomSheetDialog mBottomSheetDialog;

    public static IndustryFragment newInstance() {
        return new IndustryFragment();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_industry, container, false);

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

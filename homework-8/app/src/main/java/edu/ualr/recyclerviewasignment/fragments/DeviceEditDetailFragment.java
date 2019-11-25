package edu.ualr.recyclerviewasignment.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewasignment.R;
import edu.ualr.recyclerviewasignment.data.DeviceDataFormatTools;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceDataViewModel;

import static android.content.Context.MODE_PRIVATE;

public class DeviceEditDetailFragment extends Fragment {
    private DeviceDataViewModel mDeviceDatavm;
    private int mDevicePosition;

    private DeviceEditDetailFragment.OnDetailSaved mOnDetailSavedListener;
    public interface OnDetailSaved {
        void onSave ();
    }

    public void setOnDetailSavedListener (DeviceEditDetailFragment.OnDetailSaved pOnDetailSavedListener) {
        mOnDetailSavedListener = pOnDetailSavedListener;
    }


    public DeviceEditDetailFragment (DeviceDataViewModel deviceDataViewModel, int position) {
        mDeviceDatavm = deviceDataViewModel;
        mDevicePosition = position;
    }

    @Override
    public View onCreateView (
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(
                R.layout.device_detail_fragment,
                container,
                false
        );

        DeviceEditViewHolder devh = new DeviceEditViewHolder(this, view);


        return view;
    }

    public class DeviceEditViewHolder {
        private TextView name;
        private TextView status;
        private TextView lastTimeConnection;

        private Spinner type;
        private Button saveBtn;
        private View greyBg;

        public DeviceEditViewHolder (final Fragment f, View v) {
            Device d = mDeviceDatavm.getDevice(mDevicePosition);

            // Device Name
            name = v.findViewById(R.id.detail_device_name_edittext);
            name.setText(d.getName());

            // Device Text Status
            status = v.findViewById(R.id.detail_device_status);

            // Device Type Spinner
            type = v.findViewById(R.id.device_type_spinner);

            final List<String> list=new ArrayList<String>();
            list.add("Unknown");
            list.add("Desktop");
            list.add("Laptop");
            list.add("Tablet");
            list.add("Smartphone");
            list.add("SmartTV");
            list.add("Game Console");

            ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            type.setAdapter(adp);

            switch (d.getDeviceType()) {
                case Unknown:
                    type.setSelection(0);
                    break;
                case Desktop:
                    type.setSelection(1);
                    break;
                case Laptop:
                    type.setSelection(2);
                    break;
                case Tablet:
                    type.setSelection(3);
                    break;
                case Smartphone:
                    type.setSelection(4);
                    break;
                case SmartTV:
                    type.setSelection(5);
                    break;
                case GameConsole:
                    type.setSelection(6);
                    break;
            }

            // Device Status Text
            switch (d.getDeviceStatus()) {
                case Connected:
                    status.setText("Connected");
                    break;
                case Ready:
                    status.setText("Ready");
                    break;
                case Linked:
                    status.setText("Linked");
                    break;
            }

            // Status Mark
            DeviceDataFormatTools.setDeviceStatusMark(getContext(), (ImageView) v.findViewById(R.id.detail_status_mark), d);

            // Thumbnail
            DeviceDataFormatTools.setDeviceThumbnail(getContext(), (RelativeLayout) v.findViewById(R.id.detail_thumbnail_image), d);

            // Last Time Connected
            lastTimeConnection = v.findViewById(R.id.last_time_connection_textview);
            lastTimeConnection.setText(DeviceDataFormatTools.getTimeSinceLastConnection(getContext(), d));

            // Save Button
            saveBtn = v.findViewById(R.id.save_btn);
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Name
                    mDeviceDatavm.setName(mDevicePosition, name.getText().toString());

                    // Type
                    switch ((String) type.getSelectedItem()) {
                        case "Unknown":
                            mDeviceDatavm.setType(mDevicePosition, Device.DeviceType.Unknown);
                            break;
                        case "Desktop":
                            mDeviceDatavm.setType(mDevicePosition, Device.DeviceType.Desktop);
                            break;
                        case "Laptop":
                            mDeviceDatavm.setType(mDevicePosition, Device.DeviceType.Laptop);
                            break;
                        case "Tablet":
                            mDeviceDatavm.setType(mDevicePosition, Device.DeviceType.Tablet);
                            break;
                        case "Smartphone":
                            mDeviceDatavm.setType(mDevicePosition, Device.DeviceType.Smartphone);
                            break;
                        case "SmartTV":
                            mDeviceDatavm.setType(mDevicePosition, Device.DeviceType.SmartTV);
                            break;
                        case "Game Console":
                            mDeviceDatavm.setType(mDevicePosition, Device.DeviceType.GameConsole);
                            break;
                    }

                    // save values
                    mOnDetailSavedListener.onSave();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(f).commit();
                }
            });

            // Grey Background
            greyBg = v.findViewById(R.id.grey_bg);
            greyBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(f).commit();
                }
            });
        }

    }

};
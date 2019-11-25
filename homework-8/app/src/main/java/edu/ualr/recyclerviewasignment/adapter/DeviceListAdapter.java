package edu.ualr.recyclerviewasignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewasignment.R;
import edu.ualr.recyclerviewasignment.data.DeviceDataFormatTools;
import edu.ualr.recyclerviewasignment.fragments.DeviceEditDetailFragment;

import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;
import edu.ualr.recyclerviewasignment.model.DeviceSection;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceDataViewModel;

/**
 * Created by irconde on 2019-10-04.
 */
public class DeviceListAdapter extends RecyclerView.Adapter {

    private static final int DEVICE_VIEW = 0;
    private static final int SECTION_VIEW = 1;

    private SortedList<DeviceListItem> mItems;
    private Context mContext;
    private DeviceDataViewModel mDeviceDataViewModel;

    public View.OnClickListener loadDetailFragmentOnClick;

    private DeviceListAdapter.OnDeviceClicked mOnDeviceClickListener;
    public interface OnDeviceClicked {
        void onClick (int position);
    }

    public DeviceListAdapter(Context context, DeviceDataViewModel deviceDataViewModel) {
        this.mContext = context;
        this.mItems = deviceDataViewModel.getDevices();
        this.mDeviceDataViewModel = deviceDataViewModel;

        mDeviceDataViewModel.setOnDatasetChanged(new DeviceDataViewModel.OnDatasetChanged() {
            @Override
            public void onChanged (int position, int count) {
                notifyDataSetChanged();
            }
        });
    }

    public void updateDataset () {
        this.mItems = mDeviceDataViewModel.getDevices();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == DEVICE_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
            vh = new DeviceViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_header, parent, false);
            vh = new SectionViewHolder(v);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceListItem item = mItems.get(position);

        if (holder instanceof DeviceViewHolder) {
            DeviceViewHolder view = (DeviceViewHolder) holder;

            Device device = (Device) item;

            view.name.setText(device.getName());
            view.elapsedTimeLabel.setText(DeviceDataFormatTools.getTimeSinceLastConnection(mContext, device));
            DeviceDataFormatTools.setDeviceStatusMark(mContext, view.statusMark, device);
            DeviceDataFormatTools.setDeviceThumbnail(mContext, view.image, device);
            DeviceDataFormatTools.setConnectionBtnLook(mContext, view.connectBtn, device.getDeviceStatus());
        } else {
            SectionViewHolder view = (SectionViewHolder) holder;
            DeviceSection section = (DeviceSection) item;
            view.title_section_label.setText(section.getLabel());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.mItems.get(position).isSection() ? SECTION_VIEW : DEVICE_VIEW;
    }

    public void setOnDeviceClickListener (DeviceListAdapter.OnDeviceClicked pOnDeviceClicked) {
        mOnDeviceClickListener = pOnDeviceClicked;
    }

    private class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView title_section_label;

        public SectionViewHolder(View v) {
            super(v);
            title_section_label = v.findViewById(R.id.title_section_label);
        }
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout image;
        private ImageView statusMark;
        private TextView name;
        private TextView elapsedTimeLabel;
        private ImageButton connectBtn;
        private View layoutParent;


        public DeviceViewHolder (View v) {
            super(v);

            image = v.findViewById(R.id.image);
            statusMark = v.findViewById(R.id.status_mark);
            name = v.findViewById(R.id.name);
            elapsedTimeLabel = v.findViewById(R.id.elapsed_time);

            connectBtn = v.findViewById(R.id.device_connect_btn);
            connectBtn.setOnClickListener(this);

            layoutParent = v.findViewById(R.id.device_item_container);
            layoutParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnDeviceClickListener.onClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
            Device device = (Device) mItems.get(getAdapterPosition());
            Device.DeviceStatus deviceStatus = device.getDeviceStatus();

            if (deviceStatus == Device.DeviceStatus.Connected) {
                mDeviceDataViewModel.setStatus(getAdapterPosition(), Device.DeviceStatus.Ready);
                mDeviceDataViewModel.setDate(getAdapterPosition(), new Date());
            } else if (deviceStatus == Device.DeviceStatus.Ready) {
                device.setDeviceStatus(Device.DeviceStatus.Connected);
            }

            mItems.updateItemAt(getAdapterPosition(), device);
        }
    }
}

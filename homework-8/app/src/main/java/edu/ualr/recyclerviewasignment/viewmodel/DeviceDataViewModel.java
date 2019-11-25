package edu.ualr.recyclerviewasignment.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.SortedList;

import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewasignment.data.DataGenerator;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;

public class DeviceDataViewModel extends ViewModel {
    private SortedList<DeviceListItem> mDevices;

    private OnDatasetChanged m_OnDatasetChanged;
    public interface OnDatasetChanged {
        void onChanged (int position, int count);
    }

    public void instantiateData (List<DeviceListItem> dli) {
        this.mDevices = new SortedList<>(DeviceListItem.class, new SortedList.Callback<DeviceListItem>() {
            @Override
            public int compare(DeviceListItem o1, DeviceListItem o2) {

                if (o1.isSection() && !o2.isSection()) {
                    if ( o1.getDeviceStatus().ordinal() <= o2.getDeviceStatus().ordinal()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                else if (!o1.isSection() && o2.isSection()) {
                    if ( o1.getDeviceStatus().ordinal() < o2.getDeviceStatus().ordinal()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                else if ((!o1.isSection() && !o2.isSection()) || (o1.isSection() && o2.isSection())){
                    return o1.getDeviceStatus().ordinal() - o2.getDeviceStatus().ordinal();
                }
                else return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                //notifyDataSetChanged();
                m_OnDatasetChanged.onChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(DeviceListItem oldItem, DeviceListItem newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(DeviceListItem item1, DeviceListItem item2) {
                return false;
            }

            @Override
            public void onInserted(int position, int count) {
                //notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                //notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                //notifyItemMoved(fromPosition, toPosition);
            }
        });


        // set the data
        mDevices.beginBatchedUpdates();
        for (int i = 0; i < dli.size(); i++) {
            mDevices.add(dli.get(i));
        }
        mDevices.endBatchedUpdates();
    }

    public void setOnDatasetChanged (OnDatasetChanged p_onDatasetChangedListener) {
        this.m_OnDatasetChanged = p_onDatasetChangedListener;
    }

    public SortedList<DeviceListItem> getDevices () {
        return mDevices;
    }

    public Device getDevice (int position) {
        return (Device) mDevices.get(position);
    }

    public void setStatus (int position, Device.DeviceStatus newStatus) {
        Device device = getDevice(position);
        device.setDeviceStatus(newStatus);
    }

    public void setName (int position, String name) {
        Device device = getDevice(position);
        device.setName(name);
    }

    public void setDate (int position, Date newDate) {
        Device device = getDevice(position);
        device.setLastConnection(newDate);
    }

    public void setType (int position, Device.DeviceType newType) {
        Device device = getDevice(position);
        device.setDeviceType(newType);
    }

    public void beginUpdate () {
        mDevices.beginBatchedUpdates();
    }

    public void endUpdate () {
        mDevices.endBatchedUpdates();
    }
}

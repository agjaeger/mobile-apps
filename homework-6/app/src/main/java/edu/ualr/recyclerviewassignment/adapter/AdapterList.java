package edu.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.ualr.recyclerviewassignment.R;
import edu.ualr.recyclerviewassignment.model.Device;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter {

    private List<Device> m_items;
    private Context m_context;

    public AdapterList (Context p_context, List<Device> p_items) {
        m_items = p_items;
        m_context = p_context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup p_parent, int p_viewType) {
        View itemView = LayoutInflater.from(p_parent.getContext()).inflate(
                R.layout.item_device,
                p_parent,
                false
        );

        return new DeviceViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceViewHolder vh = (DeviceViewHolder) holder;
        Device d = m_items.get(position);

        // assign vh params
        vh.deviceName.setText(d.getName());

        // set image on device type
        switch (d.getDeviceType()) {
            case Unknown:
                vh.icon.setImageResource(R.drawable.ic_unknown_device);
                break;

            case Desktop:
                vh.icon.setImageResource(R.drawable.ic_pc);
                break;

            case Laptop:
                vh.icon.setImageResource(R.drawable.ic_laptop);
                break;

            case Tablet:
                vh.icon.setImageResource(R.drawable.ic_tablet_android);
                break;

            case Smartphone:
                vh.icon.setImageResource(R.drawable.ic_phone_android);
                break;

            case SmartTV:
                vh.icon.setImageResource(R.drawable.ic_tv);
                break;

            case GameConsole:
                vh.icon.setImageResource(R.drawable.ic_gameconsole);
                break;
        };

        switch (d.getDeviceStatus()) {
            case Ready:
                vh.status.setImageResource(R.drawable.status_mark_ready);

                if (d.getLastConnection() == null) {
                    vh.connection.setText(R.string.never_connected);
                } else {
                    vh.connection.setText(R.string.recently);
                }
                break;

            case Linked:
                vh.connection.setText(R.string.linked);
                break;

            case Connected:
                vh.status.setImageResource(R.drawable.status_mark_connected);
                vh.connection.setText(R.string.currently_connected);
                break;
        }
    }

    @Override
    public int getItemCount () {
        return m_items.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public ImageView status;
        public TextView deviceName;
        public TextView connection;

        public DeviceViewHolder (View p_view) {
            super(p_view);

            icon = p_view.findViewById(R.id.id_deviceIcon);
            status = p_view.findViewById(R.id.id_deviceStatus);
            deviceName = p_view.findViewById(R.id.id_deviceName);
            connection = p_view.findViewById(R.id.id_connectionTimestamp);
        }
    }
}
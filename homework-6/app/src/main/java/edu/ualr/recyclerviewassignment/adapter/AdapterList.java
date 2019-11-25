package edu.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.ualr.recyclerviewassignment.R;
import edu.ualr.recyclerviewassignment.model.Item;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.model.Section;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter {

    private static final int DEVICE_VIEW = 0;
    private static final int SECTION_VIEW = 1;

    private OnItemClickListener m_OnItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(View view, Device obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener p_ItemClickListener) {
        this.m_OnItemClickListener = p_ItemClickListener;
    }

    private List<Item> m_items;
    private Context m_context;

    public AdapterList (Context p_context, List<Item> p_items) {
        m_context = p_context;
        m_items = buildItems(p_items);


    }

    public void rebuildList () {
        List<Item> deviceItems  = filterDevicesFromList(m_items);
        m_items = buildItems(deviceItems);
    }

    public List<Item> buildItems (List<Item> p_items) {
        // Sort the list
        Collections.sort(p_items, new Comparator<Item>() {
            // order: Connected, Ready, Linked
            public int compare(Item itemA, Item itemB) {
                Device deviceA = (Device) itemA;
                Device deviceB = (Device) itemB;

                return Integer.valueOf(deviceA.getDeviceStatus().ordinal())
                        .compareTo(
                                Integer.valueOf(deviceB.getDeviceStatus().ordinal())
                        );
            }
        });

        List<Item> connectedItems = filterListOnStatus(p_items, Device.DeviceStatus.Connected);
        List<Item> readyItems = filterListOnStatus(p_items, Device.DeviceStatus.Ready);
        List<Item> linkedItems = filterListOnStatus(p_items, Device.DeviceStatus.Linked);


        // Build organized list by extending the filered

        List<Item> organized = new ArrayList<>();

        if (connectedItems.size() > 0) {
            organized.add(new Section("Connected"));
            organized.addAll(connectedItems);
        }

        if (readyItems.size() > 0) {
            organized.add(new Section("Ready"));
            organized.addAll(readyItems);
        }

        if (linkedItems.size() > 0) {
            organized.add(new Section("Linked"));
            organized.addAll(linkedItems);
        }

        return organized;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup p_parent, int p_viewType) {

        RecyclerView.ViewHolder vh = null;
        View itemView = null;

        switch (p_viewType) {
            case SECTION_VIEW:
                itemView = LayoutInflater.from(p_parent.getContext()).inflate(
                        R.layout.item_section,
                        p_parent,
                        false
                );

                vh =  new SectionHeaderViewHolder(itemView);
                break;

            case DEVICE_VIEW:
                itemView = LayoutInflater.from(p_parent.getContext()).inflate(
                        R.layout.item_device,
                        p_parent,
                        false
                );

                vh =  new DeviceViewHolder(itemView);
                break;
        };

        return vh;
    }


    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = m_items.get(position);

        if (item.isSection()) {
            SectionHeaderViewHolder viewHolder = (SectionHeaderViewHolder) holder;
            Section section = (Section) item;
            viewHolder.label.setText(section.getLabel());
        } else {
            DeviceViewHolder vh = (DeviceViewHolder) holder;
            Device device = (Device) item;

            // set device name
            vh.deviceName.setText(device.getName());

            // set image on device type
            switch (device.getDeviceType()) {
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
            }

            // set status image and connection text
            switch (device.getDeviceStatus()) {
                case Connected:
                    vh.status.setVisibility(View.VISIBLE);
                    vh.connectBtn.setVisibility(View.VISIBLE);

                    vh.status.setImageResource(R.drawable.status_mark_connected);
                    vh.connection.setText(R.string.currently_connected);
                    break;

                case Ready:
                    vh.status.setVisibility(View.VISIBLE);
                    vh.connectBtn.setVisibility(View.VISIBLE);

                    vh.status.setImageResource(R.drawable.status_mark_ready);

                    if (device.getLastConnection() == null) {
                        vh.connection.setText(R.string.never_connected);
                    } else {
                        vh.connection.setText(R.string.recently);
                    }

                    break;

                case Linked:
                    vh.connectBtn.setVisibility(View.GONE);
                    vh.status.setVisibility(View.GONE);
                    vh.connection.setText(R.string.linked);
                    //vh.iconBg.setImageResource(R.drawable.thumbnail_background_wire);
                    break;
            }
        }
    }

    @Override
    public int getItemCount () {
        return m_items.size();
    }

    @Override
    public int getItemViewType (int position) {
        if (this.m_items.get(position).isSection()) {
            return SECTION_VIEW;
        }

        return DEVICE_VIEW;
    }

    private List<Item>  filterListOnStatus (List<Item> items, Device.DeviceStatus targetStatus) {
        List<Item> filteredItems = new ArrayList<>();

        for (Item item : items) {
            Device d = (Device) item;
            if (d.getDeviceStatus() == targetStatus) {
                filteredItems.add(d);
            }
        }

        return filteredItems;
    }

    private List<Item>  filterDevicesFromList (List<Item> items) {
        List<Item> filteredItems = new ArrayList<>();

        for (Item item : items) {
            if (item instanceof Device) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconBg;
        public ImageView icon;
        public ImageView status;
        public TextView deviceName;
        public TextView connection;
        public ImageButton connectBtn;
        public View lyt_parent;

        public DeviceViewHolder (View p_view) {
            super(p_view);

            iconBg = p_view.findViewById(R.id.id_background_img);
            icon = p_view.findViewById(R.id.id_deviceIcon);
            status = p_view.findViewById(R.id.id_deviceStatus);
            deviceName = p_view.findViewById(R.id.id_deviceName);
            connection = p_view.findViewById(R.id.id_connectionTimestamp);
            connectBtn = p_view.findViewById(R.id.id_connectBtn);

            lyt_parent = p_view.findViewById(R.id.lyt_deviceParent);

            connectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Device d = (Device) m_items.get(getLayoutPosition());
                    m_OnItemClickListener.onItemClick(view, d, getLayoutPosition());
                }
            });

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("TEST");
                }
            });
        }
    }

    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView label;

        public SectionHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.label = itemView.findViewById(R.id.title_section);
        }
    }
}
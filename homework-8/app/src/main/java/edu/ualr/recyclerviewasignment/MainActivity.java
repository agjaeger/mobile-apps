package edu.ualr.recyclerviewasignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.SortedList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.ualr.recyclerviewasignment.adapter.DeviceListAdapter;
import edu.ualr.recyclerviewasignment.data.DataGenerator;
import edu.ualr.recyclerviewasignment.fragments.DeviceEditDetailFragment;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceDataViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FragmentManager mFragmentManager;
    private DeviceListAdapter mAdapter;
    private DeviceDataViewModel mDeviceDatavm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initRecyclerView();
    }

    private void initRecyclerView(){
        mFragmentManager = getSupportFragmentManager();
        mRecyclerView = findViewById(R.id.devices_recycler_view);
        mDeviceDatavm = ViewModelProviders.of(this).get(DeviceDataViewModel.class);
        mDeviceDatavm.instantiateData(DataGenerator.getDevicesDataset(5));

        // RecyclerView
        mAdapter = new DeviceListAdapter(this, mDeviceDatavm);
        mRecyclerView.setAdapter(mAdapter);

        // Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Callbacks
        mAdapter.setOnDeviceClickListener(new DeviceListAdapter.OnDeviceClicked() {
            @Override
            public void onClick(int position) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                DeviceEditDetailFragment dedf = new DeviceEditDetailFragment(mDeviceDatavm, position);
                dedf.setOnDetailSavedListener(new DeviceEditDetailFragment.OnDetailSaved() {
                    @Override
                    public void onSave() {
                        mAdapter.updateDataset();
                    }
                });

                ft.add(R.id.testContainer, dedf);
                ft.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SortedList<DeviceListItem> slDevices = mDeviceDatavm.getDevices();
        List<DeviceListItem> newDevices = new ArrayList<>();

        switch (item.getItemId()) {
            case R.id.connect_all:
                for (int idx = 0; idx < slDevices.size(); idx++) {
                    if (slDevices.get(idx) instanceof Device) {
                        Device d = mDeviceDatavm.getDevice(idx);
                        if (d.getDeviceStatus() == Device.DeviceStatus.Ready) {
                            d.setStatus(Device.DeviceStatus.Connected);
                        }
                        newDevices.add(d);
                    } else {
                        newDevices.add(slDevices.get(idx));
                    }
                }

                break;

            case R.id.disconnect_all:
                for (int idx = 0; idx < slDevices.size(); idx++) {
                    if (slDevices.get(idx) instanceof Device) {
                        Device d = mDeviceDatavm.getDevice(idx);
                        if (d.getDeviceStatus() == Device.DeviceStatus.Connected) {
                            d.setStatus(Device.DeviceStatus.Ready);
                        }
                        newDevices.add(d);
                    } else {
                        newDevices.add(slDevices.get(idx));
                    }
                }
        }

        mDeviceDatavm.instantiateData(newDevices);
        mAdapter.updateDataset();

        return true;

    }
}

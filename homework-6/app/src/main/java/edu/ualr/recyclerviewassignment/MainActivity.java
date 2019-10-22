package edu.ualr.recyclerviewassignment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.ualr.recyclerviewassignment.data.DataGenerator;
import edu.ualr.recyclerviewassignment.model.Item;
import edu.ualr.recyclerviewassignment.model.Section;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.adapter.AdapterList;

import java.util.List;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterList mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView () {
        // TODO. Create and initialize the RecyclerView instance here
        List<Item> items = DataGenerator.getDevicesDataset(10);

        // TODO 07: Instantiate the adapter and pass its data source.
        // TODO 10: Create the new AdapterListBasic class
        mAdapter = new AdapterList(this, items);

        // TODO 08: Get our RecyclerView layout and plug the adapter into the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);

        // TODO 09: Define the LayoutManager and plug it into RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter.setOnItemClickListener(new AdapterList.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Device obj, int position) {
                if (obj.getDeviceStatus() == Device.DeviceStatus.Connected) {
                    obj.setDeviceStatus(Device.DeviceStatus.Ready);
                    obj.setLastConnection(new Date());
                } else {
                    obj.setDeviceStatus(Device.DeviceStatus.Connected);
                }

                mAdapter.rebuildList();
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}

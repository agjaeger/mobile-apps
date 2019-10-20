package edu.ualr.recyclerviewassignment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import edu.ualr.recyclerviewassignment.data.DataGenerator;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.adapter.AdapterList;

import java.util.List;

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
        List<Device> items = DataGenerator.getDevicesDataset(6);

        // TODO 07: Instantiate the adapter and pass its data source.
        // TODO 10: Create the new AdapterListBasic class
        mAdapter = new AdapterList(this, items);

        // TODO 08: Get our RecyclerView layout and plug the adapter into the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);

        // TODO 09: Define the LayoutManager and plug it into RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}

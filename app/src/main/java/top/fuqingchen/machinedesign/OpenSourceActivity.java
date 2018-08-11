package top.fuqingchen.machinedesign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Fu_Qingchen
 */
public class OpenSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);

        final ArrayList<OpenSource> list = new ArrayList<>();

        list.add(new OpenSource(getString(R.string.OpenSource_MDI), getString(R.string.OpenSource_MDI_web)));
        list.add(new OpenSource(getString(R.string.OpenSource_AAS), getString(R.string.OpenSource_AAS_web)));
        list.add(new OpenSource(getString(R.string.OpenSource_ASL), getString(R.string.OpenSource_ASL_web)));
//        list.add(new OpenSource(getString(R.string.OpenSource_Y), getString(R.string.OpenSource_Y_web)));
        list.add(new OpenSource(getString(R.string.OpenSource_ACML), getString(R.string.OpenSource_ACML_web)));
        list.add(new OpenSource(getString(R.string.OpenSource_exp4j), getString(R.string.OpenSource_exp4j_web)));

        OpenSourceAdapter openSourceAdapter = new OpenSourceAdapter(this, list);
        ListView listView = findViewById(R.id.OpenSource_list);
        listView.setAdapter(openSourceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("Test", "Good");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(i).getDetal()));
                startActivity(intent);
            }
        });
    }
}

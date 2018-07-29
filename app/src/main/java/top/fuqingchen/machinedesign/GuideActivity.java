package top.fuqingchen.machinedesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Button button = findViewById(R.id.guide_finish);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox intro_1 = findViewById(R.id.guide_intro_1);
                CheckBox intro_2 = findViewById(R.id.guide_intro_2);
                CheckBox intro_3 = findViewById(R.id.guide_intro_3);

                if (intro_1.isChecked() && intro_2.isChecked() && intro_3.isChecked()) {
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(GuideActivity.this, R.string.guide_message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

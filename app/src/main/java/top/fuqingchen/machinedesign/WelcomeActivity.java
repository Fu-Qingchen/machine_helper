package top.fuqingchen.machinedesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Fu_Qingchen
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date();
    }

    private void date() {
        SharedPreferences sharedPreferences = getSharedPreferences("count", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count", 0);
        if (count != 0) {
            Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        } else {
            Intent in = new Intent(WelcomeActivity.this, GuideActivity.class);
            startActivity(in);
            finish();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count", ++count);
        editor.apply();
    }
}

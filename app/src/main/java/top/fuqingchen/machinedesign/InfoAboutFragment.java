package top.fuqingchen.machinedesign;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoAboutFragment extends Fragment {


    public InfoAboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_about, container, false);

        //contact_Rate
        LinearLayout contact_Rate = view.findViewById(R.id.contact_Rate);
        contact_Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String str = "market://details?id=" + getString(R.string.app_packageName);
                    Intent localIntent = new Intent(Intent.ACTION_VIEW);
                    localIntent.setData(Uri.parse(str));
                    startActivity(localIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "打开失败(ﾟДﾟ≡ﾟдﾟ)!?", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //contact_Contact
        LinearLayout contact_Contact = view.findViewById(R.id.contact_Contact);
        contact_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://weibo.com/fuqingchen"));
                startActivity(intent);
            }
        });

        //contact_Feedback
        LinearLayout contact_Feedback = view.findViewById(R.id.contact_Feedback);
        contact_Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + getString(R.string.app_addresses)));
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_subject));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        //contact_My_Website
        LinearLayout contact_My_Website = view.findViewById(R.id.contact_My_Website);
        contact_My_Website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://fu-qingchen.github.io/HelloWorld/"));
                startActivity(intent);
            }
        });

        //development_code
        LinearLayout development_code = view.findViewById(R.id.development_code);
        development_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/Fu-Qingchen/MachineDesign"));
                startActivity(intent);
            }
        });

        //development_Components
        LinearLayout development_Components = view.findViewById(R.id.development_Components);
        development_Components.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OpenSourceActivity.class);
                startActivity(intent);
            }
        });

        TextView copyright = view.findViewById(R.id.copyright);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "CaviarDreams.ttf");
        copyright.setTypeface(typeface);

        return view;
    }
}

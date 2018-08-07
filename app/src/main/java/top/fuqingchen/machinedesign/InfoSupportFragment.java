package top.fuqingchen.machinedesign;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * @author Fu_Qingchen
 */
public class InfoSupportFragment extends Fragment {


    public InfoSupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_support, container, false);

        //support_Red_Envelope
        LinearLayout support_Red_Envelope = view.findViewById(R.id.support_Red_Envelope);
        support_Red_Envelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2Fc1x02895dpzoxkinmpitm55%3F_s%3Dweb-other"));
                startActivity(intent);
            }
        });

        //donations_alipay
        TextView donations_alipay = view.findViewById(R.id.donations_alipay);
        donations_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2Ftsx03183rcmrdpeaqgqmi8b%3F_s%3Dweb-other"));
                startActivity(intent);
            }
        });
        return view;

    }

}

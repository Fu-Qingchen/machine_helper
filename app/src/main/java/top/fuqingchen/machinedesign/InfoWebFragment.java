package top.fuqingchen.machinedesign;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * @author Fu_Qingchen
 */
public class InfoWebFragment extends Fragment {


    public InfoWebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_web, container, false);

        //GB
        LinearLayout GB_linearLayout = view.findViewById(R.id.GB_linearLayout);
        GB_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.Web_GB688_web)));
                startActivity(intent);
            }
        });

        //GBHelp
        LinearLayout GBhelp_linearLayout = view.findViewById(R.id.GBhelp_linearLayout);
        GBhelp_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.Web_GB688_help_web)));
                startActivity(intent);
            }
        });

        TextView copy = view.findViewById(R.id.web_copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager =
                        (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Result", getString(R.string.Web_GB688_web));
                clipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), R.string.copy_done, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}

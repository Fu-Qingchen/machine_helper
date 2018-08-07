package top.fuqingchen.machinedesign;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


/**
 * @author Fu_Qingchen
 */
public class InfoFragment extends Fragment {


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, null);
        ViewPager viewPager = view.findViewById(R.id.viewPager_info);
        viewPager.setAdapter(new InfoPaperAdapter(getActivity(), getChildFragmentManager()));

        TabLayout tabLayout = view.findViewById(R.id.tabs_info);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}

package top.fuqingchen.machinedesign;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
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

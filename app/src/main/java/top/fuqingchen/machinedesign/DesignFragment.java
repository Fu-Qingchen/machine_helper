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
public class DesignFragment extends Fragment {

    public DesignFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_design, null);
        ViewPager viewPager = view.findViewById(R.id.viewPager_design);
        viewPager.setAdapter(new DesignPaperAdapter(getActivity(), getChildFragmentManager()));

        TabLayout tabLayout = view.findViewById(R.id.tabs_design);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}

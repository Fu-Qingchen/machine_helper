package top.fuqingchen.machinedesign;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculationFragment extends Fragment {


    public CalculationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculation, null);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager_calculation);
        viewPager.setAdapter(new CalculationPaperAdaptter(getActivity(), getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs_calculation);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}

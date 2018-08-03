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
public class CalculationFragment extends Fragment {


    public CalculationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculation, null);
        ViewPager viewPager = view.findViewById(R.id.viewPager_calculation);
        viewPager.setAdapter(new CalculationPaperAdaptter(getActivity(), getChildFragmentManager()));

        TabLayout tabLayout = view.findViewById(R.id.tabs_calculation);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}

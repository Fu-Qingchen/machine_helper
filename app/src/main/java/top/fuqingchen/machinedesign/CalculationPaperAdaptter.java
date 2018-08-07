package top.fuqingchen.machinedesign;

import android.content.Context;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author Fu_Qingchen
 */
public class CalculationPaperAdaptter extends FragmentPagerAdapter {
    private Context mContext;
    private List<String> tagLists;
    private FragmentManager mfragmentManager;

    public CalculationPaperAdaptter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
        mfragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CalculationInterpolationFragment();
            case 1:
                return new CalculationIntegrationFragment();
            case 2:
                return new CalculationDifferentiationFragment();
            case 3:
                return new CalculationSolveFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.calculation_Interpolation);
            case 1:
                return mContext.getString(R.string.calculation_Integration);
            case 2:
                return mContext.getString(R.string.calculation_Differentiation);
            case 3:
                return mContext.getString(R.string.calculation_Matrix);
            default:
                return null;
        }
    }
}

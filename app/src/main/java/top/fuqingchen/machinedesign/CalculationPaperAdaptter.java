package top.fuqingchen.machinedesign;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CalculationPaperAdaptter extends FragmentPagerAdapter {
    private Context mContext;

    public CalculationPaperAdaptter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
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
                return new CalculationSafeFragment();
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

package top.fuqingchen.machinedesign;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author Fu_Qingchen
 */
public class DesignPaperAdapter extends FragmentStatePagerAdapter {
    /**
     * Context of the app
     */
    private Context mContext;

    public DesignPaperAdapter(Context context, FragmentManager fragmentManager) {
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
            return new DesignGearFragment();
            case 1:
            return new DesignKeyFragment();
            case 2:
            return new DesignBearingFragment();
            case 3:
                return new DesignLimitsFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.design_Gear);
            case 1:
                return mContext.getString(R.string.design_FlatKey);
            case 2:
                return mContext.getString(R.string.design_Bearing);
            case 3:
                return mContext.getString(R.string.design_Limits);
            default:
                return null;
        }
    }

}
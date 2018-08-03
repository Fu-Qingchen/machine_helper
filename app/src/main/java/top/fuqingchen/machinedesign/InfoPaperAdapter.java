package top.fuqingchen.machinedesign;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class InfoPaperAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public InfoPaperAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InfoSettingsFragment();
            case 1:
                return new InfoAboutFragment();
            case 2:
                return new InfoSupportFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.info_Settings);
            case 1:
                return mContext.getString(R.string.info_About);
            case 2:
                return mContext.getString(R.string.info_Support);
            default:
                return null;
        }
    }
}

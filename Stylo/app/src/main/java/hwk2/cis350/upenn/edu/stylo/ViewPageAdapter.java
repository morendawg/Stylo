package hwk2.cis350.upenn.edu.stylo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;


/**
 * @author DanielMoreno
 * This class sets up the different fragments in the Menu Activity
 */
public class ViewPageAdapter extends FragmentPagerAdapter{
    CharSequence tabTitles[]; // this stores the titles of the tabs
    int numTabs; // stores the number of tabs
    Context context;

    public ViewPageAdapter(FragmentManager fm,CharSequence tabTitles[], int numberOfTabs, Context c) {
        super(fm);
        this.context = c;
        this.tabTitles = tabTitles;
        this.numTabs = numberOfTabs;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                FeedFragment tab1 = new FeedFragment();
                return tab1;
            case 1:
                ChannelsFragment tab2 = new ChannelsFragment();
                return tab2;
            case 2:
                UploadFragment tab3 = new UploadFragment();
                return tab3;
            case 3:
                SettingsFragment tab4 = new SettingsFragment();
                return tab4;
            default:
                return null;
        }

    }

    // Array containing the icons for each tab (used instead of a title for each tab)
    private int[] imageResId = {
            R.drawable.feed,
            R.drawable.channels,
            R.drawable.upload,
            R.drawable.settings
    };

    /**
     * This method draws the appropriate icon for each tab in the right location
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, 80, 80);
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BASELINE);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    // This method return the Number of tabs in page adapter
    @Override
    public int getCount() {
        return numTabs;
    }
}
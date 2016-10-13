package hwk2.cis350.upenn.edu.stylo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Daniel Moreno
 * This class serves as a container of the fragments that make up the Menu
 */

public class MenuActivity extends AppCompatActivity {

    //The pager objects allow for multiple fragments to be displayed
    ViewPager pager;
    ViewPageAdapter adapter;
    //This lets the user swipe between the fragments on the screen
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Feed","Channels", "Upload", "Settings"};
    int numTabs =4;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // sets the adapter and adds a listener to the page swiper
        adapter =  new ViewPageAdapter(getSupportFragmentManager(),Titles,numTabs, getApplicationContext());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.custom_tab, 0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //no scrolling is implemented as all tabs fit on the screen
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //changes the title of the action bar to the current fragment
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setActionBarTitle("Feed");
                        break;
                    case 1:
                        setActionBarTitle("Channels");
                        break;
                    case 2:
                        setActionBarTitle("Upload");
                        break;
                    case 3:
                        setActionBarTitle("Settings");
                        break;
                    default:
                        setActionBarTitle("Stylo");
                        break;
                }
            }

            // pageScrollState is never changed, but method is required
            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        //sets initial title to the first page, the Feed
        setActionBarTitle("Feed");
        tabs.setViewPager(pager);
    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}

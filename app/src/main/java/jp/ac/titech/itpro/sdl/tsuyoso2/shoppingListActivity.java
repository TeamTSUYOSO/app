package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Yamada on 2016/10/10.
 */
public class shoppingListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otsukai_list);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = (Fragment) new OtsukaiDayFragment();
                        break;
                    case 1:
                        fragment = (Fragment) new OtsukaiMatomeFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "レシピ別";
                    case 1:
                        return "まとめ買いリスト";
                    default:
                        return "";
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        //オートマチック方式: これだけで両方syncする
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("MainActivity", "onPageSelected() position="+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class OtsukaiDayFragment extends Fragment {

        public OtsukaiDayFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_otsukai_day, container, false);
            LinearLayout shoppingList = (LinearLayout) view.findViewById(R.id.shopping_list_ingredients);
            TAsyncShoppingList asyncJson = new TAsyncShoppingList(this.getActivity(), shoppingList);
            asyncJson.execute();
            return view;
        }
    }

    public static class OtsukaiMatomeFragment extends Fragment {

        public OtsukaiMatomeFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_otsukai_matome, container, false);

            LinearLayout header = (LinearLayout)view.findViewById(R.id.otsukai_list_header);
            for(int i = 0; i < 7; i++){
                View dateView = inflater.inflate(R.layout.header_otsukai_date, header, false);
                TextView month = (TextView) dateView.findViewById(R.id.month);
                month.setText("12");
                TextView day = (TextView) dateView.findViewById(R.id.day);
                day.setText(i + "");
                header.addView(dateView);
            }

            ListView shoppingList = (ListView)view.findViewById(R.id.shopping_list_ingredients);
            TAsyncShoppingMatomeList asyncJson = new TAsyncShoppingMatomeList(this.getActivity(), shoppingList);
            asyncJson.execute();
            return view;
        }
    }
}
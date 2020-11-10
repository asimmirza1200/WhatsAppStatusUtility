package com.wadownloader.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.wadownloader.whatsappstatussaver.Fargments.AboutUsFragment;
import com.wadownloader.whatsappstatussaver.Fargments.HistoryFragment;
import com.wadownloader.whatsappstatussaver.Fargments.HomeFragment;
import com.wadownloader.whatsappstatussaver.models.Admob;

import java.util.ArrayList;
import java.util.Arrays;


    public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
        private MenuAdapter mMenuAdapter;
        private ViewHolder mViewHolder;


        private ArrayList<String> mTitles = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713



            mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

            // Initialize the views
            mViewHolder = new ViewHolder();

            // Handle toolbar actions
            handleToolbar();

            // Handle menu actions
            handleMenu();

            // Handle drawer actions
            handleDrawer();

            // Show main fragment in container
            goToFragment(new HomeFragment(), false);
            mMenuAdapter.setViewSelected(0, true);
            setTitle(mTitles.get(0));
        }

        private void handleToolbar() {
            setSupportActionBar(mViewHolder.mToolbar);
        }

        private void handleDrawer() {
            DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                    mViewHolder.mDuoDrawerLayout,
                    mViewHolder.mToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
            duoDrawerToggle.syncState();

        }

        private void handleMenu() {
            mMenuAdapter = new MenuAdapter(mTitles);

            mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
            mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
        }

        @Override
        public void onFooterClicked() {
            finish();
        }

        @Override
        public void onHeaderClicked() {
            Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
        }

        private void goToFragment(Fragment fragment, boolean addToBackStack) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (addToBackStack) {
                transaction.addToBackStack("sdfsdf");
            }

            transaction.replace(R.id.container, fragment).commit();
        }

        @Override
        public void onOptionClicked(int position, Object objectClicked) {
            // Set the toolbar title
            setTitle(mTitles.get(position));

            // Set the right options selected
            mMenuAdapter.setViewSelected(position, true);

            // Navigate to the right fragment
            switch (position) {
                case 0:

                    goToFragment(new HomeFragment(), true);
                    break;
                case 1:

                    goToFragment(new HistoryFragment(), true);
                    break;

                case 2:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    break;
                case 3:

                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id=7945477530981096882")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=7945477530981096882")));
                    }
                    break;
                case 4:

                    goToFragment(new AboutUsFragment(), true);
                    break;

            }

            // Close the drawer
            mViewHolder.mDuoDrawerLayout.closeDrawer();
        }

        private class ViewHolder {
            private DuoDrawerLayout mDuoDrawerLayout;
            private DuoMenuView mDuoMenuView;
            private Toolbar mToolbar;

            ViewHolder() {
                mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
                mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
                mToolbar = (Toolbar) findViewById(R.id.toolbar);
            }
        }

    }

/*
 * Copyright (c) 2015. 2Lines Software,Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.twolinessoftware.android.mobileresume.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;
import com.twolinessoftware.android.mobileresume.R;
import com.twolinessoftware.android.mobileresume.event.OnNavigateToMenu;
import com.twolinessoftware.android.mobileresume.fragment.AboutMeImageFragment;
import com.twolinessoftware.android.mobileresume.fragment.BaseFragmentAdapter;
import com.twolinessoftware.android.mobileresume.fragment.BasicImageFragment;
import com.twolinessoftware.android.mobileresume.fragment.ContactViewFragment;
import com.twolinessoftware.android.mobileresume.fragment.NavigationDrawerFragment;
import com.twolinessoftware.android.mobileresume.fragment.ResumeViewFragment;
import com.twolinessoftware.android.mobileresume.util.Ln;

import java.util.ArrayList;

import butterknife.InjectView;


public class MainActivity extends BaseActivity {

    /**
     * All the fragments belong in here.
     */
    @InjectView(R.id.view_pager_main)
    ViewPager m_viewPager;

    @InjectView(R.id.toolbar)
    Toolbar m_toolbar;

    @InjectView(R.id.image_background)
    ImageView m_backgroundImage;


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment m_navigationDrawerFragment;

    private BaseFragmentAdapter m_adapter;

    private DrawerLayout m_drawerLayout;

    private ActionBarDrawerToggle m_drawerToggle;

    // Store a reference to the title
    private CharSequence m_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_title = getTitle();

        setSupportActionBar(m_toolbar);
        m_toolbar.inflateMenu(R.menu.global);

        getSupportActionBar().setElevation(getResources().getDimensionPixelSize(R.dimen.toolbar_elevation));

        // Set up the drawer.
        setupNavigationDrawer();

        // Set up the fragments
        setupFragments();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupNavigationDrawer() {
        m_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        m_drawerToggle = new ActionBarDrawerToggle(
                this,                    /* Host activity */
                m_drawerLayout,           /* DrawerLayout object */
                m_toolbar,
                R.string.navigation_drawer_open,    /* "open drawer" description */
                R.string.navigation_drawer_close) { /* "close drawer" description */

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(m_title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        m_drawerLayout.setDrawerListener(m_drawerToggle);

        m_navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        m_navigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                m_toolbar,
                m_drawerLayout);
    }

    private void setupFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        // About Me
        fragments.add(AboutMeImageFragment.newInstance());

        // Resume
        fragments.add(ResumeViewFragment.newInstance());

        // Contacts
        fragments.add(ContactViewFragment.newInstance());

        m_adapter = new BaseFragmentAdapter(getSupportFragmentManager(),fragments);
        m_viewPager.setAdapter(m_adapter);
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(m_title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!m_navigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onNavigationEvent(OnNavigateToMenu event){
        Ln.v("Navigating to menu index:"+event.getMenuIndex());
        int menuId = event.getMenuIndex();
        m_viewPager.setCurrentItem(menuId);
    }



}

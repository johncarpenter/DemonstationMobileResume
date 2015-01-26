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

package com.twolinessoftware.android.mobileresume.fragment;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.twolinessoftware.android.mobileresume.Constants;
import com.twolinessoftware.android.mobileresume.Injector;
import com.twolinessoftware.android.mobileresume.R;
import com.twolinessoftware.android.mobileresume.event.OnNavigateToMenu;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationDrawerFragment extends Fragment {

    /**
    * Remember the position of the selected item.
    */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle m_drawerToggle;

    @Inject
    protected Bus m_eventBus;

    @InjectView(R.id.recycler_menu)
    RecyclerView m_recyclerView;

    @Inject protected SharedPreferences prefs;

    private View fragmentContainerView;

    private DrawerLayout drawerLayout;

    private LinearLayoutManager m_layoutManager;

    private MenuAdapter m_adapter;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.inject(this);

        setRetainInstance(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        m_eventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        m_eventBus.unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
        ButterKnife.inject(this, getView());

        m_layoutManager = new LinearLayoutManager(getActivity());

        m_adapter = new MenuAdapter(configureMenuItems());
        m_recyclerView.setLayoutManager(m_layoutManager);
        m_recyclerView.setAdapter(m_adapter);

    }

    private List<MenuItemHolder> configureMenuItems() {
        ArrayList<MenuItemHolder> menuItems = new ArrayList<MenuItemHolder>();

        menuItems.add(new MenuItemHolder(Constants.MENU_ABOUT,getString(R.string.title_section1)));
        menuItems.add(new MenuItemHolder(Constants.MENU_RESUME,getString(R.string.title_section2)));
        menuItems.add(new MenuItemHolder(Constants.MENU_CONTACT,getString(R.string.title_section3)));

        return menuItems;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    public boolean isDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, Toolbar toolbar, DrawerLayout drawerLayout) {
        fragmentContainerView = getActivity().findViewById(fragmentId);
        this.drawerLayout = drawerLayout;

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        m_drawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                NavigationDrawerFragment.this.drawerLayout,                    /* DrawerLayout object */
                toolbar,
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // Defer code dependent on restoration of previous instance state.
        this.drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                m_drawerToggle.syncState();
            }
        });

        this.drawerLayout.setDrawerListener(m_drawerToggle);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        m_drawerToggle.onConfigurationChanged(newConfig);
    }



    private class MenuAdapter extends RecyclerView.Adapter<ViewHolder>{

        private final List<MenuItemHolder> m_menuItems;

        public MenuAdapter(List<MenuItemHolder> menuItems){
            this.m_menuItems = menuItems;
        }

        public MenuItemHolder getMenuItem(int position){
            return m_menuItems.get(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_drawer_menu, parent, false);

            v.setOnClickListener(new MenuClickListener());

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            MenuItemHolder menuItem = m_menuItems.get(position);

            holder.textView.setText(menuItem.text);

        }

        @Override
        public int getItemCount() {
            return m_menuItems.size();
        }


    }

    private class MenuClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int itemPosition = m_recyclerView.getChildPosition(view);
            MenuItemHolder item = m_adapter.getMenuItem(itemPosition);

            m_eventBus.post(new OnNavigateToMenu(item.index));

            drawerLayout.closeDrawer(Gravity.START);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_menu_name);
        }
    }

    public static class MenuItemHolder{
        public int index;
        public String text;

        public MenuItemHolder(int index, String text) {
            this.index = index;
            this.text = text;
        }
    }


}

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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Simple adapter to hold a fixed list of fragments
 *
 * Created by John on 2015-01-22.
 */
public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> m_fragments;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.m_fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return m_fragments.get(position);
    }

    @Override
    public int getCount() {
        return m_fragments.size();
    }
}

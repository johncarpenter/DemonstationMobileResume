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

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Preconditions;
import com.twolinessoftware.android.mobileresume.R;

import butterknife.ButterKnife;

/**
 * Provides a shell fragment where a resource is passed in.
 */
public class BasicImageFragment extends Fragment {

    private static final String EXT_RESOURCE = "EXT_RESOURCE";

    private int m_resourceId;

    public static BasicImageFragment newInstance(int layoutResourceId) {
        BasicImageFragment fragment = new BasicImageFragment();
        Bundle args = new Bundle();
        args.putInt(EXT_RESOURCE, layoutResourceId);
        fragment.setArguments(args);
        return fragment;
    }

    public BasicImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            m_resourceId = getArguments().getInt(EXT_RESOURCE);

            // @TODO check m_resourceId is a valid layout
        }else{
            throw new IllegalArgumentException("newInstance(resourceId) needs to be called to launch fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Preconditions.checkArgument(m_resourceId != 0);
        return inflater.inflate(m_resourceId, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.inject(this, getView());

    }

}

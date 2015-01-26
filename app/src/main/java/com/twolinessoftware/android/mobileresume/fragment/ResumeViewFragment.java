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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.twolinessoftware.android.mobileresume.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Provides a shell fragment where a resource is passed in.
 */
public class ResumeViewFragment extends Fragment {

    @InjectView(R.id.webview_resume_body)
    WebView m_webViewResumeBody;

    public static ResumeViewFragment newInstance() {
        ResumeViewFragment fragment = new ResumeViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ResumeViewFragment() {
        // Required empty public constructor

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.inject(this, getView());
        updateViews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resume, container, false);
    }


    private void updateViews() {
        // Method 2 of displaying html content.
        m_webViewResumeBody.loadUrl("file:///android_asset/resume.html");
    }
}

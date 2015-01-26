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

package com.twolinessoftware.android.mobileresume;

import com.squareup.otto.Bus;
import com.twolinessoftware.android.mobileresume.activity.MainActivity;
import com.twolinessoftware.android.mobileresume.fragment.NavigationDrawerFragment;
import com.twolinessoftware.android.mobileresume.util.PostFromAnyThreadBus;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        injects = {
                ResumeApplication.class,
                MainActivity.class,
                NavigationDrawerFragment.class
        }
)
public class CoreModule {

    @Singleton
    @Provides
    Bus providesOttoBus() {
        return new PostFromAnyThreadBus();
    }

}
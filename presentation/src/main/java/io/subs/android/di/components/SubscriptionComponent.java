/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.subs.android.di.components;

import dagger.Component;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.ActivityModule;
import io.subs.android.di.modules.SubscriptionModule;
import io.subs.android.views.screens.add_subscription.AddSubscriptionFragment;
import io.subs.android.views.screens.add_subscription.SubscriptionListFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, SubscriptionModule.class
}) public interface SubscriptionComponent extends ActivityComponent {
    void inject(SubscriptionListFragment subscriptionListFragment);

    void inject(AddSubscriptionFragment addSubscriptionFragment);
}

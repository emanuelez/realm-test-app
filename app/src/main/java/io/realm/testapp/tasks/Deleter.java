/*
 * Copyright 2014 Realm Inc.
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
package io.realm.testapp.tasks;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.testapp.models.MyObject;

public class Deleter implements Runnable {

    Context context;

    public Deleter(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Realm realm = Realm.getInstance(context);
        RealmResults<MyObject> myObjects = realm.allObjects(MyObject.class);
        realm.beginTransaction();
        myObjects.clear();
        realm.commitTransaction();
    }
}

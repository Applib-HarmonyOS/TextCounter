/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.premnirmal.textcounterdemo;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.github.premnirmal.textcounter.CounterView;
import com.github.premnirmal.textcounter.Formatter;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Sample app to test the textcounter library functionality.
 */
public class ParanormalSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_paranormal);
        final CounterView counterView =
                (CounterView) findComponentById(ResourceTable.Id_secondCounter);
        counterView.setAutoFormat(false);
        counterView.setFormatter(new Formatter() {
            @Override
            public String format(String prefix, String suffix, float value) {
                return prefix + NumberFormat.getNumberInstance(Locale.US).format(value) + suffix;
            }
        });
        counterView.setAutoStart(false);
        counterView.setStartValue(200f);
        counterView.setEndValue(1000f);
        // the amount the number increments at each time interval
        counterView.setIncrement(5f);
        // the time interval (ms) at which the text changes
        counterView.setTimeInterval(2);
        counterView.setPrefix("You have ");
        counterView.setSuffix(" points!");
        counterView.start();
    }
}
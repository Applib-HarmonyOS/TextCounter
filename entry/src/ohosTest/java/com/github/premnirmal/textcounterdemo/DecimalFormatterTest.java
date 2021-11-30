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

import com.github.premnirmal.textcounter.formatters.DecimalFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DecimalFormatterTest {
    private DecimalFormatter decimalFormatter;

    @Test
    public void testDecimalFormatter() {
        decimalFormatter = new DecimalFormatter();
        String m = decimalFormatter.format("decimal","formater",3f);
        assertEquals("decimal3.00formater",m);
    }

    @Test
    public void testDecimalFormatter1() {
        decimalFormatter = new DecimalFormatter();
        String m = decimalFormatter.format("Counter","view",3f);
        assertNotEquals("Counterview3",m);
    }

}
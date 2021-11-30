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

import com.github.premnirmal.textcounter.formatters.CommaSeparatedDecimalFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommaSeparatedDecimalFormatterTest {
    private CommaSeparatedDecimalFormatter commaSeparatedDecimalFormatter;

    @Test
    public void testCommaSeparatedDecimalFormatter() {
        commaSeparatedDecimalFormatter = new CommaSeparatedDecimalFormatter();
        String m = commaSeparatedDecimalFormatter.format("comma","formater",3f);
        assertEquals("comma3.00formater",m);
    }

    @Test
    public void testCommaSeparatedDecimalFormatter1() {
        commaSeparatedDecimalFormatter = new CommaSeparatedDecimalFormatter();
        String m = commaSeparatedDecimalFormatter.format("Counter","view",3f);
        assertNotEquals("Counterview3",m);
    }

}
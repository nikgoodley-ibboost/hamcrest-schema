/**
 * Copyright 2010 Wilfred Springer
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
package nl.flotsam.hamcrest.schema.relaxng;

import org.hamcrest.SelfDescribing;
import org.iso_relax.verifier.Verifier;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * The interface to be implemented by objects capable of invoking the {@link Verifier} in a proper way, knowing the type
 * of object passed in.
 *
 * @param <T> The type of object expected in {@link #validate(org.iso_relax.verifier.Verifier, Object)}.
 */
interface Validator<T> extends SelfDescribing {

    /**
     * Returns <code>true</code> if the type passed in is supported; returns <code>false</code> otherwise. (This allows
     * the matcher to pick the proper validator.)
     */
    boolean appliesTo(Class<?> type);

    /**
     * Validates the object against the schema, by calling the appropriate methods on the {@link Verifier}.
     */
    boolean validate(Verifier verifier, T verifiable) throws SAXException, IOException;

}

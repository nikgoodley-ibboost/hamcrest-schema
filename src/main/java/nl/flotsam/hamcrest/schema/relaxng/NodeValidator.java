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

import org.hamcrest.Description;
import org.iso_relax.verifier.Verifier;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * A {@link Validator} capable of dealing with {@link Node Nodes}.
 */
final class NodeValidator implements Validator<Node> {

    public boolean appliesTo(Class<?> type) {
        return Node.class.isAssignableFrom(type);
    }

    public boolean validate(Verifier verifier, Node verifiable) throws SAXException, IOException {
        return verifier.verify(verifiable);
    }

    public void describeTo(Description description) {
        description.appendText("instance of org.w3c.dom.Node");
    }

}

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
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** A matcher, validating various types of objects against a RelaxNG schema. */
public class RelaxNGMatcher extends TypeSafeDiagnosingMatcher<Object> {

    private final Schema schema;
    private final String id;
    private static final List<Validator> validators = new ArrayList<Validator>();

    static {
        validators.add(new FileValidator());
        validators.add(new NodeValidator());
        validators.add(new InputSourceValidator());
        validators.add(new ResourceValidator());
    }

    public RelaxNGMatcher(Schema schema, String id) {
        assert schema != null;
        assert id != null;
        this.schema = schema;
        this.id = id;
    }

    @Override
    protected boolean matchesSafely(Object verifiable, Description description) {
        try {
            Validator validator = selectValidator(verifiable.getClass());
            if (validator == null) {
                description.appendText("No validation for object of type ");
                description.appendText(verifiable.getClass().getName());
                description.appendList("; supports only ", ", ", "", validators);
                return false;
            } else {
                Verifier verifier = createVerifier(schema, description);
                return validator.validate(verifier, verifiable);
            }
        } catch (SAXException e) {
            throw new IllegalStateException("Failed to process node.", e);
        } catch (VerifierConfigurationException e) {
            throw new IllegalStateException("Failed to create verifier from " + schema);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read from input.", e);
        }
    }

    private Validator selectValidator(Class<?> type) {
        for (Validator validator : validators) {
            if (validator.appliesTo(type)) {
                return validator;
            }
        }
        return null;
    }

    public void describeTo(Description description) {
        description.appendText("conforming to " + id);
    }

    private Verifier createVerifier(Schema schema, final Description description) throws VerifierConfigurationException {
        Verifier verifier = schema.newVerifier();
        verifier.setErrorHandler(new ErrorHandler() {
            public void warning(SAXParseException exception) throws SAXException {
                // Not interested in warnings
            }

            public void error(SAXParseException exception) throws SAXException {
                appendException(exception, description);
            }

            public void fatalError(SAXParseException exception) throws SAXException {
                appendException(exception, description);
            }
        });
        return verifier;
    }

    private void appendException(SAXParseException exception, Description description) {
        description.appendText(exception.getMessage());
        if (exception.getLineNumber() != 0) {
            description
                    .appendText(" at line ")
                    .appendValue(exception.getLineNumber());

        }
        description.appendText("\n");
    }
}

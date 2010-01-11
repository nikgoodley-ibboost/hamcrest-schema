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

import org.apache.commons.io.IOUtils;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.VerifierFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

/**
 * A factory for different types of RelaxNG matchers, based on various schema sources. Note that <em>none</em> of the
 * matchers provided by this class are truly typesafe. Problem is that different representations of XML do not share a
 * common base class. An XML document might be represented by a String containing the actual text, a DOM Node, an
 * InputSource, a File, etc. Having various factory methods for type-safe matchers would have generated at least a
 * couple of methods per type, and they would all have to be called differently. (Not an attractive option.) <p/> So,
 * this is how you might use this class:</p>
 * <pre>
 * import nl.flotsam.hamcrest.RelaxNGMatchers.*;
 * ...
 * assertThat(new FileSystemResource(xmlLocation), isValidatedBy(new FileSystemResource(schemaLocation));
 * assertThat(xmlFile, isValidatedBy(schemaFile));
 * // etc.
 * ...
 * </pre>
 */
public final class RelaxNGMatchers {

    private RelaxNGMatchers() {
    }

    /**
     * Creates a RelaxNG matcher based on a {@link Schema} object. (XML Syntax)
     *
     * @param schema The {@link Schema} object.
     * @param id     A textual reference to the schema. (Can be whatever you like, as long as it uniquely identifies the
     *               schema. This is the reference that is printed as part of the message.)
     */
    public static TypeSafeDiagnosingMatcher<Object> isValidatedBy(Schema schema, String id) {
        return new RelaxNGMatcher(schema, id);
    }

    /**
     * Creates a RelaxNG matcher based on a {@link File} object pointing to a RelaxNG schema. (XML Syntax)
     */
    public static TypeSafeDiagnosingMatcher<Object> isValidatedBy(File file) {
        return isValidatedBy(new FileSystemResource(file));
    }

    /**
     * Creates a RelaxNG matcher based on a {@link Resource} object pointing to a RelaxNG schema. (XML Syntax)
     */
    public static TypeSafeDiagnosingMatcher<Object> isValidatedBy(Resource resource) {
        VerifierFactory factory = new com.sun.msv.verifier.jarv.TheFactoryImpl();
        InputStream in = null;
        try {
            URI uri = resource.getURI();
            in = resource.getInputStream();
            if (uri == null) {
                return isValidatedBy(factory.compileSchema(in), resource.toString());
            } else {
                return isValidatedBy(factory.compileSchema(in, resource.getURI().toASCIIString()), resource.getFilename());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to construct matcher", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

}

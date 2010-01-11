package nl.flotsam.hamcrest.schema.relaxng;

import org.hamcrest.Description;
import org.iso_relax.verifier.Verifier;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * A {@link Validator} for {@link Resource Resources}.
 */
class ResourceValidator implements Validator<Resource> {

    public boolean appliesTo(Class<?> type) {
        return Resource.class.isAssignableFrom(type);
    }

    public boolean validate(Verifier verifier, Resource verifiable) throws SAXException, IOException {
        InputSource source = new InputSource(verifiable.getInputStream());
        source.setSystemId(verifiable.getURI().toASCIIString());
        return new InputSourceValidator().validate(verifier, source);
    }

    public void describeTo(Description description) {
        description.appendText("instance of org.xml.sax.InputSource");
    }

}

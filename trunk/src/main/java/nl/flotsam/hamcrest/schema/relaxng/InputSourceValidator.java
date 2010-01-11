package nl.flotsam.hamcrest.schema.relaxng;

import org.hamcrest.Description;
import org.iso_relax.verifier.Verifier;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * A {@link Validator} capable of dealing with {@link InputSource InputSources}.
 */
final class InputSourceValidator implements Validator<InputSource> {

    public boolean appliesTo(Class<?> type) {
        return InputSource.class.isAssignableFrom(type);
    }

    public boolean validate(Verifier verifier, InputSource verifiable) throws SAXException, IOException {
        return verifier.verify(verifiable);
    }

    public void describeTo(Description description) {
        description.appendText("instance of org.xml.sax.InputSource");
    }

}

package nl.flotsam.hamcrest.schema.relaxng;

import org.hamcrest.Description;
import org.iso_relax.verifier.Verifier;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * An {@link Validator} capable of dealing with Files.
 */
final class FileValidator implements Validator<File> {

    public boolean appliesTo(Class<?> type) {
        return type == File.class;
    }

    public boolean validate(Verifier verifier, File verifiable) throws SAXException, IOException {
        return verifier.verify(verifiable);
    }

    public void describeTo(Description description) {
        description.appendText("an instance of java.io.File");
    }

}

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

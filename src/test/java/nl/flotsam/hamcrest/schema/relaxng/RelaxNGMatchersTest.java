package nl.flotsam.hamcrest.schema.relaxng;

import nl.flotsam.hamcrest.schema.relaxng.RelaxNGMatcher;
import nl.flotsam.hamcrest.schema.relaxng.RelaxNGMatchers;
import org.hamcrest.StringDescription;
import org.iso_relax.verifier.Schema;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(MockitoJUnitRunner.class)
public class RelaxNGMatchersTest {

    @Mock
    private Schema schema;

    @Test
    public void shouldWorkCorrectly() {
        Resource schema = new ClassPathResource("/sample.rng");
        Resource document = new ClassPathResource("/valid-sample.xml");
        assertThat(document, RelaxNGMatchers.isValidatedBy(schema));
    }

    @Test
    public void shouldDetectErrors() {
        Resource schema = new ClassPathResource("/sample.rng");
        Resource document = new ClassPathResource("/invalid-sample.xml");
        assertThat(document, not(RelaxNGMatchers.isValidatedBy(schema)));
    }

    @Test
    public void shouldReportMissingValidators() {
        RelaxNGMatcher matcher = new RelaxNGMatcher(schema, "foo.rng");
        StringDescription description = new StringDescription();
        assertThat(matcher.matchesSafely(new Date(), description), is(false));
        assertThat(description.toString(), is("No validation for object of type java.util.Date; "
                + "supports only an instance of java.io.File, "
                + "instance of org.w3c.dom.Node, "
                + "instance of org.xml.sax.InputSource, "
                + "instance of org.xml.sax.InputSource"));
    }

}

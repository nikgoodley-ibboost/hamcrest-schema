# Hamcrest XML Schema Matchers #

A project providing matchers for validating your XML documents using Hamcrest. It currently provides a collection of RelaxNG based matchers, but more matchers are coming.

&lt;wiki:gadget url="http://www.ohloh.net/p/480431/widgets/project\_users\_logo.xml" height="43" border="0"/&gt;

## Usage ##

This is how you use Hamcrest Schema from JUnit:

```
File xml = new File(...); // An XML file
File schema = new File(...); // A RelaxNG schema file 
assertThat(xml, isValidatedBy(schema));
```

If the XML file is a valid instance of the document type defined by the schema file, then this code will just silently continue. However, if the XML file is _not_ an instance of the document type defined by the schema, then you could get something like this:

```
java.lang.AssertionError: 
Expected: conforming to sample.rng
     but: tag name "foo" is not allowed. Possible tag names are: <bar> at line <2>

	at org.hamcrest.MatcherAssert.assertThat(MatcherAssert.java:20)
```

(Note that you will _only_ get the details about the actual violation if you use Hamcrest 1.2's MatcherAssert class. If you use JUnit's Assert class, will not get this level of detail.)

Hamcrest Schema also allows you to validate a DOM node, an InputSource or a Spring Resource. Similarly, the schema itself can also be identified by a Spring Resource. (Which makes things considerably easier to use in a Maven project, when using several IDEs.)

## Limitations ##

Current version supports RelaxNG (XML syntax) only. Consider that an act of rebellion against XML Schema.

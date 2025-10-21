Copyright 2013 Nicolas Morel

Copyright 2025 Murata ID Solutions.

This software has been modified for compatibility with newer versions of jackson-annotations, 
with gratitude to Nicolas Morel for the original development and to Europace for the fork.

Changes are:

- Use Gradle as build system
- Upgrade dependencies: GWT, Jackson Annotations, Javapoet
- Upgrade jackson-annotations version and GWT source overrides in `src/main/resources/com/github/nmorel/gwtjackson/super/com/fasterxml/jackson/annotation`

To keep in sync with future versions of jackson-annotations:

- Upgrade the jackson-annotations version in `build.gradle.kts`
- If some classes are not compatible with GWT, either exclude them in
  `src/main/resources/com/fasterxml/jackson/annotation/JacksonAnnotation.gwt.xml`
  (if not needed) or copy them in
  `src/main/resources/com/github/nmorel/gwtjackson/super/com/fasterxml/jackson/annotation`
  and modify them to make them compatible
- Build with `gradlew build`
  - This will build the main `gwt-jackson` module. Examples, extensions and unit tests unfortunately are not supported by this fork at the moment.

Original README below:

gwt-jackson [![Build Status](https://travis-ci.org/nmorel/gwt-jackson.svg?branch=master)](https://travis-ci.org/nmorel/gwt-jackson)
=====
gwt-jackson is a JSON parser for [GWT](http://www.gwtproject.org/). It uses [Jackson 2.x annotations](https://github.com/FasterXML/jackson-annotations) to customize the serialization/deserialization process.

Most of the [Jackson 2.x annotations](https://github.com/FasterXML/jackson-annotations) are supported. You can find an up-to-date list [here](https://github.com/nmorel/gwt-jackson/wiki/Jackson-annotations-support).
You can also find a lot of use cases in the [tests](gwt-jackson/src/test/java/com/github/nmorel/gwtjackson).

Jackson 1.x annotations (`org.codehaus.jackson.*`) are not supported.

Check the [wiki](https://github.com/nmorel/gwt-jackson/wiki) for more informations.

Quick start
-------------
Add `<inherits name="com.github.nmorel.gwtjackson.GwtJackson" />` to your module descriptor XML file.

Then just create an interface extending `ObjectReader`, `ObjectWriter` or `ObjectMapper` if you want to read JSON, write an object to JSON or both.

Here's an example without annotation :

```java
public class TestEntryPoint implements EntryPoint {

    public static interface PersonMapper extends ObjectMapper<Person> {}

    public static class Person {

        private String firstName;
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    @Override
    public void onModuleLoad() {
        PersonMapper mapper = GWT.create( PersonMapper.class );

        String json = mapper.write( new Person( "John", "Doe" ) );
        GWT.log( json ); // > {"firstName":"John","lastName":"Doe"}

        Person person = mapper.read( json );
        GWT.log( person.getFirstName() + " " + person.getLastName() ); // > John Doe
    }
}
```

And if you want to make your class immutable for example, you can add some Jackson annotations :

```java
public class TestEntryPoint implements EntryPoint {

    public static interface PersonMapper extends ObjectMapper<Person> {}

    public static class Person {

        private final String firstName;
        private final String lastName;

        @JsonCreator
        public Person( @JsonProperty( "firstName" ) String firstName,
                       @JsonProperty( "lastName" ) String lastName ) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    @Override
    public void onModuleLoad() {
        PersonMapper mapper = GWT.create( PersonMapper.class );

        String json = mapper.write( new Person( "John", "Doe" ) );
        GWT.log( json ); // > {"firstName":"John","lastName":"Doe"}

        Person person = mapper.read( json );
        GWT.log( person.getFirstName() + " " + person.getLastName() ); // > John Doe
    }
}
```

With Maven
-------------

```xml
<dependency>
  <groupId>com.github.nmorel.gwtjackson</groupId>
  <artifactId>gwt-jackson</artifactId>
  <version>0.15.4</version>
  <scope>provided</scope>
</dependency>
```

You can also get maven snapshots using the following repository :

```xml
<repository>
  <id>oss-sonatype-snapshots</id>
  <url>https://oss.sonatype.org/content/repositories/snapshots</url>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
</repository>
```

Without Maven
-------------
In addition of gwt-jackson jar you can find [here](https://github.com/nmorel/gwt-jackson/releases), you also need
- com.fasterxml.jackson.core:jackson-annotations
- com.fasterxml.jackson.core:jackson-annotations sources
- com.squareup:javapoet 1.x for the GWT compilation only

Server communication
-------------
If you need to communicate with your server using REST/Json payload, you can check these framework which integrates gwt-jackson :
- [GWTP Rest Dispatch](http://dev.arcbees.com/gwtp/communication/index.html). Check the [example](https://github.com/nmorel/gwt-jackson/tree/master/examples/gwtp).
- [RestyGWT](http://resty-gwt.github.io/). Check the [example](https://github.com/nmorel/gwt-jackson/tree/master/examples/restygwt).
- [Requestor](http://reinert.io/requestor/latest/). Check the [example](https://github.com/nmorel/gwt-jackson/tree/master/examples/requestor).
- [gwt-jackson-rest](https://github.com/nmorel/gwt-jackson-rest). Check the [example](https://github.com/nmorel/gwt-jackson-rest/tree/master/examples/simple).
- [GWT RequestBuilder](http://www.gwtproject.org/javadoc/latest/com/google/gwt/http/client/RequestBuilder.html). Check the [example](https://github.com/nmorel/gwt-jackson/tree/master/examples/hello).

Copyright and license
-------------

Copyright 2014 Nicolas Morel under the [Apache 2.0 license](LICENSE).


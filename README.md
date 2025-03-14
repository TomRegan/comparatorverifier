# comparatorverifier

ComparatorVerifier is intended for use in tests to validate the contract for Comparator is met.

## Getting Started

### With Maven

```xml
<dependency>
    <groupId>co.mp</groupId>
    <artifactId>comparatorverifier</artifactId>
    <version>x.y.x</version>
    <scope>test</scope>
</dependency>
```

### With JUnit

```java
import co.mp.ComparatorVerifier;

@Test
void comparatorContract() {
    ComparatorVerifier.forComparator(Foo.class).verify();
}
```

### Further Documentation

Add a link to your project wiki or site.

## Developing

### Build Instructions

```bash
mvn verify
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

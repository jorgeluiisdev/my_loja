# Generate maven scripts

``` bash
mvn -N io.takari:maven:wrapper
```

# Alternatively

### You may also run the following command:

``` bash
mvn -N io.takari:maven:wrapper -Dmaven=${ALT_VER}
```

### Where 'ALT_VER' is your target version number, ex:

```
mvn -N io.takari:maven:wrapper -Dmaven=3.6.3
```

#### Which is the version used in this project  
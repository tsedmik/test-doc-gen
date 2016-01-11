# test-doc-gen
test-doc-gen is a tool which converts JavaDoc to Documentation of tests in various data formats (HTML, XML, ...).

Currently we support the following use cases:
* GitHub --> HTML

## How to run

`mvn clean package site -Dgithub-api-resource=...`

In `github-api-resource` specify path to Java classes. Use GitHub API - `https://api.github.com/repos/${user}/${repository}/contents/${path-to-java-classes}`

After the build is successfully completed, search for `target/javadoc.html` file. In the file are stored converted JavaDocs from all Java classes in defined location.

weka-clj
===

A set of weka helper functions for clojure.

[![Codeship Status](https://codeship.com/projects/c5a62ca0-6402-0134-a722-7ac11de88606/status?branch=master)](https://www.codeship.io/projects/175508) [![Clojars Project](https://img.shields.io/clojars/v/weka-clj.svg)](https://clojars.org/weka-clj)

[API](https://github.com/comamitc/weka-clj)

# Test

```sh
$> lein [auto] test
```

**note:** `auto` is optional.

# Releasing

Increment the `project.clj` version according to `SEMVER` and then:

```sh
$> lein codox
$> git tag v{version}
&> git push origin master --tags
&> lein deploy clojars
```

# License

The MIT License (MIT)

Copyright (c) 2016 Mitch Comardo and contributors

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

# java-base64

This is a base64 implementation purely in Java. There is a base64 class
that provides static methods for encoding and decoding. There is also
a simple client that has similar functionality to GNU base64.

## Performance

This implementation seems pretty robust AFAIK but it lacks performance
probably due to insufficient bit comparisons. If you want to use
base64 in one of your projects I encourage you to use Apache commons
codec. If your goal is to learn how base64 works then I hope that my
code is well written! So, in that case have a nice reading :)

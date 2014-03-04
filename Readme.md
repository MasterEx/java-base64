# java-base64

This is a base64 implementation purely in Java. There is a base64 class
that provides static methods for encoding and decoding. There is also
a simple client that has similar functionality to GNU base64.

## Performance

This implementation seems pretty robust and performs a little slower
compared to Apache commons. However if you want to use
base64 in one of your projects I encourage you to use Apache commons
codec as it both performs better and is more well tested.
If your goal is to learn how base64 works then I hope that my
code is well written and it will help you better understand base64! 
So, in that case have a nice reading :)

Note that the base64 client included in the package is pretty naive and
doesn't perform well especially due to stream IO. I do not intend to rewrite
it as it is out of this projects scope but fill free to send me a better
implementation. If it is actually better I might include it instead of
the current :)

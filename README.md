audioboo-java-oauth
===================

An example command line tool using OAuth to talk to audioboom.com

Depends on [Signpost 1.2](https://code.google.com/p/oauth-signpost/) and the Commons Codec

You can compile & run using: 

```
javac -cp 'lib/*' src/BooOAuth.java
java -cp 'lib/*:src' BooOAuth <api_key> <api_secret>
```

The project also includes BooOAuthUpload, which is a simple demonstration of uploading a boo:
```
javac -cp 'lib/*' src/BooOAuthUpload.java
java -cp 'lib/*:src' BooOAuthUpload /path/to/an_audio_file
```

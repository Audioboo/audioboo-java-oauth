// You'll need Signpost 1.2 (https://code.google.com/p/oauth-signpost/) and the Apache Components in your classpath
import java.io.File;
import java.net.URL;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


class BooOAuthUpload {
  public static void main(String[] args) throws Exception {

    if (args.length!=1) {
      throw new IllegalArgumentException("Invoke with a file path to upload");
    }

    // Obtain your api key & secret from https://audioboom.com/account/services
    String apiKey = "...";
    String apiSecret = "...";
    // You'll also need an authorized access token.  Try running the 'BooOAuth' java program to obtain one.
    String accessToken = "...";
    String accessSecret = "...";

    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(apiKey, apiSecret);
    consumer.setTokenWithSecret(accessToken, accessSecret);

    File f = new File(args[0]);
    HttpPost post = new HttpPost("https://api.audioboom.com/account/audio_clips");
    MultipartEntity entity = new MultipartEntity();
    entity.addPart("audio_clip[title]", new StringBody("my first boo"));
    entity.addPart("audio_clip[uploaded_data]", new FileBody(f));
    post.setEntity(entity);

    consumer.sign(post);

    System.out.println("Uploading...");
    HttpClient client = new DefaultHttpClient();
    HttpResponse response = client.execute(post);
    HttpEntity responseEntity = response.getEntity();
    System.out.println("Got a response - ");
    System.out.println(EntityUtils.toString(responseEntity));
  }
}

// You'll need Signpost 1.2 (https://code.google.com/p/oauth-signpost/) and the Commons Codec in your classpath
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

class BooOAuth {
  public static void main(String[] args) throws Exception {
    // Obtain your api key & secret from https://audioboo.fm/account/services
    String apiKey = "...";
    String apiSecret = "...";

    OAuthConsumer consumer = new DefaultOAuthConsumer(apiKey, apiSecret);
    OAuthProvider provider = new DefaultOAuthProvider(
        "http://api.audioboo.fm/oauth/request_token",
        "http://api.audioboo.fm/oauth/access_token",
        "http://api.audioboo.fm/oauth/authorize");

    System.out.println("Fetching request token from Audioboo...");

    // we do not support callbacks, thus pass OOB
    String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);

    System.out.println("Request token: " + consumer.getToken());
    System.out.println("Token secret: " + consumer.getTokenSecret());

    System.out.println("Now visit:\n" + authUrl + "\n... and grant this app authorization");
    System.out.println("Enter the PIN code and hit ENTER when you're done:");

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String pin = br.readLine();

    System.out.println("Fetching access token from Audioboo...");

    provider.retrieveAccessToken(consumer, pin);
    System.out.println("Access token: " + consumer.getToken());
    System.out.println("Token secret: " + consumer.getTokenSecret());

    // Now that we have an access token we can use it to make authenticated requests to the API.  For example, fetching account details from http://api.audioboo.fm/account 

    System.out.println("Fetching account details...");
    URL url = new URL("http://api.audioboo.fm/account");
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    consumer.sign(request);
    request.connect();

    BufferedReader responseBody = new BufferedReader(new InputStreamReader(request.getInputStream()));
    String responseLine;
    while ((responseLine = responseBody.readLine()) != null)
      System.out.println(responseLine);
  }
}

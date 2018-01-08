package InventarioApp.socket.http;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class HttpRequestImplTest {

  private static final String REQUEST_1 = "GET /hotelInfo?hotelId=25622 HTTP/1.1\n"
      + "Host: localhost:7000\n"
      + "Connection: keep-alive\n"
      + "Cache-Control: max-age=0\n"
      + "Upgrade-Insecure-Requests: 1\n"
      + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36\n"
      + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n"
      + "Accept-Encoding: gzip, deflate, br\n"
      + "Accept-Language: es-419,es;q=0.8,en-US;q=0.6,en;q=0.4\n"
      + "Cookie: username-localhost-8888=\"2|1:0|10:1508790998|23:username-localhost-8888|44:YTZlYzY3NDNjZTU2NDhlNWIzN2MwMDU3NWZlMjU5YjE=|46800c1aec543766674d01c4f7ec29caf89a750bba8e2fc82aa0125c82a365f2\"; _xsrf=2|fb20ef07|9962e7331db5197ef7f65d36629b15f7|1508790998\n";

  @Test
  public void retrieveHeaders_success() throws Exception {
    HttpRequestImpl httpRequest = new HttpRequestImpl(80, REQUEST_1);

    Assert.assertEquals("HTTP/1.1", httpRequest.getProtocol());
    Assert.assertEquals("GET", httpRequest.getMethod());
    Assert.assertEquals(80, httpRequest.getServerPort());
    Assert.assertEquals("localhost:7000", httpRequest.getHost());
    Assert.assertEquals("/hotelInfo", httpRequest.getContextPath());
    Assert.assertEquals("keep-alive", httpRequest.getHeader("Connection"));
    Assert.assertEquals("es-419,es;q=0.8,en-US;q=0.6,en;q=0.4", httpRequest.getHeader("Accept-Language"));
    Assert.assertEquals("hotelId=25622", httpRequest.getQueryString());
    Assert.assertEquals("25622", httpRequest.getParameter("hotelId"));
  }
}
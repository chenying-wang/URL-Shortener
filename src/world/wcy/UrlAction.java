package world.wcy;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UrlAction extends ActionSupport {

    private static String urlDefaultProtocol = "http";
    private static final String urlProtocolPattern = "://";

    private String url;
    private String id;

    @Autowired
    private UrlService service;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String shorten() {
        if(url == null) return NONE;
        checkAndCreateUrlProtocol();

        boolean success;
        String shortUrl = null;
        try {
            shortUrl = service.shorten(url);
            success = shortUrl != null;
        } catch (InvalidUrlException e) {
            success = false;
        }
        sendUrlAndStatus(shortUrl, success);
        return NONE;
    }

    public String restore() {
        if(url == null) return NONE;
        checkAndCreateUrlProtocol();

        String longUrl = service.restore(url);
        boolean success = longUrl != null;
        sendUrlAndStatus(longUrl, success);
        return NONE;
    }

    public String redirect() {
        HttpServletResponse response = ServletActionContext.getResponse();

        String longUrl = service.redirect(id);
        if (longUrl == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setHeader("Connection", "close");
        }

        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", longUrl);
        response.setHeader("Connection", "close");
        return NONE;
    }

    private void checkAndCreateUrlProtocol() {
        if(!url.contains(urlProtocolPattern)) {
            url = urlDefaultProtocol + urlProtocolPattern + url;
        }
    }

    private void sendUrlAndStatus(String url, boolean status) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            PrintWriter out = response.getWriter();
            out.print("{\"url\":\"" + url + "\",\"status\":\"" + status + "\"}");
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

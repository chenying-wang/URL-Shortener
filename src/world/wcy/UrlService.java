package world.wcy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.ArrayList;

@Transactional
public class UrlService {

    private static final int SUFFIX_LENGTH = 5;

    private static ArrayList<Character> CHAR_POOL;
    private static int CHAR_POOL_LENGTH;

    static {
        CHAR_POOL = new ArrayList<>();
        for(Character i = '0'; i <= '9'; ++i) {
            CHAR_POOL.add(i);
        }
        for(Character i = 'A'; i <= 'Z'; ++i) {
            CHAR_POOL.add(i);
        }

        for(Character i = 'a'; i <= 'z'; ++i) {
            CHAR_POOL.add(i);
        }
        CHAR_POOL_LENGTH = CHAR_POOL.size();
    }

    @Autowired
    private UrlDao dao;
    private String urlRoot;

    public String getUrlRoot() {
        return urlRoot;
    }

    public void setUrlRoot(String urlRoot) {
        this.urlRoot = urlRoot;
    }

    public String shorten(String longUrl) throws InvalidUrlException {

        if (!checkUrlValidity(longUrl)) {
            throw new InvalidUrlException(longUrl + " is invalid");
        }

        String shortUrl = dao.retrieveShortUrl(longUrl);
        if (shortUrl == null) {
            shortUrl = getRandomShortUrl(SUFFIX_LENGTH);
            dao.create(longUrl, shortUrl);
        }
        return shortUrl;
    }

    public String restore(String shortUrl) {
        return dao.retrieveLongUrl(shortUrl);
    }

    public String redirect(String id) {
        return restore(urlRoot + id);
    }

    private boolean checkUrlValidity (String url) {

        boolean isValid;
        try {
            (new URL(url)).openStream();
            isValid = true;
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

    private String getRandomShortUrl(int length) {
        StringBuilder randomString = new StringBuilder(urlRoot);
        for(int i = 0, rand; i < length; ++i) {
            rand = (int) Math.floor(Math.random() * CHAR_POOL_LENGTH);
            randomString.append(CHAR_POOL.get(rand));
        }
        return randomString.toString();
    }
}

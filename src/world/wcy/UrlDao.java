package world.wcy;

public interface UrlDao {

    void create(String longUrl, String shortUrl);

    String retrieveLongUrl(String shortUrl);

    String retrieveShortUrl(String longUrl);

    void delete(String longUrl);

}

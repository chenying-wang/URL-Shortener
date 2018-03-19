package world.wcy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

public class UrlDaoImpl implements UrlDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void create(String longUrl, String shortUrl) {
        UrlMap map = new UrlMap(longUrl, shortUrl);
        hibernateTemplate.save(map);
    }

    @Override
    public String retrieveLongUrl(String shortUrl) {
        UrlMap map = new UrlMap();
        map.setShortUrl(shortUrl);
        List<UrlMap> list = hibernateTemplate.findByExample(map);

        if(list.size() == 0) return null;
        String longUrl = list.get(0).getLongUrl();
        return longUrl;
    }

    @Override
    public String retrieveShortUrl(String longUrl) {
        UrlMap map = new UrlMap();
        map.setLongUrl(longUrl);
        List<UrlMap> list = hibernateTemplate.findByExample(map);

        if(list.size() == 0) return null;
        String shortUrl = null;
        int i = 0;
        for (UrlMap m : list) {
            if (i == 0) {
                shortUrl = m.getShortUrl();
            } else {
                hibernateTemplate.delete(m);
            }
            ++i;
        }

        return shortUrl;
    }

    @Override
    public void delete(String longUrl) {
        UrlMap map = new UrlMap();
        map.setLongUrl(longUrl);
        hibernateTemplate.delete(map);
    }

}

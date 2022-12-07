package run.ikaros.server.tripartite.dmhy;

import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;
import run.ikaros.server.tripartite.dmhy.enums.DmhyCategory;
import run.ikaros.server.tripartite.dmhy.model.DmhyRssItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Proxy;
import java.util.List;

/**
 * @see <a href="https://dmhy.org/">动漫花园</a>
 */
public interface DmhyClient {

    interface Api {
        String RSS_BAS_URL = "https://dmhy.org/topics/rss/rss.xml";
    }

    /**
     * @param keyword 关键词 多个用空格隔开
     * @param category 动漫花园的分类
     * @return RSS项列表
     * @see DmhyCategory
     */
    @Retryable
    List<DmhyRssItem> findRssItems(@Nonnull String keyword, @Nullable DmhyCategory category);

    void setProxy(Proxy proxy);
}
package fr.osb.deployapi.repository;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Abstract layer for repository managers.<br>
 * Provides REST exchange utility methods.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public abstract class AbstractRepositoryManager implements RepositoryManager {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepositoryManager.class);

    /**
     * REST template.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Gets the result from the given REST API {@code url}.
     *
     * @param url
     *         The REST API URL.
     * @param resultType
     *         The result type corresponding class.
     * @param <T>
     *         The result type.
     * @return The result from the given REST API {@code url}.
     */
    protected final <T> T get(final String url, final Class<T> resultType) {

        return get(url, resultType, (HttpEntity) null);
    }

    /**
     * Gets the result from the given REST API {@code url}.
     *
     * @param url
     *         The REST API URL.
     * @param resultType
     *         The result type corresponding class.
     * @param httpEntity
     *         The HTTP entity that may contain HTTP headers.
     * @param <T>
     *         The result type.
     * @return The result from the given REST API {@code url}.
     */
    protected final <T> T get(final String url, final Class<T> resultType, final HttpEntity httpEntity) {

        return get(url, resultType, httpEntity, (Object[]) null);
    }

    /**
     * Gets the result from the given REST API {@code url}.
     *
     * @param url
     *         The REST API URL.
     * @param resultType
     *         The result type corresponding class.
     * @param urlVariables
     *         The URL variable(s).
     * @param <T>
     *         The result type.
     * @return The result from the given REST API {@code url}.
     */
    protected final <T> T get(final String url, final Class<T> resultType, final Object... urlVariables) {

        return get(url, resultType, null, urlVariables);
    }

    /**
     * Gets the result from the given REST API {@code url}.
     *
     * @param url
     *         The REST API URL.
     * @param resultType
     *         The result type corresponding class.
     * @param httpEntity
     *         The HTTP entity that may contain HTTP headers.
     * @param urlVariables
     *         The URL variable(s).
     * @param <T>
     *         The result type.
     * @return The result from the given REST API {@code url}.
     */
    protected final <T> T get(final String url, final Class<T> resultType, final HttpEntity httpEntity, final Object... urlVariables) {

        LOGGER.debug("REST GET exchange: {} ; URL variables: {}", url, ArrayUtils.toString(urlVariables));

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity == null ? HttpEntity.EMPTY : httpEntity,
                resultType,
                ArrayUtils.nullToEmpty(urlVariables)).getBody();
    }

    /**
     * Gets the result from the given REST API {@code url}.
     *
     * @param url
     *         The REST API URL.
     * @param resultType
     *         The result type corresponding class.
     * @param httpEntity
     *         The HTTP entity that may contain HTTP headers.
     * @param urlVariables
     *         The URL variable(s).
     * @param <T>
     *         The result type.
     * @return The result from the given REST API {@code url}.
     */
    protected final <T> T get(final String url, final Class<T> resultType, final HttpEntity httpEntity, final Map<String, String> urlVariables) {

        LOGGER.debug("REST GET exchange: {} ; URL variables: {}", url, urlVariables);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity == null ? HttpEntity.EMPTY : httpEntity,
                resultType,
                MapUtils.emptyIfNull(urlVariables)).getBody();
    }

}


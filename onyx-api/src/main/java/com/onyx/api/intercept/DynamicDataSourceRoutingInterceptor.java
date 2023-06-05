package com.onyx.api.intercept;

import com.onyx.commons.beans.BasketballPlayerStatisticsDataStoreContextContainer;
import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.sql.DataSource;
import java.util.Map;

import static com.onyx.commons.util.Constants.TARGET_DATA_STORE_DESTINATION;

@Component
@RequiredArgsConstructor
public class DynamicDataSourceRoutingInterceptor implements HandlerInterceptor {

    private final Map<BasketballPlayerStatisticsDataStore, DataSource> datastores;

    /**
     * Interception point before the execution of a handler. Called after
     * HandlerMapping determined an appropriate handler object, but before
     * HandlerAdapter invokes the handler.
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can decide to abort the execution chain,
     * typically sending an HTTP error or writing a custom response.
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link AsyncHandlerInterceptor}.
     * <p>The default implementation returns {@code true}.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * next interceptor or the handler itself. Else, DispatcherServlet assumes
     * that this interceptor has already dealt with the response itself.
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        val header = request.getHeader(TARGET_DATA_STORE_DESTINATION);
        if (StringUtils.isNotBlank(header)) {
            val dataStore = BasketballPlayerStatisticsDataStore.getFromFmtAgnosticString(header);
            if (dataStore != null && datastores.containsKey(dataStore))
                BasketballPlayerStatisticsDataStoreContextContainer.setContext(dataStore);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

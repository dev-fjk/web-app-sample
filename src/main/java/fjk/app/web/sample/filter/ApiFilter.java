package fjk.app.web.sample.filter;

import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/** リクエストIDを生成してMDCに設定し、アクセスログを出力するFilter */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiFilter implements Filter {

  private static final String REQUEST_ID_KEY = "requestId";
  private static final String REQUEST_START_TIME_KEY = "requestStartTime";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // リクエストIDを生成してMDCに設定
    String requestId = UUID.randomUUID().toString();
    MDC.put(REQUEST_ID_KEY, requestId);
    long startTime = System.currentTimeMillis();
    MDC.put(REQUEST_START_TIME_KEY, String.valueOf(startTime));

    try {
      // リクエストボディを読み取るためにラップ
      ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
      ContentCachingResponseWrapper wrappedResponse =
          new ContentCachingResponseWrapper(httpResponse);

      chain.doFilter(wrappedRequest, wrappedResponse);

      // レスポンスタイムを計算
      long endTime = System.currentTimeMillis();
      long responseTime = endTime - startTime;

      // アクセスログを出力
      logAccess(httpRequest, wrappedResponse, responseTime);

      // レスポンスボディをコピー
      wrappedResponse.copyBodyToResponse();
    } finally {
      // MDCをクリア
      MDC.clear();
    }
  }

  /**
   * アクセスログを出力
   *
   * @param request HTTPリクエスト
   * @param response HTTPレスポンス
   * @param responseTime レスポンスタイム（ミリ秒）
   */
  private void logAccess(
      HttpServletRequest request, ContentCachingResponseWrapper response, long responseTime) {
    String method = request.getMethod();
    String uri = request.getRequestURI();
    String queryString = request.getQueryString();
    String fullUri = queryString != null ? uri + "?" + queryString : uri;
    int status = response.getStatus();
    String remoteAddr = request.getRemoteAddr();

    log.info(
        "ACCESS_LOG method={} uri={} status={} responseTime={}ms remoteAddr={}",
        method,
        fullUri,
        status,
        responseTime,
        remoteAddr);
  }
}

package fjk.app.web.sample.aop;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Controllerのリクエスト/レスポンスとレスポンスタイムをログ出力するAOPアスペクト */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

  private final ObjectMapper objectMapper;

  /**
   * Controllerのメソッド実行前後にログを出力
   *
   * @param joinPoint ジョインポイント
   * @return メソッドの戻り値
   * @throws Throwable 例外
   */
  @Around("execution(* fjk.app.web.sample.controller..*.*(..))")
  public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();

    // リクエスト情報を取得
    HttpServletRequest request = getHttpServletRequest();
    String method = request != null ? request.getMethod() : "UNKNOWN";
    String uri = request != null ? request.getRequestURI() : "UNKNOWN";
    String queryString = request != null ? request.getQueryString() : null;
    String fullUri = queryString != null ? uri + "?" + queryString : uri;

    // メソッド情報
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();

    // リクエストパラメータをログ出力
    logRequest(method, fullUri, className, methodName, args);

    Object result = null;
    Throwable exception = null;

    try {
      result = joinPoint.proceed();
      return result;
    } catch (Throwable t) {
      exception = t;
      throw t;
    } finally {
      long endTime = System.currentTimeMillis();
      long responseTime = endTime - startTime;

      // レスポンス情報をログ出力
      logResponse(className, methodName, result, exception, responseTime);
    }
  }

  /**
   * リクエスト情報をログ出力
   *
   * @param method HTTPメソッド
   * @param uri URI
   * @param className クラス名
   * @param methodName メソッド名
   * @param args 引数
   */
  private void logRequest(
      String method, String uri, String className, String methodName, Object[] args) {
    try {
      // GETリクエストの場合はargsを出力しない（クエリパラメータはURIに含まれているため）
      if ("GET".equalsIgnoreCase(method)) {
        log.info(
            "REQUEST_LOG method={} uri={} controller={} method={}",
            method,
            uri,
            className,
            methodName);
        return;
      }

      // 引数をJSON形式で出力（機密情報を含む可能性があるため、簡易的に）
      String argsJson =
          Arrays.stream(args)
              .map(
                  arg -> {
                    if (arg == null) {
                      return "null";
                    }
                    // HttpServletRequestやBindingResultなどは除外
                    if (arg instanceof HttpServletRequest
                        || arg.getClass().getName().contains("BindingResult")) {
                      return arg.getClass().getSimpleName();
                    }
                    try {
                      return objectMapper.writeValueAsString(arg);
                    } catch (Exception e) {
                      return arg.toString();
                    }
                  })
              .collect(Collectors.joining(", ", "[", "]"));

      log.info(
          "REQUEST_LOG method={} uri={} controller={} method={} args={}",
          method,
          uri,
          className,
          methodName,
          argsJson);
    } catch (Exception e) {
      log.warn("Failed to log request information", e);
    }
  }

  /**
   * レスポンス情報をログ出力
   *
   * @param className クラス名
   * @param methodName メソッド名
   * @param result 戻り値
   * @param exception 例外（発生した場合）
   * @param responseTime レスポンスタイム（ミリ秒）
   */
  private void logResponse(
      String className, String methodName, Object result, Throwable exception, long responseTime) {
    try {
      if (exception != null) {
        log.error(
            "RESPONSE_LOG controller={} method={} exception={} responseTime={}ms",
            className,
            methodName,
            exception.getClass().getSimpleName(),
            responseTime,
            exception);
      } else {
        String resultJson = "null";
        if (result != null) {
          // ResponseEntityの場合はbodyを取得
          if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();
            if (body != null) {
              try {
                resultJson = objectMapper.writeValueAsString(body);
              } catch (Exception e) {
                resultJson = body.toString();
              }
            }
          } else {
            try {
              resultJson = objectMapper.writeValueAsString(result);
            } catch (Exception e) {
              resultJson = result.toString();
            }
          }
        }

        log.info(
            "RESPONSE_LOG controller={} method={} response={} responseTime={}ms",
            className,
            methodName,
            resultJson,
            responseTime);
      }
    } catch (Exception e) {
      log.warn("Failed to log response information", e);
    }
  }

  /**
   * HttpServletRequestを取得
   *
   * @return HttpServletRequest
   */
  private HttpServletRequest getHttpServletRequest() {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return attributes != null ? attributes.getRequest() : null;
  }
}

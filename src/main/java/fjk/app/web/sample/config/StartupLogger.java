package fjk.app.web.sample.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * アプリケーション起動時にURLをログ出力するコンポーネント
 *
 * <p>Spring Bootアプリケーションの起動完了時に、 アクセス可能なURLをわかりやすく表示します。
 */
@Slf4j
@Component
public class StartupLogger implements ApplicationListener<ApplicationReadyEvent> {

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    final Environment env = event.getApplicationContext().getEnvironment();
    final String protocol = getProtocol(env);
    final String serverPort = env.getProperty("server.port", "8080");
    final String contextPath = env.getProperty("server.servlet.context-path", "");
    final String hostAddress = getHostAddress();
    final String swaggerPath = env.getProperty("springdoc.swagger-ui.path", "/swagger-ui.html");
    final String apiDocsPath = env.getProperty("springdoc.api-docs.path", "/v3/api-docs");

    logStartupInfo(protocol, serverPort, contextPath, hostAddress, swaggerPath, apiDocsPath);
  }

  /**
   * プロトコルを取得
   *
   * @param env Environment
   * @return プロトコル (http or https)
   */
  private String getProtocol(final Environment env) {
    final String sslEnabled = env.getProperty("server.ssl.enabled");
    return "true".equalsIgnoreCase(sslEnabled) ? "https" : "http";
  }

  /**
   * ホストアドレスを取得
   *
   * @return ホストアドレス
   */
  private String getHostAddress() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (final UnknownHostException e) {
      log.warn("Failed to determine host address", e);
      return "localhost";
    }
  }

  /**
   * 起動情報をログ出力
   *
   * @param protocol プロトコル
   * @param port ポート番号
   * @param contextPath コンテキストパス
   * @param hostAddress ホストアドレス
   * @param swaggerPath Swaggerパス
   * @param apiDocsPath APIドキュメントパス
   */
  private void logStartupInfo(
      final String protocol,
      final String port,
      final String contextPath,
      final String hostAddress,
      final String swaggerPath,
      final String apiDocsPath) {

    final String localUrl = String.format("%s://localhost:%s%s", protocol, port, contextPath);
    final String networkUrl =
        String.format("%s://%s:%s%s", protocol, hostAddress, port, contextPath);
    final String swaggerUrl =
        String.format("%s://localhost:%s%s%s", protocol, port, contextPath, swaggerPath);
    final String apiDocsUrl =
        String.format("%s://localhost:%s%s%s", protocol, port, contextPath, apiDocsPath);

    final String line = "─".repeat(80);
    final StringBuilder message = new StringBuilder("\n\n");
    message.append("╔").append(line).append("╗\n");
    message.append("║  🚀 Application started successfully!").append(" ".repeat(42)).append("║\n");
    message.append("╠").append(line).append("╣\n");
    message.append(String.format("║  ➜  Local:     %-60s ║\n", localUrl));
    message.append(String.format("║  ➜  Network:   %-60s ║\n", networkUrl));
    message.append("╠").append(line).append("╣\n");
    message.append(String.format("║  📚 Swagger UI:  %-58s ║\n", swaggerUrl));
    message.append(String.format("║  📖 API Docs:    %-58s ║\n", apiDocsUrl));
    message.append("╚").append(line).append("╝\n");

    log.info(message.toString());
  }
}

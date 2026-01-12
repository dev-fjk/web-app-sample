package fjk.app.web.sample.config;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

  // 日本向けTimeZoneの設定
  private static final String JP_TIME_ZONE = "Asia/Tokyo";

  /**
   * 日本標準時のタイムゾーンを持つClockを生成
   *
   * @return Clock
   */
  @Bean
  public Clock clock() {
    return Clock.system(ZoneId.of(JP_TIME_ZONE));
  }

  /**
   * CORS設定
   *
   * <p>ローカル開発環境でのフロントエンド（localhost:3000）からのリクエストを許可
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("http://localhost:3000", "http://localhost")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}

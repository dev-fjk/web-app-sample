package fjk.app.web.sample.config;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

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
}

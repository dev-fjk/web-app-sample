package fjk.app.web.sample.models.constants;

/**
 * 日時フォーマット定数
 *
 * <p>APIレスポンス等で使用する日時フォーマットパターンを定義
 */
public final class DateTimeFormat {

  private DateTimeFormat() {
    throw new AssertionError("Utility class should not be instantiated");
  }

  /** ISO 8601形式の日時フォーマット: yyyy-MM-dd'T'HH:mm:ss */
  public static final String ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";

  /** ISO 8601形式の日時フォーマット（ミリ秒付き）: yyyy-MM-dd'T'HH:mm:ss.SSS */
  public static final String ISO_DATETIME_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

  /** ISO 8601形式の日付フォーマット: yyyy-MM-dd */
  public static final String ISO_DATE = "yyyy-MM-dd";

  /** ISO 8601形式の時刻フォーマット: HH:mm:ss */
  public static final String ISO_TIME = "HH:mm:ss";

  /** 日本語形式の日時フォーマット: yyyy年MM月dd日 HH:mm:ss */
  public static final String JP_DATETIME = "yyyy年MM月dd日 HH:mm:ss";

  /** 日本語形式の日付フォーマット: yyyy年MM月dd日 */
  public static final String JP_DATE = "yyyy年MM月dd日";
}

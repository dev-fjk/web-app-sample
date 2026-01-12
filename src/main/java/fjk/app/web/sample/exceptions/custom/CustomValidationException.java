package fjk.app.web.sample.exceptions.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.Getter;

/**
 * カスタムバリデーション例外
 *
 * <p>ビジネスロジック内でのバリデーションエラーを表現するための例外クラス。 フィールド名とエラーメッセージのペアを複数保持できます。
 *
 * <p>使用例:
 *
 * <pre>
 * // 単一エラー
 * throw CustomValidationException.of("email", "既に登録されているメールアドレスです");
 *
 * // 複数エラー
 * throw CustomValidationException.builder()
 *     .addError("email", "既に登録されているメールアドレスです")
 *     .addError("username", "ユーザー名は既に使用されています")
 *     .build();
 * </pre>
 */
@Getter
public class CustomValidationException extends RuntimeException {

  private final List<ValidationError> errors;

  /**
   * コンストラクタ（単一エラー用）
   *
   * @param field フィールド名
   * @param message エラーメッセージ
   */
  public CustomValidationException(final String field, final String message) {
    this(Collections.singletonList(new ValidationError(field, message)));
  }

  /**
   * コンストラクタ（複数エラー用）
   *
   * @param errors バリデーションエラーのリスト
   */
  public CustomValidationException(final List<ValidationError> errors) {
    super(buildErrorMessage(errors));
    this.errors = Collections.unmodifiableList(errors);
  }

  /**
   * 単一エラーのインスタンスを生成
   *
   * @param field フィールド名
   * @param message エラーメッセージ
   * @return CustomValidationException
   */
  public static CustomValidationException of(final String field, final String message) {
    return new CustomValidationException(field, message);
  }

  /**
   * ビルダーを取得
   *
   * @return CustomValidationExceptionBuilder
   */
  public static CustomValidationExceptionBuilder builder() {
    return new CustomValidationExceptionBuilder();
  }

  /**
   * エラーメッセージを構築
   *
   * @param errors バリデーションエラーのリスト
   * @return エラーメッセージ
   */
  private static String buildErrorMessage(final List<ValidationError> errors) {
    if (errors.isEmpty()) {
      return "Validation error occurred";
    }
    return errors.stream()
        .map(e -> String.format("%s: %s", e.getField(), e.getMessage()))
        .reduce((a, b) -> a + ", " + b)
        .orElse("Validation error occurred");
  }

  /** バリデーションエラー情報 */
  @Data
  public static class ValidationError {
    private final String field;
    private final String message;

    public ValidationError(final String field, final String message) {
      this.field = field;
      this.message = message;
    }
  }

  /** CustomValidationException用ビルダー */
  public static class CustomValidationExceptionBuilder {
    private final List<ValidationError> errors = new ArrayList<>();

    /**
     * エラーを追加
     *
     * @param field フィールド名
     * @param message エラーメッセージ
     * @return ビルダー自身
     */
    public CustomValidationExceptionBuilder addError(final String field, final String message) {
      this.errors.add(new ValidationError(field, message));
      return this;
    }

    /**
     * 例外を構築
     *
     * @return CustomValidationException
     * @throws IllegalStateException エラーが1つも追加されていない場合
     */
    public CustomValidationException build() {
      if (errors.isEmpty()) {
        throw new IllegalStateException("At least one error must be added");
      }
      return new CustomValidationException(errors);
    }
  }
}

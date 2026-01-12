package fjk.app.web.sample.models.infra.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * ユーザーエンティティ
 *
 * <p>usersテーブルに対応するエンティティ
 */
@Data
public class User {

  /** ユーザーID */
  private Long id;

  /** ユーザー名 */
  private String name;

  /** メールアドレス */
  private String email;

  /** 電話番号 */
  private String phoneNumber;

  /** 作成日時 */
  private LocalDateTime createdAt;

  /** 更新日時 */
  private LocalDateTime updatedAt;
}

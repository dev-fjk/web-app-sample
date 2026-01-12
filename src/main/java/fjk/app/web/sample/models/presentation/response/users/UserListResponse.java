package fjk.app.web.sample.models.presentation.response.users;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import fjk.app.web.sample.models.constants.DateTimeFormat;
import fjk.app.web.sample.models.infra.entity.User;
import fjk.app.web.sample.models.presentation.response.common.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * ユーザーリストレスポンス
 *
 * <p>ユーザー一覧取得APIのレスポンス
 */
@Data
@Builder
@Schema(description = "ユーザーリストレスポンス(/v1/users)")
public class UserListResponse {

  @Schema(description = "ページング情報")
  private Pagination pagination;

  @Schema(description = "ユーザーリスト")
  private List<UserView> data;

  /**
   * ユーザー情報
   *
   * <p>ユーザーの基本情報を表示
   */
  @Data
  @Builder
  @Schema(description = "ユーザー情報")
  public static class UserView {

    @Schema(description = "ユーザーID", example = "1")
    private Long id;

    @Schema(description = "ユーザー名", example = "山田太郎")
    private String userName;

    @Schema(description = "メールアドレス", example = "yamada@example.com")
    private String email;

    @Schema(description = "電話番号", example = "090-1234-5678")
    private String phoneNumber;

    @Schema(description = "作成日時", example = "2024-01-01T12:00:00")
    @JsonFormat(pattern = DateTimeFormat.ISO_DATETIME)
    private LocalDateTime createdAt;

    @Schema(description = "更新日時", example = "2024-01-01T12:00:00")
    @JsonFormat(pattern = DateTimeFormat.ISO_DATETIME)
    private LocalDateTime updatedAt;

    /**
     * Entity to ResponseModel.
     *
     * @param user user
     * @return {@link UserView}
     */
    public static UserView fromEntity(User user) {
      return UserView.builder()
          .id(user.getId())
          .userName(user.getName())
          .email(user.getEmail())
          .phoneNumber(user.getPhoneNumber())
          .createdAt(user.getCreatedAt())
          .updatedAt(user.getUpdatedAt())
          .build();
    }
  }
}

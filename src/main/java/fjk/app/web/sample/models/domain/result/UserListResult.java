package fjk.app.web.sample.models.domain.result;

import java.util.Collections;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import fjk.app.web.sample.models.infra.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListResult {

  private Integer totalCount;

  private List<User> users;

  /**
   * 空のUserListResultを生成する
   *
   * @param totalCount 総件数
   * @return {@link UserListResult}
   */
  public static UserListResult empty() {
    return UserListResult.builder().totalCount(0).users(Collections.emptyList()).build();
  }

  public Integer getSize() {
    if (CollectionUtils.isEmpty(users)) {
      return 0;
    }
    return users.size();
  }
}

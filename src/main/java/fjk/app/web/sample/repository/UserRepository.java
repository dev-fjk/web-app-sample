package fjk.app.web.sample.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import fjk.app.web.sample.mapper.UserMapper;
import fjk.app.web.sample.models.domain.dto.PagingDto;
import fjk.app.web.sample.models.domain.result.UserListResult;
import fjk.app.web.sample.models.infra.dto.UserDbSearchDto;
import fjk.app.web.sample.models.infra.entity.User;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final UserMapper userMapper;

  /**
   * ユーザー一覧を検索する
   *
   * @param pagingDto pagingDto
   * @param searchDto 検索条件
   * @return 検索結果
   */
  public UserListResult search(final PagingDto pagingDto, final UserDbSearchDto searchDto) {
    final Integer totalCount = userMapper.count(searchDto);
    if (totalCount == 0) {
      return UserListResult.empty();
    }

    final List<User> users = userMapper.findAll(searchDto, pagingDto);
    return UserListResult.builder().totalCount(totalCount).users(users).build();
  }
}

package fjk.app.web.sample.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import fjk.app.web.sample.mapper.UserMapper;
import fjk.app.web.sample.models.domain.dto.PagingDto;
import fjk.app.web.sample.models.domain.result.UserListResult;
import fjk.app.web.sample.models.infra.dto.UserDbSearchDto;
import fjk.app.web.sample.models.infra.entity.User;
import fjk.app.web.sample.models.presentation.query.UserListQuery;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserRepositoryのテスト")
class UserRepositoryTest {

  @Mock private UserMapper userMapper;

  @InjectMocks private UserRepository userRepository;

  private User user;
  private PagingDto pagingDto;
  private UserDbSearchDto searchDto;

  @BeforeEach
  void setUp() {
    final LocalDateTime now = LocalDateTime.now();
    user = new User();
    user.setId(1L);
    user.setName("山田太郎");
    user.setEmail("yamada@example.com");
    user.setPhoneNumber("090-1234-5678");
    user.setCreatedAt(now);
    user.setUpdatedAt(now);

    pagingDto = PagingDto.of(1, 10);
    final UserListQuery query = new UserListQuery();
    searchDto = UserDbSearchDto.create(query);
  }

  @Test
  @DisplayName("ユーザー一覧検索 - 正常系")
  void testSearch_Success() {
    // Given
    when(userMapper.count(any(UserDbSearchDto.class))).thenReturn(1);
    when(userMapper.findAll(any(UserDbSearchDto.class), any(PagingDto.class)))
        .thenReturn(List.of(user));

    // When
    final UserListResult result = userRepository.search(pagingDto, searchDto);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getTotalCount()).isEqualTo(1);
    assertThat(result.getUsers()).hasSize(1);
    assertThat(result.getUsers().get(0).getId()).isEqualTo(1L);
    assertThat(result.getUsers().get(0).getName()).isEqualTo("山田太郎");
  }

  @Test
  @DisplayName("ユーザー一覧検索 - 検索結果が0件の場合")
  void testSearch_Empty() {
    // Given
    when(userMapper.count(any(UserDbSearchDto.class))).thenReturn(0);

    // When
    final UserListResult result = userRepository.search(pagingDto, searchDto);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getTotalCount()).isEqualTo(0);
    assertThat(result.getUsers()).isEmpty();
  }

  @Test
  @DisplayName("ユーザー一覧検索 - 複数件取得")
  void testSearch_Multiple() {
    // Given
    final User user2 = new User();
    user2.setId(2L);
    user2.setName("佐藤花子");
    user2.setEmail("sato@example.com");
    user2.setPhoneNumber("080-2345-6789");
    user2.setCreatedAt(LocalDateTime.now());
    user2.setUpdatedAt(LocalDateTime.now());

    when(userMapper.count(any(UserDbSearchDto.class))).thenReturn(2);
    when(userMapper.findAll(any(UserDbSearchDto.class), any(PagingDto.class)))
        .thenReturn(List.of(user, user2));

    // When
    final UserListResult result = userRepository.search(pagingDto, searchDto);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getTotalCount()).isEqualTo(2);
    assertThat(result.getUsers()).hasSize(2);
  }
}

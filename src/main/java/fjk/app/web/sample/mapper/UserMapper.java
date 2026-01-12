package fjk.app.web.sample.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import fjk.app.web.sample.models.domain.dto.PagingDto;
import fjk.app.web.sample.models.infra.dto.UserDbSearchDto;
import fjk.app.web.sample.models.infra.entity.User;

/**
 * ユーザーマッパー
 *
 * <p>usersテーブルへのデータアクセスを提供
 */
@Mapper
public interface UserMapper {

  /** 検索条件のWHERE句 */
  String WHERE_CONDITION =
      """
      <where>
        <if test="dto.id != null">
          AND id = #{dto.id}
        </if>
        <if test="dto.userName != null and dto.userName != ''">
          AND name LIKE CONCAT('%', #{dto.userName}, '%')
        </if>
        <if test="dto.email != null and dto.email != ''">
          AND email LIKE CONCAT('%', #{dto.email}, '%')
        </if>
        <if test="dto.phoneNumber != null and dto.phoneNumber != ''">
          AND phone_number LIKE CONCAT('%', #{dto.phoneNumber}, '%')
        </if>
      </where>
      """;

  /**
   * ユーザー一覧を検索（ページング）
   *
   * @param dto 検索条件
   * @return ユーザーリスト
   */
  @Select(
      """
      <script>
        SELECT *
        FROM users
      """
          + WHERE_CONDITION
          + """
             ORDER BY id DESC
             LIMIT #{pagingDto.offset}, #{pagingDto.limit}
           </script>
          """)
  List<User> findAll(@Param("dto") UserDbSearchDto dto, @Param("pagingDto") PagingDto pagingDto);

  /**
   * 検索条件に一致するユーザーの総件数を取得
   *
   * @param dto 検索条件
   * @return 総件数
   */
  @Select(
      """
      <script>
        SELECT COUNT(*)
        FROM users
      """
          + WHERE_CONDITION
          + """
          </script>
          """)
  int count(@Param("dto") UserDbSearchDto dto);
}

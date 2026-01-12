package fjk.app.web.sample.models.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingDto {

  private Integer page;

  private Integer pageSize;

  /**
   * PagingDtoを作成する
   *
   * @param page
   * @param pageSize
   * @return {@link PagingDto}
   */
  public static PagingDto of(final Integer page, final Integer pageSize) {
    return PagingDto.builder().page(page).pageSize(pageSize).build();
  }

  public Integer getOffset() {
    return (page - 1) * pageSize;
  }

  public Integer getLimit() {
    return pageSize;
  }
}

package fjk.app.web.sample.models.presentation.response.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * ページング情報
 *
 * <p>ページング機能を持つAPIのレスポンスに含まれるページング情報
 */
@Data
@Builder
@Schema(description = "ページング情報")
public class Pagination {

  @Schema(description = "現在のページ番号（1から開始）", example = "1")
  private Integer page;

  @Schema(description = "1ページあたりの件数", example = "10")
  private Integer pageSize;

  @Schema(description = "総ページ数", example = "10")
  private Integer totalPage;

  @Schema(description = "検索条件に一致する総件数", example = "100")
  private Integer totalCount;

  @Schema(description = "今回取得したデータ件数", example = "10")
  private Integer fetchSize;

  @Schema(description = "次のページが存在するか", example = "true")
  private Boolean hasNext;

  /**
   * PaginationViewを生成するヘルパーメソッド
   *
   * @param page 現在のページ番号
   * @param pageSize ページサイズ
   * @param totalCount 総件数
   * @param fetchSize 取得件数
   * @return PaginationView
   */
  public static Pagination of(
      final Integer page,
      final Integer pageSize,
      final Integer totalCount,
      final Integer fetchSize) {
    final int totalPage = (int) Math.ceil((double) totalCount / pageSize);
    final boolean hasNext = page < totalPage;

    return Pagination.builder()
        .page(page)
        .pageSize(pageSize)
        .totalPage(totalPage)
        .totalCount(totalCount)
        .fetchSize(fetchSize)
        .hasNext(hasNext)
        .build();
  }
}

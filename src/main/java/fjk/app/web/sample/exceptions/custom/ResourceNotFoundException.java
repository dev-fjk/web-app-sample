package fjk.app.web.sample.exceptions.custom;

/**
 * リソース未検出例外
 *
 * <p>指定されたリソースが見つからない場合にスローされる例外
 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(final String message) {
    super(message);
  }
}

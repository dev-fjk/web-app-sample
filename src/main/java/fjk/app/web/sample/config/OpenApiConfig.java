package fjk.app.web.sample.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fjk.app.web.sample.models.presentation.response.errors.ApiErrorResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

  private final ObjectMapper objectMapper;

  @Value("${springdoc.swagger-ui.server-url:}")
  private String serverUrl;

  @Bean
  public OpenApiCustomizer registerSchemas() {
    return openApi -> {
      Components components = openApi.getComponents();
      var schemas = ModelConverters.getInstance().read(ApiErrorResponse.class);
      schemas.forEach(components::addSchemas);

      // servers設定を追加
      // serverUrlが設定されている場合はそれを使用、そうでなければ/apiを使用
      String url = serverUrl.isEmpty() ? "/api" : serverUrl;
      openApi.addServersItem(
          new io.swagger.v3.oas.models.servers.Server().url(url).description("API Server"));
    };
  }

  @Bean
  public OperationCustomizer globalErrorResponses() {
    return (final Operation operation, final HandlerMethod handlerMethod) -> {

      // BAD_REQUEST 400
      operation
          .getResponses()
          .addApiResponse("400", buildResponse(HttpStatus.BAD_REQUEST, "Invalid Request"));

      // NOT_FOUND 404
      operation
          .getResponses()
          .addApiResponse("404", buildResponse(HttpStatus.NOT_FOUND, "Resource Not Found"));

      // INTERNAL_SERVER_ERROR 500
      operation
          .getResponses()
          .addApiResponse(
              "500", buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error Occurred"));

      return operation;
    };
  }

  private ApiResponse buildResponse(final HttpStatus status, final String detail) {
    try {
      final ApiErrorResponse response =
          ApiErrorResponse.builder()
              .code(status.value())
              .message(status.getReasonPhrase())
              .detail(detail)
              .build();

      final String example = objectMapper.writeValueAsString(response);
      final Schema<?> schema = new Schema<>().$ref("ApiErrorResponse");

      final Content content =
          new Content()
              .addMediaType(
                  MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                  new io.swagger.v3.oas.models.media.MediaType().schema(schema).example(example));

      return new ApiResponse().description(status.getReasonPhrase()).content(content);

    } catch (final JsonProcessingException e) {
      throw new IllegalStateException("Swaggerコンテンツ作成に失敗しました", e);
    }
  }
}

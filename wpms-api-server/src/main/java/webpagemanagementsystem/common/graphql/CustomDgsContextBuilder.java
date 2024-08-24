package webpagemanagementsystem.common.graphql;

import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest;
import graphql.GraphQLContext;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import webpagemanagementsystem.user.service.UserService;

@Component
@RequiredArgsConstructor
public class CustomDgsContextBuilder implements DgsCustomContextBuilderWithRequest<GraphQLContext> {

  private final UserService userService;

  @Override
  public GraphQLContext build(@Nullable Map<String, ?> extensions, @Nullable HttpHeaders headers,
      @Nullable WebRequest webRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    webRequest.setAttribute("user", authentication, 0);
    // Authentication을 GraphQLContext에 추가
    return GraphQLContext.newContext()
        .of("authentication", authentication)
        .build();
  }
}

package webpagemanagementsystem.common.graphql;

import com.netflix.graphql.dgs.context.DgsContext;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.service.UserService;

@Component
@RequiredArgsConstructor
public class DgsAuthentication {
  private final UserService userService;

  public Users convertAuthenticationToUser(DataFetchingEnvironment dfe) {
    GraphQLContext graphQLContext = DgsContext.getCustomContext(dfe);
    Authentication authentication = graphQLContext.get("authentication");
    return userService.findByEmailAndIsUseY(authentication.getName());
  }
}

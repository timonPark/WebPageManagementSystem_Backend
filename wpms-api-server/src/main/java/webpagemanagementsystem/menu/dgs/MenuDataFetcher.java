package webpagemanagementsystem.menu.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import webpagemanagementsystem.common.graphql.ApiResponseFormat;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.service.MenuService;

@DgsComponent
@RequiredArgsConstructor
public class MenuDataFetcher {
  private final MenuService menuService;

  @DgsQuery
  public ApiResponseFormat<List<Menu>> menus(){
    try {
      return ApiResponseFormat.success(HttpStatus.OK, this.menuService.findAll());
    } catch (Exception e) {
      return ApiResponseFormat.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

}

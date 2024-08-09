package webpagemanagementsystem.menu.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.service.MenuService;

@DgsComponent
@RequiredArgsConstructor
public class MenuDataFetcher {
  private final MenuService menuService;

  @DgsQuery
  public List<Menu> menus(){
    return this.menuService.findAll();
  }

}

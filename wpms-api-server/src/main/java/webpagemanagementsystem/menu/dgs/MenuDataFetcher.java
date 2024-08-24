package webpagemanagementsystem.menu.dgs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import org.springframework.http.HttpStatus;
import webpagemanagementsystem.common.graphql.ApiResponseFormat;
import webpagemanagementsystem.common.graphql.DgsAuthentication;
import webpagemanagementsystem.common.graphql.MutationInputConvert;
import webpagemanagementsystem.menu.dto.CreateMenuReqDto;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.service.MenuService;

@DgsComponent

public class MenuDataFetcher {
  private final MenuService menuService;
  private final ObjectMapper objectMapper;
  private final MutationInputConvert<CreateMenuReqDto> mutationInputConvert;
  private final DgsAuthentication dgsAuthentication;
  public MenuDataFetcher(MenuService menuService, ObjectMapper objectMapper, DgsAuthentication dgsAuthentication) {
    this.menuService = menuService;
    this.objectMapper = objectMapper;
    this.mutationInputConvert = new MutationInputConvert<>();
    this.dgsAuthentication = dgsAuthentication;
  }

  @DgsQuery
  public ApiResponseFormat<List<Menu>> menus(){
    try {
      return ApiResponseFormat.success(HttpStatus.OK, this.menuService.findAll());
    } catch (Exception e) {
      return ApiResponseFormat.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @DgsMutation
  public ApiResponseFormat<Menu> createMenu(DataFetchingEnvironment dfe){
    CreateMenuReqDto createMenuReqDto = mutationInputConvert.convert(dfe, objectMapper, CreateMenuReqDto.class);
    try {
      return ApiResponseFormat.success(
          HttpStatus.OK,
          this.menuService.createMenu(
              createMenuReqDto,
              this.dgsAuthentication.convertAuthenticationToUser(dfe)
          )
      );
    } catch (Exception e) {
      return ApiResponseFormat.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

}

package webpagemanagementsystem.common.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;


public class MutationInputConvert <T> {
  public T convert(DataFetchingEnvironment dataFetchingEnvironment, ObjectMapper objectMapper, Class<T> t) {
    Map<String,Object> input = dataFetchingEnvironment.getArgument("input");
     return objectMapper.convertValue(input, t);
  }
}

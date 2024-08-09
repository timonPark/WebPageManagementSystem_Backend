package webpagemanagementsystem.common.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeScalar {

  public static final GraphQLScalarType DateTime = GraphQLScalarType.newScalar()
      .name("DateTime")
      .description("A custom scalar that handles DateTime")
      .coercing(new Coercing<LocalDateTime, String>() {
        @Override
        public String serialize(Object dataFetcherResult) {
          return ((LocalDateTime) dataFetcherResult).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        @Override
        public LocalDateTime parseValue(Object input) {
          return LocalDateTime.parse(input.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        @Override
        public LocalDateTime parseLiteral(Object input) {
          if (input instanceof StringValue) {
            return LocalDateTime.parse(((StringValue) input).getValue(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
          }
          return null;
        }
      })
      .build();
}


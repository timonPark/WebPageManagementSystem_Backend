package webpagemanagementsystem.common.variable;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "social")
public class SocialProperties {

  public Map<String, SocialUrlInfo> platform;
  @Getter
  @Setter
  public static class SocialUrlInfo {
    private String baseUrl;
    private String pathUrl;

  }
}

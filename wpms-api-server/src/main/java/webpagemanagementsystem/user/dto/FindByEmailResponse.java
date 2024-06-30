package webpagemanagementsystem.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webpagemanagementsystem.user.entity.Users;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindByEmailResponse {

    public FindByEmailResponse(Users user){
        this.userNo = user.getUserNo();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    private Long userNo;

    private String name;

    private String email;

    private String picture;

}

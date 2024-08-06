package webpagemanagementsystem.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webpagemanagementsystem.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}

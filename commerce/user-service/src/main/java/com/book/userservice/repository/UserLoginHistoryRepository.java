package com.book.userservice.repository;

import com.book.userservice.entity.User;
import com.book.userservice.entity.UserLoginHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Integer> {
  List<UserLoginHistory> findByUserOrderByLoginTimeDesc(User user);
}

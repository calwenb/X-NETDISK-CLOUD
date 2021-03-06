package com.wen.oauth.serivce;

import com.wen.common.pojo.Result;
import com.wen.common.pojo.User;
import com.wen.oauth.serivce.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "netdisc-service-user", path = "/user", fallback = UserServiceFallback.class)
public interface UserService {
    @GetMapping("/get/{userId}")
    Result<User> getUserById(@PathVariable String userId);

}

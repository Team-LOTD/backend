package LOTD.project.global.login.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    public void delValues(String refreshToken) {
        redisTemplate.delete(refreshToken.substring(7)); // bearer 제외한 실제 refreshToken 값
    }

    /**
     * @author 윤재성
     * @date 2024-01-10
     * @description : 로그아웃 시 블랙리스트에 AT저장
     * @return
     */
    public void addToBlacklist(String accessToken) {
        String key = "accessToken:blackList";
        redisTemplate.opsForSet().add(key, accessToken);
    }


    public void setRefreshToken(String memberId, String refreshToken) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(memberId, refreshToken, Duration.ofDays(14)); // 2주 저장 설정
    }

    public String getRefreshToken(String memberId) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(memberId);
    }

    // redis 값 출력
    public void getRedisValue() {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        System.out.println(setOperations.members("accessToken:blackList"));  // 전체 조회
    }
    public void deleteRedisElementsAll(){
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    /**
     * @author 윤재성
     * @date 2024-01-10
     * @description : AT가 Redis의 블랙리스트에 저장되어있는지 확인, opsForSet을 통해 SET데이터에 접근하여 ACCESSTOKEN이 있는지 확인
     * @return
     */
    public boolean isTokenInBlacklist(String accessToken) {
        try {
            String key = "accessToken:blackList";
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, accessToken));

        } catch (Exception e) {
            throw new RuntimeException("Failed to check token blacklist", e);
        }
    }
}




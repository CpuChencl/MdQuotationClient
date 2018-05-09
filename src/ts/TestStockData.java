package ts;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;


@Component
@Lazy(false)
public class TestStockData {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@PostConstruct
	public void test(){
		HashOperations opsForHash = redisTemplate.opsForHash();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("test1", "test2");
		opsForHash.putAll("", map);
		
		System.out.println("00000000000000000000000000000000" + opsForHash.get("E60035", "op"));
		
		ValueOperations opsForValue = redisTemplate.opsForValue();
		opsForValue.set("test", "test1234");
		
		System.out.println("00000000000000000000000000000000" + opsForValue.get("test"));
	}
	
	
}
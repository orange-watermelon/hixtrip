package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.shaded.freemarker.template.utility.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
@Slf4j
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Integer  getInventory(String skuId){
        Object o = redisTemplate.opsForValue().get(skuId);
        if (ObjectUtils.isEmpty(o)){

            return (Integer)o;
        }
        return 0;
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    public Integer  changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity){
        if (sellableQuantity.equals(0l)) {
            return -1;
        }
        // 为了保证原子性，防止库存被减成负数，使用lua脚本
         String luaScript =
                "local c_s = redis.call('get', KEYS[1])\n" +
                "if not c_s or tonumber(c_s) < tonumber(KEYS[2]) then\n" +
                "   return -1\n" +
                "end\n" +
                "return redis.call('decrby',KEYS[1], KEYS[2])\n" ;
        RedisScript<String> redisScript = RedisScript.of(luaScript, String.class);
        List<String> keys = Arrays.asList(new String[]{skuId, occupiedQuantity.toString()});
        String result = redisTemplate.execute(redisScript, keys);

        if (result.equals("-1")) {
            log.warn("库存扣减失败");
        }
        try {
            return Integer.parseInt(result);
        } catch (Exception e) {
            return 0;
        }
    }
}

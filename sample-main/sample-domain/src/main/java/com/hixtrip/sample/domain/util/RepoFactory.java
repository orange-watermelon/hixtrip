package com.hixtrip.sample.domain.util;

import java.util.Collection;
import java.util.Map;

public class RepoFactory {


    /**
     * 根据仓储接口类型获取对应实现且默认取值第一个
     *
     * @param tClass 目标仓储接口类型
     * @param <T>    目标类型
     * @return 如果不是指定实现，默认获得第一个实现Bean
     */
    public static <T> T get(Class<? extends T> tClass) {

        Map<String, ? extends T> map =SpringUtils.getBeansOfType(tClass);
        Collection<? extends T> collection = map.values();
        if (collection.isEmpty()) {
            //throw new PersistException("未找到仓储接口或其指定的实现:" + tClass.getSimpleName() );
        }
        return collection.stream().findFirst().get();
    }

}

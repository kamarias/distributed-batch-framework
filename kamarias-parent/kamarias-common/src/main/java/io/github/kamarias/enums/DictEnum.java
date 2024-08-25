package io.github.kamarias.enums;

public interface DictEnum<K,V> {


    /**
     * 获取字典键
     * @return
     */
    K getKey();

    /**
     * 获取字典值
     * @return 字典值
     */
    V getValue();


}

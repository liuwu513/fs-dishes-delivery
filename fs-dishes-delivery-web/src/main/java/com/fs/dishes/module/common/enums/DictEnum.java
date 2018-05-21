package com.fs.dishes.module.common.enums;

/**
 * Created by Liuwu on 2018/4/21.
 */
public class DictEnum {

    /**
     * 单位枚举
     */
    public enum UnitEnum{
        JIN(1,"斤"),PING(2,"瓶"),DAI(1,"袋"),ZHI(1,"只"),GE(1,"个"),BAN(6,"板"),BAO(7,"包"),KUAI(8,"块"),JIAN(9,"件"),XIANG(10,"箱");

        private int code;
        private String desc;

        private UnitEnum(int code,String desc){
            this.code = code;
            this.desc = desc;
        }

        public static UnitEnum formCode(int code){
            for (UnitEnum unitEnum: UnitEnum.values()) {
                if (code == unitEnum.code()){
                    return unitEnum;
                }
            }
            return UnitEnum.JIN;
        }

        public int code(){
            return this.code;
        }

        public String desc(){
            return this.desc;
        }

    }

}

package cn.andylhl.xy.common.test;

import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import org.junit.Test;

/***
 * @Title: TestResultCodeEnum
 * @Description:
 * @author: lhl
 * @date: 2021/1/30 17:22
 */
public class TestResultCodeEnum {

    @Test
    public void testReslut(){
        // 获取枚举对象
        ResultCodeEnum resultCodeEnum = ResultCodeEnum.BAD_SQL_GRAMMAR;
        System.out.println(resultCodeEnum.getSuccess());
        System.out.println(resultCodeEnum.getCode());
        System.out.println(resultCodeEnum.getMessage());
    }

}

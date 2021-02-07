package cn.andylhl.xy.common.base.exception;

import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import lombok.Data;

/***
 * @Title: XyCollegeException
 * @Description: 自定义异常
 * @author: lhl
 * @date: 2021/2/6 23:12
 */

@Data
public class XyCollegeException extends RuntimeException {

    // 响应状态码
    private Integer code;

    /**
     * 接受状态码和异常信息
     * @param code
     * @param message
     */
    public XyCollegeException( Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 接受一个枚举值进行创建异常对象
     * @param resultCodeEnum
     */
    public XyCollegeException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "XyCollegeException{" +
                "code=" + code +
                "message=" + super.getMessage() +
                '}';
    }
}

package cn.andylhl.xy.service.base.handler;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.common.base.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * @Title: GlobalExceptionHandler
 * @Description: 统一异常处理器
 * @author: lhl
 * @date: 2021/1/30 21:53
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public @ResponseBody R error(Exception e) {
        // 打印异常堆栈信息
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage());
    }

    /**
     * json解析异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody R error(HttpMessageNotReadableException e) {
        // 打印异常堆栈信息
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    /**
     * sql语法错误
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    /**
     * 项目自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(XyCollegeException.class)
    @ResponseBody
    public R error(XyCollegeException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.error().code(e.getCode()).message(e.getMessage());
    }

}

package cn.andylhl.xy.service.oss.controller.admin;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.time.LocalTime;

/***
 * @Title: FileController
 * @Description: 文件上传控制器
 * @author: lhl
 * @date: 2021/2/6 18:34
 */


@Slf4j
//@CrossOrigin
@RestController
@Api(tags = "阿里云文件管理")
@RequestMapping("/admin/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile multipartFile,
            @ApiParam(value = "模块", required = true) @RequestParam("module") String module)  {
        log.info("进入service_oss, 文件上传");
        try {
            InputStream inputStream = multipartFile.getInputStream();
            String originalFilename = multipartFile.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, module, originalFilename);
            return R.ok().message("文件上传成功").data("url", uploadUrl);
        } catch (Exception e) {
            throw new XyCollegeException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/remove")
    public R removeFile(
            @ApiParam(value = "要删除的文件路径", required = true) @RequestBody String url){
        log.info("进入service_oss, 删除文件");
        fileService.removeFile(url);
        return R.ok().message("文件删除成功");
    }

    @ApiOperation("oss服务-测试方法")
    @GetMapping("/test")
    public R test(HttpServletRequest request){
        log.info("oss test被调用-开始睡眠");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("oss test被调用-睡眠结束");
        return R.ok().data("time", LocalTime.now()).data("port", request.getServerPort());
    }
}

package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
//允许浏览器进行跨域请求
@CrossOrigin
//负责接受请求，响应数据
@RequestMapping("/depts")
public class DeptController {
//    采用日志输出
//    private static Logger log = LoggerFactory.getLogger(DeptController.class);
//    相当于@Slf4j

    @Autowired //依赖注入
    private DeptService deptService;

    //    @RequestMapping(value = "/depts" ,method = RequestMethod.GET)//限定请求方式为get
    @GetMapping //相当于上述注解
    public Result list() {
        log.info("查询全部部门数据");

        //调用service查询部门数据
        List<Dept> deptList =  deptService.list();
        return Result.success(deptList);
    }
    /*
    删除部门
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门：{}",id);
        //调用service删除部门
        deptService.delete(id);
        return Result.success();
    }
    /*
    新增部门
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门：{}",dept);
        deptService.add(dept);
        return Result.success();
    }
    /*
    根据id查询部门
     */
    @GetMapping("/{id}")
    public Result select(@PathVariable Integer id){
        log.info("修改部门：{}",id);
        Dept dept = deptService.select(id);
        return Result.success(dept);
    }
    /*
    修改部门
     */
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("修改部门：{}",dept);
        deptService.update(dept);
        return Result.success();
    }
}

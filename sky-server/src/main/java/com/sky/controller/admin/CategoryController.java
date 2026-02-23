package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类：{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("分类查询")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询，参数为：{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping
    @ApiOperation("分类修改")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("分类修改，参数为：{}",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("启用禁用分类：{},{}",status,id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result delete(String id){
        log.info("删除分类信息：{}",id);
        if(dishMapper.countByCategoryId(Long.parseLong(id)) != 0){
            log.info("删除失败，有关联的菜品信息");
            return Result.error("删除失败，有关联的菜品信息");
        }
        if(setmealMapper.countByCategoryId(Long.parseLong(id)) != 0){
            log.info("删除失败，有关联的套餐信息");
            return Result.error("删除失败，有关联的套餐信息");
        }
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result selectByType(String type){
        log.info("根据类型查询分类：{}",type);
        Category category = categoryService.selectByType(type);
        return Result.success(category);
    }

}

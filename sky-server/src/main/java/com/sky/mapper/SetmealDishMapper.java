package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    void insertBatch(List<SetmealDish> setmealDishes);

    List<SetmealDish> getSetmealDishesBySetmealId(Long id);

    void deleteBySetmealId(Long id);

    void deleteBatch(List<Long> ids);
}

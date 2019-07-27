package com.baomidou.mybatisplus.samples.pagination.model;

import com.baomidou.mybatisplus.samples.pagination.entity.Children;
import com.baomidou.mybatisplus.samples.pagination.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author miemie
 * @since 2019-06-12
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserChildren extends User {

    private List<Children> c;
}

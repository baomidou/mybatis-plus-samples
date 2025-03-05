package com.baomidou.mybatisplus.samples.kotlin

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.samples.kotlin.entity.User
import com.baomidou.mybatisplus.samples.kotlin.mapper.UserMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @author nieqiurong
 */
@SpringBootTest
class ApplicationTests {

    private val logger = LoggerFactory.getLogger(ApplicationTests::class.java)

    @Autowired
    private lateinit var userMapper: UserMapper

    @Test
    fun test() {
        logger.info("--------------演示分页查询--------------------")
        val page = userMapper.selectPage(Page(1, 3), null)
        Assertions.assertEquals(5, page.total)
        Assertions.assertEquals(3, page.size)
        val insertUser = User()
        insertUser.name = "demo"
        insertUser.age = 10
        insertUser.email = "demo@example.com"
        logger.info("--------------演示写入--------------------")
        Assertions.assertTrue(userMapper.insert(insertUser) > 0)
        Assertions.assertNotNull(insertUser.createUserId)
        Assertions.assertNotNull(insertUser.createTime)
        Assertions.assertNull(insertUser.updateTime)
        Assertions.assertNull(insertUser.updateTime)
        logger.info("--------------演示查询--------------------")
        val selectUser = userMapper.selectById(insertUser.id)
        Assertions.assertNotNull(selectUser)
        Assertions.assertEquals("demo", selectUser.name)
        Assertions.assertEquals("demo@example.com", selectUser.email)
        Assertions.assertEquals(10, selectUser.age)
        logger.info("--------------演示更新--------------------")
        val updateUser = User()
        updateUser.id = insertUser.id
        updateUser.name = "test"
        Assertions.assertTrue(userMapper.updateById(updateUser) > 0)
        val selectUpdateUser = userMapper.selectById(insertUser.id)
        Assertions.assertNotNull(selectUpdateUser)
        Assertions.assertEquals("test", selectUpdateUser.name)
        Assertions.assertEquals("demo@example.com", selectUpdateUser.email)
        Assertions.assertEquals(10, selectUser.age)
        Assertions.assertNotNull(selectUpdateUser.createUserId)
        Assertions.assertNotNull(selectUpdateUser.createTime)
        Assertions.assertNotNull(selectUpdateUser.updateTime)
        Assertions.assertNotNull(selectUpdateUser.updateTime)
        logger.info("--------------演示删除--------------------")
        Assertions.assertTrue(userMapper.deleteById(insertUser.id) > 0)
        Assertions.assertNull(userMapper.selectById(insertUser.id))
        logger.info("--------------演示default方法调用--------------------")
        val user = userMapper.user(1)
        Assertions.assertNotNull(user)
    }

}
# Mybatis-Plus通用枚举

在一些情况下，我们希望实体的字段是固定的几个值，比如：用户表的性别(Gender)，我们希望是固定的Male,Female
我们可能在设计数据库字段时，采用了int(1):
* 1: 男
* 2: 女

如果实体使用了Integer，那么无法保证新增时传入的参数是1/2

## 通用枚举就是为了解决这样的场景

1. 首先我们定义一个枚举类型
```java
public enum GenderEnum {
    
    MALE(1, "男"),
    FEMALE(2, "女");
    
    private final int code;
    private final String descp;
    
    //。。。省略其他
}
```

2. 定义用户实体时，使用枚举类型
```java
public class User {
    
    private String name;
    /**
     * 使用枚举类型来限制输入值
     */
    private GenderEnum gender;
    
}
```

这样是不是就好了呢？

- 答案是NO

mybatis原生默认是以枚举的名称： Enum.name()作为默认值
```java
String val = GenderEnum.FEMALE.name();
```
也就是说，对应数据库的字段必须是一个varchar类型。这显然违背了我们的初衷。

Mybatis-Plus的通用枚举处理，增强了原生的枚举处理，让匹配的数据可以自由定制。

你只需要极少的配置即可：

1. 配置扫描枚举类的路径
```yaml
mybatis-plus:
  type-enums-package: com.baomidou.mybatisplus.samples.enums.enums
```

2. 在枚举类，你有两种方式

方式一：枚举实现IEnum接口
```java
public enum AgeEnum implements IEnum<Integer> {
  ONE(1, "一岁"),
  TWO(2, "二岁"),
  THREE(3, "三岁");

  private int value;
  private String desc;

  AgeEnum(final int value, final String desc) {
    this.value = value;
    this.desc = desc;
  }

  @Override
  public Integer getValue() {
    return value;
  }
}
```
通过getValue()方法，告诉Mybatis-Plus，枚举类的真正的value是什么，这样就实现了自由定制。

方式二：使用@EnumValue注解
```java
@Getter
public enum GradeEnum {

    PRIMARY(1, "小学"),
    SECONDORY(2, "中学"),
    HIGH(3, "高中");

    GradeEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    @EnumValue
    private final int code;
    private final String descp;

}
```
通过@EnumValue注解，告诉Mybatis-Plus, 枚举类的真正的value是什么，同样也就实现了自由定制。



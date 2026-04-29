# 在线考试系统

基于 Spring Boot 3 + MyBatis + Thymeleaf + Bootstrap 5 + MySQL 的在线考试系统。

## 功能特性

### 三种角色
- **管理员**：用户管理、课程管理、全局成绩查看
- **教师**：题库管理、组卷管理、阅卷管理
- **学生**：在线考试、成绩查询

### 核心功能
1. **用户认证**：登录/注册/退出，Session 拦截，角色权限控制
2. **用户管理**：CRUD、多条件搜索
3. **课程管理**：CRUD、指定授课教师
4. **题库管理**：单选/多选/判断三种题型，支持难度分级
5. **组卷系统**：手动选题、设置分值、发布/取消发布
6. **在线考试**：倒计时、答题卡、自动提交
7. **自动评分**：客观题提交即出分
8. **阅卷管理**：查看答卷、成绩统计（平均分/最高分/最低分/及格率）
9. **成绩查询**：学生查看个人成绩，教师/管理员查看全局成绩

## 技术栈
- **后端**：Spring Boot 3.2.5 + MyBatis 3.0.3
- **前端**：Thymeleaf + Bootstrap 5 + Bootstrap Icons
- **数据库**：MySQL 8
- **构建**：Maven

## 快速开始

### 环境要求
- JDK 17+
- MySQL 8+
- Maven 3.6+

### 运行步骤

1. **创建数据库**
```sql
CREATE DATABASE exam_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. **修改配置**（可选）
编辑 `src/main/resources/application.yml`，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/exam_system
    username: root
    password: root
```

3. **运行项目**
```bash
mvn spring-boot:run
```

4. **访问系统**
打开浏览器访问 http://localhost:8080

### 测试账号
| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | admin123 |
| 教师 | teacher1 | 123456 |
| 学生 | student1 | 123456 |

## 项目结构
```
src/main/java/com/example/demo/
├── DemoApplication.java          # 启动类
├── config/WebConfig.java         # Web配置
├── interceptor/LoginInterceptor.java  # 登录拦截器
├── entity/                       # 实体类
├── mapper/                       # MyBatis Mapper接口
├── service/                      # 业务逻辑层
│   └── impl/
└── controller/                   # 控制器层

src/main/resources/
├── application.yml               # 配置文件
├── db/schema.sql                 # 建表脚本
├── db/data.sql                   # 示例数据
├── mapper/                       # MyBatis XML
└── templates/                    # Thymeleaf模板
    ├── fragments/layout.html     # 公共布局
    ├── auth/                     # 登录注册
    ├── admin/                    # 管理员后台
    ├── teacher/                  # 教师工作台
    └── student/                  # 学生考试
```

## 使用 IDEA 开发

1. File → Open → 选择项目根目录
2. IDEA 自动识别 Maven 项目并下载依赖
3. 运行 `DemoApplication.java` 即可启动
4. 如需修改数据库配置，编辑 `application.yml`

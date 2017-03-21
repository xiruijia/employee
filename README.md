![Alt text](/path/to/img.jpg)
# 手机端员工管理系统

## 后台java使用技术

### 1.spring-boot
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.3.RELEASE</version>
	</parent>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>
### 2.mysql数据库
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
	</dependency>
	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>druid</artifactId>
		<version>1.0.27</version>
	</dependency>
### 3.mybatis
	<dependency>
		<groupId>org.mybatis.spring.boot</groupId>
		<artifactId>mybatis-spring-boot-starter</artifactId>
		<version>1.1.1</version>
	</dependency>
### 4.PageHelper
	<dependency>
		<groupId>com.github.pagehelper</groupId>
		<artifactId>pagehelper-spring-boot-starter</artifactId>
		<version>1.0.0</version>
	</dependency>
### 5.json解析
	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>fastjson</artifactId>
		<version>${fastjson.version}</version>
	</dependency>
### 6.redis存取
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-redis</artifactId>
	</dependency>
### 7.web框架--spring-mvc
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
### 8.环境配置插件
	<build>
		<defaultGoal>compile</defaultGoal>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
					<encoding>UTF8</encoding>
				</configuration>
			</plugin>
			<plugin>
				<!-- 打包插件 -->
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
### 9.maven构建
	一个eclipse中使用maven的教程
	https://yq.aliyun.com/articles/28591
## 页面前台使用技术

### 1.jquery-3.1.1
	菜鸟教程
	http://www.runoob.com/jquery
### 2.bootstrap-4.0.0-alpha.6
	教程和文档
	https://v4.bootcss.com/
### 3.tehter-1.4.0
	教程和文档
	http://tether.io/
### 4.angular-1.6.3
	菜鸟教程
	http://www.runoob.com/angularjs/

## 启动方法
### 启动main方法
	com.bandaoti.employee.Application
### 命令行启动
	java -jar employee.jar
### maven启动
	根目录下
	mvn spring-boot:run
### tomcat容器启动
#### 1.修改pom.xml中<packaging>jar</packaging> 改为<packaging>war</packaging>
#### 2.根目录执行命令mvn clean package
#### 3.把~/.m2/repository/com/bandaoti/employee/0.0.1-SNAPSHOT/employee-0.0.1-SNAPSHOT.war
	改名为employee.war再放到tomcat中的webapps下
	启动tomcat后访问
	http://localhost/employee
	
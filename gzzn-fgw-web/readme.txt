
▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇SVN版本管理▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇
	svn提交请设置忽略target目录(android项目为bin,gen,target目录),并先提交子项目,在提交父项目
▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇SVN版本管理▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇


▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇maven 命令说明▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇
	 多条命令组合:空格(-X clean install),eclipse与 myeclipse中可以省略mvn
	-X 命令前添加:输出调试信息
		mvn compile 编译源代码
		mvn package  打包程序(target目录下,可配置,自动调用mvn test,优先使用install)
		mvn test 使用JUnit测试项目(JUnit环境与测试类)
		mvn clean 清除target(可配置)下已经打包好的程序
		mvn install 打包安装程序(target目录下,可配置,自动调用mvn test)
		
		mvn tomcat7:run  web模块使用tomcat7运行(pop.xml文件中plugin配置configuration)
		mvn tomcat6:run  web模块使用tomcat6运行(pop.xml文件中plugin配置configuration)
		mvn jetty:run -Djetty.port=9090    web模块使用jetty测试并指定端口为9090,默认为8080
		
		mvn hibernate3:hbm2java    model模块使用hibernate映射数据库
		
		mvn android:deploy    android模块部署到模拟器(需先启动模拟器)或真机(USB调式模式连接)
		
		mvn assembly:assembly swing模块打包为可执行jar包
		mvn exec:java     swing模块java启动
▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇maven 命令说明▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇
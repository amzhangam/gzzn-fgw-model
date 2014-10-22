
▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇SVN版本管理▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇
	svn提交请忽略target目录,并先提交子项目,在提交父项目
▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇SVN版本管理▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇


▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇maven 命令说明▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇
	 多条命令组合:空格(-X clean install),eclipse与 myeclipse中可以省略mvn
	-X 命令前添加:输出调试信息
		mvn test 使用JUnit测试项目(JUnit环境与测试类)
		mvn clean 清除target(可配置)下已经打包好的程序
		mvn install 打包安装程序(target目录下,可配置,自动调用mvn test)
		mvn package  打包程序(target目录下,可配置,自动调用mvn test)
		
		mvn hibernate3:hbm2java    model模块使用hibernate映射数据库
▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇maven 命令说明▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇
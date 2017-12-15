# FileTypeChecker-java
<a href="http://blog.fpliu.com/it/data/file#checkType" target="_blank">根据魔数检测文件的类型</a><br><br>
使用<a href="http://blog.fpliu.com/it/language/Java" target="_blank">java</a>语言实现<br><br>
<code>jar</code>包保存在<a href="https://bintray.com/fpliu/newton/FileTypeChecker-java" target="_blank">jcenter</a>中。

## 1、<a href="http://blog.fpliu.com/it/software/gradle" target=_blank>gradle</a>引用
build.gradle中配置如下：
```
dependencies {
    compile 'com.fpliu:FileTypeChecker-java:1.0.0'
}
```
## 2、<a href="http://blog.fpliu.com/it/software/Maven" target="_blank">Maven</a>引用
```
<dependency>
  <groupId>com.fpliu</groupId>
  <artifactId>FileTypeChecker-java</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
## 3、在命令行执行
查看使用帮助：
```
java -jar FileTypeChecker-java-1.0.0.jar -h
```
运行结果：
```
usage:   java -jar FileTypeChecker.jar <filePath> [expectType]
example: java -jar FileTypeChecker.jar ~/xx.png
         java -jar FileTypeChecker.jar ~/xx.png PNG
```

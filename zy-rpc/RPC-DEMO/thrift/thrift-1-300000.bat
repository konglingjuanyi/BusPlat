java -Xms2G -Xmx2G -classpath ./out/production/thrift  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc1.log -Dwrite.statistics=true -Djava.ext.dirs="./lib" com.th.hello.test.THTest 1 300000 > benchmark1.log
pause
java -Xms2G -Xmx2G -classpath ./target/classes  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc1.log -Dwrite.statistics=true -Djava.ext.dirs="./lib" io.grpc.examples.test.GRPCTest 1 300000 > benchmark1.log
pause
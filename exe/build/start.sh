rm -f tpid

nohup /usr/java/jdk1.8.0_201-amd64/bin/java -jar YZS-2.1.2.RELEASE.jar --name="YZSSpringBoot" --server.port=443 > log.log 2>&1 &

echo $! > tpid

echo Start Success!

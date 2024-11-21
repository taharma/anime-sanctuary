#!/bin/bash

REPOSITORY=/home/ec2-user/webservice/step2
PROJECT_NAME=SpringBoot-animesanctuary

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동중인 애플리케이션: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
	echo "> kill =15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME="anime-sanctuary-0.0.1-SNAPSHOT.jar"

echo "> JAR Name: $JAR_NAME"

nohup java -jar \
-Dspring.config.location=/home/ec2-user/webservice/application-real.yml \
$REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

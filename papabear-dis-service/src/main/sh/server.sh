#! /bin/sh
#Create By chenzehe 20150810
#设置SERVER服务名字,供一台机器部署多个服务进行区分用
#同一台机器上不要使用相同名字,否则只能启动一个服务
#守护进程需要配置一样的名字,以便于守护进程进行检查
APP_NAME="papabear-dis-service"
#JAVA虚拟机参数设置
JAVA_OPTS="-Xms256m -Xmx256m"
#启动方法    
start(){
 #设置java
 if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
 fi
 echo ">>>>>>>>>>hello $APP_NAME..."
 #判断服务是否已经存在
 ps -ef| grep $APP_NAME| grep -v "grep" |awk '{print $2}'|while read pid
 do
    echo ">>>>>>>>>>$APP_NAME has bean started ,run processing PID:"$pid 
    kill 0      
 done
 echo ">>>>>>>>>>$APP_NAME began to start."
 showprogress
 JAVA_OPTS="$JAVA_OPTS "
 #exec $JAVA $JAVA_OPTS -Djava.ext.dirs=./  -jar papabear-dis-service.jar &
 nohup $JAVA $JAVA_OPTS -Djava.ext.dirs=./  -jar papabear-dis-service.jar > stdout.log 2>&1 &
 echo ">>>>>>>>>>$APP_NAME has bean started."
 ps -ef| grep $APP_NAME | grep -v "grep" | awk '{print $2}'|while read pid
 do
    echo ">>>>>>>>>>Run Processing PID:"$pid
 done 
}  
#停止方法  
stop(){  
 ps -ef| grep $APP_NAME| grep -v "grep" |awk '{print $2}'|while read pid  
 do  
    echo ">>>>>>>>>>Run Processing PID:"$pid
    kill $pid  
 done
 echo ">>>>>>>>>>$APP_NAME began to close."
 echo ">>>>>>>>>>$APP_NAME has been closed."
 echo ">>>>>>>>>>bye..."  
}
#显示服务运行状态
status(){
  pid=`ps -ef| grep $APP_NAME| grep -v "grep" |awk '{print $2}'`
  if [ "$pid" != "" ] ; then
    echo ">>>>>>>>>>$APP_NAME is running,Run Processing PID:"$pid
  else
    echo ">>>>>>>>>>$APP_NAME is not run."
  fi
} 

showprogress(){
 ii=0
 while [ $ii -lt 98 ]
 do
        for jj in '-' '\' '|' '/'
        do
                printf "loading: %s%s\r" $jj$ii%
                sleep 0.1
        done
	ii=`expr $ii + 17 `
 done
}
 
case "$1" in  
start)  
start $3
;;  
stop)  
stop  
;;    
restart)  
stop  
sleep 3
start $3  
;;
status)
status
;;  
*)  
printf 'Usage: %s {start|stop|restart|status}\n' "$prog"  
exit 1  
;;  
esac

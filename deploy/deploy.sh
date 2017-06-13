#!/bin/bash
tomcatPath=/home/zhuanquan/apache-tomcat-7.0.77
webPath=/home/zhuanquan/web
sourcePath=/home/source/WEB/app_zhuanquan
warBackupPath=/home/backup
targetBuild=prod1



#主进程，部署
function deploy()
{
   updateAndBuild

   backup

   unzipAndRestart
}


#更新代码，编译
function updateAndBuild()
{
    echo "
    ###########################################################
    #                       git开始更新代码
    ###########################################################
    "  
    cd $sourcePath
    git pull

    echo "
    ###########################################################
    #                       maven开始编译源码
    ###########################################################
    "  
    mvn clean package -P $targetBuild

    rc=$?
    if [[$rc -ne 0]];then
    echo "
    ###########################################################
    #                        maven编译失败
    ###########################################################
    " 

    cd -
    exit $rc
    fi   
}



#备份war  
function backup()  
{  
    echo "
    ###########################################################
    #                       开始备份war包....
    ###########################################################
    "  

    fileDate=$(date "+%Y%m%d")  
    fileDateFull=$(date "+%Y%m%d%H%M%S")  
    fileName=app-server_${fileDateFull}.war  

    dir=$warBackupPath/$fileDate
    echo "backup path is $dir/$fileName"

    if [ -d "$dir" ]; then  

        cp ${sourcePath}/server/target/app-server.war  $dir/$fileName
        echo "
        ###########################################################
        #                   备份完毕...
        ###########################################################
        "  
     else
     	echo "
        ###########################################################
        #              不存在$dir,开始创建备份目录
        ###########################################################
        " 
        mkdir $dir  
  
        cp ${sourcePath}/server/target/app-server.war  $dir/$fileName
        echo "
        ###########################################################
        #                  备份完毕...
        ###########################################################
        "  

    fi  
}  

#解压war,重启tomcat
function unzipAndRestart()
{
    echo "
    ###########################################################
    #                     开始部署到tomcat....
    ###########################################################
    "  
    rm -rf ${webPath}/*

    unzip ${sourcePath}/server/target/app-server.war -d $webPath

    echo "
    ###########################################################
    #                     开始重启tomcat....
    ###########################################################
    "  
    service zhuanquan restart

    echo "
    ###########################################################
    #                   请查看tomcat启动日志
    ###########################################################
    "
 
    tail -200f $tomcatPath/logs/catalina.out 

}



deploy
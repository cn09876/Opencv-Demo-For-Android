# Opencv-Demo-For-Android-UDP


基于Opencv官方SDK编译的DEMO


在这里下载最新的SDK


https://github.com/Itseez/opencv


增加对UDP发送接收的功能，不另外项目，直接在这里就当做记录了


etc/下有DELPHI的配套收发的电脑端程序，只在是一个局域网内就能通信


以下用的都是MAC OS,其它平台类似


执行python platforms/android/build_sdk.py可生成so


如果提示Ninja错误，请执行


brew install Ninja


先安装一下Ninja


JAVA层通过JNI直接调用OPENCV底层


这只是一个DEMO只使用了一小部分功能

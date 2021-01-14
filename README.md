# 漏洞描述
致远OA是一套办公协同管理软件。由于致远OA旧版本某些接口存在未授权访问，以及部分函数存在过滤不足，攻击者通过构造恶意请求，可在无需登录的情况下上传恶意脚本文件，从而控制服务器。致远OA官方已针对该漏洞提供补丁。鉴于漏洞危害较大，建议用户尽快应用补丁更新。

# 漏洞影响
致远OA V8.0
致远OA V7.1、V7.1SP1
致远OA V7.0、V7.0SP1、V7.0SP2、V7.0SP3
致远OA V6.0、V6.1SP1、V6.1SP2
致远OA V5.x
致远OA G6

# 使用方法

```
java -jar seeyon2021.jar http://127.0.0.1:8080  test.txt
```

将尝试利用漏洞上传指定的文件到服务器，上传成功则上传位置为

http://127.0.0.1:8080/seeyon/test.txt

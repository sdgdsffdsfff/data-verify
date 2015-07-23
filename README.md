
## 数据导出验证模块

> 提供OA导出数据验证服务，主要是验证网页内容是否仍然存在，
1. 若存在，在更新网页在solr索引里的内容,导出最新数据到mysql；
2. 若不存在，则不对solr索引进行操作，忽略导出到mysql。

提供给OA的接口描述如下:

### 打包部署

`打包`：mvn clean package -Dmaven.test.skip=true

### 验证

Method: Post

Path: /bot/verify
示例: 

```bash
curl -X POST -H 'Content-Type:application/json' http://192.168.4.137:8219/bot/verify \
--data '{"filename":"test0", "recs":[{"recordId":"67263CE8357F83BDA4DCC666F41FC88D","keyword":"黑火药 东莞"}, \
{"recordId":"800A43BE80146DE7ABE4A18CBF499E83","keyword":"东阳市 副书记"}]}'
```

Parameters:

参数名 | 类型  | 描述
-------| ----- |-------
filename| String|文件名, 每次OA请求时的唯一标识，由OA提供，以供查询
records | List<Record> | Record集合,Record包含recordId和keyword字段，recordId是在solr索引中的数据id；keyword是命中关键词,用户勾选的记录中命中的关键词，DataVerificationBot需要根据关键词来验证内容是否符合



Return Type: Json

```json
{
    "code":0,
    "msg": "success:
    "data":null
}
```


DataVerificationBot验证完后写入mysql数据，写入表及字段结构定义如下:

字段    | 描述
-------- | -------
filename  | 文件名，共OA查询下载
record    | Json格式

### 获取结果
Method: Get

Path: /bot/verify
示例: http://192.168.4.137:8219/bot/verify?filename=test0&start=0&rows=100

Parameters:

参数名 | 描述
-------|-------
filename | 文件名
start | 分页起始
rows  | 一页记录数

Return: 文件名是filename的数据

Return Type: Json

```json
{
    "code":0,
    "msg":"success",
    "data": {
        "num": 901
        "records": [
            {},
            {},
            ... ...
        ]
    }
}
```

###问题
1. 关于境外的网站需要连接VPN
2. 微博类没有处理


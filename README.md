
## 数据导出验证模块

> 提供OA导出数据验证服务，主要是验证网页内容是否仍然存在，
1. 若存在，在更新网页在solr索引里的内容,导出最新数据到mysql；
2. 若不存在，则不对solr索引进行操作，忽略导出到mysql。

提供给OA的接口描述如下:

### 打包部署

`打包`：mvn clean package -Dmaven.test.skip=true
`部署`：scp target/data-verify-1.2.0-distribution.tar.gz user2@dataverify:~/
`启动`：tar -zxvf data-verify-1.2.0-distribution.tar.gz; cd data-verify; bin/ctl.sh start 8279
`停止`：bin/ctl.sh stop; rm -r data-verify*

### 接口服务

#### POST

```bash
curl -X POST -H 'Content-Type:application/json' http://192.168.25.31:8279/bot/verify --data '{"filename":"test0", "recs":[{"recordId":"9A1090C920AFC18193ACA11A3DB70DB7","keyword":"合肥"},{"recordId":"0C64F84C8B7335A25365054F8B039B49","keyword":"广场"}]}'
```

`参数描述`

参数名 | 类型  | 描述
-------| ----- |-------
filename| String|文件名, 每次OA请求时的唯一标识，由OA提供，以供查询
records | List<Record> | Record集合,Record包含recordId和keyword字段，recordId是在solr索引中的数据id；keyword是命中关键词,用户勾选的记录中命中的关键词，DataVerificationBot需要根据关键词来验证内容是否符合

`成功返回结果`

```json
{
    "code":0,
    "msg": "success:
    "data":null
}
```

> 验证完后写入mysql数据，写入表及字段结构定义如下:

字段    | 描述
-------- | -------
filename  | 文件名，共OA查询下载
record    | Json格式

#### GET

```bash
curl http://192.168.25.31:8279/bot/verify?filename=test0&start=0&rows=100
```

`参数描述`

参数名 | 描述
-------|-------
filename | 文件名
start | 分页起始
rows  | 一页记录数

`成功返回结果`

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

### 相关问题

>1. 关于境外的网站需要连接VPN

>2. 微博类没有处理


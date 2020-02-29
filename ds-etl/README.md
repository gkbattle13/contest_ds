# ds_etl

> ETL模块使用阿里巴巴的[DataX](https://github.com/alibaba/DataX)，将sample.txt转存到ES服务器。

## JOB文件信息

```json
{
  "setting": {},
  "job": {
      "setting": {
          "speed": {
              "channel": 2
          }
      },
      "content": [
          {
              "reader": {
                  "name": "txtfilereader",
                  "parameter": {
                      "path": ["~/ds-etl/data/sample.txt"],
                      "encoding": "UTF-8",
                      "column": [
                          {
                              "index": 0,
                              "type": "long"
                          },
                          {
                              "index": 1,
                              "type": "string"
                          },
                          {
                              "index": 2,
                              "type": "long"
                          },
                          {
                              "index": 3,
                              "type": "double"
                          },
                          {
                              "index": 4,
                              "type": "double"
                          },
                          {
                              "index": 5,
                              "type": "string"
                          },
                          {
                              "index": 6,
                              "type": "string"
                          },
                          {
                              "index": 7,
                              "type": "double"
                          },
                          {
                              "index": 8,
                              "type": "date",
                              "format": "yyyy-MM-dd HH:mm:ss"
                          }
                      ],
                      "fieldDelimiter": "\t"
                  }
              },
              "writer": {
                "name": "elasticsearchwriter",
                "parameter": {
                  "endpoint": "http://127.0.0.1:9200",
                  "accessId": "xxx",
                  "accessKey": "xxx",
                  "index": "sample_v1",
                  "type": "sample",
                  "cleanup": true,
                  "batchSize": 1000,
                  "discovery": false,
                  "settings": {
                    "index": {
                        "number_of_shards": "5",
                        "number_of_replicas": "1"
                    }
                  },
                  "splitter": ",",
                  "column": [
                    {"name": "id", "type": "keyword"},
                    {"name": "deviceCode", "type": "keyword"},
                    {"name": "tripNo", "type": "long"},
                    {"name": "speed", "type": "double"},
                    {"name": "distance", "type": "double"},
                    {"name": "longitude", "type": "text"},
                    {"name": "latitude", "type": "text"},
                    {"name": "angle", "type": "long"},
                    {"name": "collectTime", "type": "date", "format": "yyyy-MM-dd"}
                  ]
                }
              }
          }
      ]
  }
}
```

## 命令执行

```bash
# su - root
sh> cd ~/datax/bin
sh> python ./datax.py ~/ds-etl/txt2es.json
```

## 命令执行结果示例

```powershell
2020-02-29 12:36:58.847 [job-0] INFO  JobContainer - PerfTrace not enable!
2020-02-29 12:36:58.848 [job-0] INFO  StandAloneJobContainerCommunicator - Total 12000000 records, 766222833 bytes | Speed 341.67KB/s, 5479 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 2,067.719s |  All Task WaitReaderTime 58.327s | Percentage 100.00%
2020-02-29 12:36:58.849 [job-0] INFO  JobContainer -
任务启动时刻                    : 2020-02-29 12:00:22
任务结束时刻                    : 2020-02-29 12:36:58
任务总计耗时                    :               2196s
任务平均流量                    :          341.67KB/s
记录写入速度                    :           5479rec/s
读出记录总数                    :            12000000
读写失败总数                    :                   0
```

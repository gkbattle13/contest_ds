# ds_etl

> ETL模块使用阿里巴巴的[DataX](https://github.com/alibaba/DataX)，将sample.txt转存到ES服务器。

## JOB文件信息

|序号|任务文件|备注|
|:-:|:-----:|---|
|1|`txt2es.json`|处理原始数据存储|
|1|`txt2es_millage.json`|处理里程数据|

## 命令执行

```bash
# su - root
sh> cd ~/datax/bin
# origin
sh> python ./datax.py ~/ds-etl/txt2es.json
# millage
sh> python ./datax.py ~/ds-etl/txt2es_millage.json
```

## 命令执行结果示例

```bash
2020-03-01 17:05:14.307 [job-0] INFO  JobContainer - PerfTrace not enable!
2020-03-01 17:05:14.308 [job-0] INFO  StandAloneJobContainerCommunicator - Total 12000000 records, 766222833 bytes | Speed 1.11MB/s, 18181 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 605.598s |  All Task WaitReaderTime 26.280s | Percentage 100.00%
2020-03-01 17:05:14.309 [job-0] INFO  JobContainer - 
任务启动时刻                    : 2020-03-01 16:54:13
任务结束时刻                    : 2020-03-01 17:05:14
任务总计耗时                    :                660s
任务平均流量                    :            1.11MB/s
记录写入速度                    :          18181rec/s
读出记录总数                    :            12000000
读写失败总数                    :                   0
```

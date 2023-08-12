## 项目架构设计
![AIGC-SD框架设计.png](docs%2FAIGC-SD%E6%A1%86%E6%9E%B6%E8%AE%BE%E8%AE%A1.png)

## 时序图
![sd_qc_pipeline.svg](docs%2Fsd_qc_pipeline.svg)

## 本地测试
- 在idea打开项目，打开`aigc-controller/src/main/java/com/yiyun/ai/App.java`文件
- 点击`序号边上的三角run`按钮

## ci
```
bash build.sh
```

## docker 清理所有容器 & None Image

```bash
docker system prune -a
docker container rm $(docker container ls -aq)
docker rmi $(docker images -f "dangling=true" -q)
```
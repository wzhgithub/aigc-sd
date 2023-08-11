## build

```
bash build.sh
```

## docker 清理

```bash
docker system prune -a
```

## docker 清理所有容器 & None Image

```bash
docker container rm $(docker container ls -aq)
docker rmi $(docker images -f "dangling=true" -q)
```
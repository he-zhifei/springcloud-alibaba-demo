### 1. SpringCloudAlibaba组件说明
```
1. nacos            服务注册中心、配置中心、数据总线，单机启动：startup.cmd -m standalone
2. sentinel         服务熔断降级
3. ribbon/openfeign         服务远程调用
4. gateway          服务网关
5. seata            分布式事务
```
### 2. 模块说明
```
1. springcloud-commons          公共模块，包含SpringSecurity的token校验过滤器、Ribbon/OpenFeign服务间调用请求头丢失问题的解决
2. springcloud-authentication-center         认证中心（SpringSecurity+jwt+redis）
3. springcloud-gateway          网关微服务，请求入口
4. springcloud-order            订单微服务，消费storage的服务
5. springcloud-storage          库存微服务，被order调用的服务
```

### 3. nacos集群配置

```
到官网(https://github.com/alibaba/nacos/releases)下载nacos，以nacos-server-2.0.3.tar.gz为例：
tar -zxvf nacos-server-2.0.3.tar.gz		解压缩
mv -f nacos nacos3333		重命名
cp -rf nacos3333 nacos4444 && cp -rf nacos3333 nacos5555	拷贝两份，这里使用单机集群（伪集群）

vi nacos3333/conf/cluster.conf		创建集群节点文件
192.168.199.128:3333
192.168.199.128:4444
192.168.199.128:5555

cp nacos3333/conf/cluster.conf nacos4444/conf/ 		复制到4444节点下
cp nacos3333/conf/cluster.conf nacos5555/conf/ 		复制到5555节点下
vi nacos3333/conf/application.properties		配置端口以及数据库信息，0代表第几个数据库，同理其它节点也是类似的配置
server.port=3333

spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos-config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user.0=root
db.password.0=123456

source /opt/software/nacos3333/conf/nacos-mysql.sql			登录数据库，在数据库中创建nacos-config数据库，并导入conf/nacos-mysql.sql

################################################### nginx负载均衡配置 #####################################################
upstream nacosServers {
    server 192.168.199.128:3333;    
    server 192.168.199.128:4444;
    server 192.168.199.128:5555;     
}
server {
    listen       7890;
    server_name  localhost;

    #charset koi8-r;

    #access_log  logs/host.access.log  main;

    location / {
        proxy_pass http://nacosServers;
    }
}

最后，开放防火墙的7890端口（如果有问题再把3333/4444/5555端口也打开），启动nginx，分别切换到nacos目录，
并启动3个节点的nacos下的bin/startup.sh，jps查看是否启动成功，访问：http://192.168.199.128:7890/nacos，
默认用户名/密码：nacos，项目中nacos地址改为192.168.199.128:7890

如若启动后，cluster.conf文件出现几个不同ip，但是端口相同的地址，说明配置出错了，把该文件中的ip配置成生成的ip，端口不需要改，最后把生成的地址删除掉
```


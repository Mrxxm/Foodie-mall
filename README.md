## Java电商单体项目演进

* 项目api文档地址

[链接:地址](http://api.kenrou.cn:8018/foodie-dev-api/doc.html)
`http://api.kenrou.cn:8018/foodie-dev-api/doc.html`

* 项目地址

[项目地址](http://shop.kenrou.cn)
`http://shop.kenrou.cn`

* nginx配置

```
upstream api.kenrou.cn {
        server 172.19.190.238:8018;
}

server {

        listen 80;
        server_name api.kenrou.cn;

        location / {
                proxy_pass http://api.kenrou.cn;
        }
}

server{
	listen 80;
	server_name shop.kenrou.cn;

	location / {
		root /var/www/foodie-webapps/foodie-shop;
		index index.html;
	}
}

server {

	listen 80;
	server_name center.kenrou.cn;

	location / {
		root /var/www/foodie-webapps/foodie-center;
                index index.html;
	}
}
```

* 项目部署目录

```
/usr/local/tomcat-api/webapps/foodie-dev-api.war
```

* tomac启动目录

```
/usr/local/tomcat-api/bin/startup.sh
```

* 数据库备份

[数据库](http://blog.kenrou.cn/foodie-shop-dev.sql)

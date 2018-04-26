# fs-dishes-delivery 食品配送系统

## 一.后台系统使用步骤

### 1.克隆代码
    git clone https://github.com/liuwu513/fs-dishes-delivery.git
   
### 2.build工程
    cd fs-dishes-delivery-web
    mvn install -Dmaven.test.skip=true
    
### 3.启动工程
	java -jar fs-dishes-delivery-web.jar
   
### 4.访问swagger,查看接口情况
    http://127.0.0.1:8080/swagger-ui.html
    
## 二.前台系统启动步骤

### 1.克隆项目后，进入pls-manage 目录
    cd pls-manage
    
### 2.启动工程
    #启动开发环境
    npm run dev
    
    #打包生产环境
    npm run build
    
    #打包进入 manage目录，拷贝manage目录进入/home/app/manage/
    cp manage /home/app/
    
### 3.配置nginx代理
    server {
        listen       80;
        server_name  xxxxx;

        #charset koi8-r;
        #access_log  /var/log/nginx/host.access.log  main;

        location /manage {
            proxy_pass http://127.0.0.1:8080/manage;
            proxy_set_header Host $host:8080;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header REMOTE-HOST $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header Cookie $http_cookie;
        }
	
        #location ~ .*\.(html|htm|ico|txt|js|css|jpg|png)$ 
        location /
        { 
            root /home/app/manage/;
            index  index.html index.htm;
            expires      7d; 
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
           root   /usr/share/nginx/html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}
     }
     
### 4.启动nginx
     nginx
     nginx -s reload
     
### 5. http://xxxx/
     

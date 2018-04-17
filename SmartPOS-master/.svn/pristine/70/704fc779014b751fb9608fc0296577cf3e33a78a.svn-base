
- 生成根证书私钥和根证书:  
```shell
openssl req \
        -newkey rsa:2048 -nodes -keyout boc.key \
        -x509 -days 365 -out boc.crt
```
- 生成根证书请求文件:  
```shell
openssl x509 \
       -in boc.crt \
       -signkey boc.key \
       -x509toreq -out boc.csr
```
- 生成工作证书私钥和请求文件:  
```shell
openssl req \
       -newkey rsa:2048 -nodes -keyout lanxin.key \
       -out lanxin.csr
```
- 对工作证书进行签名:  
```shell
openssl ca \
        -keyfile boc.key \
        -cert boc.crt \
        -in lanxin.csr \
        -out lanxin.crt
```
##### 遇到的错误列表（附解决）：
- /etc/pki/CA/newcerts: No such file or directory 
 ```shell
mkdir /etc/pki/CA/newcerts
```
- /etc/pki/CA/index.txt: No such file or directory 
```shell
touch /etc/pki/CA/index.txt
```
- /etc/pki/CA/serial: No such file or directory 
```shell
touch /etc/pki/CA/serial
```
- error while loading serial number 
```shell
echo 01 >serial
```
更多使用[点击](https://www.digitalocean.com/community/tutorials/openssl-essentials-working-with-ssl-certificates-private-keys-and-csrs)
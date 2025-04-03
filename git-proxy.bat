@echo off
setlocal enabledelayedexpansion

:menu
echo.
echo  [Git 代理配置脚本 - Windows]
echo  ------------------------------
echo  1. 设置代理
echo  2. 取消代理
echo  3. 退出
echo.
set /p choice=请选择操作 (1/2/3):

if "%choice%"=="1" goto set_proxy
if "%choice%"=="2" goto unset_proxy
if "%choice%"=="3" exit
echo 无效选择，请重新输入！
goto menu

:set_proxy
echo.
echo  >>> 设置代理
set /p proxy_type=请输入代理类型 (http/socks5，默认 http):
if "!proxy_type!"=="" set proxy_type=http

set /p proxy_addr=请输入代理地址和端口 (格式 127.0.0.1:7890，默认 127.0.0.1:7890):
if "!proxy_addr!"=="" set proxy_addr=127.0.0.1:7890

set /p scope=配置作用域 (global/local，默认 local):
if "!scope!"=="" set scope=local

if "!proxy_type!"=="socks5" (
    git config --!scope! http.proxy socks5://!proxy_addr!
    git config --!scope! https.proxy socks5://!proxy_addr!
) else (
    git config --!scope! http.proxy http://!proxy_addr!
    git config --!scope! https.proxy https://!proxy_addr!
)


goto show_status

:unset_proxy
echo.
echo  >>> 取消代理
set /p scope=配置作用域 (global/local，默认 local):
if "!scope!"=="" set scope=local

git config --!scope! --unset http.proxy
git config --!scope! --unset https.proxy


:show_status
echo.
echo  >>> 当前 Git 代理配置
git config --local --get http.proxy
git config --local --get https.proxy
git config --local http.proxy http://127.0.0.1:7890
git config --local https.proxy https://127.0.0.1:7890

git config --global --get http.proxy
git config --global --get https.proxy
git config --global http.proxy http://127.0.0.1:7890
git config --global https.proxy https://127.0.0.1:7890
echo.

pause
exit